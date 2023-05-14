/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package phyner.kinder.entities.goals;

import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import phyner.kinder.entities.AbstractGemEntity;

public class GemWanderAroundGoal
        extends WanderAroundFarGoal {
    protected AbstractGemEntity gem = (AbstractGemEntity) mob;
    public GemWanderAroundGoal(PathAwareEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d);
    }
    @Override
    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle() && !this.mob.hasPassengers() && gem.getMovementType() == 0 && !this.gem.collidedSoftly;
    }
    @Override
    public boolean canStart() {
        Vec3d vec3d;
        if (this.mob.hasPassengers()) {
            return false;
        }
        if (!this.ignoringChance) {
            if (this.mob.getRandom().nextInt(WanderAroundGoal.toGoalTicks(this.chance)) != 0) {
                return false;
            }
        }
        if ((vec3d = this.getWanderTarget()) == null) {
            return false;
        }
        if (gem.getMovementType() != 0) {
            return false;
        }
        this.targetX = vec3d.x;
        this.targetY = vec3d.y;
        this.targetZ = vec3d.z;
        this.ignoringChance = false;
        return true;
    }
}

