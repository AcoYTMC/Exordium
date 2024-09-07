package net.acoyt.exordium.client;

import net.acoyt.exordium.cca.LongswordComponent;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.profiler.Profiler;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ExordiorItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, IdentifiableResourceReloadListener {
    private static final Set<ModelTransformation.Mode> inventoryModes;
    public static PlayerEntity entity;
    private final Identifier rendererId;
    private final Identifier itemId;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryModel;
    private BakedModel worldModel;
    private BakedModel blockingModel;
    private BakedModel siphonModel;

    public ExordiorItemRenderer(Identifier itemId) {
        this.rendererId = new Identifier(itemId.getNamespace(), itemId.getPath() + "_renderer");
        this.itemId = itemId;
    }

    public Identifier getFabricId() {
        return this.rendererId;
    }

    public CompletableFuture<Void> reload(ResourceReloader.Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return synchronizer.whenPrepared(Unit.INSTANCE).thenRunAsync(() -> {
            applyProfiler.startTick();
            applyProfiler.push("listener");
            MinecraftClient client = MinecraftClient.getInstance();
            this.itemRenderer = client.getItemRenderer();
            this.inventoryModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.itemId, "inventory"));
            this.worldModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.itemId + "_handheld", "inventory"));
            this.blockingModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.itemId + "_blocking", "inventory"));
            this.siphonModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.itemId + "_accumulate", "inventory"));
            applyProfiler.pop();
            applyProfiler.endTick();
        }, applyExecutor);
    }

    static {
        inventoryModes = Set.of(Mode.GUI, Mode.GROUND);
    }

    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.pop();
        matrices.push();
        if (inventoryModes.contains(mode)) {
            this.itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryModel);
        } else {
            boolean leftHanded;
            switch (mode) {
                case FIRST_PERSON_LEFT_HAND:
                case THIRD_PERSON_LEFT_HAND:
                    leftHanded = true;
                    break;
                default:
                    leftHanded = false;
            }

            if (entity != null) {
                LongswordComponent longsword = LongswordComponent.get(entity);

                if (longsword.isBlocking()) {
                    this.itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.blockingModel);
                    return;
                }
            }

            this.itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.worldModel);
        }

    }
}
