package honeyedlemons.kinder.init;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class KinderVillagerTrades {

    public static void registerCustomTrades()
    {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER,3,
                factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 6),
                    new ItemStack(KinderItems.DRIED_GEM_SEEDS, 1),
                    8,5,0.05f
            ));
        });
    }
}
