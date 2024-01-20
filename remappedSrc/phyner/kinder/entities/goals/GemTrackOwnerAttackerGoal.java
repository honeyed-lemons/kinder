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

    private final AbstractGemEntity tameable;
    private LivingEntity attacker;
    private int lastAttackedTime;
    private final boolean canFight;

    public GemTrackOwnerAttackerGoal(AbstractGemEntity tameable, boolean canFight) {
        super(tameable, false);
        this.tameable = tameable;
        this.canFight = canFight;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    public boolean canStart() {
        if (this.tameable.isTamed()) {
            LivingEntity livingEntity = this.tameable.getOwner();
            if (livingEntity == null)
            {
                return false;
            }
            if (!canFight)
            {
                return false;
            }
            else
            {
                this.attacker = livingEntity.getAttacker();
                int i = livingEntity.getLastAttackedTime();
                return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.tameable.canAttackWithOwner(this.attacker, livingEntity);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity != null) {
            this.lastAttackedTime = livingEntity.getLastAttackedTime();
        }
        super.start();
    }
}