package phyner.kinder;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyner.kinder.init.KinderEntities;
import phyner.kinder.init.KinderItems;

public class KinderMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Kindergartening");
    public static String MOD_ID = "kindergartening";

    @Override
    public void onInitialize() {
        LOGGER.info("Starting up.");
        KinderItems.registerItems();
        KinderEntities.registerEntities();
        KinderEntities.registerAttributes();
    }
}