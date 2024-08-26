package com.example.coffeemachine.dto;

import lombok.Data;
import java.util.List;

@Data
public class RecipeDTO {

    private String name;
    private List<RecipeIngredientDTO> ingredients;

}
