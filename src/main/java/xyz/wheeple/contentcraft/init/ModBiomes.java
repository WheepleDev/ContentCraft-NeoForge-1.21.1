package xyz.wheeple.contentcraft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import xyz.wheeple.contentcraft.Contentcraft;


public class ModBiomes {
        public static final ResourceKey<Biome> MAPLE_FOREST = register("maple_forest");
    public static final ResourceKey<Biome> WISTERIA_GROVE = register("wisteria_grove");

        private static ResourceKey<Biome> register(String name) {
            return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Contentcraft.MOD_ID, name));
        }
}
