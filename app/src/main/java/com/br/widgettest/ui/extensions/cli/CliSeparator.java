package com.br.widgettest.ui.extensions.cli;

import android.content.Context;

import com.br.widgettest.ui.extensions.cli.widgets.CliTextViewWidget;

public class CliSeparator extends CliTextViewWidget.V {
    private static final String TEXT = "---------------------------" +
            "------------------------------------------------------" +
            "------------------------------------------------------";

    public CliSeparator(Context context) {
        super(context, TEXT);
    }
}
