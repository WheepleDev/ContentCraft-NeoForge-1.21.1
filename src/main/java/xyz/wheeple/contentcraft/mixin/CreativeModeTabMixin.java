package xyz.wheeple.contentcraft.mixin;

import xyz.wheeple.contentcraft.util.LangTooltips;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreativeModeTab.class)
public abstract class CreativeModeTabMixin {
    @Shadow private Component displayName;
    @Inject(method = "getDisplayName", at = @At("RETURN"), cancellable = true)
    private void injectGradientTitle(CallbackInfoReturnable<Component> cir) {
        Component original = cir.getReturnValue();
        if (original.getContents() instanceof TranslatableContents translatable) {
            String key = translatable.getKey();
            if (key.startsWith("creativetab.contentcraft")) {cir.setReturnValue(LangTooltips.decodeTranslationKey(key));}
        }
    }
}