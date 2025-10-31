package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonMinus, buttonPlus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        buttonC = assignId(R.id.button_c);
        buttonBrackOpen = assignId(R.id.button_open_bracket);
        buttonBrackClose = assignId(R.id.button_close_bracket);
        buttonDivide = assignId(R.id.button_divide);
        buttonMultiply = assignId(R.id.button_multiply);
        buttonMinus = assignId(R.id.button_minus);
        buttonPlus = assignId(R.id.button_plus);
        button0 = assignId(R.id.button_0);
        button1 = assignId(R.id.button_1);
        button2 = assignId(R.id.button_2);
        button3 = assignId(R.id.button_3);
        button4 = assignId(R.id.button_4);
        button5 = assignId(R.id.button_5);
        button6 = assignId(R.id.button_6);
        button7 = assignId(R.id.button_7);
        button8 = assignId(R.id.button_8);
        button9 = assignId(R.id.button_9);
        buttonAC = assignId(R.id.button_ac);
        buttonDot = assignId(R.id.button_dot);
        buttonEquals = assignId(R.id.button_equal);
    }

    private MaterialButton assignId(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
        return btn;
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        switch (buttonText) {
            case "AC":
                solutionTv.setText("");
                resultTv.setText("0");
                return;

            case "=":
                solutionTv.setText(resultTv.getText());
                return;

            case "C":
                if (!dataToCalculate.isEmpty()) {
                    dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                }
                break;

            default:
                dataToCalculate += buttonText;
        }

        solutionTv.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }

    private String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1); // cần cho Android (tránh crash do JIT)
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        } finally {
            Context.exit();
        }
    }
}
