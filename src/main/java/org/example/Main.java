package org.example;

import java.util.Scanner;

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
                int operand1 = Integer.parseInt(expr[1]);
                int operand2 = Integer.parseInt(expr[2]);
                int result = calculate(operator, operand1, operand2);
                System.out.println("The result is: " + result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static int calculate(String operator, int operand1, int operand2) {
        switch (operator) {
            case "ADD":
                return operand1 + operand2;
            case "SUB":
                return operand1 - operand2;
            case "MUL":
                return operand1 * operand2;
            case "DIV":
                if (operand2 == 0) throw new IllegalArgumentException("A number cannot be divided by zero");
                return operand1 / operand2;
            default:
                System.out.println("Invalid operator");
        }
        return 0;
    }


}