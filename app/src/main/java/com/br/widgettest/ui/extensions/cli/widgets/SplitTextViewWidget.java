package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;
import android.widget.LinearLayout;

import com.br.widgettest.ui.extensions.ColoredText;
import com.br.widgettest.ui.extensions.cli.CliBaseWidget;

import static com.br.widgettest.ui.extensions.cli.widgets.SCStringFormat.Type.*;

public class SplitTextViewWidget extends CliBaseWidget<SplitTextViewWidget.V> {
    private CharSequence left;
    private CharSequence right;

    public SplitTextViewWidget(Context context, CharSequence left, CharSequence right) {
        super(V.class, context, left, right);

        this.left = left;
        this.right = right;
    }

    @Override
    public String cliView() {
        String rep = "| #                           # |\n";
        rep = SCStringFormat.format(ChangeInPlace, rep, left.toString());
        rep = SCStringFormat.format(ChangeFromPlace, rep, right.toString());

        return rep;
    }

    public static class V extends LinearLayout {

        public V(Context context, String left, String right) {
            this(context, ((CharSequence) left), ((CharSequence) right));
        }

        public V(Context context, CharSequence left, CharSequence right) {
            super(context);
            setOrientation(HORIZONTAL);
            LinearLayout.LayoutParams lp = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT,
                    0.5f
            );
            CliTextViewWidget.V leftTv = new CliTextViewWidget.V(context, left, CliTextViewWidget.Alignment.Start);
            CliTextViewWidget.V rightTv = new CliTextViewWidget.V(context, right, CliTextViewWidget.Alignment.End);

            leftTv.setLayoutParams(lp);
            rightTv.setLayoutParams(lp);

            addView(leftTv);
            addView(rightTv);
        }
    }

}
