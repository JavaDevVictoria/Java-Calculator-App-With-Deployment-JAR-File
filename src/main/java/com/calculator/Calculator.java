package com.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;					   

public class Calculator {
    private final ConfigManager config;
    private final DecimalFormat formatter;
	private final List<String> calculationHistory;
	private final int maxHistorySize = 5;									 
    public Calculator(ConfigManager config) {
        this.config = config;
        int precision = config.getIntProperty("app.precision", 2);
        String pattern = "#." + "#".repeat(precision);
        this.formatter = new DecimalFormat(pattern);
		this.calculationHistory = new ArrayList<>();							 
    }

    public double calculate(double num1, String operation, double num2) {
		double result;			  
        switch (operation) {
            case "+":
                result = num1 + num2;
                break;  
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
		// Add to history
        saveHistory(num1, operation, num2, result);
		return result;
    }

    public String formatResult(double result) {
        return formatter.format(result);
    }

    private void saveHistory(double num1, String operation, double num2, double result) {
        String calculation = String.format("%.1f %s %.1f = %s", 
                                          num1, operation, num2, formatResult(result));
        calculationHistory.add(calculation);
        
        // Keep only last 5 calculations
        if (calculationHistory.size() > maxHistorySize) {
            calculationHistory.remove(0);
        }
    }
	
	public void showHistory() {
        if (calculationHistory.isEmpty()) {
            System.out.println("No calculations yet.");
            return;
        }
        
        System.out.println("=== Calculation History ===");
        for (int i = 0; i < calculationHistory.size(); i++) {
            System.out.println((i + 1) + ". " + calculationHistory.get(i));
        }
    }
}