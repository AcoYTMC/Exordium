package net.acoyt.exordium.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.acoyt.exordium.cca.LongswordComponent;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.Vanishable;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ExordiorItem extends SwordItem implements Vanishable {
    protected static final UUID ATTACK_REACH_MODIFIER_ID = UUID.fromString("76a8dee3-3e7e-4e11-ba46-a19b0c724567");
    protected static final UUID REACH_MODIFIER_ID = UUID.fromString("a31c8afc-a716-425d-89cd-0d373380e6e7");
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ExordiorItem(FabricItemSettings settings) {
        super(ExordiumToolMaterial.INSTANCE, 8, -2.7F, settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 7.5, Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -2.7, Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_REACH_MODIFIER_ID, "Weapon modifier", 0.75, Operation.ADDITION));
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Weapon modifier", 1.5, Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public float getAttackDamage() {
        return 12.0F;
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            LongswordComponent.get(player).setBlocking(false);
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.exordium.exordior.desc").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}
