package honeyedlemons.kinder.entities.gems;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.entities.AbstractVaryingGemEntity;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.util.GemPlacements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SapphireEntity extends AbstractVaryingGemEntity {
    private static final TrackedData<Integer> PREDICTION_TICKS = DataTracker.registerData(SapphireEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public SapphireEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes() {
        return createDefaultGemAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.75);
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
        return 6;
    }

    @Override
    public int outfitVariantCount() {
        return 4;
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
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new EscapeDangerGoal(this, getSpeed()));
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
            case 1 -> KinderItems.SAPPHIRE_GEM_1.getDefaultStack();
            case 2 -> KinderItems.SAPPHIRE_GEM_2.getDefaultStack();
            case 3 -> KinderItems.SAPPHIRE_GEM_3.getDefaultStack();
            case 4 -> KinderItems.SAPPHIRE_GEM_4.getDefaultStack();
            case 5 -> KinderItems.SAPPHIRE_GEM_5.getDefaultStack();
            case 6 -> KinderItems.SAPPHIRE_GEM_6.getDefaultStack();
            case 7 -> KinderItems.SAPPHIRE_GEM_7.getDefaultStack();
            case 8 -> KinderItems.SAPPHIRE_GEM_8.getDefaultStack();
            case 9 -> KinderItems.SAPPHIRE_GEM_9.getDefaultStack();
            case 10 -> KinderItems.SAPPHIRE_GEM_10.getDefaultStack();
            case 11 -> KinderItems.SAPPHIRE_GEM_11.getDefaultStack();
            case 12 -> KinderItems.SAPPHIRE_GEM_12.getDefaultStack();
            case 13 -> KinderItems.SAPPHIRE_GEM_13.getDefaultStack();
            case 15 -> KinderItems.SAPPHIRE_GEM_15.getDefaultStack();
            default -> KinderItems.SAPPHIRE_GEM_0.getDefaultStack();
        };
    }

    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PREDICTION_TICKS, 0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("PredictionTicks", this.dataTracker.get(PREDICTION_TICKS));
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(PREDICTION_TICKS, nbt.getInt("PredictionTicks"));
    }

    @Override
    public void interactGem(PlayerEntity player) {
        if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
            if (getPredictionTicks() <= 0) {
                getPrediction(predictionQuality());
                setPredictionTicks(12000);
            } else {
                player.sendMessage(Text.literal(this.getName().getString() + ": ").append(Text.translatable("kinder.sapphire.prediction.fail")));
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
        return this.getDataTracker().get(PREDICTION_TICKS);
    }

    public void setPredictionTicks(int perfectionTicks) {
        this.getDataTracker().set(PREDICTION_TICKS, perfectionTicks);
    }

    public boolean predictionQuality() {
        int val = (int) (getPerfection() * 2 * (random.nextFloat() * 1.25f));
        val = Math.min(6, Math.max(1, val));
        if (val >= 4) {
            return true;
        } else if (val <= 2) {
            return false;
        } else {
            return random.nextFloat() > 0.5;
        }
    }

    public void getPrediction(boolean bool) {
        if (bool) {
            switch (random.nextInt(7)) {
                case 0 -> grantPrediction(true, StatusEffects.STRENGTH);
                case 1 -> grantPrediction(true, StatusEffects.SPEED);
                case 2 -> grantPrediction(true, StatusEffects.RESISTANCE);
                case 3 -> grantPrediction(true, StatusEffects.HASTE);
                case 4 -> grantPrediction(true, StatusEffects.ABSORPTION);
                case 5 -> grantPrediction(true, StatusEffects.NIGHT_VISION);
                case 6 -> grantPrediction(true, StatusEffects.LUCK);
            }
        } else {
            switch (random.nextInt(7)) {
                case 0 -> grantPrediction(true, StatusEffects.WEAKNESS);
                case 1 -> grantPrediction(true, StatusEffects.SLOWNESS);
                case 2 -> grantPrediction(true, StatusEffects.BAD_OMEN);
                case 3 -> grantPrediction(true, StatusEffects.MINING_FATIGUE);
                case 4 -> grantPrediction(true, StatusEffects.NAUSEA);
                case 5 -> grantPrediction(true, StatusEffects.BLINDNESS);
                case 6 -> grantPrediction(true, StatusEffects.LEVITATION);
                case 7 -> grantPrediction(true, StatusEffects.UNLUCK);
            }
        }
    }

    public void grantPrediction(boolean morality, StatusEffect statusEffect) {
        float duration = 3600;
        if (morality) {
            duration *= (getPerfection() >= 3) ? ((float) getPerfection() / 3) : getPerfection() * 2;
        } else {
            duration *= (getPerfection() >= 3) ? getPerfection() * 2 : ((float) getPerfection() / 3);
        }
        List<Entity> list = this.getWorld().getEntitiesByClass(Entity.class, this.getBoundingBox().expand(16, 16, 16), EntityPredicates.VALID_LIVING_ENTITY);
        for (Entity entity : list) {
            if (entity instanceof TameableEntity tameableEntity) {
                if (tameableEntity.getOwnerUuid() == this.getOwnerUuid()) {
                    tameableEntity.addStatusEffect(new StatusEffectInstance(statusEffect, ((int) duration), 0, false, false), this);
                }
            }
            if (entity instanceof PlayerEntity player) {
                if (isOwner(player)) {
                    player.addStatusEffect(new StatusEffectInstance(statusEffect, ((int) duration), 0, false, false), this);
                    player.sendMessage(Text.literal(this.getName().getString() + ": ").append(Text.translatable("kinder.sapphire.prediction.base").append(Text.translatable("kinder.sapphire.prediction." + statusEffect.getTranslationKey()))));
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
        return SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value();
    }

    @Override
    public boolean UsesUniqueNames() {
        return true;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
    }

    @Override
    public int[] neglectedColors() {
        return new int[14];
    }
}
