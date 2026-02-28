package xyz.wheeple.contentcraft.datagen;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import xyz.wheeple.contentcraft.block.wisteria.WisteriaVines;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.init.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.XAENON_BLOCK.get());
        dropSelf(ModBlocks.XAENON_CRYSTAL_BLOCK.get());
        add(
                ModBlocks.XAENON_CLUSTER.get(),
                createShardDrops(
                        ModBlocks.XAENON_CLUSTER.get(),
                        ModItems.XAENON_SHARD.get(),
                        2.0F,
                        4.0F
                )
        );
        dropSelf(ModBlocks.ALLOY_FORGE.get());
        dropSelf(ModBlocks.STEEL_BLOCK.get());

        this.dropSelf(ModBlocks.MAPLE_LOG.get());
        this.dropSelf(ModBlocks.MAPLE_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_MAPLE_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_MAPLE_WOOD.get());
        this.dropSelf(ModBlocks.MAPLE_PLANKS.get());
        this.dropSelf(ModBlocks.MAPLE_SAPLING.get());

        this.add(ModBlocks.MAPLE_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.MAPLE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropSelf(ModBlocks.MAPLE_STAIRS.get());
        add(ModBlocks.MAPLE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.MAPLE_SLAB.get()));

        dropSelf(ModBlocks.MAPLE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.MAPLE_BUTTON.get());

        dropSelf(ModBlocks.MAPLE_FENCE.get());
        dropSelf(ModBlocks.MAPLE_FENCE_GATE.get());
        dropSelf(ModBlocks.MAPLE_TRAPDOOR.get());

        add(ModBlocks.MAPLE_DOOR.get(),
                block -> createDoorTable(ModBlocks.MAPLE_DOOR.get()));

        this.dropSelf(ModBlocks.WISTERIA_LOG.get());
        this.dropSelf(ModBlocks.WISTERIA_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_WISTERIA_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_WISTERIA_WOOD.get());
        this.dropSelf(ModBlocks.WISTERIA_PLANKS.get());
        this.dropSelf(ModBlocks.WISTERIA_SAPLING.get());

        this.add(ModBlocks.WISTERIA_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.WISTERIA_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        dropSelf(ModBlocks.WISTERIA_STAIRS.get());
        add(ModBlocks.WISTERIA_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.WISTERIA_SLAB.get()));

        dropSelf(ModBlocks.WISTERIA_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.WISTERIA_BUTTON.get());

        dropSelf(ModBlocks.WISTERIA_FENCE.get());
        dropSelf(ModBlocks.WISTERIA_FENCE_GATE.get());
        dropSelf(ModBlocks.WISTERIA_TRAPDOOR.get());

        add(ModBlocks.WISTERIA_DOOR.get(),
                block -> createDoorTable(ModBlocks.WISTERIA_DOOR.get()));

        this.add(ModBlocks.WISTERIA_VINES.get(), block -> createWisteriaVineDrop(block));
        this.add(ModBlocks.WISTERIA_VINES_PLANT.get(), block -> createWisteriaVineDrop(block));
    }

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    protected LootTable.Builder createShardDrops(
            Block block,
            Item shard,
            float minDrops,
            float maxDrops
    ) {
        HolderLookup.RegistryLookup<Enchantment> enchantments =
                this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        return this.createSilkTouchDispatchTable(
                block,
                this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(shard)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(minDrops, maxDrops)
                                ))
                                .apply(ApplyBonusCount.addOreBonusCount(
                                        enchantments.getOrThrow(Enchantments.FORTUNE)
                                ))
                )
        );
    }

    protected LootTable.Builder createWisteriaVineDrop(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(ModItems.WISTERIA_POD.get())
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(xyz.wheeple.contentcraft.block.wisteria.WisteriaVines.LIT, true)))));
    }



    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
