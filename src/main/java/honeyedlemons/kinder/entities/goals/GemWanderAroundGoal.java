package honeyedlemons.kinder.entities.goals;

import honeyedlemons.kinder.entities.AbstractGemEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class GemWanderAroundGoal extends WaterAvoidingRandomStrollGoal {
    protected AbstractGemEntity gem;
    protected float gemProbability;

    public GemWanderAroundGoal(PathfinderMob pathAwareEntity, double d, float gemProbability) {
        super(pathAwareEntity, d);
        gem = (AbstractGemEntity) mob;
        this.gemProbability = gemProbability;
    }

    @Override
    public boolean canContinueToUse() {
        return !gem.getNavigation().isDone() && !gem.isVehicle() && gem.getMovementType() == 0;
    }

    @Override
    public boolean canUse() {
        if (gem.isVehicle() || (!forceTrigger && gem.getRandom().nextInt(RandomStrollGoal.reducedTickDelay(interval)) != 0)) {
            return false;
        }
        Vec3 vec3d = getPosition();
        if (vec3d == null) {
            return false;
        }
        if (gem.getMovementType() != 0) {
            return false;
        }
        wantedX = vec3d.x;
        wantedY = vec3d.y;
        wantedZ = vec3d.z;
        forceTrigger = false;
        return true;
    }

    @Override
    @Nullable
    protected Vec3 getPosition() {
        if (this.gem.isInWaterOrBubble()) {
            Vec3 vec3d = LandRandomPos.getPos(this.gem, 15, 7);
            return vec3d == null ? super.getPosition() : vec3d;
        }
        if (this.gem.getRandom().nextFloat() >= this.gemProbability) {
            return LandRandomPos.getPos(this.gem, 10, 7);
        }
        return super.getPosition();
    }
}