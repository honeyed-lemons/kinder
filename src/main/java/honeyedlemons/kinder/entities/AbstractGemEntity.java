package honeyedlemons.kinder.entities;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.client.render.screens.handlers.PearlScreenHandler;
import honeyedlemons.kinder.entities.goals.GemAttackWithOwnerGoal;
import honeyedlemons.kinder.entities.goals.GemFollowOwnerGoal;
import honeyedlemons.kinder.entities.goals.GemTrackOwnerAttackerGoal;
import honeyedlemons.kinder.entities.goals.GemWanderAroundGoal;
import honeyedlemons.kinder.init.KinderItems;
import honeyedlemons.kinder.items.DestabItem;
import honeyedlemons.kinder.util.GemColors;
import honeyedlemons.kinder.util.GemPlacements;
import honeyedlemons.kinder.util.InventoryNbtUtil;
import honeyedlemons.kinder.util.PaletteType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings ("unchecked")
public abstract class AbstractGemEntity extends TamableAnimal implements GeoEntity, ContainerListener, OwnableEntity {
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Byte> TAMABLE_FLAGS = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> MOVEMENT_TYPE = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> REBEL = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> REBEL_CHANCE = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> REBEL_BASE_CHANCE = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> REBEL_TICKS = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PERFECTION = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HAIR_COLOR = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SKIN_COLOR = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GEM_COLOR_VARIANT = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HAIR_VARIANT = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OUTFIT_VARIANT = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INSIGNIA_VARIANT = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OUTFIT_COLOR = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INSIGNIA_COLOR = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GEM_PLACEMENT = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GEM_COLOR = SynchedEntityData.defineId(AbstractGemEntity.class, EntityDataSerializers.INT);
    private static UUID FOLLOW_ID;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public int initalGemColorVariant = 0;
    public boolean setGemVariantOnInitialSpawn = true;
    public SimpleContainer inventory;

