package com.santhos.truelocker.customComponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.StyleableRes;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.santhos.truelocker.R;


public class RollingTextView extends LinearLayout {

    @StyleableRes
    int number = 0;

    String numberToBeSplited = null;

    TickerView num1 , num2 , num3 , num4;
    public RollingTextView(Context context , AttributeSet attrs ) {
        super(context , attrs);
        init(context , attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.rolling_text_view, this);
        int[] sets = {R.attr.otp};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
        numberToBeSplited = String.valueOf(typedArray.getText(number));
        String[] numbers = numberToBeSplited.split("");
        initComponents();
        num1.setCharacterLists(TickerUtils.provideNumberList());
        num2.setCharacterLists(TickerUtils.provideNumberList());
        num3.setCharacterLists(TickerUtils.provideNumberList());
        num4.setCharacterLists(TickerUtils.provideNumberList());

        num1.setText(numbers[1]);
        num2.setText(numbers[2]);
        num3.setText(numbers[3]);
        num4.setText(numbers[4]);



    }


    private void initComponents() {
        num1 =  findViewById(R.id.num1);
        num2 =  findViewById(R.id.num2);
        num3 =  findViewById(R.id.num3);
        num4 =  findViewById(R.id.num4);
    }

    public void setNumber(CharSequence number) {
        numberToBeSplited = String.valueOf(number);
        String[] numbers = numberToBeSplited.split("");

        num1.setText(numbers[1]);
        num2.setText(numbers[2]);
        num3.setText(numbers[3]);
        num4.setText(numbers[4]);
    }

}
