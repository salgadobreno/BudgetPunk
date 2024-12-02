package com.br.widgettest.ui.extensions.cli;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import static android.view.Gravity.CENTER;

//public class BaseCliTextView extends AppCompatTextView implements CliBaseWidget {
public abstract class BaseCliTextView extends AppCompatTextView {

    public BaseCliTextView(Context context) {
        this(context, null);
    }

    public BaseCliTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public BaseCliTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
//        requestLayout();
        setTextSize(17);
//        setLayoutParams(new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        ));
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
        setLineSpacing(-10, -2.5f);
//        setTextAlignment(TEXT_ALIGNMENT_CENTER);
//        setGravity(CENTER);
    }

//    abstract String cliView();

//    @Override
//    public String toStringRepresentation() {
//        return "|                             |";
//    }
}
