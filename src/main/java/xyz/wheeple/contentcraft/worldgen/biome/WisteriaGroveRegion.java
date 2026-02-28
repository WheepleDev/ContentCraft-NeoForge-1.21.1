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

public class WisteriaGroveRegion extends Region {
    public WisteriaGroveRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        // These parameters mirror the vanilla Cherry Grove's placement
        new ParameterPointListBuilder()
                .temperature(Temperature.COOL)
                .humidity(Humidity.NEUTRAL, Humidity.HUMID)
                .continentalness(Continentalness.span(Continentalness.COAST, Continentalness.FAR_INLAND))
                .erosion(Erosion.EROSION_0, Erosion.EROSION_1) // High peaks/mountain slopes
                .depth(Depth.SURFACE)
                .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.HIGH_SLICE_NORMAL_ASCENDING)
                .build().forEach(point -> builder.add(point, ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("contentcraft", "wisteria_grove"))));

        builder.build().forEach(mapper);
    }
}