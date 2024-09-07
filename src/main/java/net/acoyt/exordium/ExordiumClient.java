package net.acoyt.exordium;

import net.acoyt.exordium.cca.LongswordComponent;
import net.acoyt.exordium.client.ExordiorItemRenderer;
import net.acoyt.exordium.registry.ExordiumItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExordiumClient implements ClientModInitializer {
    public ExordiumClient() {
    }

    public void onInitializeClient() {
        registerAmariteLongsword(ExordiumItems.EXORDIOR);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex == 0 && ExordiorItemRenderer.entity != null) {
                LongswordComponent component = LongswordComponent.get(ExordiorItemRenderer.entity);
                return component.getChargeTint(stack);
            } else {
                return 16777215;
            }
        }, new ItemConvertible[]{ExordiumItems.EXORDIOR});
    }

    public static void registerAmariteLongsword(Item item) {
        Identifier itemId = Registry.ITEM.getId(item);
        ExordiorItemRenderer inventoryItemRenderer = new ExordiorItemRenderer(itemId);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(inventoryItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(item, inventoryItemRenderer);
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier(itemId, "inventory"));
            out.accept(new ModelIdentifier("" + itemId + "_handheld", "inventory"));
            out.accept(new ModelIdentifier("" + itemId + "_blocking", "inventory"));
            out.accept(new ModelIdentifier("" + itemId + "_accumulate", "inventory"));
        });
    }
}
