package com.br.widgettest.ui.extensions.cli;

import android.content.Context;

import com.br.widgettest.ui.extensions.cli.widgets.CliTextViewWidget;

public class CliEmptyLine extends CliTextViewWidget.V {
    public CliEmptyLine(Context context) {
        super(context, "");
    }
}
