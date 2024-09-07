package net.acoyt.exordium.enchantments;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.acoyt.exordium.registry.ExordiumEnchantments;
import net.acoyt.exordium.registry.ExordiumItems;

public class DoubleDashEnchantment extends Enchantment {
    public DoubleDashEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.isOf(ExordiumItems.EXORDIOR);
    }

    public int getMinPower(int level) {
        return 15;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    protected boolean canAccept(Enchantment other) {
        return other != ExordiumEnchantments.SIPHONING && super.canAccept(other);
    }
}
