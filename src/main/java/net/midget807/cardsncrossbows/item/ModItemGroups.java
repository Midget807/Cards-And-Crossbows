package net.midget807.cardsncrossbows.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.midget807.cardsncrossbows.CardsNCrossbowsMain;
import net.minecraft.item.ItemGroups;

public class ModItemGroups {
    private static void addItemsToCombatGroup(FabricItemGroupEntries entries) {
        entries.add(ModItems.MANIC_VESSEL);
        entries.add(ModItems.CARDS_OF_MADNESS);
    }

    public static void registerModItemGroups() {
        CardsNCrossbowsMain.LOGGER.info("Registering Mod Item Groups");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItemGroups::addItemsToCombatGroup);
    }
}
