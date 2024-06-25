package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class KinderLootTables {
    private static final Identifier DESERT_TEMPLE_CHEST = new Identifier("chests/desert_pyramid");
    private static final Identifier BURIED_TREASURE_CHEST = new Identifier("chests/buried_treasure");
    private static final Identifier JUNGLE_TEMPLE_CHEST = new Identifier("chests/jungle_temple");
    private static final Identifier ABANDONED_MINESHAFT_CHEST = new Identifier("chests/abandoned_mineshaft");

    public static List<Identifier> tables_to_add_seeds = new ArrayList<>();
    public static void modifyTables()
    {
        tables_to_add_seeds.add(DESERT_TEMPLE_CHEST);
        tables_to_add_seeds.add(BURIED_TREASURE_CHEST);
        tables_to_add_seeds.add(JUNGLE_TEMPLE_CHEST);
        tables_to_add_seeds.add(ABANDONED_MINESHAFT_CHEST);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            for (Identifier identifier : tables_to_add_seeds) {
                if (source.isBuiltin() && identifier.equals(id) && KinderMod.config.driedGemSeedConfig.addtostructure) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .with(ItemEntry.builder(KinderItems.DRIED_GEM_SEEDS).conditionally(RandomChanceLootCondition.builder(KinderMod.config.driedGemSeedConfig.chance)));

                    tableBuilder.pool(poolBuilder);
                }
            }
        });
    }
}
