package phyner.kinder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import phyner.kinder.init.*;

public class KinderMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger ("Kindergartening");
    public static String MOD_ID = "kindergartening";
    public static final GameRules.Key<GameRules.BooleanRule> REJUVOTHERGEMS = GameRuleRegistry.register("shouldRejuvOtherGems", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));

    @Override public void onInitialize (){
        LOGGER.info ("Starting Kindergartening");
        KinderBlocks.registerBlocks ();
        KinderItems.registerItems ();
        KinderGemEntities.registerEntities ();
        KinderGemEntities.registerAttributes ();
        KinderItemGroups.registerItemGroups ();
        KinderSounds.registerSounds ();
    }
}