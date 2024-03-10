package com.example.calcify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.calcify.databinding.ActivityMainBinding
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var btnSix: Button
    private lateinit var btnSeven: Button
    private lateinit var btnEight: Button
    private lateinit var btnNine: Button
    private lateinit var btnZero: Button
    private lateinit var btnAdd: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnModulus: Button
    private lateinit var btnClear: Button
    private lateinit var btnEquals: Button
    private lateinit var resultTv: TextView
    private lateinit var tvCurrentOperator: TextView

    // Variables
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        btnOne = binding.btnOne
        btnTwo = binding.btnTwo
        btnThree = binding.btnThree
        btnFour = binding.btnFour
        btnFive = binding.btnFive
        btnSix = binding.btnSix
        btnSeven = binding.btnSeven
        btnEight = binding.btnEight
        btnNine = binding.btnNine
        btnZero = binding.btnZero
        btnAdd = binding.btnAdd
        btnMultiply = binding.btnMultiply
        btnDivide = binding.btnDivide
        btnSubtract = binding.btnSubtract
        btnModulus = binding.btnModulus
        btnClear = binding.btnClear
        btnEquals = binding.btnEquals
        resultTv = binding.resultTv
        tvCurrentOperator = binding.tvCurrentOperator

        // Setup ViewModel
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        // Set Listener for digits
        setDigitClickListener()
        // Set Listener for operators
        setOperatorListener()
        // Set Listener for Equals sign
        setEqualsListener()
        // Set Clear Button Listener
        setClearButtonListener()
        // Authentication
        viewModel.incrementCounter()
        if (viewModel.counter > 1) {
            if (viewModel.isDigitClicked) {
                if (viewModel.isOperatorClicked) {
                    resultTv.hint = "Enter Second operand"
                } else {
                    resultTv.text = viewModel.currentOperand.toInt().toString()
                }

            }

            if (viewModel.currentOperation != "None") {
                tvCurrentOperator.text = viewModel.currentOperation
            }
            Log.i("MainActivity", "Current Operator:${viewModel.currentOperation} ")
        }
        Log.i("MainActivity", "Counter: ${viewModel.counter}")
        Log.i("MainActivity", "newOperation: ${viewModel.newOperation}")
        Log.i("MainActivity", "Operand1: ${viewModel.operand1}")
        Log.i("MainActivity", "Operand2: ${viewModel.operand2}")
        Log.i("MainActivity", "Result: ${viewModel.result}")
    }


    private fun setUpUi() {}


    private fun setDigitClickListener() {
        val digitButtons = arrayListOf(
            btnOne,
            btnTwo,
            btnThree,
            btnFour,
            btnFive,
            btnSix,
            btnSeven,
            btnEight,
            btnNine,
            btnZero
        )

        digitButtons.forEach { button ->
            button.setOnClickListener {
                viewModel.isDigitClicked = true
                val digit = button.text.toString().toInt()
                Log.i("MainActivity", "digit:$digit ")
                if (viewModel.newOperation) {
                    resultTv.text = ""
                    viewModel.newOperation = false
                }
                val currentText = resultTv.text.toString()
                resultTv.text = currentText + digit.toString()
                // Save
                viewModel.currentOperand = resultTv.text.toString().toDouble()
            }
        }
    }

    private fun setOperatorListener() {
        val operators = arrayListOf(btnAdd, btnDivide, btnSubtract, btnMultiply, btnModulus)

        operators.forEach { button ->
            button.setOnClickListener {
                viewModel.isOperatorClicked = true
                viewModel.operand1 = resultTv.text.toString().toDouble()
                viewModel.operator = button.text.toString()
                resultTv.text = ""
                resultTv.hint = "Enter Second operand"
                tvCurrentOperator.text = "Sign: ${viewModel.operator}"
                // Save
                viewModel.currentOperation = tvCurrentOperator.text.toString()
            }
        }
    }

    private fun setEqualsListener() {
        btnEquals.setOnClickListener {
            viewModel.operand2 = resultTv.text.toString().toDouble()
//            when(viewModel.operator){
//                "+" -> viewModel.result = viewModel.operand1 + viewModel.operand2
//                "-" -> viewModel.result = viewModel.operand1 - viewModel.operand2
//                "*" -> viewModel.result = viewModel.operand1 * viewModel.operand2
//                "%" -> viewModel.result = viewModel.operand1 % viewModel.operand2
//                "/" -> viewModel.result = viewModel.operand1 / viewModel.operand2
//            }
            //val resultString = "$operand1 $operator $operand2 = $result"
//            resultTv.text = viewModel.result.toInt().toString()
            resultTv.text = viewModel.performOperation(viewModel.operator).toInt().toString()
            viewModel.newOperation = true
            // Save
            viewModel.currentOperand = resultTv.text.toString().toDouble()
        }

    }

    private fun setClearButtonListener() {
        btnClear.setOnClickListener {
            resultTv.text = ""
            viewModel.operand1 = 0.0
            viewModel.operand2 = 0.0
            viewModel.newOperation = true
            resultTv.hint = "Enter First Operand"
            viewModel.isDigitClicked = false
            viewModel.isOperatorClicked = false
        }
    }


}