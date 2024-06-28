package honeyedlemons.kinder.entities.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractVaryingGemEntity;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.util.GemPlacements;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class QuartzEntity extends AbstractVaryingGemEntity {
    public QuartzEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes() {
        return createDefaultGemAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6);
    }

    @Override
    public int maxHealth() {
        return KinderMod.config.quartzConfig.max_health;
    }

    @Override
    public int attackDamage() {
        return KinderMod.config.quartzConfig.attack_damage;
    }

    @Override
    public boolean isSolider() {
        return true;
    }

    @Override
    public int hairVariantCount() {
        return 6;
    }

    @Override
    public int outfitVariantCount() {
        return 1;
    }

    @Override
    public boolean hasOutfitPlacementVariant() {
        return true;
    }

    @Override
    public int defaultOutfitColor() {
        return -1;
    }

    @Override
    public int defaultInsigniaColor() {
        return -1;
    }


    @Override
    public GemPlacements[] getPlacements() {
        return new GemPlacements[]{
                GemPlacements.CHEST,
                GemPlacements.NOSE,
                GemPlacements.BACK,
                GemPlacements.BELLY,
                GemPlacements.FOREHEAD,
                GemPlacements.RIGHT_EYE,
                GemPlacements.LEFT_EYE,
                GemPlacements.RIGHT_SHOULDER,
                GemPlacements.LEFT_SHOULDER,
                GemPlacements.LEFT_CHEEK,
                GemPlacements.RIGHT_CHEEK,
                GemPlacements.RIGHT_HAND,
                GemPlacements.LEFT_HAND,
                GemPlacements.RIGHT_KNEE,
                GemPlacements.LEFT_KNEE,
                GemPlacements.BACK_OF_HEAD};
    }

    @Override
    public ItemStack gemItem() {
        return switch (getGemColorVariant()) {
            case 1 -> KinderItems.QUARTZ_GEM_1.getDefaultStack();
            case 2 -> KinderItems.QUARTZ_GEM_2.getDefaultStack();
            case 3 -> KinderItems.QUARTZ_GEM_3.getDefaultStack();
            case 4 -> KinderItems.QUARTZ_GEM_4.getDefaultStack();
            case 5 -> KinderItems.QUARTZ_GEM_5.getDefaultStack();
            case 6 -> KinderItems.QUARTZ_GEM_6.getDefaultStack();
            case 7 -> KinderItems.QUARTZ_GEM_7.getDefaultStack();
            case 8 -> KinderItems.QUARTZ_GEM_8.getDefaultStack();
            case 9 -> KinderItems.QUARTZ_GEM_9.getDefaultStack();
            case 10 -> KinderItems.QUARTZ_GEM_10.getDefaultStack();
            case 11 -> KinderItems.QUARTZ_GEM_11.getDefaultStack();
            case 12 -> KinderItems.QUARTZ_GEM_12.getDefaultStack();
            case 13 -> KinderItems.QUARTZ_GEM_13.getDefaultStack();
            case 14 -> KinderItems.QUARTZ_GEM_14.getDefaultStack();
            case 15 -> KinderItems.QUARTZ_GEM_15.getDefaultStack();
            default -> KinderItems.QUARTZ_GEM_0.getDefaultStack();
        };
    }

    @Override
    public int[] outfitPlacementVariants() {
        return new int[]{12, 15};
    }

    @Override
    public @NotNull SoundEvent gemInstrument() {
        return SoundEvents.BLOCK_NOTE_BLOCK_BASS.value();
    }

    @Override
    public boolean UsesUniqueNames() {
        return true;
    }

    @Override
    public int[] neglectedColors() {
        return null;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
    }
}
