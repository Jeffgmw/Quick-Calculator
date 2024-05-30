package com.example.quickcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickcalculator.model.Number
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivityViewModel : ViewModel() {

    val numberLiveData: LiveData<String> get() = mutableNumber
    private val mutableNumber = MutableLiveData<String>().apply { postValue("") }

    val resultLiveData: LiveData<Number> get() = mutableResult
    private val mutableResult = MutableLiveData<Number>()

    private var appendedString: String = ""

    fun evaluateExpression(string: String, clear: Boolean) {
        if (clear) {
            appendedString = ""
        } else {
            appendedString += string
        }
        mutableNumber.value = appendedString
    }


    fun deleteLastCharacter() {
        if (appendedString.isNotEmpty()) {
            appendedString = appendedString.dropLast(1)
            mutableNumber.value = appendedString
        }
    }

    fun calculateEquals(number: Number) {
        val expression = ExpressionBuilder(number.result).build()
        val result = expression.evaluate()
        val longResult = result.toLong()

        mutableResult.value = if (result == longResult.toDouble()) {
            Number(longResult.toString())
        } else {
            Number(result.toString())
        }
    }

}
