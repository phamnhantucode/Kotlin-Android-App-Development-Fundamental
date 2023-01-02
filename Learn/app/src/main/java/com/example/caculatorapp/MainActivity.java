package com.example.caculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private String [] BODMAS_RULE = {"÷", "×", "\\+", "-"};
    private int CURRENT_RULE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView resultHolder = findViewById(R.id.resultHolder);
        final TextView expression = findViewById(R.id.expression);

        final Button cBtn = findViewById(R.id.cBtn);
        final Button zeroBtn = findViewById(R.id.zeroBtn);
        final Button oneBtn = findViewById(R.id.oneBtn);
        final Button twoBtn = findViewById(R.id.twoBtn);
        final Button threeBtn = findViewById(R.id.threeBtn);
        final Button fourBtn = findViewById(R.id.fourBtn);
        final Button fiveBtn = findViewById(R.id.fiveBtn);
        final Button sixBtn = findViewById(R.id.sixBtn);
        final Button sevenBtn = findViewById(R.id.sevenBtn);
        final Button eightBtn = findViewById(R.id.eightBtn);
        final Button nineBtn = findViewById(R.id.nineBtn);
        final Button delBtn = findViewById(R.id.delBtn);
        final Button addBtn = findViewById(R.id.addBtn);
        final Button subBtn = findViewById(R.id.subBtn);
        final Button multiBtn = findViewById(R.id.multiBtn);
        final Button divideBtn = findViewById(R.id.divideBtn);
        final Button equalBtn = findViewById(R.id.equalBtn);
        final Button modulusBtn = findViewById(R.id.modulusBtn);
        final Button dotBtn = findViewById(R.id.dotBtn);



        dotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] nums =expression.getText().toString().split("\n");
                final String numLast = nums[nums.length-1];
                if (!(checkDot(numLast) || checkSign(numLast)))
                    expression.setText(expression.getText().toString() + '.');

            }
        });

        zeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expression.getText().toString() != "")
                    expression.setText(expression.getText().toString() + '0');
            }
        });

        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '1');
            }
        });

        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '2');
            }
        });

        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '3');
            }
        });

        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '4');
            }
        });

        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '5');
            }
        });

        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '6');
            }
        });

        sevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '7');
            }
        });

        eightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '8');
            }
        });

        nineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText(expression.getText().toString() + '9');
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textExpression = expression.getText().toString();
                if (!textExpression.isEmpty()) {
                    expression.setText(textExpression.substring(0, textExpression.length() - 1));
                }
            }
        });

        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression.setText("");
                resultHolder.setText("");
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textExpression = expression.getText().toString();
                if (textExpression != "") {
                    expression.setText(textExpression.substring(0, textExpression.length() -1));
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textExpression = expression.getText().toString();

                if (textExpression.isEmpty()) {
                    expression.setText("0+");
                }
                else {
                    final char getLastFinalCharacter = textExpression.charAt(textExpression.length()-1);
                    if (checkSign(String.valueOf(getLastFinalCharacter))) {
                        expression.setText(textExpression.substring(0, textExpression.length() -1) + '+');

                    } else {
                        expression.setText(textExpression + '+');
                    }
                }
            }
        });

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textExpression = expression.getText().toString();

                if (textExpression.isEmpty()) {
                    expression.setText("0-");
                }
                else {
                    final char getLastFinalCharacter = textExpression.charAt(textExpression.length()-1);

                    if (checkSign(String.valueOf(getLastFinalCharacter))) {
                        expression.setText(textExpression.substring(0, textExpression.length() -1) + '-');
                    } else {
                        expression.setText(textExpression + '-');
                    }
                }
            }
        });

        multiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textExpression = expression.getText().toString();

                if (textExpression.isEmpty()) {
                    expression.setText("0×");
                }
                else {
                    final char getLastFinalCharacter = textExpression.charAt(textExpression.length()-1);

                    if (checkSign(String.valueOf(getLastFinalCharacter))) {
                        expression.setText(textExpression.substring(0, textExpression.length() -1) + '×');
                    } else {
                        expression.setText(textExpression + '×');
                    }
                }
            }
        });

        divideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textExpression = expression.getText().toString();

                if (textExpression.isEmpty()) {
                    expression.setText("0÷");
                }
                else {
                    final char getLastFinalCharacter = textExpression.charAt(textExpression.length()-1);

                    if (checkSign(String.valueOf(getLastFinalCharacter))) {
                        expression.setText(textExpression.substring(0, textExpression.length() -1) + '÷');
                    } else {
                        expression.setText(textExpression + '÷');
                    }
                }
            }
        });

        equalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String textExpression = expression.getText().toString();

                if (containSign(textExpression)) {
                    final char getLastCharacter = textExpression.charAt(textExpression.length() -1);
                    if (!checkSign(String.valueOf(getLastCharacter))) {
                        calculateResult(expression, resultHolder, textExpression);
                    }

                }
            }
        });

    }

    private boolean containSign(String textExpression) {
        return (textExpression.contains("+") || textExpression.contains("-") || textExpression.contains("×") || textExpression.contains("÷"));
    }

    protected boolean checkSign(String value) {
        return (value.equals("+") || value.equals("-") || value.equals("×") || value.equals("÷"));
    }

    protected boolean checkDot(String string) {
        return string.contains(".");
    }

    private void calculateResult(TextView expression, TextView resultHolder, String textExpression) {
        String textExpression2 = textExpression;

        while (true) {
            if (CURRENT_RULE == 2 && !textExpression2.contains("+")) {
                CURRENT_RULE ++;
                continue;
            }
            else if (CURRENT_RULE != 2 && !textExpression2.contains(BODMAS_RULE[CURRENT_RULE])) {
                if (CURRENT_RULE == 3) break;
                else {
                    CURRENT_RULE ++;
                    continue;
                }
            }

            String [] expressionArray = textExpression2.split(BODMAS_RULE[CURRENT_RULE]);
            if (expressionArray.length == 1 || expressionArray[0].isEmpty()) {
                break;
            }

            StringBuilder firstValue = new StringBuilder();
            StringBuilder secondValue = new StringBuilder();

            for (int i = expressionArray[0].length() -1; i >=0 ; i--) {
                if (checkSign(String.valueOf(expressionArray[0].charAt(i)))) break;
                else {
                    firstValue.insert(0, expressionArray[0].charAt(i));
                }
            }

            for (int i = 0; i < expressionArray[1].length(); i++) {
                if (checkSign(String.valueOf(expressionArray[1].charAt(i)))) break;
                else {
                    secondValue.append(expressionArray[1].charAt(i));
                }
            }

            double results;
            switch (BODMAS_RULE[CURRENT_RULE]) {
                case "÷":
                    results = Double.parseDouble(firstValue.toString()) / Double.parseDouble(secondValue.toString());
                    break;
                case "×":
                    results = Double.parseDouble(firstValue.toString()) * Double.parseDouble(secondValue.toString());
                    break;
                case "\\+":
                    results = Double.parseDouble(firstValue.toString()) + Double.parseDouble(secondValue.toString());
                    break;
                case "-":
                    results = Double.parseDouble(firstValue.toString()) - Double.parseDouble(secondValue.toString());
                    break;
                default:
                    results = 0;
                    break;
            }

            textExpression2 = textExpression2.replaceFirst(firstValue + BODMAS_RULE[CURRENT_RULE] + secondValue, String.valueOf(results));
//            System.out.println(textExpression);
        }

        CURRENT_RULE = 0;
        resultHolder.setText(textExpression);
        String [] finalResultArray = textExpression2.split("\\.");
        if (finalResultArray[1].length() == 1 && finalResultArray[1].equals("0")) {
            expression.setText(finalResultArray[0]);
        } else {
            expression.setText(textExpression2);
        }

    }
}