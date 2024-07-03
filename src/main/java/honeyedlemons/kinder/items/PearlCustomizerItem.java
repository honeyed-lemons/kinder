package honeyedlemons.kinder.items;

import honeyedlemons.kinder.entities.gems.PearlEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
            mode = (getMode(itemStack) + 1) % 5;
            switch (mode) {
                case 0 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.hair"));
                case 1 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.hair_extra"));
                case 2 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.outfit"));
                case 3 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.insignia"));
                case 4 -> user.sendSystemMessage(Component.translatable("kinder.item.pearlcustomizer.hat"));

            }
            setMode(itemStack, mode);
            return InteractionResultHolder.pass(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    public static void changeHair(PearlEntity gem) {
        if (gem.getHairVariant() == gem.hairVariantCount()) {
            gem.setHairVariant(1);
        } else {
            gem.setHairVariant(gem.getHairVariant() + 1);
        }
    }

    public static void changeHairExtra(PearlEntity gem) {
        if (gem.getHairExtraVariant() == gem.hairExtraVariantCount()) {
            gem.getEntityData().set(PearlEntity.HAIR_EXTRA_VARIANT,42);
        } else {
            gem.setHairExtraVariant(gem.getHairExtraVariant() + 1);
        }
    }

    public static void changeOutfit(PearlEntity gem) {
        if (gem.getOutfitVariant() == gem.outfitVariantCount()) {
            gem.setOutfitVariant(1);
        } else {
            gem.setOutfitVariant(gem.getOutfitVariant() + 1);
        }
    }

    public static void changeInsignia(PearlEntity gem) {
        if (gem.getInsigniaVariant() == gem.insigniaVariantCount()) {
            gem.setInsigniaVariant(1);
        } else {
            gem.setInsigniaVariant(gem.getInsigniaVariant() + 1);
        }
    }
    public static void changeHat(PearlEntity gem) {
        if (gem.getHatVariant() == gem.hatVariantCount()) {
            gem.setHatVariant(0);
        } else {
            gem.setHatVariant(gem.getHatVariant() + 1);
        }
    }
    public int getMode(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("mode");
    }
    public void setMode(ItemStack itemStack,int mode) {
        itemStack.getOrCreateTag().putInt("mode",mode);
    }
}
