package com.learning.calculatorapp

import java.text.DecimalFormat
import java.util.*

class ExpressionParse {

    companion object {
        var symbols = hashMapOf<String, Int>("+" to 0, "-" to 0, "%" to 1, "^" to 2, "÷" to 1, "×" to 1)
        var numeric = listOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'e', '.', 'π')
        var result = 0
        var value = Stack<Double>()
        var math = Stack<String>()
        var unit = "Rad"
        fun parse(txt: String): String {

            var text = txt.replace(".", "")
            text = text.replace(",", ".")

            var i = 0;
            var number = StringBuilder()
            var symbols = StringBuilder()
            while (i < text.length) {
                if (text[i] in numeric) {
                    number.append(text[i])
                    if (symbols.length > 0) {
                        math.push(symbols.toString())
                        symbols.clear()
                        evaluate(false)
                    }
                } else {
                    if (ExpressionParse.symbols.containsKey(text[i].toString())) {
                        symbols.append(text[i])
                        if (number.length > 0) {
                            value.push(getValue(number.toString()))
                            number.clear()
                        }
                    } else {
                        if (symbols.length > 0 && ExpressionParse.symbols.containsKey(symbols.toString())) {
                            math.push(symbols.toString())
                            symbols.clear()
                        }
                        if (number.length > 0) {
                            value.push(getValue(number.toString()))
                            number.clear()
                        }
                        symbols.append(text[i])
                        evaluate(false)
                    }
                }
                i++
                if (i == text.length) {
                    if (symbols.length > 0) {
                        math.push(symbols.toString())
                        symbols.clear()
                        evaluate(false)
                    }
                    if (number.length > 0) {
                        value.push(getValue(number.toString()))
                        number.clear()
                    }
                    evaluate(true)
                }
            }

            return modifyResult(value.pop())
        }

        private fun getValue(toString: String): Double? {
            if (toString.equals("e")) return Math.E
            else if (toString.equals("π")) return Math.PI
            return toString.toDouble()
        }

        fun evaluate(isEnded: Boolean) {
            when {
                isEnded -> {
                    while (!math.isEmpty()) {
                        value.push(calculator(value.pop(), value.pop(), math.pop()))
                    }
                }
                math.size == 1 && value.size == 2 && isEnded ->
                        value.push(calculator(value.pop(), value.pop(), math.pop()))
                math.size > 1 && !isEnded-> {
                    var mathSymbol = math.pop()
                    if (mathSymbol.equals(")")) {
                        while (!math.isEmpty() && !isOpenBrace(math.peek())) {
                            value.push(calculator(value.pop(), value.pop(), math.pop()))
                        }
                        var openBrace = math.pop()
                        if (openBrace.length > 1) {
                            value.push(calculator(value.pop(), openBrace))
                        }
                    } else {
                        while (!math.isEmpty() && symbols.containsKey(math.peek()) && symbols.containsKey(mathSymbol) && symbols[mathSymbol]!! < symbols[math.peek()]!!) {
                            value.push(calculator(value.pop(), value.pop(), math.pop()))
                        }
                        math.push(mathSymbol)

                    }
                }

            }

        }

        fun isOpenBrace(string: String): Boolean {
            return string.last().equals('(')
        }

        fun calculator(b: Double, a: Double, mathSymbol: String): Double {
            var result: Double = 0.0
            when (mathSymbol) {
                "+" -> result = a + b
                "-" -> result = a - b
                "%" -> result = a % b
                "÷" -> result = a / b
                "×" -> result = a * b
                "^" -> result = Math.pow(a, b)
            }
            return result
        }

        fun calculator(a: Double, mathSymbol: String): Double {
            var result: Double = 0.0
            when (mathSymbol) {
                "sin(" -> result = if (unit.equals("R")) Math.sin(a) else Math.sin(Math.toRadians(a))
                "cos(" -> result = if (unit.equals("R")) Math.cos(a) else Math.cos(Math.toRadians(a))
                "tan(" -> result = if (unit.equals("R")) Math.tan(a) else Math.tan(Math.toRadians(a))
                "abs(" -> result = Math.abs(a)
                "√(" -> result = Math.sqrt(a)
            }
            return result
        }

        fun modifyResult(a: Double): String {
            val df = DecimalFormat("###,###,###,###,###.#########")
            return df.format(a)
        }
    }

}