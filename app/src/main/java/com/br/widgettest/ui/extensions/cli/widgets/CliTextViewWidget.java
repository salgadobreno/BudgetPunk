package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import com.br.widgettest.ui.extensions.cli.BaseCliTextView;
import com.br.widgettest.ui.extensions.cli.CliBaseWidget;

public class CliTextViewWidget extends CliBaseWidget<CliTextViewWidget.V> {
    public enum Alignment {
        Start(Gravity.START),
        Center(Gravity.CENTER),
        End(Gravity.END);

        private int orientation;

        Alignment(int orientation) {
            this.orientation = orientation;
        }

        public int getOrientation() {
            return orientation;
        }
    }

    private CharSequence text;

    public CliTextViewWidget(Context context, CharSequence text) {
        super(V.class, context, text);
        this.text = text;
    }

    @Override
    public String cliView() {
        String rep = "| #                             |\n";

        return SCStringFormat.format(SCStringFormat.Type.ChangeInPlace, rep, this.text.toString());
    }

    public static class V extends BaseCliTextView {

        public V(Context context, CharSequence text) {
            this(context, text, Alignment.Start);
        }

        public V(Context context, CharSequence text, Alignment alignment) {
            this(context, text, alignment, null);
        }

        public V(Context context, CharSequence text, Alignment alignment, AttributeSet attrs) {
            super(context, attrs);
            setText(text);
            setGravity(alignment.getOrientation());
        }
    }
}

