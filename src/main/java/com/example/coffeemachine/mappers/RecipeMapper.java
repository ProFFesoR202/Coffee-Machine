package com.example.coffeemachine.mappers;

import com.example.coffeemachine.dto.RecipeDTO;
import com.example.coffeemachine.dto.RecipeIngredientDTO;
import com.example.coffeemachine.entities.Ingredient;
import com.example.coffeemachine.entities.Recipe;
import com.example.coffeemachine.entities.RecipeIngredient;
import com.example.coffeemachine.repositories.IngredientRepository;
import com.example.coffeemachine.repositories.RecipeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeDTO toDTO(Recipe recipe) {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName(recipe.getName());
        recipeDTO.setIngredients(recipe.getIngredients().stream()
                .map(RecipeMapper::toDTO)
                .collect(Collectors.toList()));
        return recipeDTO;
    }

    public static RecipeIngredientDTO toDTO(RecipeIngredient recipeIngredient) {
        RecipeIngredientDTO dto = new RecipeIngredientDTO();
        dto.setIngredientName(recipeIngredient.getIngredient().getName());
        dto.setQuantity(recipeIngredient.getQuantity());
        return dto;
    }

    public static Recipe toEntity(RecipeDTO recipeDTO, IngredientRepository ingredientRepository) {
        Recipe recipe = new Recipe();
        recipe.setName(recipeDTO.getName());

        List<RecipeIngredient> ingredients = recipeDTO.getIngredients().stream()
                .map(dto -> toEntity(dto, ingredientRepository, recipe))
                .collect(Collectors.toList());
        recipe.setIngredients(ingredients);

        return recipe;
    }

    public static RecipeIngredient toEntity(RecipeIngredientDTO dto, IngredientRepository ingredientRepository, Recipe recipe) {
        Ingredient ingredient = ingredientRepository.findByName(dto.getIngredientName())
                .orElseThrow(() -> new IllegalArgumentException("Ингредиент не найден"));

        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(dto.getQuantity());

        return recipeIngredient;
    }
}
