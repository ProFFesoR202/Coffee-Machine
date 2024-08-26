package com.example.coffeemachine.validators;

public class AmountValidator {

    public static void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Количество должно быть больше нуля");
        }
    }

}
