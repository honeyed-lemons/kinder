package phyner.kinder.entities.gems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.entities.goals.GemAttackWithOwnerGoal;
import phyner.kinder.entities.goals.GemTrackOwnerAttackerGoal;
import phyner.kinder.init.KinderItems;

public class RubyEntity extends AbstractGemEntity {
    public RubyEntity(EntityType<? extends AbstractGemEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes(){
        return createDefaultGemAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, GemAttributeHealth())
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,GemAttributeSpeed())
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,GemAttributeDamage());
    }
    public void initGoals()
    {
        this.goalSelector.add(6, new GemAttackWithOwnerGoal(this));
        this.goalSelector.add(6, new GemTrackOwnerAttackerGoal(this,getLastAttackedTime()));
    }
    static double GemAttributeHealth(){
        return 25.0;
    }
    static double GemAttributeSpeed(){
        return 0.4;
    }
    static double GemAttributeDamage(){
        return 3.0;
    }
    @Override
    @NotNull
    public SoundEvent gemInstrument(){
        return SoundEvents.BLOCK_NOTE_BLOCK_BASS.value();
    }
    @Override
    public double getWanderSpeed(){
        return GemAttributeSpeed();
    }

    @Override
    public double getFollowSpeed(){
        return GemAttributeSpeed() + 0.6;
    }

    @Override
    public boolean isSoliderGem(){
        return true;
    }
    @Override
    public boolean canFight(){
        return true;
    }
    @Override
    public ItemStack gemItem() {
        return new ItemStack(KinderItems.RUBY_GEM);
    }

    @Override
    public DyeColor gemColor(){
        return DyeColor.RED;
    }
}
