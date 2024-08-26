package com.example.coffeemachine.controllers;

import com.example.coffeemachine.dto.RecipeDTO;
import com.example.coffeemachine.services.MainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coffee")
@Tag(name = "Кофемашина", description = "API для управления кофемашиной")
public class MainController {

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @Operation(summary = "Приготовление напитка", description = "Приготовление кофейного напитка на основе рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Напиток успешно приготовлен"),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден"),
            @ApiResponse(responseCode = "400", description = "Недостаточно ингредиентов")
    })
    @PostMapping("/make")
    public ResponseEntity<String> makeCoffee(@Parameter(description = "Имя рецепта кофе", required = true) @RequestParam String coffeeName) {
        String result = mainService.makeCoffee(coffeeName);
        if (result.startsWith("Недостаточно")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.ok(result);
    }

    //Вынести Parameter в отдельный метод
    @Operation(summary = "Добавление рецепта", description = "Добавление нового рецепта для кофемашины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно добавлен"),
            @ApiResponse(responseCode = "404", description = "Ингредиент не найден")
    })
    @PostMapping("/recipe")
    public ResponseEntity<String> addRecipe(@Parameter(description = "Название рецепта и список ингредиентов с количеством", required = true)
                                            @RequestBody RecipeDTO recipeDTO) {
        String result = mainService.addRecipe(recipeDTO);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Получить самый популярный кофе", description = "Получение самого популярного кофе по статистике заказов")
    @ApiResponse(responseCode = "200", description = "Возвращает название самого популярного кофе")
    @GetMapping("/popular")
    public ResponseEntity<String> getMostPopularCoffee() {
        String mostPopularDrink = mainService.getMostPopularCoffee();
        return ResponseEntity.ok(mostPopularDrink);
    }
}
