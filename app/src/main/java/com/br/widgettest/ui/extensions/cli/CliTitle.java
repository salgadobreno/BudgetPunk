package com.br.widgettest.ui.extensions.cli;

import android.content.Context;
import android.widget.LinearLayout;

import com.br.widgettest.ui.extensions.cli.widgets.CliTextViewWidget;

public class CliTitle extends LinearLayout {
    private final CliTextViewWidget.V title;
    private final CliSeparator separator;

    public CliTitle(Context context, String text) {
        super(context);

        this.title = new CliTextViewWidget.V(context, text);
        this.separator = new CliSeparator(context);

        setOrientation(LinearLayout.VERTICAL);
//        setOrientation(LinearLayout.VERTICAL);
//        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(title);
        addView(separator);
    }
}
