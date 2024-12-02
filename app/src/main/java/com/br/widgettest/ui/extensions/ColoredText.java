package com.br.widgettest.ui.extensions;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class ColoredText extends SpannableString {

    public enum ColorCode {
        Info(Color.BLUE),
        Neutral(Color.LTGRAY),
        Negative(Color.RED),
        Positive(Color.GREEN);

        private final int color;

        ColorCode(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }

    private final ColorCode colorCode;

    public ColoredText(CharSequence text, ColorCode colorCode) {
        super(text);

        this.colorCode = colorCode;
        setSpan(new ForegroundColorSpan(colorCode.getColor()), 0, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
    }
}
