/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package honeyedlemons.kinder.entities.goals;

import honeyedlemons.kinder.entities.AbstractGemEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class GemTrackOwnerAttackerGoal extends TargetGoal {

    private final AbstractGemEntity tameable;
    private final boolean canFight;
    private LivingEntity attacker;
    private int lastAttackedTime;

    public GemTrackOwnerAttackerGoal(AbstractGemEntity tameable, boolean canFight) {
        super(tameable, false);
        this.tameable = tameable;
        this.canFight = canFight;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        if (this.tameable.isTame() && !this.tameable.isRebel()) {
            LivingEntity livingEntity = this.tameable.getOwner();
            if (livingEntity == null) {
                return false;
            }
            if (!canFight) {
                return false;
            } else {
                this.attacker = livingEntity.getLastHurtByMob();
                int i = livingEntity.getLastHurtByMobTimestamp();
                return i != this.lastAttackedTime && this.canAttack(this.attacker, TargetingConditions.DEFAULT) && this.tameable.wantsToAttack(this.attacker, livingEntity);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity != null) {
            this.lastAttackedTime = livingEntity.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}