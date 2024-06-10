package com.example.quickcalculator.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quickcalculator.databinding.ActivityMainBinding
import com.example.quickcalculator.model.Number
import com.example.quickcalculator.utils.Constants
import com.example.quickcalculator.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViewModel()
        observableLiveData()

        with(binding){

            btOne.setOnClickListener {
                evaluateExpression(Constants.Value.ONE)
            }

            btTwo.setOnClickListener {
                evaluateExpression(Constants.Value.TWO)
            }

            btThree.setOnClickListener {
                evaluateExpression(Constants.Value.THREE)
            }
            btFour.setOnClickListener {
                evaluateExpression(Constants.Value.FOUR)
            }

            btFive.setOnClickListener {
                evaluateExpression(Constants.Value.FIVE)
            }

            btSix.setOnClickListener {
                evaluateExpression(Constants.Value.SIX)
            }

            btSeven.setOnClickListener {
                evaluateExpression(Constants.Value.SEVEN)
            }

            btEight.setOnClickListener {
                evaluateExpression(Constants.Value.EIGHT)
            }

            btNine.setOnClickListener {
                evaluateExpression(Constants.Value.NINE)
            }

            btZero.setOnClickListener {
                evaluateExpression(Constants.Value.ZERO)
            }

            /*Operators*/

            btPlus.setOnClickListener {
                evaluateExpression(Constants.Expression.ADD)
            }

            btMinus.setOnClickListener {
                evaluateExpression(Constants.Expression.SUBSTRACT)
            }

            btMul.setOnClickListener {
                evaluateExpression(Constants.Expression.MULTIPLE)
            }

            btDivide.setOnClickListener {
                evaluateExpression(Constants.Expression.DIVIDE)
            }

            btDot.setOnClickListener {
                evaluateExpression(Constants.Expression.DOT)
            }

            btPercent.setOnClickListener {
                evaluateExpression(Constants.Expression.PERCENTAGE)
            }

            btBrackets.setOnClickListener {
                evaluateExpression(Constants.Expression.BRACKETS)
            }

            btClear.setOnClickListener {
                viewModel.evaluateExpression("", true)
                btExpression.text = ""
                btResult.text = ""
            }

            btEquals.setOnClickListener {
                val text = btExpression.text.toString()
                viewModel.calculateEquals(Number(text))
            }

            btDel.setOnClickListener {
                viewModel.deleteLastCharacter()
                btResult.text = ""
            }
        }
    }

    //Function to calculate the expressions using expression builder library
    private fun evaluateExpression(string: String) {
        viewModel.evaluateExpression(string, false)
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private fun observableLiveData() {
        viewModel.numberLiveData.observe(this) { number ->
            with(binding) {
                btResult.text = ""
                btExpression.text = number
            }
        }

        viewModel.resultLiveData.observe(this) { result ->
            binding.btResult.text = result.result
        }
    }
}
