package honeyedlemons.kinder.mixin;

import honeyedlemons.kinder.KinderMod;
import honeyedlemons.kinder.init.KinderItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@SuppressWarnings("AmbiguousMixinReference")
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow public abstract ItemModels getModels();

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useRejuv(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (stack.isOf(KinderItems.REJUVENATOR) && renderMode != ModelTransformationMode.GUI) {
            return this.getModels().getModelManager().getModel(new ModelIdentifier(KinderMod.MOD_ID, "rejuvenator_held", "inventory"));
        }
        return value;
    }
}