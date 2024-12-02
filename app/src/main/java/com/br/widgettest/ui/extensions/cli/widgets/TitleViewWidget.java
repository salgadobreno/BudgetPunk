package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

public class TitleViewWidget extends CliBaseAggregateWidget {

    public TitleViewWidget(Context context, String title) {
        super(context);

        CliTextViewWidget textViewWidget = new CliTextViewWidget(context, title);
        DashedTextViewWidget dashedTextViewWidget = new DashedTextViewWidget(context);

        getWidgets().add(textViewWidget);
        getWidgets().add(dashedTextViewWidget);
    }

}
