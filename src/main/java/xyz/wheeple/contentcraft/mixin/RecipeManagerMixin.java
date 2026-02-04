package xyz.wheeple.contentcraft.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(
            method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",
            at = @At("HEAD")
    )
    private void contentcraft$removeVanillaRecipes(Map<ResourceLocation, RecipeHolder<?>> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        // We use the ID to find the vanilla recipe and remove it from the map
        ResourceLocation vanillaId = ResourceLocation.withDefaultNamespace("flint_and_steel");

        if (map.containsKey(vanillaId)) {
            map.remove(vanillaId);
        }
    }
}