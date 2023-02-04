package com.learning.calculatorapp

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.*
import androidx.core.view.allViews
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var expression: TextView? = null
    var resultHolder: TextView? = null
    var numOfOpenBracket = 0;
    var numOfCloseBracket = 0;

    var currentNumber = StringBuilder()
    var symbols = listOf<String>("+", "-", "%", "^", "√", "÷", "×", "(", "()")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListenerBtn()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("unit", findViewById<TextView>(R.id.unitBtn).text.toString())
        outState.putCharSequence("expression", expression!!.text)
        outState.putCharSequence("resultHolder", resultHolder!!.text)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        findViewById<TextView>(R.id.unitBtn).text = savedInstanceState.getString("unit")
        expression!!.text = savedInstanceState.getCharSequence("expression")
        resultHolder!!.text = savedInstanceState.getCharSequence("resultHolder")
    }

    fun initListenerBtn() {
        expression = findViewById(R.id.expression)
        resultHolder = findViewById(R.id.resultHolder)
        findViewById<ImageButton>(R.id.backspaceBtn)?.setOnClickListener {
            when {
                expression!!.text.length ==  1 -> {
                    clearExpression()
                }
                expression!!.text[expression!!.text.length - 1].equals('(') -> {
                    expression!!.text = expression!!.text.subSequence(0, expression!!.text.length - 1)
                    while (expression!!.text.length > 1 && !expression!!.text[expression!!.text.length - 1].equals('(') && (!isNumber(expression!!.text[expression!!.text.length - 1]) || !isSymbol(expression!!.text[expression!!.text.length - 1].toString())))  {
                        expression!!.text = expression!!.text.subSequence(0, expression!!.text.length - 1)
                    }
                    if (expression!!.text.length ==  1) {
                        clearExpression()
                    }
                }
                else -> {
                    expression!!.text = expression!!.text.subSequence(0, expression!!.text.length - 1)
                }
            }
        }
        findViewById<TextView>(R.id.unitBtn).setOnClickListener {
            when ((it as TextView).text) {
                "Rad" -> {
                    it.setText("Deg")
                    ExpressionParse.unit = "R"
                }
                "Deg" -> {
                    it.setText("Rad")
                    ExpressionParse.unit = "D"
                }
            }
        }
        findViewById<ImageButton>(R.id.switchOrientationBtn).setOnClickListener {
            changeOrientation()
        }
        findViewById<LinearLayout>(R.id.listBtn)?.allViews?.forEach {
            if (it is Button) {
                it.setOnClickListener {
                    onClickBtn(it as Button)
                }
            }
        }
    }

    fun onClickBtn(button: Button) {
        when (button.text) {
            "0" -> {
                if (!isSymbol(expression!!.text[expression!!.text.length-1].toString()) && !(expression!!.text.get(expression!!.text.length-1).equals('0') && expression!!.text.length == 1))
                    expression!!.text = expression?.text.toString() + button.text
            }
            "ce" -> {
                clearExpression()
            }
            "c" -> {
                clearExpression()
                clearResultHolder()
            }
            "^" -> {
                if (!(isSymbol(expression!!.text[expression!!.text.length-1].toString()) || isCleaned())) {
                    expression!!.text = expression!!.text.toString() + "^"
                }
            }
            "+" -> {
                when {
                    !isSymbol(expression!!.text[expression!!.text.length-1].toString()) -> {
                        expression!!.text = expression!!.text.toString() + "+"
                    }
                }
            }
            "√","sin", "cos", "tan", "log"  -> {
                when {
                    isCleaned() -> {
                        expression!!.text = button.text.toString() + "("
                        numOfOpenBracket++
                    }
                    isNumber(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text =
                            expression!!.text.toString() + "×" + button.text.toString() + "("
                        numOfOpenBracket++
                    }
                    isMathSymbols(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text =
                            expression!!.text.toString() + button.text.toString() + "("
                        numOfOpenBracket++
                    }
                }
            }
            "|x|" -> {
                when {
                    isCleaned() -> {
                        expression!!.text = "abs" + "("
                        numOfOpenBracket++
                    }
                    isNumber(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text =
                            expression!!.text.toString() + "×" + "abs" + "("
                        numOfOpenBracket++
                    }
                    isMathSymbols(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text =
                            expression!!.text.toString() + "abs" + "("
                        numOfOpenBracket++
                    }
                }
            }
            "1/x" -> {
                when {
                    isCleaned() -> {
                        expression!!.text = "1÷"
                        numOfOpenBracket++
                    }
                    isNumber(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text =
                            expression!!.text.toString() + "×" + "1÷"
                        numOfOpenBracket++
                    }
                    isMathSymbols(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text =
                            expression!!.text.toString() + "1÷"
                        numOfOpenBracket++
                    }
                }
            }
            "-" -> {
                when {
                    isCleaned() -> expression!!.text =button.text
                    !isSymbol(expression!!.text[expression!!.text.length-1].toString()) -> {
                        expression!!.text = expression!!.text.toString() + button.text
                    }
                }
            }
            "×", "÷", "%" -> {
                if (isCleaned()) {
                    illegal()
                }
                else if (!isSymbol(expression!!.text[expression!!.text.length-1].toString())) {
                    expression!!.text = expression!!.text.toString() + button.text
                }
            }
            "," -> {
                when {
                    isCleaned() || currentNumber.indexOf(',') < 0 -> {
                        expression!!.text = expression!!.text.toString() + button.text
                    }

                }
            }
            "()" -> {
                when {
                    isCleaned() -> {
                        numOfOpenBracket++
                        expression!!.text = "("
                    }
                    isSymbol(expression!!.text[expression!!.text.length-1].toString()) && !(expression!!.text[expression!!.text.length-1].equals('^')) -> {
                        numOfOpenBracket++
                        expression!!.text = expression!!.text.toString() + "("
                    }
                    numOfCloseBracket < numOfOpenBracket && isNumber(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text = expression!!.text.toString() + ")"
                        numOfCloseBracket++
                    }
                }
            }
            "e" -> {
                when {
                    isCleaned() -> {
                        expression!!.text = "e"
                    }
                    isNumber(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text = expression!!.text.toString() + "×e"
                    }
                    else -> {
                        expression!!.text = expression!!.text.toString() + "e"
                    }
                }
            }
            "π" -> {
                when {
                    isCleaned() -> {
                        expression!!.text =  "π"
                    }
                    isNumber(expression!!.text[expression!!.text.length-1]) -> {
                        expression!!.text = expression!!.text.toString() + "×π"
                    }
                    else -> {
                        expression!!.text = expression!!.text.toString() + "π"
                    }
                }
            }

            "=" -> {
                if (numOfCloseBracket == numOfOpenBracket && (isNumber(expression!!.text[expression!!.text.length-1]) || expression!!.text[expression!!.text.length-1].equals(')'))) {
                    resultHolder!!.text = expression?.text.toString()
                    expression!!.text = ExpressionParse.parse(expression?.text.toString())
                } else {
                    illegal()
                }
            }
            else -> {
                if (button.text[0] in '0'..'9' || button.text[0].equals(',')) {

                    if (currentNumber.indexOf(',') >= 0) {
                        if (currentNumber.subSequence(0, currentNumber.indexOf(',')).length > 12) {
                            illegal("Number illegal!")
                        } else {
                            currentNumber.append(button.text)
                            if (expression!!.text.length == 1 && expression!!.text.toString().equals("0"))
                                expression!!.text = button.text
                            else expression!!.text = expression?.text.toString() + button.text
                        }
                    } else {
                        if (currentNumber.length > 12) {
                            illegal("Number illegal!")
                        } else {
                            currentNumber.append(button.text)
                            if (expression!!.text.length == 1 && expression!!.text.toString().equals("0"))
                                expression!!.text = button.text
                            else expression!!.text = expression?.text.toString() + button.text
                        }
                    }

                } else {
                    if (expression!!.text.length == 1 && expression!!.text.toString().equals("0"))
                        expression!!.text = button.text
                    else expression!!.text = expression?.text.toString() + button.text
                    currentNumber.clear()
                }
            }
        }

    }

    private fun illegal(string: String? = "Illegal") {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun isMathSymbols(c: Char): Boolean {
        return c.equals('+') || c.equals('-') || c.equals("÷") || c.equals("×") || c.equals("%") || c.equals("^")
    }

    fun isSymbol(string: String): Boolean {
        if (symbols.indexOf(string) >= 0)
            return true
        else return false
    }

    fun isNumber(char: Char): Boolean {
        if (char in ExpressionParse.numeric)
            return true
        return  false
    }

    fun isCalculated(): Boolean {
        if (expression!!.text.length == 1 && expression!!.text[0].equals('0'))
            return false
        else return true

        if (isSymbol(expression!!.text[expression!!.text.length-1].toString()))
            return false
        return true
    }

    fun isCleaned(): Boolean {
        if (expression!!.text.length == 1 && expression!!.text[0].equals('0'))
            return true
        else return false
    }


    fun clearExpression() {
        expression!!.text = "0"
    }

    fun clearResultHolder() {
        resultHolder!!.text = ""
    }

    fun changeOrientation() {
        when (resources.configuration.orientation) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT -> {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            else -> {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }
}