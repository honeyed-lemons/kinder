package phyner.kinder.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phyner.kinder.init.KinderItems;

@Mixin(ItemEntity.class)
abstract public class DriedSeedMixin extends Entity implements Ownable {
    public DriedSeedMixin(EntityType<?> type,World world){
        super(type,world);
    }

    @Shadow
    public abstract ItemStack getStack();

    @Shadow
    public abstract void setStack(ItemStack stack);

    @Inject(at = @At("HEAD"), method = "tick()V")
    public void tick(CallbackInfo ci){
        if (this.isTouchingWater() && this.getStack().getItem() == KinderItems.DRIED_GEM_SEEDS) {
            switch (random.nextBetween(0,3))
            {
                case 0 : this.setStack(new ItemStack(KinderItems.WHITE_GEM_SEEDS));
                case 1 : this.setStack(new ItemStack(KinderItems.YELLOW_GEM_SEEDS));
                case 2 : this.setStack(new ItemStack(KinderItems.BLUE_GEM_SEEDS));
                case 3 : this.setStack(new ItemStack(KinderItems.PINK_GEM_SEEDS));
            }
        }
    }
}
