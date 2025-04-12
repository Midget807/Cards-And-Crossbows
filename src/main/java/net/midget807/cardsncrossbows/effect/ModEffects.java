package net.midget807.cardsncrossbows.effect;

import net.midget807.cardsncrossbows.CardsNCrossbowsMain;
import net.midget807.cardsncrossbows.effect.custom.EmptyEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffects {
    public static final StatusEffect SCHIZOPHRENIA = registerEffect("schizophrenia", new EmptyEffect(StatusEffectCategory.NEUTRAL, 0x800000));

    private static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, CardsNCrossbowsMain.id(name), effect);
    }

    public static void registerModEffects() {
        CardsNCrossbowsMain.LOGGER.info("Registering Mod Effects");
    }
}
