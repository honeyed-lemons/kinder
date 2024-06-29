package honeyedlemons.kinder.entities.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractVaryingGemEntity;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.util.GemPlacements;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class QuartzEntity extends AbstractVaryingGemEntity {
    public QuartzEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.@NotNull Builder createGemAttributes() {
        return createDefaultGemAttributes().add(Attributes.MOVEMENT_SPEED, 0.6);
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
            case 1 -> KinderItems.QUARTZ_GEM_1.getDefaultInstance();
            case 2 -> KinderItems.QUARTZ_GEM_2.getDefaultInstance();
            case 3 -> KinderItems.QUARTZ_GEM_3.getDefaultInstance();
            case 4 -> KinderItems.QUARTZ_GEM_4.getDefaultInstance();
            case 5 -> KinderItems.QUARTZ_GEM_5.getDefaultInstance();
            case 6 -> KinderItems.QUARTZ_GEM_6.getDefaultInstance();
            case 7 -> KinderItems.QUARTZ_GEM_7.getDefaultInstance();
            case 8 -> KinderItems.QUARTZ_GEM_8.getDefaultInstance();
            case 9 -> KinderItems.QUARTZ_GEM_9.getDefaultInstance();
            case 10 -> KinderItems.QUARTZ_GEM_10.getDefaultInstance();
            case 11 -> KinderItems.QUARTZ_GEM_11.getDefaultInstance();
            case 12 -> KinderItems.QUARTZ_GEM_12.getDefaultInstance();
            case 13 -> KinderItems.QUARTZ_GEM_13.getDefaultInstance();
            case 14 -> KinderItems.QUARTZ_GEM_14.getDefaultInstance();
            case 15 -> KinderItems.QUARTZ_GEM_15.getDefaultInstance();
            default -> KinderItems.QUARTZ_GEM_0.getDefaultInstance();
        };
    }

    @Override
    public int[] outfitPlacementVariants() {
        return new int[]{12, 15};
    }

    @Override
    public @NotNull SoundEvent gemInstrument() {
        return SoundEvents.NOTE_BLOCK_BASS.value();
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
    public void containerChanged(Container sender) {
    }
}
