package xyz.wheeple.contentcraft.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import terrablender.api.ParameterUtils.ParameterPointListBuilder;

import java.util.function.Consumer;

import static terrablender.api.ParameterUtils.*;

public class MapleForestRegion extends Region {
    public MapleForestRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        new ParameterPointListBuilder()
                .temperature(Temperature.COOL) // Specific point, no span
                .humidity(Humidity.HUMID)     // Specific point, no span
                .continentalness(Continentalness.FAR_INLAND)
                .erosion(Erosion.EROSION_1)   // Specific terrain type
                .depth(Depth.SURFACE)
                .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING)
                .build().forEach(point -> builder.add(point, ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("contentcraft", "maple_forest"))));

        builder.build().forEach(mapper);
    }
}