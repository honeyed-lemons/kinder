package phyner.kinder.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import phyner.kinder.KinderMod;
import phyner.kinder.blocks.IncubatorBlock;
import phyner.kinder.blocks.OysterBlock;
import phyner.kinder.blocks.entities.IncubatorBlockEntity;
import phyner.kinder.blocks.entities.OysterBlockEntity;

public class KinderBlocks {
    public static final OysterBlock OYSTER_BLOCK  = new OysterBlock(FabricBlockSettings.create().strength(4.0f).nonOpaque());
    public static final IncubatorBlock INCUBATOR_BLOCK  = new IncubatorBlock(FabricBlockSettings.create().strength(4.0f).nonOpaque());
    public static void registerBlock(Block block, String name)
    {
        Registry.register(Registries.BLOCK, new Identifier(KinderMod.MOD_ID, name), block);
        Registry.register(Registries.ITEM, new Identifier(KinderMod.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }
    public static final BlockEntityType<OysterBlockEntity> OYSTER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(KinderMod.MOD_ID, "oyster_block_entity"),
            FabricBlockEntityTypeBuilder.create(OysterBlockEntity::new, OYSTER_BLOCK).build()
    );

    public static final BlockEntityType<IncubatorBlockEntity> INCUBATOR_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier(KinderMod.MOD_ID, "incubator_block_entity"),
            FabricBlockEntityTypeBuilder.create(IncubatorBlockEntity::new, INCUBATOR_BLOCK).build()
    );
    public static void registerBlocks()
    {
        registerBlock(OYSTER_BLOCK,"oyster");
        registerBlock(INCUBATOR_BLOCK, "incubator");
    }

}
