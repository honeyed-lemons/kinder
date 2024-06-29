package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import java.util.ArrayList;
import java.util.List;

public class KinderLootTables {
    private static final ResourceLocation DESERT_TEMPLE_CHEST = new ResourceLocation("chests/desert_pyramid");
    private static final ResourceLocation BURIED_TREASURE_CHEST = new ResourceLocation("chests/buried_treasure");
    private static final ResourceLocation JUNGLE_TEMPLE_CHEST = new ResourceLocation("chests/jungle_temple");
    private static final ResourceLocation ABANDONED_MINESHAFT_CHEST = new ResourceLocation("chests/abandoned_mineshaft");

    public static List<ResourceLocation> tables_to_add_seeds = new ArrayList<>();

    public static void modifyTables() {
        tables_to_add_seeds.add(DESERT_TEMPLE_CHEST);
        tables_to_add_seeds.add(BURIED_TREASURE_CHEST);
        tables_to_add_seeds.add(JUNGLE_TEMPLE_CHEST);
        tables_to_add_seeds.add(ABANDONED_MINESHAFT_CHEST);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            for (ResourceLocation identifier : tables_to_add_seeds) {
                if (source.isBuiltin() && identifier.equals(id) && KinderMod.config.driedGemSeedConfig.addtostructure) {
                    LootPool.Builder poolBuilder = LootPool.lootPool()
                            .add(LootItem.lootTableItem(KinderItems.DRIED_GEM_SEEDS).when(LootItemRandomChanceCondition.randomChance(KinderMod.config.driedGemSeedConfig.chance)));

                    tableBuilder.withPool(poolBuilder);
                }
            }
        });
    }
}
