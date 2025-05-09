package net.midget807.cardsncrossbows;

import net.fabricmc.api.ModInitializer;

import net.midget807.cardsncrossbows.command.ModCommands;
import net.midget807.cardsncrossbows.effect.ModEffects;
import net.midget807.cardsncrossbows.entity.ModEntities;
import net.midget807.cardsncrossbows.item.ModItemGroups;
import net.midget807.cardsncrossbows.item.ModItems;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CardsNCrossbowsMain implements ModInitializer {
	public static final String MOD_ID = "cardsncrossbows";

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static final Logger LOGGER = LoggerFactory.getLogger("Cards & Crossbows");

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModEntities.registerModEntities();
		ModEffects.registerModEffects();
		ModCommands.registerModCommands();

		LOGGER.info("yup im here");
	}
}