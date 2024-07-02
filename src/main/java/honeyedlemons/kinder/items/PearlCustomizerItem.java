package honeyedlemons.kinder.items;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.gems.PearlEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PearlCustomizerItem extends Item {
    int mode = 0;

    public PearlCustomizerItem(Properties settings) {
        super(settings);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (user.isDiscrete() && !world.isClientSide() && hand.equals(InteractionHand.MAIN_HAND)) {
            mode = (getMode(itemStack) + 1) % 4;
            switch (mode) {
                case 0 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.hair"));
                case 1 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.hair_extra"));
                case 2 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.outfit"));
                case 3 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.insignia"));
            }
            setMode(itemStack, mode);
            return InteractionResultHolder.pass(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
        if (entity instanceof PearlEntity pearl && !user.isDiscrete()) {
            if (pearl.getOwner() == user) {
                if (stack.getOrCreateTag().get("mode") != null)
                {
                    setMode(stack,0);
                }
                switch (this.getMode(stack)) {
                    case 0 -> changeHair((PearlEntity) entity);
                    case 1 -> changeHairExtra((PearlEntity) entity);
                    case 2 -> changeOutfit((PearlEntity) entity);
                    case 3 -> changeInsignia((PearlEntity) entity);
                }
            }
            return super.interactLivingEntity(stack, user, entity, hand);
        }
        return super.interactLivingEntity(stack, user, entity, hand);
    }

    public void changeHair(PearlEntity gem) {
        if (gem.getHairVariant() == gem.hairVariantCount()) {
            gem.setHairVariant(1);
        } else {
            gem.setHairVariant(gem.getHairVariant() + 1);
        }
    }

    public void changeHairExtra(PearlEntity gem) {
        if (gem.getHairExtraVariant() == gem.hairExtraVariantCount()) {
            KinderMod.LOGGER.info(String.valueOf(0));
            gem.setHairExtraVariant(0);
        } else {
            KinderMod.LOGGER.info(String.valueOf(gem.getHairExtraVariant() + 1));
            gem.setHairExtraVariant(gem.getHairExtraVariant() + 1);
        }
    }

    public void changeOutfit(PearlEntity gem) {
        if (gem.getOutfitVariant() == gem.outfitVariantCount()) {
            gem.setOutfitVariant(1);
        } else {
            gem.setOutfitVariant(gem.getOutfitVariant() + 1);
        }
    }

    public void changeInsignia(PearlEntity gem) {
        if (gem.getInsigniaVariant() == gem.insigniaVariantCount()) {
            gem.setInsigniaVariant(1);
        } else {
            gem.setInsigniaVariant(gem.getInsigniaVariant() + 1);
        }
    }

    public int getMode(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("mode");
    }
    public void setMode(ItemStack itemStack,int mode) {
        itemStack.getOrCreateTag().putInt("mode",mode);
    }
}
