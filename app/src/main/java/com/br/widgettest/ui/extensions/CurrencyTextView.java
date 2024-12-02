package com.br.widgettest.ui.extensions;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.br.widgettest.R;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by Breno on 1/23/2016.
 */
public class CurrencyTextView extends android.support.v7.widget.AppCompatTextView {
    private NumberFormat nf = NumberFormat.getCurrencyInstance();
    {
        nf.setParseIntegerOnly(false);
    }

    public CurrencyTextView(Context context) {
        super(context);
    }

    public CurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            Number number = nf.parse(getText().toString());
            if (number.doubleValue() >= 0) {
                setTextColor(getContext().getResources().getColor(R.color.currency_positive));
            } else {
                setTextColor(getContext().getResources().getColor(R.color.currency_negative));
            }
        } catch (ParseException e) {
            Log.d(getClass().getName(), "Can't parse this text: " + getText(), e);
        }

        super.onDraw(canvas);
    }
}
