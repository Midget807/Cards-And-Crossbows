package net.midget807.cardsncrossbows.item.custom;

import net.midget807.cardsncrossbows.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ManicVesselItem extends Item {
    public ManicVesselItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.addStatusEffect(new StatusEffectInstance(ModEffects.SCHIZOPHRENIA, 20 * 5, 0, false, true, true));

        return super.use(world, user, hand);
    }
}
