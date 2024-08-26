package com.example.coffeemachine.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "recipe_ingredients")
@Data
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private int quantity;

}
