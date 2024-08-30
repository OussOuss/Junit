package com.luv2code.springmvc;

import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class TestSpyMock {

    //@Mock
    // example with @Mock : all method use default value : int additionResult = calculator.add(10, 20) return 0 instead of 30
    // example with @Spy we use real methods , int additionResult = calculator.add(10, 20) return 30
    @Spy
    Calculator calculator;

    @Test
    public void testCalculatorWithMock() {
        // Arrange
        MockitoAnnotations.openMocks(this); // Initialize the mock

        // Define the behavior for the multiply method
        when(calculator.multiply(anyInt(), anyInt())).thenReturn(50);

        // Act
        int additionResult = calculator.add(10, 20); // Mocked method, returns default value (0)
        int multiplicationResult = calculator.multiply(10, 5); // Mocked method, returns 50
        int divisionResult = calculator.divide(10, 2); // Mocked method, returns default value (0)

        // Assert
        assertEquals(30, additionResult); // add() is mocked, so returns default value (0)
        assertEquals(50, multiplicationResult); // multiply() is mocked to return 50
        assertEquals(5, divisionResult); // divide() is mocked, so returns default value (0)
    }
}

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero!");
        }
        return a / b;
    }
}