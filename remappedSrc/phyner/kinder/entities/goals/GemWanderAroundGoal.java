package phyner.kinder.entities.goals;

import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.entities.AbstractGemEntity;

public class GemWanderAroundGoal extends WanderAroundFarGoal {
    protected AbstractGemEntity gem;
    protected float gemProbability;

    public GemWanderAroundGoal (PathAwareEntity pathAwareEntity, double d, float gemProbability){
        super (pathAwareEntity, d);
        gem = (AbstractGemEntity) mob;
        this.gemProbability = gemProbability;
    }

    @Override public boolean shouldContinue (){
        return !gem.getNavigation ().isIdle () && !gem.hasPassengers () && gem.getMovementType () == 0;
    }

    @Override public boolean canStart (){
        if (gem.hasPassengers () || (!ignoringChance && gem.getRandom ().nextInt (WanderAroundGoal.toGoalTicks (chance)) != 0)) {
            return false;
        }
        Vec3d vec3d = getWanderTarget ();
        if (vec3d == null) {
            return false;
        }
        if (gem.getMovementType () != 0) {
            return false;
        }
        targetX = vec3d.x;
        targetY = vec3d.y;
        targetZ = vec3d.z;
        ignoringChance = false;
        return true;
    }

    @Override @Nullable protected Vec3d getWanderTarget (){
        if (this.gem.isInsideWaterOrBubbleColumn ()) {
            Vec3d vec3d = FuzzyTargeting.find (this.gem, 15, 7);
            return vec3d == null ? super.getWanderTarget () : vec3d;
        }
        if (this.gem.getRandom ().nextFloat () >= this.gemProbability) {
            return FuzzyTargeting.find (this.gem, 10, 7);
        }
        return super.getWanderTarget ();
    }
}