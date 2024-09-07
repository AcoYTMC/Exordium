package net.acoyt.exordium.items;

import net.acoyt.exordium.registry.ExordiumItems;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ExordiumToolMaterial implements ToolMaterial {
    public static final ToolMaterial INSTANCE = new ExordiumToolMaterial();


    public ExordiumToolMaterial() {
    }

    @Override
    public int getDurability() {
        return 0;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 10.5F;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return 4;
    }

    @Override
    public int getEnchantability() {
        return 36;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(new ItemConvertible[]{ExordiumItems.EXORDIOR});
    }
}
