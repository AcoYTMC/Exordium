package net.acoyt.exordium.registry;

import net.acoyt.exordium.Exordium;
import net.acoyt.exordium.items.ExordiorItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public interface ExordiumItems {
    ArrayList<ItemStack> EXORDIUM_ITEMS = new ArrayList();
    ItemGroup EXORDIUM_GROUP = FabricItemGroupBuilder.create(Exordium.id("exordium_group")).icon(Exordium::getRecipeKindIcon).build();
    Map<Item, Identifier> ITEMS = new LinkedHashMap();
    Item EXORDIOR = createItem("exordior", new ExordiorItem((new FabricItemSettings()).group(EXORDIUM_GROUP)));



    static void init() {
        ITEMS.keySet().forEach((item) -> {
            Registry.register(Registry.ITEM, (Identifier)ITEMS.get(item), item);
        });
    }

    static <T extends Item> T createItem(String name, T item, ItemStack... stacks) {
        ITEMS.put(item, Exordium.id(name));
        if (stacks.length > 0) {
            ItemStack[] var3 = stacks;
            int var4 = stacks.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                ItemStack stack = var3[var5];
                if (!stack.isEmpty()) {
                    EXORDIUM_ITEMS.add(stack);
                }
            }
        } else {
            EXORDIUM_ITEMS.add(item.getDefaultStack());
        }

        return item;
    }
}
