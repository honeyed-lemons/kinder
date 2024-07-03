package honeyedlemons.kinder.entities.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.entities.AbstractVaryingGemEntity;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.items.PearlCustomizerItem;
import honeyedlemons.kinder.util.GemPlacements;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PearlEntity extends AbstractVaryingGemEntity {
    public static final EntityDataAccessor<Integer> HAIR_EXTRA_VARIANT = SynchedEntityData.defineId(PearlEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> HAT_VARIANT = SynchedEntityData.defineId(PearlEntity.class, EntityDataSerializers.INT);

    public PearlEntity(EntityType<? extends AbstractGemEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.@NotNull Builder createGemAttributes() {
        return createDefaultGemAttributes().add(Attributes.MOVEMENT_SPEED, 0.585);
    }

    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        this.setHairExtraVariant(generateHairExtraVariant());
        this.setHatVariant(generateHatVariant());
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public int maxHealth() {
        return KinderMod.config.pearlConfig.max_health;
    }

    @Override
    public int attackDamage() {
        return KinderMod.config.pearlConfig.attack_damage;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PanicGoal(this, getSpeed()));
    }

    @Override
    public void interactGem(Player player) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack == ItemStack.EMPTY) {
            player.openMenu(new GemScreenHandlerFactory());
        }
        if (stack.getItem() == KinderItems.PEARL_CUSTOMIZER) {
            int mode = stack.getOrCreateTag().getInt("mode");
            if (stack.getOrCreateTag().get("mode") == null) {
                mode = 0;
            }
            switch (mode) {
                case 0 -> PearlCustomizerItem.changeHair(this);
                case 1 -> PearlCustomizerItem.changeHairExtra(this);
                case 2 -> PearlCustomizerItem.changeOutfit(this);
                case 3 -> PearlCustomizerItem.changeInsignia(this);
                case 4 -> PearlCustomizerItem.changeHat(this);
            }
        }
    }

    @Override
    public boolean isSolider() {
        return false;
    }

    @Override
    public int hairVariantCount() {
        return 16;
    }

    public int hairExtraVariantCount() {
        return 5;
    }
    public int hatVariantCount() {
        return 2;
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(HAIR_EXTRA_VARIANT, 0);
        this.getEntityData().define(HAT_VARIANT, 0);

    }

    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        nbt.putInt("HairExtraVariant", this.entityData.get(HAIR_EXTRA_VARIANT));
        nbt.putInt("HatVariant", this.entityData.get(HAT_VARIANT));
        super.addAdditionalSaveData(nbt);
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        this.getEntityData().set(HAIR_EXTRA_VARIANT, nbt.getInt("HairExtraVariant"));
        this.getEntityData().set(HAT_VARIANT, nbt.getInt("HatVariant"));
        super.readAdditionalSaveData(nbt);
    }

    @Override
    public int outfitVariantCount() {
        return 10;
    }

    public int insigniaVariantCount() {
        return 11;
    }

    public int getHairExtraVariant() {
        return this.getEntityData().get(HAIR_EXTRA_VARIANT);
    }

    public void setHairExtraVariant(int hairVariant) {
        this.getEntityData().set(HAIR_EXTRA_VARIANT, hairVariant);
    }
    public int getHatVariant() {
        return this.getEntityData().get(HAT_VARIANT);
    }

    public void setHatVariant(int hatVariant) {
        this.getEntityData().set(HAT_VARIANT, hatVariant);
    }
    @Override
    public int generateInsigniaVariant() {
        if (insigniaVariantCount() != 0) {
            return this.random.nextIntBetweenInclusive(1, insigniaVariantCount());
        } else return 0;
    }

    public int generateHairExtraVariant() {
        return this.random.nextInt(hairExtraVariantCount());
    }
    public int generateHatVariant() {
        if (random.nextFloat() < 0.25) {
            return this.random.nextInt(hatVariantCount());
        }
        else
        {
            return 0;
        }
    }

    @Override
    public boolean hasOutfitPlacementVariant() {
        return false;
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
                GemPlacements.LEFT_KNEE};
    }

    @Override
    public ItemStack gemItem() {
        return switch (getGemColorVariant()) {
            case 1 -> KinderItems.PEARL_GEM_1.getDefaultInstance();
            case 2 -> KinderItems.PEARL_GEM_2.getDefaultInstance();
            case 3 -> KinderItems.PEARL_GEM_3.getDefaultInstance();
            case 4 -> KinderItems.PEARL_GEM_4.getDefaultInstance();
            case 5 -> KinderItems.PEARL_GEM_5.getDefaultInstance();
            case 6 -> KinderItems.PEARL_GEM_6.getDefaultInstance();
            case 7 -> KinderItems.PEARL_GEM_7.getDefaultInstance();
            case 8 -> KinderItems.PEARL_GEM_8.getDefaultInstance();
            case 9 -> KinderItems.PEARL_GEM_9.getDefaultInstance();
            case 10 -> KinderItems.PEARL_GEM_10.getDefaultInstance();
            case 11 -> KinderItems.PEARL_GEM_11.getDefaultInstance();
            case 12 -> KinderItems.PEARL_GEM_12.getDefaultInstance();
            case 13 -> KinderItems.PEARL_GEM_13.getDefaultInstance();
            case 14 -> KinderItems.PEARL_GEM_14.getDefaultInstance();
            case 15 -> KinderItems.PEARL_GEM_15.getDefaultInstance();
            default -> KinderItems.PEARL_GEM_0.getDefaultInstance();
        };
    }

    @Override
    public @NotNull SoundEvent gemInstrument() {
        return SoundEvents.NOTE_BLOCK_HARP.value();
    }

    @Override
    public boolean UsesUniqueNames() {
        return true;
    }

    @Override
    public void containerChanged(Container sender) {
    }

    @Override
    public int[] neglectedColors() {
        return null;
    }

}
