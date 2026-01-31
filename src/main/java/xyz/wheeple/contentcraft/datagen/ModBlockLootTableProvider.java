package xyz.wheeple.contentcraft.datagen;

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
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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



    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
