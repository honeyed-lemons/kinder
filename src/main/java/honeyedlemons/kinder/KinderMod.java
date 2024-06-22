package honeyedlemons.kinder;

import honeyedlemons.kinder.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KinderMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger ("Kinder");
    public static String MOD_ID = "kinder";
    public static final GameRules.Key<GameRules.BooleanRule> REJUVOTHERGEMS = GameRuleRegistry.register("shouldRejuvOtherGems", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));

    @Override public void onInitialize (){
        LOGGER.info ("Starting Kinder");

        KinderBlocks.registerBlocks ();
        KinderItems.registerItems ();
        KinderGemEntities.registerEntities ();
        KinderGemEntities.registerAttributes ();
        KinderFluidHandling.registerFluids();
        KinderItems.registerFluidItems();
        KinderItemGroups.registerItemGroups ();
        KinderSounds.registerSounds ();
        KinderLootTables.modifyTables();
        KinderVillagerTrades.registerCustomTrades();
    }
}