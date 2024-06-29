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

public class GemAttackWithOwnerGoal extends TargetGoal {
    private final AbstractGemEntity gem;
    private final boolean canFight;
    private LivingEntity attacking;
    private int lastAttackTime;

    public GemAttackWithOwnerGoal(AbstractGemEntity gem, boolean canFight) {
        super(gem, false);
        this.gem = gem;
        this.canFight = canFight;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        if (this.gem.isTame() && !this.gem.isRebel()) {
            LivingEntity livingEntity = this.gem.getOwner();
            if (livingEntity == null || gem.isRebel()) {
                return false;
            }
            if (!canFight) {
                return false;
            } else {
                this.attacking = livingEntity.getLastHurtMob();
                int i = livingEntity.getLastHurtMobTimestamp();
                return i != this.lastAttackTime && this.canAttack(this.attacking, TargetingConditions.DEFAULT) && this.gem.wantsToAttack(this.attacking, livingEntity);
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.mob.setTarget(this.attacking);
        LivingEntity livingEntity = this.gem.getOwner();
        if (livingEntity != null) {
            this.lastAttackTime = livingEntity.getLastHurtMobTimestamp();
        }
        super.start();
    }
}
