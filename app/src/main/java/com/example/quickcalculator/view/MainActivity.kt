package com.example.quickcalculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.quickcalculator.constants.Constant
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
                evaluateExpression(Constant.Value.ONE)
            }

            tvTwo.setOnClickListener {
                evaluateExpression(Constant.Value.TWO)
            }

            tvThree.setOnClickListener {
                evaluateExpression(Constant.Value.THREE)
            }
            tvFour.setOnClickListener {
                evaluateExpression(Constant.Value.FOUR)
            }

            tvFive.setOnClickListener {
                evaluateExpression(Constant.Value.FIVE)
            }

            tvSix.setOnClickListener {
                evaluateExpression(Constant.Value.SIX)
            }

            tvSeven.setOnClickListener {
                evaluateExpression(Constant.Value.SEVEN)
            }

            tvEight.setOnClickListener {
                evaluateExpression(Constant.Value.EIGHT)
            }

            tvNine.setOnClickListener {
                evaluateExpression(Constant.Value.NINE)
            }

            tvZero.setOnClickListener {
                evaluateExpression(Constant.Value.ZERO)
            }

            /*Operators*/

            tvPlus.setOnClickListener {
                evaluateExpression(Constant.Expression.ADD)
            }

            tvMinus.setOnClickListener {
                evaluateExpression(Constant.Expression.SUBSTRACT)
            }

            tvMul.setOnClickListener {
                evaluateExpression(Constant.Expression.MULTIPLE)
            }

            tvDivide.setOnClickListener {
                evaluateExpression(Constant.Expression.DIVIDE)
            }

            tvDot.setOnClickListener {
                evaluateExpression(Constant.Expression.DOT)
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

            tvBack.setOnClickListener {
                viewModel.deleteLastCharacter()
                tvResult.text = ""
            }
        }
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

    //Function to calculate the expressions using expression builder library
    private fun evaluateExpression(string: String) {
        viewModel.evaluateExpression(string, false)
    }

}
