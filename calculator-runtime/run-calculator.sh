#!/bin/bash
cd "$(dirname "$0")"
./bin/java -cp simple-calculator-1.0.0.jar com.calculator.CalculatorApp "$@"
