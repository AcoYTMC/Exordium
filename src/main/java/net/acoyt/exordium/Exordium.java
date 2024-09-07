package net.acoyt.exordium;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.acoyt.exordium.cca.LongswordComponent;
import net.acoyt.exordium.registry.ExordiumEnchantments;
import net.acoyt.exordium.registry.ExordiumItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Exordium implements ModInitializer {
	public static final String MOD_ID = "exordium";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ComponentKey<LongswordComponent> LONGSWORD = ComponentRegistry.getOrCreate(id("longsword"), LongswordComponent.class);
	public static final Identifier LONGSWORD_ABILITY;

	public Exordium() {
	}

	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(PlayerEntity.class, LONGSWORD).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LongswordComponent::new);
	}

	@Override
	public void onInitialize() {
		ExordiumItems.init();
		ExordiumEnchantments.init();
	}

	public static ItemStack getRecipeKindIcon() {
		return ExordiumItems.EXORDIOR.getDefaultStack();
	}

	public static Identifier id(String name) {
		return new Identifier("exordium", name);
	}

	static {
		LONGSWORD_ABILITY = id("longsword_ability");
	}
}