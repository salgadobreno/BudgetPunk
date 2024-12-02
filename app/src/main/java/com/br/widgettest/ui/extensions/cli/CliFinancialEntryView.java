package com.br.widgettest.ui.extensions.cli;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.br.widgettest.ui.extensions.cli.widgets.CliTextViewWidget;

public class CliFinancialEntryView extends LinearLayout {
    private final CliTextViewWidget.V leftTv;
    private final CliTextViewWidget.V rightTv;

    public CliFinancialEntryView(Context context, String leftStr, String rightStr) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        );

        leftTv = new CliTextViewWidget.V(context, leftStr);
        leftTv.setLayoutParams(new LayoutParams(
                new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f)
        ));
        leftTv.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);

        rightTv = new CliTextViewWidget.V(context, rightStr);
        rightTv.setLayoutParams(new LayoutParams(
                new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f)
        ));
        rightTv.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);

        addView(leftTv);
        addView(rightTv);
    }
}
