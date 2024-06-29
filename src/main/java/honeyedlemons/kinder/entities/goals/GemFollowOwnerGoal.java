package honeyedlemons.kinder.entities.goals;

import honeyedlemons.kinder.entities.AbstractGemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

public class GemFollowOwnerGoal extends Goal {
    private final AbstractGemEntity gem;
    private final Level world;
    private final double speed;
    private final PathNavigation navigation;
    private final float maxDistance;
    private final float minDistance;
    private final boolean leavesAllowed;
    private LivingEntity owner;
    private int updateCountdownTicks;
    private float oldWaterPathfindingPenalty;

    public GemFollowOwnerGoal(AbstractGemEntity gem, double speed, float minDistance, float maxDistance, boolean leavesAllowed) {
        this.gem = gem;
        this.world = gem.level();
        this.speed = speed;
        this.navigation = gem.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(gem.getNavigation() instanceof GroundPathNavigation) && !(gem.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean canUse() {
        if (gem.getFollowId() == null) {
            return false;
        }
        LivingEntity livingEntity = this.world.getPlayerByUUID(this.gem.getFollowId());
        if (livingEntity == null) {
            return false;
        }
        if (livingEntity.isSpectator()) {
            return false;
        }
        if (this.cannotFollow()) {
            return false;
        }
        if (this.gem.distanceToSqr(livingEntity) < (double) (this.minDistance * this.minDistance)) {
            return false;
        }
        this.owner = livingEntity;
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.navigation.isDone()) {
            return false;
        }
        if (this.cannotFollow()) {
            return false;
        }
        return !(this.gem.distanceToSqr(this.owner) <= (double) (this.maxDistance * this.maxDistance));
    }

    private boolean cannotFollow() {
        return this.gem.isPassenger() || this.gem.isLeashed() || this.gem.getMovementType() != 2 || this.gem.getFollowId() == null;
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.gem.getPathfindingMalus(BlockPathTypes.WATER);
        this.gem.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.gem.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick() {
        this.gem.getLookControl().setLookAt(this.owner, 10.0f, this.gem.getMaxHeadXRot());
        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.adjustedTickDelay(10);
        if (this.gem.distanceToSqr(this.owner) >= 144.0) {
            this.tryTeleport();
        } else {
            this.navigation.moveTo(this.owner, this.speed);
        }
    }

    private void tryTeleport() {
        BlockPos blockPos = this.owner.blockPosition();
        for (int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(-3, 3);
            int k = this.getRandomInt(-1, 1);
            int l = this.getRandomInt(-3, 3);
            boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (!bl) continue;
            return;
        }
    }

    private boolean tryTeleportTo(int x, int y, int z) {
        if (Math.abs((double) x - this.owner.getX()) < 2.0 && Math.abs((double) z - this.owner.getZ()) < 2.0) {
            return false;
        }
        if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        this.gem.moveTo((double) x + 0.5, y, (double) z + 0.5, this.gem.getYRot(), this.gem.getXRot());
        this.navigation.stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos) {
        BlockPathTypes pathNodeType = WalkNodeEvaluator.getBlockPathTypeStatic(this.world, pos.mutable());
        if (pathNodeType != BlockPathTypes.WALKABLE) {
            return false;
        }
        BlockState blockState = this.world.getBlockState(pos.below());
        if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
            return false;
        }
        BlockPos blockPos = pos.subtract(this.gem.blockPosition());
        return this.world.noCollision(this.gem, this.gem.getBoundingBox().move(blockPos));
    }

    private int getRandomInt(int min, int max) {
        return this.gem.getRandom().nextInt(max - min + 1) + min;
    }
}

