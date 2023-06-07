package phyner.kinder;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyner.kinder.init.KinderGemEntities;
import phyner.kinder.init.KinderItemGroups;
import phyner.kinder.init.KinderItems;

public class KinderMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Kindergartening");
    public static String MOD_ID = "kindergartening";

    @Override public void onInitialize() {
        LOGGER.info("Starting up.");
        KinderItems.registerItems();
        //KinderItemGroups.addItems();
        KinderGemEntities.registerEntities();
        KinderGemEntities.registerAttributes();
    }
}