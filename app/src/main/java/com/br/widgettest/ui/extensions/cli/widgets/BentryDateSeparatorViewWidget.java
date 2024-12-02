package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BentryDateSeparatorViewWidget extends CliBaseAggregateWidget {

    public BentryDateSeparatorViewWidget(Context context, Date date) {
        super(context);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

        getWidgets().add(new CliTextViewWidget(context, sdf.format(date)));
        getWidgets().add(new DashedTextViewWidget(context));
    }
}
