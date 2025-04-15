package src.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line;

        while (!"quit".equalsIgnoreCase(line = scanner.nextLine())){
            String[] expr = line.split(" ");
            if (expr.length != 3) {
                System.out.println("Invalid expression received: " + line);
                continue;
            }

            try {
                String operator = expr[0].toUpperCase();
                double operand1 = Double.parseDouble(expr[1]);
                double operand2 = Double.parseDouble(expr[2]);
                double result = calculate(operator, operand1, operand2);
                System.out.println("The result is: " + result);
            } catch (NumberFormatException e) {
                System.out.println("Invalid operand " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (ArithmeticException e) {
                System.out.println("Invalid operator");
            }


        }

    }

    private static double calculate(String operator, double operand1, double operand2) {
        Map<String, BiFunction<Double, Double, Double>> operations = new HashMap<>();
        operations.put("ADD", Double::sum);
        operations.put("SUB", (a, b) -> a - b);
        operations.put("MUL", (a, b) -> a * b);
        operations.put("MOD", (a, b) -> a % b);
        operations.put("DIV", (a, b) -> {
            if (b == 0) throw new IllegalArgumentException("A number cannot be divided by zero");
            return a / b;
        });
        operations.put("POW", (a, b) -> {
            double result = 1.0;
            for (int i = 0; i < b; i++) {
                result *= a;
            }
            return result;
        });

        BiFunction<Double, Double, Double> operation = operations.get(operator);
        if (operator == null) {
            throw new ArithmeticException("Invalid operator: " + operator);
        }
        return operation.apply(operand1, operand2);
    }
}