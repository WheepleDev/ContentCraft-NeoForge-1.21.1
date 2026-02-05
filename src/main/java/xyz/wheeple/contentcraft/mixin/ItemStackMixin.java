package xyz.wheeple.contentcraft.mixin;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.wheeple.contentcraft.util.LangTooltips;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();
    @Inject(method = "getHoverName", at = @At("RETURN"), cancellable = true)
    private void injectCustomName(CallbackInfoReturnable<Component> cir) {
        String key = "tooltip." + BuiltInRegistries.ITEM.getKey(this.getItem()).toLanguageKey() + ".name";
        if (I18n.exists(key)) {cir.setReturnValue(LangTooltips.decodeTranslationKey(key));}
    }
}