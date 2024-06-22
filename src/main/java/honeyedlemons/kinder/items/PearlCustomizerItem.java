package honeyedlemons.kinder.items;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.gems.PearlEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PearlCustomizerItem extends Item {
    public PearlCustomizerItem (Settings settings){
        super (settings);
    }

    int mode = 0;

    public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand){
        ItemStack itemStack = user.getStackInHand(hand);
        if (user.isSneaky() && !world.isClient() && hand.equals(Hand.MAIN_HAND))
        {
            KinderMod.LOGGER.info("silly");
            mode = (mode + 1) % 4;
            switch (mode) {
                case 0 -> user.sendMessage(Text.translatable("kinder.item.pearlcustomizer.hair"));
                case 1 -> user.sendMessage(Text.translatable("kinder.item.pearlcustomizer.hair_extra"));
                case 2 -> user.sendMessage(Text.translatable("kinder.item.pearlcustomizer.outfit"));
                case 3 -> user.sendMessage(Text.translatable("kinder.item.pearlcustomizer.insignia"));
            }
            return TypedActionResult.pass(itemStack);
        }
        return TypedActionResult.pass(itemStack);
    }


    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof PearlEntity && !user.isSneaky()) {
            switch (this.getMode()) {
                case 0 -> changeHair((PearlEntity) entity);
                case 1 -> changeHairExtra((PearlEntity) entity);
                case 2 -> changeOutfit((PearlEntity) entity);
                case 3 -> changeInsignia((PearlEntity) entity);
            }
            return super.useOnEntity(stack, user, entity, hand);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    public void changeHair(PearlEntity gem)
    {
        if (gem.getHairVariant () == gem.hairVariantCount())
        {
            gem.setHairVariant (1);
        }
        else
        {
            gem.setHairVariant (gem.getHairVariant() + 1);
        }
    }
    public void changeHairExtra(PearlEntity gem)
    {
        if (gem.getHairExtraVariant () == gem.hairExtraVariantCount())
        {
            KinderMod.LOGGER.info(String.valueOf(0));
            gem.setHairExtraVariant (0);
        }
        else
        {
            KinderMod.LOGGER.info(String.valueOf(gem.getHairExtraVariant()+1));
            gem.setHairExtraVariant (gem.getHairExtraVariant() + 1);
        }
    }
    public void changeOutfit(PearlEntity gem)
    {
        if (gem.getOutfitVariant() == gem.outfitVariantCount())
        {
            gem.setOutfitVariant(1);
        }
        else
        {
            gem.setOutfitVariant(gem.getOutfitVariant() + 1);
        }
    }
    public void changeInsignia(PearlEntity gem)
    {
        if (gem.getInsigniaVariant() == gem.insigniaVariantCount())
        {
            gem.setInsigniaVariant(1);
        }
        else
        {
            gem.setInsigniaVariant(gem.getInsigniaVariant() + 1);
        }
    }
    public int getMode()
    {
        return mode;
    }
}
