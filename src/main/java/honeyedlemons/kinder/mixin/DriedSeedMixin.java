package honeyedlemons.kinder.mixin;

import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin (ItemEntity.class)
abstract public class DriedSeedMixin extends Entity implements TraceableEntity {
    public DriedSeedMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getItem();

    @Shadow
    public abstract void setItem(ItemStack stack);

    @Inject (at = @At ("HEAD"), method = "tick()V")
    public void tick(CallbackInfo ci) {
        if (this.isInWater() && this.getItem().getItem() == KinderItems.DRIED_GEM_SEEDS) {
            switch (this.level().random.nextIntBetweenInclusive(0, 3)) {
                case 0 -> this.setItem(new ItemStack(KinderItems.WHITE_GEM_SEEDS));
                case 1 -> this.setItem(new ItemStack(KinderItems.YELLOW_GEM_SEEDS));
                case 2 -> this.setItem(new ItemStack(KinderItems.BLUE_GEM_SEEDS));
                case 3 -> this.setItem(new ItemStack(KinderItems.PINK_GEM_SEEDS));
            }
        }
    }
}
