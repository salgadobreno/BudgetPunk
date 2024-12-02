package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSeparatorViewWidget extends CliBaseAggregateWidget {

    public DateSeparatorViewWidget(Context context, Date date) {
        super(context);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        CenteredTextViewWidget centeredTextViewWidget = new CenteredTextViewWidget(context, sdf.format(date));
        DashedTextViewWidget dashedTextViewWidget = new DashedTextViewWidget(context);

        getWidgets().add(centeredTextViewWidget);
        getWidgets().add(dashedTextViewWidget);
    }

}
