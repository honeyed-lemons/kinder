package honeyedlemons.kinder.entities.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractGemEntity;
import honeyedlemons.kinder.entities.AbstractVaryingGemEntity;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.util.GemPlacements;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SapphireEntity extends AbstractVaryingGemEntity {
    private static final EntityDataAccessor<Integer> PREDICTION_TICKS = SynchedEntityData.defineId(SapphireEntity.class, EntityDataSerializers.INT);

    public SapphireEntity(EntityType<? extends AbstractGemEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.@NotNull Builder createGemAttributes() {
        return createDefaultGemAttributes().add(Attributes.MOVEMENT_SPEED, 0.75);
    }

    @Override
    public int maxHealth() {
        return KinderMod.config.sapphireConfig.max_health;
    }

    @Override
    public int attackDamage() {
        return KinderMod.config.sapphireConfig.attack_damage;
    }

    @Override
    public boolean isSolider() {
        return false;
    }

    @Override
    public int hairVariantCount() {
        return 9;
    }

    @Override
    public int outfitVariantCount() {
        return 13;
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
        return 0;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PanicGoal(this, getSpeed()));
    }

    @Override
    public GemPlacements[] getPlacements() {
        return new GemPlacements[]{
                GemPlacements.CHEST,
                GemPlacements.BELLY,
                GemPlacements.RIGHT_SHOULDER,
                GemPlacements.LEFT_SHOULDER,
                GemPlacements.LEFT_CHEEK,
                GemPlacements.RIGHT_CHEEK,
                GemPlacements.RIGHT_HAND,
                GemPlacements.LEFT_HAND};
    }

    @Override
    public ItemStack gemItem() {
        return switch (getGemColorVariant()) {
            case 1 -> KinderItems.SAPPHIRE_GEM_1.getDefaultInstance();
            case 2 -> KinderItems.SAPPHIRE_GEM_2.getDefaultInstance();
            case 3 -> KinderItems.SAPPHIRE_GEM_3.getDefaultInstance();
            case 4 -> KinderItems.SAPPHIRE_GEM_4.getDefaultInstance();
            case 5 -> KinderItems.SAPPHIRE_GEM_5.getDefaultInstance();
            case 6 -> KinderItems.SAPPHIRE_GEM_6.getDefaultInstance();
            case 7 -> KinderItems.SAPPHIRE_GEM_7.getDefaultInstance();
            case 8 -> KinderItems.SAPPHIRE_GEM_8.getDefaultInstance();
            case 9 -> KinderItems.SAPPHIRE_GEM_9.getDefaultInstance();
            case 10 -> KinderItems.SAPPHIRE_GEM_10.getDefaultInstance();
            case 11 -> KinderItems.SAPPHIRE_GEM_11.getDefaultInstance();
            case 12 -> KinderItems.SAPPHIRE_GEM_12.getDefaultInstance();
            case 13 -> KinderItems.SAPPHIRE_GEM_13.getDefaultInstance();
            case 15 -> KinderItems.SAPPHIRE_GEM_15.getDefaultInstance();
            default -> KinderItems.SAPPHIRE_GEM_0.getDefaultInstance();
        };
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PREDICTION_TICKS, 0);
    }

    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("PredictionTicks", this.entityData.get(PREDICTION_TICKS));
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(PREDICTION_TICKS, nbt.getInt("PredictionTicks"));
    }

    @Override
    public void interactGem(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            if (getPredictionTicks() <= 0) {
                getPrediction(predictionQuality());
                setPredictionTicks(KinderMod.config.sapphireConfig.prediction_cooldown);
            } else {
                player.sendSystemMessage(Component.literal(this.getName().getString() + ": ").append(Component.translatable("kinder.sapphire.prediction.fail")));
            }
        }
    }

    public void tick() {
        super.tick();
        if (getPredictionTicks() > 0) {
            setPredictionTicks(getPredictionTicks() - 1);
        }
    }

    public int getPredictionTicks() {
        return this.getEntityData().get(PREDICTION_TICKS);
    }

    public void setPredictionTicks(int perfectionTicks) {
        this.getEntityData().set(PREDICTION_TICKS, perfectionTicks);
    }

    public boolean predictionQuality() {
        int val = (int) (getPerfection() * 2 * (random.nextFloat() * 1.25f));
        val = Math.min(6, Math.max(1, val));
        return val >= 3;
    }

    public void getPrediction(boolean bool) {
        if (bool) {
            switch (random.nextInt(7)) {
                case 0 -> grantPrediction(true, MobEffects.DAMAGE_BOOST);
                case 1 -> grantPrediction(true, MobEffects.MOVEMENT_SPEED);
                case 2 -> grantPrediction(true, MobEffects.DAMAGE_RESISTANCE);
                case 3 -> grantPrediction(true, MobEffects.DIG_SPEED);
                case 4 -> grantPrediction(true, MobEffects.ABSORPTION);
                case 5 -> grantPrediction(true, MobEffects.NIGHT_VISION);
                case 6 -> grantPrediction(true, MobEffects.LUCK);
            }
        } else {
            switch (random.nextInt(7)) {
                case 0 -> grantPrediction(true, MobEffects.WEAKNESS);
                case 1 -> grantPrediction(true, MobEffects.MOVEMENT_SLOWDOWN);
                case 2 -> grantPrediction(true, MobEffects.BAD_OMEN);
                case 3 -> grantPrediction(true, MobEffects.DIG_SLOWDOWN);
                case 4 -> grantPrediction(true, MobEffects.CONFUSION);
                case 5 -> grantPrediction(true, MobEffects.BLINDNESS);
                case 6 -> grantPrediction(true, MobEffects.LEVITATION);
                case 7 -> grantPrediction(true, MobEffects.UNLUCK);
            }
        }
    }

    public void grantPrediction(boolean morality, MobEffect statusEffect) {
        float duration = 3600;
        if (morality) {
            duration *= (getPerfection() >= 3) ? ((float) getPerfection() / 3) : getPerfection() * 2;
        } else {
            duration *= (getPerfection() >= 3) ? getPerfection() * 2 : ((float) getPerfection() / 3);
        }
        List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(16, 16, 16), EntitySelector.LIVING_ENTITY_STILL_ALIVE);
        for (Entity entity : list) {
            if (entity instanceof TamableAnimal tameableEntity) {
                if (tameableEntity.getOwnerUUID() == this.getOwnerUUID()) {
                    tameableEntity.addEffect(new MobEffectInstance(statusEffect, ((int) duration), 0, false, false), this);
                }
            }
            if (entity instanceof Player player) {
                if (isOwnedBy(player)) {
                    player.addEffect(new MobEffectInstance(statusEffect, ((int) duration), 0, false, false), this);
                    player.sendSystemMessage(Component.literal(this.getName().getString() + ": ").append(Component.translatable("kinder.sapphire.prediction.base").append(Component.translatable("kinder.sapphire.prediction." + statusEffect.getDescriptionId()))));
                }
            }
        }
    }

    @Override
    public int[] outfitPlacementVariants() {
        return null;
    }

    @Override
    public @NotNull SoundEvent gemInstrument() {
        return SoundEvents.NOTE_BLOCK_CHIME.value();
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
        return new int[14];
    }
}
