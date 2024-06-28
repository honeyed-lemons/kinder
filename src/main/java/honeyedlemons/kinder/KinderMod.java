package honeyedlemons.kinder;

import honeyedlemons.kinder.init.*;
import honeyedlemons.kinder.modcompat.KinderConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KinderMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Kinder");
    public static String MOD_ID = "kinder";
    public static KinderConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(KinderConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(KinderConfig.class).getConfig();
        KinderBlocks.registerBlocks();
        KinderItems.registerItems();
        KinderGemEntities.registerEntities();
        KinderGemEntities.registerAttributes();
        KinderFluidHandling.registerFluids();
        KinderItems.registerFluidItems();
        KinderItemGroups.registerItemGroups();
        KinderSounds.registerSounds();
        KinderLootTables.modifyTables();
        if (config.driedGemSeedConfig.addtovillager) {
            KinderVillagerTrades.registerCustomTrades();
        }
    }
}