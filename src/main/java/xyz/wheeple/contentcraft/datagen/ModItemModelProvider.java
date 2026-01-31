package xyz.wheeple.contentcraft.datagen;


import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import xyz.wheeple.contentcraft.Contentcraft;
import xyz.wheeple.contentcraft.init.ModBlocks;
import xyz.wheeple.contentcraft.init.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Contentcraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.XAENON_INGOT.get());
        basicItem(ModItems.XAENON_SHARD.get());
        basicItem(ModItems.NETHERITE_CHUNK.get());
        basicItem(ModItems.CONTENTCRAFT_GUIDE.get());
    }
}
