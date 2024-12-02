package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;
import android.text.TextUtils;

import com.br.widgettest.ui.extensions.cli.CliBaseWidget;

public class DashedTextViewWidget extends CliBaseWidget<DashedTextViewWidget.DashedTextView> {

    public DashedTextViewWidget(Context context) {
        super(DashedTextView.class, context);
    }

    @Override
    public String cliView() {
        String baseRep = "| ----------------------------- |\n";

        return baseRep;
    }

    public static class DashedTextView extends CliTextViewWidget.V {
        static final String DASHES = "---------------------------" +
                "------------------------------------------------------" +
                "------------------------------------------------------";

        public DashedTextView(Context context) {
            super(context, DASHES);
            setEllipsize(TextUtils.TruncateAt.END);
        }
    }
}

