package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import com.br.widgettest.ui.extensions.cli.CliBaseWidget;

public class CenteredTextViewWidget extends CliBaseWidget<CenteredTextViewWidget.CenteredTextView> {
    private CharSequence text;

    public CenteredTextViewWidget(Context context, CharSequence text) {
        super(CenteredTextViewWidget.CenteredTextView.class, context, text);

        this.text = text;
    }

    @Override
    public String cliView() {
        String baseRep = "|               #               |\n";

        return SCStringFormat.format(SCStringFormat.Type.ChangeFromMiddle, baseRep, text.toString());
    }

    public static class CenteredTextView extends CliTextViewWidget.V {

        public CenteredTextView(Context context, CharSequence text) {
            super(context, text, CliTextViewWidget.Alignment.Center);
        }
    }
}


