package com.example.quickcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quickcalculator.model.HistoryItem
import com.example.quickcalculator.model.Number
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.regex.Pattern

class MainActivityViewModel : ViewModel() {

    val numberLiveData: LiveData<String> get() = mutableNumber
    private val mutableNumber = MutableLiveData<String>().apply { postValue("") }

    val resultLiveData: LiveData<Number> get() = mutableResult
    private val mutableResult = MutableLiveData<Number>()

    private var appendedString: String = ""

    private var isPendingClosingParenthesis = false


    private val _historyLiveData = MutableLiveData<List<HistoryItem>>()
    val historyLiveData: LiveData<List<HistoryItem>> get() = _historyLiveData

    private val historyList = mutableListOf<HistoryItem>()


    fun evaluateExpression(string: String, clear: Boolean) {
        if (clear) {
            appendedString = ""
            isPendingClosingParenthesis = false
        } else {
            appendedString += if (string == "(") {
                if (isPendingClosingParenthesis) {
                    isPendingClosingParenthesis = false
                    ")$string"
                } else {
                    isPendingClosingParenthesis = true
                    string
                }
            } else {
                if (isPendingClosingParenthesis) {
                    isPendingClosingParenthesis = false
                    ")$string"
                } else {
                    string
                }
            }
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
        val expression = preprocessExpression(number.result)
        val exp = ExpressionBuilder(expression).build()
        val result = exp.evaluate()
        val longResult = result.toLong()
        val resultString = if (result == longResult.toDouble()) longResult.toString() else result.toString()

        mutableResult.value = Number(resultString)
        addHistoryItem(number.result, resultString)
    }





    fun addHistoryItem(expression: String, result: String) {
        historyList.add(HistoryItem(expression, result))
        _historyLiveData.value = historyList.toList()
    }

    fun clearHistory() {
        historyList.clear()
        _historyLiveData.value = historyList.toList()
    }



    // Sake of percentage calculation
//    private fun preprocessExpression(expression: String): String {
//        val regex = Regex("(\\d+(\\.\\d+)?)%")
//        return regex.replace(expression) {
//            val value = it.groupValues[1].toDouble()
//            "(${value / 100})"
//        }
//    }

    private fun preprocessExpression(expression: String): String {
        // Regex to find patterns like "number%" and convert them to "(number/100)"
        val pattern = Pattern.compile("(\\d+(\\.\\d+)?)%")
        val matcher = pattern.matcher(expression)
        val buffer = StringBuffer()

        while (matcher.find()) {
            val number = matcher.group(1)
            matcher.appendReplacement(buffer, "(${number.toDouble() / 100})")
        }
        matcher.appendTail(buffer)

        return buffer.toString()
    }

}
