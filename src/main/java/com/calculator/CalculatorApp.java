package com.calculator;

import java.util.Scanner;

public class CalculatorApp {
    private static ConfigManager config;
    private static Calculator calculator;

    public static void main(String[] args) {
        try {
            config = new ConfigManager();
            calculator = new Calculator(config);

            if (config.getBooleanProperty("display.welcome", true)) {
                System.out.println("=== " + config.getProperty("app.name") + " ===");
                System.out.println("Version: " + config.getProperty("app.version"));
                System.out.println("Mode: " + config.getProperty("app.mode"));
                System.out.println();
            }

            runCalculator();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void runCalculator() {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		while (running) {
			if (config.getBooleanProperty("display.operations", true)) {
				System.out.println("Operations: +, -, *, /, history, quit");
			}
			
			System.out.print("Enter first number (or 'history', 'quit'): ");
			if (!scanner.hasNextDouble()) {
				String input = scanner.next();
				if ("quit".equalsIgnoreCase(input)) {
					running = false;
					continue;
				} else if ("history".equalsIgnoreCase(input)) {
					calculator.showHistory();
					System.out.println();
					continue;
				} else {
					System.out.println("Invalid input. Try again.");
					continue;
				}
			}
			double num1 = scanner.nextDouble();
			
			System.out.print("Enter operation (+, -, *, /): ");
			String operation = scanner.next();
			
			System.out.print("Enter second number: ");
			if (!scanner.hasNextDouble()) {
				System.out.println("Invalid number. Try again.");
				scanner.next();
				continue;
			}
			double num2 = scanner.nextDouble();
			
			try {
				double result = calculator.calculate(num1, operation, num2);
				if (config.getBooleanProperty("display.results", true)) {
					System.out.printf("Result: %.0f %s %.0f = %s%n", 
						num1, operation, num2, calculator.formatResult(result));
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			System.out.println();
		}
		
		scanner.close();
		System.out.println("Calculator closed.");
	}
}