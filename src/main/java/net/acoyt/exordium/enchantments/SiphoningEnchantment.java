package net.acoyt.exordium.enchantments;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantment;
import net.acoyt.exordium.registry.ExordiumEnchantments;
import net.acoyt.exordium.registry.ExordiumItems;

public class SiphoningEnchantment extends Enchantment {
    public SiphoningEnchantment() {
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
        return other != ExordiumEnchantments.DOUBLE_DASH && super.canAccept(other);
    }
}
