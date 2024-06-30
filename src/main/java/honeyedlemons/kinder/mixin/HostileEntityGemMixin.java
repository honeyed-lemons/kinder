package honeyedlemons.kinder.mixin;

import honeyedlemons.kinder.entities.AbstractGemEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin ({Zombie.class, AbstractSkeleton.class, Evoker.class, Pillager.class, Blaze.class, Creeper.class, Silverfish.class,
        Vex.class, Guardian.class, Blaze.class, WitherBoss.class, Witch.class})
public class HostileEntityGemMixin extends Monster {
    protected HostileEntityGemMixin(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    @Inject (at = @At ("HEAD"), method = "registerGoals()V")
    public void initGoals(CallbackInfo ci) {
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractGemEntity.class, true));
        ;
    }
}
