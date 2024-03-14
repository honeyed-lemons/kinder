package phyner.kinder;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyner.kinder.init.*;

public class KinderMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Kindergartening");
    public static String MOD_ID = "kindergartening";

    @Override
    public void onInitialize(){
        LOGGER.info("Starting Kindergartening");
        KinderBlocks.registerBlocks();
        KinderItems.registerItems();
        KinderGemEntities.registerEntities();
        KinderScreens.init();
        KinderGemEntities.registerAttributes();
        KinderItemGroups.registerItemGroups();
    }
}