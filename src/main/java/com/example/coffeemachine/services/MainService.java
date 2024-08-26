package com.example.coffeemachine.services;

import com.example.coffeemachine.dto.RecipeDTO;
import com.example.coffeemachine.dto.RecipeIngredientDTO;
import com.example.coffeemachine.entities.*;
import com.example.coffeemachine.exceptions.DataNotFoundException;
import com.example.coffeemachine.mappers.RecipeMapper;
import com.example.coffeemachine.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MainService {

    private final IngredientRepository ingredientRepository;

    private final RecipeRepository recipeRepository;

    private final StatisticsRepository statisticsRepository;

    @Autowired
    public MainService(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, StatisticsRepository statisticsRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.statisticsRepository = statisticsRepository;
    }

    @Transactional
    public String makeCoffee(String coffeeName) {
        Recipe recipe = recipeRepository.findByName(coffeeName)
                .orElseThrow(() -> new IllegalArgumentException("Рецепт не найден"));

        RecipeDTO recipeDTO = RecipeMapper.toDTO(recipe);

        for (RecipeIngredientDTO ingredientDTO : recipeDTO.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findByName(ingredientDTO.getIngredientName())
                    .orElseThrow(() -> new IllegalArgumentException("Ингредиент не найден"));

            if (ingredient.getQuantity() < ingredientDTO.getQuantity()) {
                return "Недостаточно " + ingredient.getName();
            }
        }

        for (RecipeIngredientDTO ingredientDTO : recipeDTO.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findByName(ingredientDTO.getIngredientName())
                    .orElseThrow(() -> new IllegalArgumentException("Ингредиент не найден"));

            ingredient.setQuantity(ingredient.getQuantity() - ingredientDTO.getQuantity());
            ingredientRepository.save(ingredient);
        }

        Statistics stat = statisticsRepository.findByCoffeeName(coffeeName).orElse(new Statistics());
        stat.setCoffeeName(coffeeName);
        stat.setOrderCount(stat.getOrderCount() + 1);
        statisticsRepository.save(stat);

        return "Напиток '" + recipeDTO.getName() + "' успешно приготовлен!";
    }

    @Transactional
    public String addRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapper.toEntity(recipeDTO, ingredientRepository);
        recipeRepository.save(recipe);

        return "Рецепт " + recipeDTO.getName() + " успешно добавлен";
    }

    public String getMostPopularCoffee() {
        return statisticsRepository.getByMaxOrderCount().orElseThrow(() -> new DataNotFoundException("Статистика не найдена"));
    }

}
