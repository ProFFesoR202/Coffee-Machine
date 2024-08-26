package com.example.coffeemachine.services;

import com.example.coffeemachine.entities.Ingredient;
import com.example.coffeemachine.repositories.IngredientRepository;
import com.example.coffeemachine.validators.AmountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    public String createIngredient(String name, int quantity) {
        AmountValidator.validateAmount(quantity);
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setQuantity(quantity);

        ingredientRepository.save(ingredient);
        return "Ингредиент '" + name + "' успешно создан с количеством " + quantity + " единиц.";
    }

    @Transactional
    public String addIngredient(String ingredientName, int amount) {
        Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                .orElseThrow(() -> new IllegalArgumentException("Ингредиент не найден"));

        ingredient.setQuantity(ingredient.getQuantity() + amount);
        ingredientRepository.save(ingredient);
        return "Ингредиент " + ingredient.getName() + " успешно пополнен на " + amount + " единиц.";
    }

}
