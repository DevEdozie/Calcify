package com.example.calcify

import androidx.lifecycle.ViewModel

class MainActivityViewModel:ViewModel() {

     var newOperation = false
     var operand1 = 0.0
     var operand2 = 0.0
     var operator = ""
     var result = 0.0
    // Tester
    var counter = 0
    // Current
    var currentOperand = 0.0
    var currentOperation = "None"
    var isDigitClicked = false
    var isOperatorClicked = false

    fun incrementCounter(){
        counter++
    }

    fun performOperation(operator: String): Double{
        when(operator){
            "+" -> result = operand1 + operand2
            "-" -> result = operand1 - operand2
            "*" -> result = operand1 * operand2
            "%" -> result = operand1 % operand2
            "/" -> result = operand1 / operand2
        }
        return result
    }

}