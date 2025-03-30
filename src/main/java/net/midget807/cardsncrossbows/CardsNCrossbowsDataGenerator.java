package net.midget807.cardsncrossbows;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.midget807.cardsncrossbows.datagen.ModModelProvider;
import net.midget807.cardsncrossbows.datagen.ModRecipeProviders;

public class CardsNCrossbowsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProviders::new);
	}
}
