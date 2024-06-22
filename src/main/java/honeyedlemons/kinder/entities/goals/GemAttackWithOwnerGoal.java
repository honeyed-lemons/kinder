/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package honeyedlemons.kinder.entities.goals;

import honeyedlemons.kinder.entities.AbstractGemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;

import java.util.EnumSet;

public class GemAttackWithOwnerGoal extends TrackTargetGoal {
    private final AbstractGemEntity gem;
    private final boolean canFight;
    private LivingEntity attacking;
    private int lastAttackTime;

    public GemAttackWithOwnerGoal (AbstractGemEntity gem, boolean canFight){
        super (gem, false);
        this.gem = gem;
        this.canFight = canFight;
        this.setControls (EnumSet.of (Goal.Control.TARGET));
    }

    public boolean canStart (){
        if (this.gem.isTamed () && !this.gem.isRebel()) {
            LivingEntity livingEntity = this.gem.getOwner ();
            if (livingEntity == null || gem.isRebel()) {
                return false;
            }
            if (!canFight) {
                return false;
            } else {
                this.attacking = livingEntity.getAttacking ();
                int i = livingEntity.getLastAttackTime ();
                return i != this.lastAttackTime && this.canTrack (this.attacking, TargetPredicate.DEFAULT) && this.gem.canAttackWithOwner (this.attacking, livingEntity);
            }
        } else {
            return false;
        }
    }

    public void start (){
        this.mob.setTarget (this.attacking);
        LivingEntity livingEntity = this.gem.getOwner ();
        if (livingEntity != null) {
            this.lastAttackTime = livingEntity.getLastAttackTime ();
        }
        super.start ();
    }
}
