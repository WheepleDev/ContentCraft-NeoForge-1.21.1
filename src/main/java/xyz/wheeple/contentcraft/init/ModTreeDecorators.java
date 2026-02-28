package xyz.wheeple.contentcraft.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.worldgen.WisteriaVineDecorator;

public class ModTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES =
            DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, Contentcraft.MOD_ID);

    public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<WisteriaVineDecorator>> WISTERIA_VINE_DECORATOR =
            TREE_DECORATOR_TYPES.register("wisteria_vine_decorator", () -> new TreeDecoratorType<>(WisteriaVineDecorator.CODEC));
}