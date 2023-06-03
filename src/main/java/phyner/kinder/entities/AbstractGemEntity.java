package phyner.kinder.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.goals.GemAttackWithOwnerGoal;
import phyner.kinder.entities.goals.GemFollowOwnerGoal;
import phyner.kinder.entities.goals.GemTrackOwnerAttackerGoal;
import phyner.kinder.entities.goals.GemWanderAroundGoal;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.lang.reflect.Array;
import java.util.*;

public abstract class AbstractGemEntity extends TameableEntity implements GeoEntity, Tameable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<Byte> TAMABLE_FLAGS = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Byte> MOVEMENT_TYPE = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Boolean> REBEL = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static UUID FOLLOW_ID;

    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public AbstractGemEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
    public void initGoals() {
        //Looking around
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 2.0f));
        this.goalSelector.add(10, new LookAroundGoal(this));
        //Movement Types and movement
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(5, new GemWanderAroundGoal(this,this.getSpeed(), 0.0005f));
        this.goalSelector.add(5, new GemFollowOwnerGoal(this,this.getSpeed(), 2,48,true));
        //Combat
        this.goalSelector.add(1, new MeleeAttackGoal(this, this.getSpeed(), false));
        this.targetSelector.add(1, new GemAttackWithOwnerGoal(this,this.canFight()));
        this.targetSelector.add(1, new GemTrackOwnerAttackerGoal(this,this.canFight()));
        super.initGoals();
    }

    // Attributes

    public static DefaultAttributeContainer.Builder createDefaultGemAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32);
    }
    public double getSpeed()
    {
        return this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }
    public boolean canFight(){
        if (isSolider())
        {
            return true;
        }
        else return isRebel();
    }
    public abstract boolean isSolider();

    //Data
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TAMABLE_FLAGS, (byte)0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
        this.dataTracker.startTracking(MOVEMENT_TYPE, (byte)0);
        this.dataTracker.startTracking(REBEL,false);
    }

    @Override
    public void onDeath(DamageSource source)
    {
        if(!this.world.isClient()){
            ItemStack item = gemItem();
            NbtCompound nbt = new NbtCompound();
            nbt.putString("id",EntityType.getId(this.getType()).toString());
            this.writeNbt(nbt);
            item.getOrCreateNbt().put("gem",nbt);
            Objects.requireNonNull(this.dropStack(item)).setNeverDespawn();
        }
        super.onDeath(source);
    }
    abstract public ItemStack gemItem();
    abstract public DyeColor gemColor();
    /*
    Movement Type Values
    0 = Wander
    1 = Stay
    2 = Follow
    */
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!player.world.isClient && !player.isSpectator()) {
            if (this.getOwner() == null) {
                KinderMod.LOGGER.info("There's no owner.");
            }
            if (player != this.getOwner()) {
                KinderMod.LOGGER.info(player.getName() + " is not the owner.");
            }
            if (player != getOwner() && getOwner() == null) {
                setOwner(player);
                player.sendMessage(Text.of(this.getName().getString() + " has been claimed."));
                return ActionResult.SUCCESS;
            }
            if (player.isSneaking() && player == getOwner() && hand == Hand.MAIN_HAND) {
                Byte movementType = this.dataTracker.get(MOVEMENT_TYPE);
                movementType = (byte) ((movementType + 1) % 3);
                FOLLOW_ID = (movementType == 2) ? player.getUuid() : null;
                switch (movementType) {
                    case 0 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.wander")));
                    case 1 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.stay")));
                    case 2 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.follow")));
                }
                this.dataTracker.set(MOVEMENT_TYPE,movementType);
                return ActionResult.SUCCESS;
            }
        }
        return super.interactMob(player, hand);
    }

    public UUID getFollowId(){
        return FOLLOW_ID;
    }

    // Animation
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(GemDefaultAnimations.genericGemWalkLegsController(this));
        controllerRegistrar.add(GemDefaultAnimations.genericGemWalkArmsController(this));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getOwnerUuid() != null) {
            nbt.putUuid("Owner", this.getOwnerUuid());
        }
        nbt.putInt("MovementType", this.getMovementType());
        if (FOLLOW_ID != null)
        {
            nbt.putUuid("FollowID",FOLLOW_ID);
        }
        nbt.putByte("MovementType", this.dataTracker.get(MOVEMENT_TYPE));
        nbt.putBoolean("Rebel", this.dataTracker.get(REBEL));
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        UUID uuid;
        super.readCustomDataFromNbt(nbt);
        if (nbt.containsUuid("Owner")) {
            uuid = nbt.getUuid("Owner");
        } else {
            String string = nbt.getString("Owner");
            uuid = ServerConfigHandler.getPlayerUuidByName(this.getServer(), string);
        }
        if (uuid != null) {
            try {
                this.setOwnerUuid(uuid);
                this.setTamed(true);
            } catch (Throwable throwable) {
                this.setTamed(false);
            }
        }
        if (nbt.containsUuid("FollowID")) {
            FOLLOW_ID = nbt.getUuid("FollowID");
        }
        this.dataTracker.set(MOVEMENT_TYPE,nbt.getByte("MovementType"));
        this.dataTracker.set(REBEL,nbt.getBoolean("Rebel"));
    }

    public byte getMovementType()
    {
        return this.dataTracker.get(MOVEMENT_TYPE);
    }

    public boolean isRebel()
    {
        return dataTracker.get(REBEL);
    }

    /* Sounds */
    @NotNull
    public abstract SoundEvent gemInstrument();

    @Override
    public void playAmbientSound() {
        this.playSound(gemInstrument(), 1, this.getSoundPitch());
    }

    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (target instanceof Tameable)
        {
            return ((Tameable) target).getOwner() != owner;
        }
        else return !(target instanceof CreeperEntity);
    }
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world,PassiveEntity entity){
        return null;
    }
    @Override
    public EntityView method_48926(){
        return this.getEntityWorld();
    }
}

