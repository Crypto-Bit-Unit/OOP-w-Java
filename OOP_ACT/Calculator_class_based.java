import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

interface Operation {
    double calculate(double operand1, double operand2);
}


class Addition implements Operation {
    @Override
    public double calculate(double operand1, double operand2) {
        return operand1 + operand2;
    }
}

class Subtraction implements Operation {
    @Override
    public double calculate(double operand1, double operand2) {
        return operand1 - operand2;
    }
}


class Multiplication implements Operation {
    @Override
    public double calculate(double operand1, double operand2) {
        return operand1 * operand2;
    }
}

class Division implements Operation {
    @Override
    public double calculate(double operand1, double operand2) {
        if (operand2 == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return operand1 / operand2;
    }
}

class Calculator {
    private final Map<Character, Operation> operations = new HashMap<>();

    public Calculator() {
        operations.put('+', new Addition());
        operations.put('-', new Subtraction());
        operations.put('*', new Multiplication());
        operations.put('/', new Division());
    }

    public double calculate(double operand1, double operand2, char operator) {
        Operation operation = operations.get(operator);
        if (operation == null) {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        return operation.calculate(operand1, operand2);
    }
}


public class CalculatorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();

        System.out.println("Enter the first number:");
        double num1 = scanner.nextDouble();

        System.out.println("Enter the second number:");
        double num2 = scanner.nextDouble();

        System.out.println("Enter the operator (+, -, *, /):");
        char operator = scanner.next().charAt(0);

        try {
            double result = calculator.calculate(num1, num2, operator);
            System.out.println("Result: " + num1 + " " + operator + " " + num2 + " = " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
} 