    @SuppressWarnings ("deprecation")
    public AbstractGemEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
        this.fixupDimensions();
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.updateInventory();
    }

    public static AttributeSupplier.Builder createDefaultGemAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE, 32).add(Attributes.ATTACK_DAMAGE);
    }

    // Attributes

    public void registerGoals() {
        //Looking around
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 2.0f));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        //Movement Types and movement
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new GemWanderAroundGoal(this, this.getSpeed(), 0.0005f));
        this.goalSelector.addGoal(5, new GemFollowOwnerGoal(this, this.getSpeed(), 2, 48, true));
        //Combat
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false, (entity) -> this.isRebel()));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 1, false, false, (entity) -> this.canFight() && (entity.getMobType().equals(MobType.UNDEAD) || entity instanceof Spider || entity.getMobType().equals(MobType.ILLAGER))));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractGemEntity.class, true, (entity) -> !entity.getEntityData().get(REBEL) && this.isRebel()));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, this.getSpeed(), false));
        this.targetSelector.addGoal(1, new GemAttackWithOwnerGoal(this, this.canFight()));
        this.targetSelector.addGoal(1, new GemTrackOwnerAttackerGoal(this, this.canFight()));
        super.registerGoals();
    }
    @Override
    public void aiStep() {
       updateSwingTime();
       super.aiStep();
    }
    public float getSpeed() {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    public boolean canFight() {
        if (isSolider()) {
            return true;
        } else return isRebel();
    }

    public abstract boolean isSolider();

    //Data
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TAMABLE_FLAGS, (byte) 0);
        this.entityData.define(DATA_OWNERUUID_ID, Optional.empty());
        this.entityData.define(MOVEMENT_TYPE, (byte) 0);
        this.entityData.define(REBEL, false);
        this.entityData.define(REBEL_TICKS, 24000);
        this.entityData.define(REBEL_CHANCE, 0f);
        this.entityData.define(REBEL_BASE_CHANCE, 5);
        this.entityData.define(GEM_COLOR_VARIANT, 0);
        this.entityData.define(HAIR_VARIANT, 0);
        this.entityData.define(HAIR_COLOR, 0);
        this.entityData.define(SKIN_COLOR, 0);
        this.entityData.define(OUTFIT_COLOR, 0);
        this.entityData.define(OUTFIT_VARIANT, 0);
        this.entityData.define(INSIGNIA_COLOR, 0);
        this.entityData.define(INSIGNIA_VARIANT, 0);
        this.entityData.define(GEM_PLACEMENT, 0);
        this.entityData.define(GEM_COLOR, 0);
        this.entityData.define(PERFECTION, 6);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.getOwnerUUID() != null) {
            nbt.putUUID("Owner", this.getOwnerUUID());
        }
        nbt.putInt("MovementType", this.getMovementType());
        if (FOLLOW_ID != null) {
            nbt.putUUID("FollowID", FOLLOW_ID);
        }
        nbt.putByte("MovementType", this.entityData.get(MOVEMENT_TYPE));
        nbt.putBoolean("Rebel", this.entityData.get(REBEL));
        nbt.putFloat("RebelChance", this.entityData.get(REBEL_CHANCE));
        nbt.putInt("RebelBaseChance", this.entityData.get(REBEL_BASE_CHANCE));
        nbt.putInt("RebelTicks", this.entityData.get(REBEL_TICKS));
        nbt.putInt("Perfection", this.entityData.get(PERFECTION));
        nbt.putInt("HairColor", this.entityData.get(HAIR_COLOR));
        nbt.putInt("SkinColor", this.entityData.get(SKIN_COLOR));
        nbt.putInt("GemColorVariant", this.entityData.get(GEM_COLOR_VARIANT));
        nbt.putInt("HairVariant", this.entityData.get(HAIR_VARIANT));
        nbt.putInt("OutfitColor", this.entityData.get(OUTFIT_COLOR));
        nbt.putInt("InsigniaColor", this.entityData.get(INSIGNIA_COLOR));
        nbt.putInt("OutfitVariant", this.entityData.get(OUTFIT_VARIANT));
        nbt.putInt("InsigniaVariant", this.entityData.get(INSIGNIA_VARIANT));
        nbt.putInt("GemPlacement", this.entityData.get(GEM_PLACEMENT));
        nbt.putInt("GemColor", this.entityData.get(GEM_COLOR));
        InventoryNbtUtil.writeInventoryNbt(nbt, "Inventory", this.inventory, this.inventory.getContainerSize());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        UUID uuid;
        this.setPerfectionThings(nbt.getInt("Perfection"));
        super.readAdditionalSaveData(nbt);
        if (nbt.hasUUID("Owner")) {
            uuid = nbt.getUUID("Owner");
        } else {
            String string = nbt.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(this.getServer()), string);
        }
        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
                this.setTame(true);
            } catch (Throwable throwable) {
                this.setTame(false);
            }
        }
        if (nbt.hasUUID("FollowID")) {
            FOLLOW_ID = nbt.getUUID("FollowID");
        }
        this.entityData.set(MOVEMENT_TYPE, nbt.getByte("MovementType"));
        this.entityData.set(REBEL, nbt.getBoolean("Rebel"));
        this.entityData.set(REBEL_CHANCE, nbt.getFloat("RebelChance"));
        this.entityData.set(REBEL_TICKS, nbt.getInt("RebelTicks"));
        this.entityData.set(REBEL_BASE_CHANCE, nbt.getInt("RebelBaseChance"));
        this.entityData.set(PERFECTION, nbt.getInt("Perfection"));
        this.entityData.set(HAIR_COLOR, nbt.getInt("HairColor"));
        this.entityData.set(SKIN_COLOR, nbt.getInt("SkinColor"));
        this.entityData.set(GEM_COLOR_VARIANT, nbt.getInt("GemColorVariant"));
        this.entityData.set(HAIR_VARIANT, nbt.getInt("HairVariant"));
        this.entityData.set(OUTFIT_COLOR, nbt.getInt("OutfitColor"));
        this.entityData.set(INSIGNIA_COLOR, nbt.getInt("InsigniaColor"));
        this.entityData.set(OUTFIT_VARIANT, nbt.getInt("OutfitVariant"));
        this.entityData.set(INSIGNIA_VARIANT, nbt.getInt("InsigniaVariant"));
        this.entityData.set(GEM_PLACEMENT, nbt.getInt("GemPlacement"));
        this.entityData.set(GEM_COLOR, nbt.getInt("GemColor"));
        InventoryNbtUtil.readInventoryNbt(nbt, "Inventory", this.inventory);
    }

    public int getRebelBaseChance() {
        return this.entityData.get(REBEL_BASE_CHANCE);
    }

    public void setRebelBaseChance(int i) {
        this.entityData.set(REBEL_BASE_CHANCE, i);
    }

    public void addRebelChance(float f) {
        this.entityData.set(REBEL_CHANCE, getRebelChance() + f);
    }

    public float getRebelChance() {
        return this.entityData.get(REBEL_CHANCE);
    }

    public int getHairColor() {
        return this.entityData.get(HAIR_COLOR);
    }

    // Colors
    public void setHairColor(int hairColor) {
        this.entityData.set(HAIR_COLOR, hairColor);
    }

    public int getSkinColor() {
        return this.entityData.get(SKIN_COLOR);
    }

    public void setSkinColor(int skinColor) {
        this.entityData.set(SKIN_COLOR, skinColor);
    }

    public int getGemColor() {
        return this.entityData.get(GEM_COLOR);
    }

    public void setGemColor(int gemColor) {
        this.entityData.set(GEM_COLOR, gemColor);
    }

    public int getGemColorVariant() {
        return this.entityData.get(GEM_COLOR_VARIANT);
    }

    public void setGemColorVariant(int colorVariant) {
        this.entityData.set(GEM_COLOR_VARIANT, colorVariant);
        if (defaultOutfitColor() == -1) {
            setOutfitColor(colorVariant);
        }
        if (defaultInsigniaColor() == -1) {
            setInsigniaColor(colorVariant);
        }
    }

    public int getPerfection() {
        return this.entityData.get(PERFECTION);
    }

    public void setPerfection(int perfection) {
        this.entityData.set(PERFECTION, perfection);
    }

    public abstract int hairVariantCount();

    public int generateHairVariant() {
        if (hairVariantCount() != 0) {
            return this.random.nextIntBetweenInclusive(1, hairVariantCount());
        } else return 0;
    }

    public int getHairVariant() {
        return this.entityData.get(HAIR_VARIANT);
    }

    public void setHairVariant(int hairVariant) {
        this.entityData.set(HAIR_VARIANT, hairVariant);
    }

    public abstract int outfitVariantCount();

    public int generateOutfitVariant() {
        if (outfitVariantCount() != 0) {
            return this.random.nextIntBetweenInclusive(1, outfitVariantCount());
        } else return 0;
    }

    public abstract boolean hasOutfitPlacementVariant();

    public int[] outfitPlacementVariants() {
        return new int[]{};
    }

    public int getOutfitVariant() {
        return this.entityData.get(OUTFIT_VARIANT);
    }

    public void setOutfitVariant(int outfitVariant) {
        this.entityData.set(OUTFIT_VARIANT, outfitVariant);
    }

    public int generateInsigniaVariant() {
        return getOutfitVariant();
    }

    public int getInsigniaVariant() {
        return this.entityData.get(INSIGNIA_VARIANT);
    }

    public void setInsigniaVariant(int insigniaVariant) {
        this.entityData.set(INSIGNIA_VARIANT, insigniaVariant);
    }

    public int getInsigniaColor() {
        return this.entityData.get(INSIGNIA_COLOR);
    }

    public void setInsigniaColor(int insigniaColor) {
        this.entityData.set(INSIGNIA_COLOR, insigniaColor);
    }

    public int getOutfitColor() {
        return this.entityData.get(OUTFIT_COLOR);
    }

    public void setOutfitColor(int outfitColor) {
        this.entityData.set(OUTFIT_COLOR, outfitColor);
    }

    public abstract int defaultOutfitColor();

    public abstract int defaultInsigniaColor();

    public abstract GemPlacements[] getPlacements();

    public int generateGemPlacement() {
        return this.getPlacements()[this.random.nextInt(this.getPlacements().length)].id;
    }

    public GemPlacements getGemPlacement() {
        return GemPlacements.getPlacement(this.entityData.get(GEM_PLACEMENT));
    }

    public void setGemPlacement(int gemPlacement) {
        this.entityData.set(GEM_PLACEMENT, gemPlacement);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        this.setPersistenceRequired();
        setPerfectionThings(getPerfection());
        setHairVariant(generateHairVariant());
        if (defaultOutfitColor() != -1) {
            setOutfitColor(defaultOutfitColor());
        }
        if (defaultInsigniaColor() != -1) {
            setInsigniaColor(defaultInsigniaColor());
        }
        this.setGemColorVariant(this.setGemVariantOnInitialSpawn ? generateGemColorVariant() : this.initalGemColorVariant);
        if (this.setGemVariantOnInitialSpawn) {
            generateColors();
        }
        setOutfitVariant(generateOutfitVariant());
        setInsigniaVariant(generateInsigniaVariant());
        setGemPlacement(generateGemPlacement());
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public float getPerfectionScaler(int perfection) {
        if (perfection >= 1 && perfection <= 3) {
            return 0.7f + (perfection * 0.1f);
        } else if (perfection >= 4 && perfection <= 6) {
            return 1.0f + ((perfection - 3) * 0.025f);
        } else {
            return 1.0f;
        }
    }

    public float damageModifier(Entity target) {
        return 1;
    }

    public void doEnemyThings(Entity target) {
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float g = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        f *= damageModifier(target);
        doEnemyThings(target);
        boolean bl = target.hurt(this.damageSources().mobAttack(this), f);
        if (bl) {
            if (g > 0.0F && target instanceof LivingEntity) {
                ((LivingEntity) target)
                        .knockback(
                                g * 0.5F,
                                Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)),
                                -Mth.cos(this.getYRot() * (float) (Math.PI / 180.0))
                        );
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }

            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }

        return bl;
    }

    @SuppressWarnings ("DataFlowIssue")
    public void setPerfectionThings(int perfection) {
        this.reapplyPosition();
        this.refreshDimensions();

        float multiplier = (getPerfection() >= 4) ? 1.25f : (getPerfection() <= 2) ? 0.75f : 1.0f;
        float perfectionScaler = getPerfectionScaler(perfection) * multiplier;

        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth() * perfectionScaler);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(attackDamage() * perfectionScaler);
    }

    public abstract int maxHealth();

    public abstract int attackDamage();

    public void generateColors() {
        setHairColor(this.generatePaletteColor(PaletteType.HAIR));
        setSkinColor(this.generatePaletteColor(PaletteType.SKIN));
        setGemColor(this.generatePaletteColor(PaletteType.GEM));
    }

    public abstract int generateGemColorVariant();

    @Override
    public @NotNull Component getName() {
        Component text = this.getCustomName();
        if (text != null) {
            return this.getCustomName();
        }
        if (this instanceof AbstractVaryingGemEntity) {
            if (((AbstractVaryingGemEntity) this).UsesUniqueNames()) {
                return Component.translatable("entity." + KinderMod.MOD_ID + "." + this.getType().toShortString() + "_" + this.getGemColorVariant());
            }
        }
        return this.getTypeName();
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide()) {
            ItemStack item = gemItem();
            CompoundTag nbt = new CompoundTag();
            nbt.putString("id", EntityType.getKey(this.getType()).toString());
            this.saveWithoutId(nbt);
            InventoryNbtUtil.writeInventoryNbt(nbt, "inventory", this.inventory, this.inventory.getContainerSize());
            item.getOrCreateTag().put("gem", nbt);
            Objects.requireNonNull(this.spawnAtLocation(item)).setUnlimitedLifetime();
        } else {
            this.level().addParticle(ParticleTypes.EXPLOSION_EMITTER, this.position().x, this.position().y, this.position().z, 1.0, 1.0, 1.0);
        }
        super.die(source);
    }

    abstract public ItemStack gemItem();

    public int generatePaletteColor(PaletteType type) {
        String locString = type.type + "_palette";
        KinderMod.LOGGER.info("[DEBUG] " + locString);
        ArrayList<Integer> colors = new ArrayList<>();
        ResourceLocation loc = new ResourceLocation(KinderMod.MOD_ID + ":gem_palettes/" + this.getType().toShortString() + "/" + locString + ".png");

        BufferedImage palette;
        MinecraftServer server = getCommandSenderWorld().getServer();
        assert server != null;
        ResourceManager resourceManager = server.getResourceManager();
        try {
            palette = ImageIO.read(resourceManager.getResource(loc).get().open());
            KinderMod.LOGGER.info("Palette Read!");
            for (int x = 0; x < palette.getWidth(); x++) {
                int color = palette.getRGB(x, this.getGemColorVariant());
                if ((color >> 24) == 0x00) {
                    continue;
                }
                colors.add(color);
            }
        } catch (IOException e) {
            e.printStackTrace();
            colors.add(0xFFFFFF);
        }

        return GemColors.lerpHex(colors);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }

        Entity attacker = source.getEntity();
        if (attacker instanceof Player player) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (itemStack.getItem() instanceof DestabItem destabItem && destabItem == KinderItems.REJUVENATOR) {
                if (!KinderMod.config.rejuvothergems && attacker != this.getOwner()) {
                    return super.hurt(source, amount);
                } else {
                    setRebel(false);
                    if (getRebelBaseChance() >= 2) {
                        setRebelBaseChance(getRebelBaseChance() - 1);
                    }
                    return super.hurt(this.damageSources().generic(), this.maxHealth() + 25);
                }
            }
        }

        if (attacker != null && !this.isRebel()) {
            if (attacker.isDiscrete() && isOwnedBy((LivingEntity) attacker)) {
                return super.hurt(this.damageSources().generic(), this.getMaxHealth() + 25);
            } else {
                this.addRebelChance(0.05f);
            }
        }

        return super.hurt(source, amount);
    }

    /*
    Movement Type Values
    0 = Wander
    1 = Stay
    2 = Follow
    */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.level().isClientSide || player.isSpectator()) {
            return super.mobInteract(player, hand);
        }
        if (hand != InteractionHand.MAIN_HAND) {
            return super.mobInteract(player, hand);
        }
        if (getOwner() == null) {
            KinderMod.LOGGER.info("There's no owner.");
        }
        if (player != getOwner()) {
            if (isTame()) {
                player.sendSystemMessage(Component.literal(getName().getString()).append(Component.translatable("kinder.gem.interact.notyours")));
                return InteractionResult.SUCCESS;
            }
            if (!isRebel()) {
                tame(player);
                player.sendSystemMessage(Component.literal(getName().getString()).append(Component.translatable("kinder.gem.interact.claimed")));
                return InteractionResult.SUCCESS;
            }
        }
        if (player == getOwner()) {
            if (isRebel()) {
                player.sendSystemMessage(Component.literal(getName().getString()).append(Component.translatable("kinder.gem.interact.rebel")));
                return InteractionResult.SUCCESS;
            }
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (itemStack.getItem() instanceof DyeItem dye) {
                int colorId = dye.getDyeColor().getId();
                if (player.isShiftKeyDown()) {
                    setOutfitColor(colorId);
                } else {
                    setInsigniaColor(colorId);
                }
                return InteractionResult.SUCCESS;
            }
            if (player.isShiftKeyDown() && itemStack.isEmpty()) {
                byte movementType = (byte) ((entityData.get(MOVEMENT_TYPE) + 1) % 3);
                FOLLOW_ID = (movementType == 2) ? player.getUUID() : null;
                String translationKey = switch (movementType) {
                    case 0 -> "kinder.gem.movement.interact.wander";
                    case 1 -> "kinder.gem.movement.interact.stay";
                    case 2 -> "kinder.gem.movement.interact.follow";
                    default -> "";
                };
                player.sendSystemMessage(Component.literal(getDisplayName().getString()).append(Component.translatable(translationKey)));
                entityData.set(MOVEMENT_TYPE, movementType);
                return InteractionResult.SUCCESS;
            } else if (!player.isShiftKeyDown()) {
                interactGem(player);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    public void interactGem(Player player) {
    }

    protected void updateInventory() {
        var previousInventory = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (previousInventory != null) {
            previousInventory.removeListener(this);
            int maxSize = Math.min(previousInventory.getContainerSize(), this.inventory.getContainerSize());

            for (int slot = 0; slot < maxSize; ++slot) {
                var stack = previousInventory.getItem(slot);
                if (!stack.isEmpty()) {
                    this.inventory.setItem(slot, stack.copy());
                }
            }
        }

        this.inventory.addListener(this);
    }

    public int getInventorySize() {
        return getPerfection() * 9;
    }

    public UUID getFollowId() {
        return FOLLOW_ID;
    }

    // Animation
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(GemDefaultAnimations.genericGemWalkLegsController(this));
        controllerRegistrar.add(GemDefaultAnimations.genericGemArmsController(this));
        controllerRegistrar.add(GemDefaultAnimations.genericGemSittingController(this));
        controllerRegistrar.add(GemDefaultAnimations.genericGemIdleHeadController(this));
        controllerRegistrar.add(GemDefaultAnimations.genericAttackAnimation(this,GemDefaultAnimations.ARMS_USE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public byte getMovementType() {
        return this.entityData.get(MOVEMENT_TYPE);
    }

    public boolean isRebel() {
        return entityData.get(REBEL);
    }

    public void setRebel(boolean b) {
        this.entityData.set(REBEL, b);
        this.entityData.set(MOVEMENT_TYPE, (byte) 0);
        this.setOwnerUUID(null);
        this.setTame(false);
    }

    /* Sounds */
    @NotNull
    public abstract SoundEvent gemInstrument();

    @Override
    public void playAmbientSound() {
        this.playSound(gemInstrument(), 1, this.getVoicePitch());
    }

    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if (target instanceof OwnableEntity) {
            if (target instanceof AbstractGemEntity && ((AbstractGemEntity) target).isRebel()) {
                return true;
            } else if (((OwnableEntity) target).getOwner() == this.getOwner()) {
                return false;
            }
        }
        return !(target instanceof Creeper);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return null;
    }


    public class GemScreenHandlerFactory implements ExtendedScreenHandlerFactory {
        private AbstractGemEntity gem() {
            return AbstractGemEntity.this;
        }

        @Override
        public Component getDisplayName() {
            return this.gem().getDisplayName();
        }

        @Override
        public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
            var gemInv = this.gem().inventory;
            return new PearlScreenHandler(syncId, inv, gemInv, this.gem(), this.gem().getPerfection());
        }

        @Override
        public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
            buf.writeVarInt(this.gem().getId());
        }
    }
}

