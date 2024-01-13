package phyner.kinder;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyner.kinder.init.*;

public class KinderMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Kindergartening");
    public static String MOD_ID = "kindergartening";

    @Override public void onInitialize() {
        LOGGER.info("Starting up.");
        KinderBlocks.registerFluids();
        KinderItems.registerItems();
        //KinderItemGroups.addItems();
        KinderGemEntities.registerEntities();
        KinderScreens.init();
        KinderGemEntities.registerAttributes();
    }
}