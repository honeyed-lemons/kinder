package phyner.kinder.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phyner.kinder.entities.AbstractGemEntity;

@Mixin({ZombieEntity.class, AbstractSkeletonEntity.class, SpiderEntity.class, EvokerEntity.class, PillagerEntity.class, BlazeEntity.class, CreeperEntity.class, SilverfishEntity.class,
        VexEntity.class, GuardianEntity.class, BlazeEntity.class, WitherEntity.class, WitchEntity.class})
public class HostileEntityGemMixin extends HostileEntity {
    protected HostileEntityGemMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "initGoals()V")
    public void initGoals(CallbackInfo ci) {
        this.targetSelector.add(3, new ActiveTargetGoal(this, AbstractGemEntity.class, true));;
    }
}
