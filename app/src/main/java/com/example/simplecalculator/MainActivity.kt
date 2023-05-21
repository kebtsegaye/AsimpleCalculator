package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var calculationWindow: TextView? = null;
    private var dotButtonPressed: Boolean = false;
    private var isTheLastEnteredIsNumeric : Boolean =false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculationWindow = findViewById(R.id.calculation_window)
    }

    fun onDigitClick(view: View) {
        calculationWindow?.append((view as Button).text)
        isTheLastEnteredIsNumeric = true
        dotButtonPressed = false
        Toast.makeText(this,"Button clicked.",Toast.LENGTH_LONG).show()
    }

    fun onClear(view: View){
        calculationWindow?.text = ""
        dotButtonPressed = false
    }

    fun turnedIntoDecimal(view :View) {
        if (!dotButtonPressed && isTheLastEnteredIsNumeric) {
            calculationWindow?.append(".")
            dotButtonPressed = true;
            isTheLastEnteredIsNumeric = false
        }
    }

    fun onOperator(view : View) {
        calculationWindow?.text?.let {
            if(!isOperatorIsAdded(it.toString()) && isTheLastEnteredIsNumeric) {
                calculationWindow?.append((view as Button).text)
                dotButtonPressed = false;
                isTheLastEnteredIsNumeric = false
            }
        }
    }

    private fun isOperatorIsAdded(value :String):Boolean {
        var result = false
        if ( value.contains("+")
                || value.contains("-")
                || value.contains("x")
                || value.contains("/")
                || value.contains("%")){
            result = true
        }
        return result
    }

    fun onEqualBtnPressed(view :View) {
        if(isTheLastEnteredIsNumeric) {
            var calcWindowValue = calculationWindow?.text.toString()
            try {
                if(calcWindowValue.contains("-")){
                    doOperation(calcWindowValue, "-")
                } else if(calcWindowValue.contains("+")) {
                    doOperation(calcWindowValue, "+")
                } else if(calcWindowValue.contains("*")) {
                    doOperation(calcWindowValue, "*")
                } else if(calcWindowValue.contains("/")) {
                    doOperation(calcWindowValue, "/")
                } else if(calcWindowValue.contains("%")) {
                    doOperation(calcWindowValue, "%")
                } else {
                    Log.e("MainActivity", "Error operating sign is not found.")
                }

            } catch (e: ArithmeticException){
                Toast.makeText(this,"Arthritic error.",Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
            dotButtonPressed = false;
            isTheLastEnteredIsNumeric = false
        }
    }

    private fun doOperation(enteredValue: String, operationSign:String) {
        var splitValue= enteredValue.split(operationSign)
        val splitValueLeft = splitValue[0]
        val splitValueRight = splitValue[1]
        var operatedValue = ""

        when(operationSign) {
            "+" -> operatedValue = (splitValueLeft.toDouble() + splitValueRight.toDouble()).toString()
            "-" -> operatedValue = (splitValueLeft.toDouble() - splitValueRight.toDouble()).toString()
            "*" -> operatedValue = (splitValueLeft.toDouble() * splitValueRight.toDouble()).toString()
            "/" -> operatedValue = (splitValueLeft.toDouble() / splitValueRight.toDouble()).toString()
            "%" -> operatedValue = (splitValueLeft.toDouble() % splitValueRight.toDouble()).toString()
        }

        // removing the .0 of a result value that is shown in our window
        val theLastTwoIndex = operatedValue.substring((operatedValue.length.toInt() -2))
        if(theLastTwoIndex == ".0") {
            calculationWindow?.text = operatedValue.substring(0, operatedValue.length.toInt() -2)
        } else {
            calculationWindow?.text = operatedValue
        }
    }
}