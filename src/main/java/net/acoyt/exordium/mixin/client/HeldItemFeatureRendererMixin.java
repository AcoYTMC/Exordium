package net.acoyt.exordium.mixin.client;

import net.acoyt.exordium.registry.ExordiumItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HeldItemFeatureRendererMixin.class})
public abstract class HeldItemFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T> & ModelWithArms> extends FeatureRenderer<T, M> {
    public HeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Inject(
            method = {"renderItem"},
            at = {@At("HEAD")},
            cancellable = true
    )
    protected void exordium$twoHanded(LivingEntity entity, ItemStack stack, ModelTransformation.Mode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        ItemStack main = entity.getMainHandStack();
        boolean mainArm = entity.getMainArm() == arm;
        if (main.isOf(ExordiumItems.EXORDIOR) && !mainArm) {
            matrices.push();
            ((ModelWithArms)this.getContextModel()).setArmAngle(arm, matrices);
            matrices.pop();
            ci.cancel();
        }
    }
}
