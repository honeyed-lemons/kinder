package phyner.kinder.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.entities.goals.GemAttackWithOwnerGoal;
import phyner.kinder.entities.goals.GemTrackOwnerAttackerGoal;
import phyner.kinder.entities.goals.GemWanderAroundGoal;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractGemEntity extends PathAwareEntity implements GeoEntity, Tameable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final TrackedData<Byte> TAMABLE_FLAGS = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.BYTE);
    public static int MOVEMENT_TYPE = 0;
    public static UUID FOLLOW_ID;

    protected static final TrackedData<Optional<UUID>> OWNER_UUID = DataTracker.registerData(AbstractGemEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);

    public AbstractGemEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.initGemGoals();
    }
    public static DefaultAttributeContainer.Builder createDefaultGemAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
    }

    public void initGemGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        //Movement Types
        this.goalSelector.add(6, new GemWanderAroundGoal(this,this.getAttributeBaseValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
        if (this.IsSoliderGem())
        {
            this.goalSelector.add(5, new GemAttackWithOwnerGoal(this));
            this.goalSelector.add(5, new GemTrackOwnerAttackerGoal(this));
        }
    }
    public abstract boolean IsSoliderGem();


    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TAMABLE_FLAGS, (byte)0);
        this.dataTracker.startTracking(OWNER_UUID, Optional.empty());
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

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!player.world.isClient) {
            if (player != getOwner() && getOwner() == null) {
                setOwner(player);
                player.sendMessage(Text.of(this.getName().getString()+" has been claimed."));
                return ActionResult.SUCCESS;
            }
            if (player.isSneaking() && player == getOwner() && hand == Hand.MAIN_HAND)
            {
                MOVEMENT_TYPE = (MOVEMENT_TYPE + 1) % 3;
                FOLLOW_ID = (MOVEMENT_TYPE == 2) ? player.getUuid() : null;
                switch (MOVEMENT_TYPE) {
                    case 0 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.wander")));
                    case 1 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.stay")));
                    case 2 -> player.sendMessage(Text.literal(this.getDisplayName().getString()).append(Text.translatable("kinder.gem.movement.interact.follow")));
                }
                return ActionResult.SUCCESS;
            }
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(GemDefaultAnimations.genericGemWalkController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    abstract public ItemStack gemItem();

    @Override
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
    }

    @Override
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
        MOVEMENT_TYPE = nbt.getInt("MovementType");
        if (nbt.containsUuid("FollowID")) {
            FOLLOW_ID = nbt.getUuid("FollowID");
        }
    }

    public int getMovementType()
    {
        return MOVEMENT_TYPE;
    }


    // TAMING STUFF

    @Nullable
    public UUID getOwnerUuid() {
        return this.dataTracker.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUuid(@Nullable UUID uuid) {
        this.dataTracker.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerUuid(player.getUuid());
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if (this.isOwner(target)) {
            return false;
        }
        return super.canTarget(target);
    }

    public boolean isOwner(LivingEntity entity) {
        return entity == this.getOwner();
    }

    public void setTamed(boolean tamed) {
        byte b = this.dataTracker.get(TAMABLE_FLAGS);
        if (tamed) {
            this.dataTracker.set(TAMABLE_FLAGS, (byte)(b | 4));
        } else {
            this.dataTracker.set(TAMABLE_FLAGS, (byte)(b & 0xFFFFFFFB));
        }
        this.onTamedChanged();
    }

    public boolean canAttackWithOwner() {
        return true;
    }

    @Override
    public AbstractTeam getScoreboardTeam() {
        LivingEntity livingEntity;
        if (this.isTamed() && (livingEntity = this.getOwner()) != null) {
            return livingEntity.getScoreboardTeam();
        }
        return super.getScoreboardTeam();
    }

    @Override
    public boolean isTeammate(Entity other) {
        if (this.isTamed()) {
            LivingEntity livingEntity = this.getOwner();
            if (other == livingEntity) {
                return true;
            }
            if (livingEntity != null) {
                return livingEntity.isTeammate(other);
            }
        }
        return super.isTeammate(other);
    }

    public boolean isTamed() {
        return (this.dataTracker.get(TAMABLE_FLAGS) & 4) != 0;
    }

    public void onTamedChanged() {
    }
    @Override
    public EntityView method_48926() {
        return super.getWorld();
    }

    // TAMING STUFF DONE
}
