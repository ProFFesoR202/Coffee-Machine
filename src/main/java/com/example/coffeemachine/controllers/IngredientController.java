package com.example.coffeemachine.controllers;

import com.example.coffeemachine.services.IngredientService;
import com.example.coffeemachine.validators.AmountValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingredient")
@Tag(name = "Ингредиенты", description = "API для работы с ингредиентами")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(summary = "Создание нового ингредиента", description = "Создание нового ингредиента для кофемашины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ингредиент успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка при создании ингредиента")
    })
    @Parameters(value = {
            @Parameter(name = "name", description = "Название ингредиента", required = true),
            @Parameter(name = "quantity", description = "Количество", required = true)
    })
    @PostMapping("/ingredient/create")
    public ResponseEntity<String> createIngredient(@RequestParam String name, @RequestParam int quantity) {
        try {
            String result = ingredientService.createIngredient(name, quantity);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Пополнение ингредиентов", description = "Пополнение запасов ингредиентов в кофемашине")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запасы ингредиента успешно пополнены"),
            @ApiResponse(responseCode = "404", description = "Ингредиент не найден")
    })
    @Parameters(value = {
            @Parameter(name = "ingredientName", description = "Имя ингредиента", required = true),
            @Parameter(name = "amount", description = "Количество для добавления", required = true)
    })
    @PostMapping("/ingredient/add")
    public ResponseEntity<String> addIngredient(@RequestParam String ingredientName, @RequestParam int amount) {
        AmountValidator.validateAmount(amount);
        String result = ingredientService.addIngredient(ingredientName, amount);
        return ResponseEntity.ok(result);
    }

}
