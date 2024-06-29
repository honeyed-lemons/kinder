package honeyedlemons.kinder.init;

import honeyedlemons.kinder.KinderMod;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

public class KinderVillagerTrades {

    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 3,
                factories -> factories.add((entity, random) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, KinderMod.config.driedGemSeedConfig.price),
                        new ItemStack(KinderItems.DRIED_GEM_SEEDS, 1),
                        8, 5, 0.05f
                )));
    }
}
