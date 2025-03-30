package net.midget807.cardsncrossbows.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.midget807.cardsncrossbows.CardsNCrossbowsMain;
import net.midget807.cardsncrossbows.item.custom.CardsOfMadnessItem;
import net.midget807.cardsncrossbows.item.custom.ManicVesselItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item MANIC_VESSEL = registerItem("manic_vessel", new ManicVesselItem(new FabricItemSettings().maxCount(8)));
    public static final Item CARDS_OF_MADNESS = registerItem("cards_of_madness", new CardsOfMadnessItem(new FabricItemSettings().maxCount(52)));
    public static final Item CARD_OF_MADNESS = registerItem("card_of_madness", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, CardsNCrossbowsMain.id(name), item);
    }

    public static void registerModItems() {
        CardsNCrossbowsMain.LOGGER.info("Register Mod Items");
    }
}
