package net.acoyt.exordium.registry;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;
import net.acoyt.exordium.Exordium;
import net.acoyt.exordium.enchantments.SiphoningEnchantment;
import net.acoyt.exordium.enchantments.DoubleDashEnchantment;

public interface ExordiumEnchantments {
    Map<Enchantment, Identifier> ENCHANTMENTS = new LinkedHashMap();
    Enchantment DOUBLE_DASH = createEnchantment("double_dash", new DoubleDashEnchantment());
    Enchantment SIPHONING = createEnchantment("siphoning", new SiphoningEnchantment());

    static void init() {
        ENCHANTMENTS.keySet().forEach((enchantment) -> {
            Registry.register(Registry.ENCHANTMENT, (Identifier)ENCHANTMENTS.get(enchantment), enchantment);
        });
    }

    static <T extends Enchantment> T createEnchantment(String name, T enchantment) {
        ENCHANTMENTS.put(enchantment, Exordium.id(name));
        return enchantment;
    }
}
