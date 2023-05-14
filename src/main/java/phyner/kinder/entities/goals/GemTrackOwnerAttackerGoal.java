/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package phyner.kinder.entities.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import phyner.kinder.entities.AbstractGemEntity;

import java.util.EnumSet;

public class GemTrackOwnerAttackerGoal
        extends TrackTargetGoal {
    private final AbstractGemEntity tamable;
    private LivingEntity attacker;
    private int lastAttackedTime;

    public GemTrackOwnerAttackerGoal(AbstractGemEntity tamable) {
        super(tamable, false);
        this.tamable = tamable;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (!this.tamable.isTamed()) {
            return false;
        }
        LivingEntity livingEntity = this.tamable.getOwner();
        if (livingEntity == null) {
            return false;
        }
        this.attacker = livingEntity.getAttacker();
        int i = livingEntity.getLastAttackedTime();
        return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.tamable.canAttackWithOwner();
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingEntity = this.tamable.getOwner();
        if (livingEntity != null) {
            this.lastAttackedTime = livingEntity.getLastAttackedTime();
        }
        super.start();
    }
}

