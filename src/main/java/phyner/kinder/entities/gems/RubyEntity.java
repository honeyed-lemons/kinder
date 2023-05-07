package phyner.kinder.entities.gems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.entities.goals.GemAttackWithOwnerGoal;
import phyner.kinder.entities.goals.GemTrackOwnerAttackerGoal;
import phyner.kinder.init.KinderItems;

public class RubyEntity extends AbstractGemEntity implements Tameable {
    public RubyEntity(EntityType<? extends AbstractGemEntity> entityType, World world) {
        super(entityType, world);
    }
    public static DefaultAttributeContainer.Builder createGemAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    @Override
    protected void initGoals() {
        this.targetSelector.add(1, new GemTrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new GemAttackWithOwnerGoal(this));
        this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0, true));
    }
    @Override
    public ItemStack gemItem() {
        return new ItemStack(KinderItems.RUBY_GEM);
    }
}
