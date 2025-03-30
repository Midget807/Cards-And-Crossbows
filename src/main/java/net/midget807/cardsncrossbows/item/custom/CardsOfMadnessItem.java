package net.midget807.cardsncrossbows.item.custom;

import net.midget807.cardsncrossbows.entity.custom.CardOfMadnessEntity;
import net.midget807.cardsncrossbows.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CardsOfMadnessItem extends Item {
    public CardsOfMadnessItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            CardOfMadnessEntity cardOfMadnessEntity = new CardOfMadnessEntity(world, user);
            cardOfMadnessEntity.setItem(new ItemStack(ModItems.CARD_OF_MADNESS));
            cardOfMadnessEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.0f, 0.25f);
            world.spawnEntity(cardOfMadnessEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.pass(itemStack);
    }
}
