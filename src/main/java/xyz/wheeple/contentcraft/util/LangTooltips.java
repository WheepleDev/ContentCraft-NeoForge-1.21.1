package xyz.wheeple.contentcraft.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangTooltips {
    public static float hue;
    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<gradient-([0-9a-fA-F]{6})-([0-9a-fA-F]{6})>(.*?)</gradient>");
    private static final Pattern RGB_PATTERN = Pattern.compile("<rgb>(.*?)</rgb>");

    public static void init() {
        NeoForge.EVENT_BUS.addListener(LangTooltips::onTooltip);
        NeoForge.EVENT_BUS.addListener(LangTooltips::onRenderFrame);
    }

    private static void onRenderFrame(RenderFrameEvent.Post event) {
        hue += 0.005f * event.getPartialTick().getRealtimeDeltaTicks();
    }

    private static void onTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        ItemStack stack = event.getItemStack();
        ResourceLocation rl = BuiltInRegistries.ITEM.getKey(stack.getItem());

        String namespace = rl.getNamespace();
        String path = rl.getPath();
        String baseKey = "tooltip." + namespace + "." + path;
        String alwaysKey = "tooltip.always." + namespace + "." + path;

        // Use an index to keep things below the title (Index 0)
        int insertionIndex = 1;

        // Add "Always" tooltips at the top
        insertionIndex = addFormattedLines(tooltip, alwaysKey, insertionIndex);

        if (I18n.exists(baseKey + ".0")) {
            if (event.getFlags().hasShiftDown()) {
                tooltip.add(insertionIndex++, Component.translatable("tooltip.contentcraft.generic.shift_down"));
                tooltip.add(insertionIndex++, Component.empty());
                addFormattedLines(tooltip, baseKey, insertionIndex);
            } else {
                tooltip.add(insertionIndex++, Component.translatable("tooltip.contentcraft.generic.shift_up"));
            }
        }
    }

    private static int addFormattedLines(List<Component> tooltip, String baseKey, int index) {
        for (int i = 0; i < 100; i++) {
            String key = baseKey + "." + i;
            if (!I18n.exists(key)) break;

            // Ensure we don't go out of bounds if the list is somehow empty
            int pos = Math.min(index, tooltip.size());
            tooltip.add(pos, decodeTranslationKey(key));
            index++;
        }
        return index;    }

    public static Component decodeTranslationKey(String key) {
        return decodeString(I18n.get(key));
    }

    public static Component decodeString(String text) {
        MutableComponent result = Component.empty();
        String remaining = text;
        Style globalStyle = Style.EMPTY;
        
        if (remaining.startsWith("&") && remaining.length() > 1) {
            ChatFormatting format = ChatFormatting.getByCode(remaining.charAt(1));
            if (format != null) {
                globalStyle = Style.EMPTY.applyFormat(format);
            }
        }

        while (!remaining.isEmpty()) {
            Matcher rgbMatcher = RGB_PATTERN.matcher(remaining);
            Matcher gradMatcher = GRADIENT_PATTERN.matcher(remaining);

            boolean hasRgb = rgbMatcher.find();
            boolean hasGrad = gradMatcher.find();

            int rgbIdx = hasRgb ? rgbMatcher.start() : Integer.MAX_VALUE;
            int gradIdx = hasGrad ? gradMatcher.start() : Integer.MAX_VALUE;

            if (!hasRgb && !hasGrad) {
                result.append(parseLegacy(remaining));
                break;
            }

            int firstMatch = Math.min(rgbIdx, gradIdx);
            if (firstMatch > 0) {
                MutableComponent legacyPart = parseLegacy(remaining.substring(0, firstMatch));
                result.append(legacyPart);
                if (!legacyPart.getSiblings().isEmpty()) {
                    globalStyle = legacyPart.getSiblings().get(legacyPart.getSiblings().size() - 1).getStyle();
                } else {
                    globalStyle = legacyPart.getStyle();
                }
            }

            if (rgbIdx < gradIdx) {
                result.append(applyRGB(rgbMatcher.group(1), globalStyle));
                remaining = remaining.substring(rgbMatcher.end());
            } else {
                int color1 = (int) Long.parseLong(gradMatcher.group(1), 16);
                int color2 = (int) Long.parseLong(gradMatcher.group(2), 16);
                result.append(applyGradient(gradMatcher.group(3), color1, color2, globalStyle));
                remaining = remaining.substring(gradMatcher.end());
            }
        }
        return result;
    }

    private static MutableComponent parseLegacy(String text) {
        MutableComponent root = Component.empty();
        String[] parts = text.split("(?=&[0-9a-fk-or])");
        Style currentStyle = Style.EMPTY;

        for (String part : parts) {
            if (part.startsWith("&") && part.length() > 1) {
                ChatFormatting format = ChatFormatting.getByCode(part.charAt(1));
                if (format != null) {
                    if (format == ChatFormatting.RESET) currentStyle = Style.EMPTY;
                    else currentStyle = currentStyle.applyFormat(format);
                    part = part.substring(2);
                }
            }
            root.append(Component.literal(part).withStyle(currentStyle));
        }
        return root;
    }

    private static Component applyRGB(String text, Style style) {
        MutableComponent comp = Component.empty();
        String cleanText = text.replaceAll("&[0-9a-fk-or]", "");
        for (int i = 0; i < cleanText.length(); i++) {
            int color = java.awt.Color.HSBtoRGB((i * 0.02f + hue) % 1.0f, 0.8f, 1.0f);
            comp.append(Component.literal(String.valueOf(cleanText.charAt(i)))
                    .withStyle(style.withColor(color)));
        }
        return comp;
    }

    private static Component applyGradient(String text, int color1, int color2, Style style) {
        MutableComponent comp = Component.empty();
        String cleanText = text.replaceAll("&[0-9a-fk-or]", "");
        for (int i = 0; i < cleanText.length(); i++) {
            float h = (i * 0.02f + hue);
            float pingPong = Math.abs(2f * (h % 1) - 1f);
            comp.append(Component.literal(String.valueOf(cleanText.charAt(i)))
                    .withStyle(style.withColor(interpolate(color1, color2, pingPong))));
        }
        return comp;
    }

    private static int interpolate(int c1, int c2, float r) {
        int r1 = (c1 >> 16) & 0xFF, g1 = (c1 >> 8) & 0xFF, b1 = c1 & 0xFF;
        int r2 = (c2 >> 16) & 0xFF, g2 = (c2 >> 8) & 0xFF, b2 = c2 & 0xFF;
        return ((int) (r1 + (r2 - r1) * r) << 16) | ((int) (g1 + (g2 - g1) * r) << 8) | (int) (b1 + (b2 - b1) * r);
    }
}