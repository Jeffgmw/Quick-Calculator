package com.example.quickcalculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.quickcalculator.utils.Constants
import com.example.quickcalculator.model.Number
import com.example.quickcalculator.databinding.ActivityMainBinding
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
            tvOne.setOnClickListener {
                evaluateExpression(Constants.Value.ONE)
            }

            tvTwo.setOnClickListener {
                evaluateExpression(Constants.Value.TWO)
            }

            tvThree.setOnClickListener {
                evaluateExpression(Constants.Value.THREE)
            }
            tvFour.setOnClickListener {
                evaluateExpression(Constants.Value.FOUR)
            }

            tvFive.setOnClickListener {
                evaluateExpression(Constants.Value.FIVE)
            }

            tvSix.setOnClickListener {
                evaluateExpression(Constants.Value.SIX)
            }

            tvSeven.setOnClickListener {
                evaluateExpression(Constants.Value.SEVEN)
            }

            tvEight.setOnClickListener {
                evaluateExpression(Constants.Value.EIGHT)
            }

            tvNine.setOnClickListener {
                evaluateExpression(Constants.Value.NINE)
            }

            tvZero.setOnClickListener {
                evaluateExpression(Constants.Value.ZERO)
            }

            /*Operators*/

            tvPlus.setOnClickListener {
                evaluateExpression(Constants.Expression.ADD)
            }

            tvMinus.setOnClickListener {
                evaluateExpression(Constants.Expression.SUBSTRACT)
            }

            tvMul.setOnClickListener {
                evaluateExpression(Constants.Expression.MULTIPLE)
            }

            tvDivide.setOnClickListener {
                evaluateExpression(Constants.Expression.DIVIDE)
            }

            tvDot.setOnClickListener {
                evaluateExpression(Constants.Expression.DOT)
            }

            tvPercent.setOnClickListener {
                evaluateExpression(Constants.Expression.PERCENTAGE)
            }

            tvClear.setOnClickListener {
                viewModel.evaluateExpression("", true)
                tvExpression.text = ""
                tvResult.text = ""
            }

            tvEquals.setOnClickListener {
                val text = tvExpression.text.toString()
                viewModel.calculateEquals(Number(text))
            }

            tvDel.setOnClickListener {
                viewModel.deleteLastCharacter()
                tvResult.text = ""
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
                tvResult.text = ""
                tvExpression.text = number
            }
        }

        viewModel.resultLiveData.observe(this) { result ->
            binding.tvResult.text = result.result
        }
    }

}
