package phyner.kinder.entities.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phyner.kinder.entities.AbstractGemEntity;

import java.util.EnumSet;

public class GemFollowOwnerGoal extends Goal {
    private final AbstractGemEntity gem;
    private final World world;
    private final double speed;
    private final EntityNavigation navigation;
    private final float maxDistance;
    private final float minDistance;
    private final boolean leavesAllowed;
    private LivingEntity owner;
    private int updateCountdownTicks;
    private float oldWaterPathfindingPenalty;

    public GemFollowOwnerGoal(AbstractGemEntity gem,double speed,float minDistance,float maxDistance,boolean leavesAllowed){
        this.gem = gem;
        this.world = gem.getWorld();
        this.speed = speed;
        this.navigation = gem.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.leavesAllowed = leavesAllowed;
        this.setControls(EnumSet.of(Goal.Control.MOVE,Goal.Control.LOOK));
        if (!(gem.getNavigation() instanceof MobNavigation) && !(gem.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean canStart(){
        if (gem.getFollowId() == null) {
            return false;
        }
        LivingEntity livingEntity = this.world.getPlayerByUuid(this.gem.getFollowId());
        if (livingEntity == null) {
            return false;
        }
        if (livingEntity.isSpectator()) {
            return false;
        }
        if (this.cannotFollow()) {
            return false;
        }
        if (this.gem.squaredDistanceTo(livingEntity) < (double) (this.minDistance * this.minDistance)) {
            return false;
        }
        this.owner = livingEntity;
        return true;
    }

    @Override
    public boolean shouldContinue(){
        if (this.navigation.isIdle()) {
            return false;
        }
        if (this.cannotFollow()) {
            return false;
        }
        return !(this.gem.squaredDistanceTo(this.owner) <= (double) (this.maxDistance * this.maxDistance));
    }

    private boolean cannotFollow(){
        return this.gem.hasVehicle() || this.gem.isLeashed() || this.gem.getMovementType() != 2 || this.gem.getFollowId() == null;
    }

    @Override
    public void start(){
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.gem.getPathfindingPenalty(PathNodeType.WATER);
        this.gem.setPathfindingPenalty(PathNodeType.WATER,0.0f);
    }

    @Override
    public void stop(){
        this.owner = null;
        this.navigation.stop();
        this.gem.setPathfindingPenalty(PathNodeType.WATER,this.oldWaterPathfindingPenalty);
    }

    @Override
    public void tick(){
        this.gem.getLookControl().lookAt(this.owner,10.0f,this.gem.getMaxLookPitchChange());
        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.getTickCount(10);
        if (this.gem.squaredDistanceTo(this.owner) >= 144.0) {
            this.tryTeleport();
        } else {
            this.navigation.startMovingTo(this.owner,this.speed);
        }
    }

    private void tryTeleport(){
        BlockPos blockPos = this.owner.getBlockPos();
        for (int i = 0; i < 10; ++i) {
            int j = this.getRandomInt(-3,3);
            int k = this.getRandomInt(-1,1);
            int l = this.getRandomInt(-3,3);
            boolean bl = this.tryTeleportTo(blockPos.getX() + j,blockPos.getY() + k,blockPos.getZ() + l);
            if (!bl) continue;
            return;
        }
    }

    private boolean tryTeleportTo(int x,int y,int z){
        if (Math.abs((double) x - this.owner.getX()) < 2.0 && Math.abs((double) z - this.owner.getZ()) < 2.0) {
            return false;
        }
        if (!this.canTeleportTo(new BlockPos(x,y,z))) {
            return false;
        }
        this.gem.refreshPositionAndAngles((double) x + 0.5,y,(double) z + 0.5,this.gem.getYaw(),this.gem.getPitch());
        this.navigation.stop();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos){
        PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.world,pos.mutableCopy());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        }
        BlockState blockState = this.world.getBlockState(pos.down());
        if (!this.leavesAllowed && blockState.getBlock() instanceof LeavesBlock) {
            return false;
        }
        BlockPos blockPos = pos.subtract(this.gem.getBlockPos());
        return this.world.isSpaceEmpty(this.gem,this.gem.getBoundingBox().offset(blockPos));
    }

    private int getRandomInt(int min,int max){
        return this.gem.getRandom().nextInt(max - min + 1) + min;
    }
}

