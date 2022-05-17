package com.newjumper.taloi.datagen.recipes;

import com.newjumper.taloi.ThatsALotOfItems;
import com.newjumper.taloi.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class StonecuttingRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public StonecuttingRecipesProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.LIMESTONE_SLAB.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.LIMESTONE.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "limestone_slab_from_limestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.LIMESTONE_STAIRS.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.LIMESTONE.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "limestone_stairs_from_limestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.LIMESTONE_WALL.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.LIMESTONE.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "limestone_wall_from_limestone_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.MARBLE.get()), ModBlocks.MARBLE_SLAB.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.MARBLE.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "marble_slab_from_marble_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.MARBLE.get()), ModBlocks.MARBLE_STAIRS.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.MARBLE.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "marble_stairs_from_marble_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.MARBLE.get()), ModBlocks.MARBLE_WALL.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.MARBLE.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "marble_wall_from_marble_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE.get()), ModBlocks.LIMESTONE_BRICKS.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.LIMESTONE.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "limestone_bricks_from_limestone_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE_BRICKS.get()), ModBlocks.LIMESTONE_SLAB.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.LIMESTONE_BRICKS.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "limestone_brick_slab_from_limestone_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE_BRICKS.get()), ModBlocks.LIMESTONE_STAIRS.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.LIMESTONE_BRICKS.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "limestone_brick_stairs_from_limestone_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.LIMESTONE_BRICKS.get()), ModBlocks.LIMESTONE_WALL.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.LIMESTONE_BRICKS.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "limestone_brick_wall_from_limestone_bricks_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SLATE.get()), ModBlocks.SLATE_BRICKS.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.SLATE.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "slate_bricks_from_slate_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SLATE_BRICKS.get()), ModBlocks.SLATE_SLAB.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.SLATE_BRICKS.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "slate_brick_slab_from_slate_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SLATE_BRICKS.get()), ModBlocks.SLATE_STAIRS.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.SLATE_BRICKS.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "slate_brick_stairs_from_slate_bricks_stonecutting"));
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SLATE_BRICKS.get()), ModBlocks.SLATE_WALL.get(), 1)
                .unlockedBy("has_material", has(ModBlocks.SLATE_BRICKS.get())).save(consumer, new ResourceLocation(ThatsALotOfItems.MOD_ID, "slate_brick_wall_from_slate_bricks_stonecutting"));
    }

    @Override
    public String getName() {
        return "Stonecutting Recipes";
    }
}
