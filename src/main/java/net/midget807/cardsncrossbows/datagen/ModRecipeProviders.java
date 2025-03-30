package net.midget807.cardsncrossbows.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.midget807.cardsncrossbows.CardsNCrossbowsMain;
import net.midget807.cardsncrossbows.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class ModRecipeProviders extends FabricRecipeProvider {
    public ModRecipeProviders(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.MANIC_VESSEL, 1)
                .pattern("NRE")
                .pattern("GWG")
                .pattern("ERN")
                .input('N', Items.GOLD_NUGGET)
                .input('R', Items.REDSTONE)
                .input('E', Items.FERMENTED_SPIDER_EYE)
                .input('G', Items.GOLD_INGOT)
                .input('W', Items.WITHER_ROSE)
                .criterion(hasItem(Items.GOLD_NUGGET), conditionsFromItem(Items.GOLD_NUGGET))
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .criterion(hasItem(Items.FERMENTED_SPIDER_EYE), conditionsFromItem(Items.FERMENTED_SPIDER_EYE))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(Items.WITHER_ROSE), conditionsFromItem(Items.WITHER_ROSE))
                .offerTo(consumer, CardsNCrossbowsMain.id(getRecipeName(ModItems.MANIC_VESSEL)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.CARDS_OF_MADNESS, 8)
                .pattern("NPP")
                .pattern("GMP")
                .pattern("LGN")
                .input('N', Items.GOLD_NUGGET)
                .input('P', Items.PAPER)
                .input('L', Items.LEATHER)
                .input('G', Items.GOLD_INGOT)
                .input('M', ModItems.MANIC_VESSEL)
                .criterion(hasItem(Items.GOLD_NUGGET), conditionsFromItem(Items.GOLD_NUGGET))
                .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
                .criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(ModItems.MANIC_VESSEL), conditionsFromItem(ModItems.MANIC_VESSEL))
                .offerTo(consumer, CardsNCrossbowsMain.id(getRecipeName(ModItems.CARDS_OF_MADNESS)));


    }
}
