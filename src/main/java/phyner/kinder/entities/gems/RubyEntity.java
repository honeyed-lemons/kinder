package phyner.kinder.entities.gems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.entities.goals.GemAttackWithOwnerGoal;
import phyner.kinder.entities.goals.GemFollowOwnerGoal;
import phyner.kinder.entities.goals.GemTrackOwnerAttackerGoal;
import phyner.kinder.entities.goals.GemWanderAroundGoal;
import phyner.kinder.init.KinderItems;

public class RubyEntity extends AbstractGemEntity {
    public RubyEntity(EntityType<? extends AbstractGemEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes(){
        return createDefaultGemAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.60)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE,3.5);
    }

    @Override
    @NotNull
    public SoundEvent gemInstrument(){
        return SoundEvents.BLOCK_NOTE_BLOCK_BASS.value();
    }

    @Override
    public boolean isSolider(){
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

    public LivingEntity getOwner(){
        return super.getOwner();
    }
}
