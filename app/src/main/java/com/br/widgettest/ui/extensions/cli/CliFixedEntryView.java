package com.br.widgettest.ui.extensions.cli;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.br.widgettest.core.FixedEntry;
import com.br.widgettest.ui.extensions.cli.widgets.CliTextViewWidget;

public class CliFixedEntryView extends LinearLayout {
    private final CliFinancialEntryView finEntryView; private final CliTextViewWidget.V bottomTv;

    public CliFixedEntryView(Context context, FixedEntry entry) {
        this(context, entry.getName().toString(), entry.getValue().toString(), entry.getMonthlyValue().toString());
    }

    public CliFixedEntryView(Context context, String leftStr, String rightStr, String bottomStr) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);

        finEntryView = new CliFinancialEntryView(context, leftStr, rightStr);

        bottomTv = new CliTextViewWidget.V(context, bottomStr);
        bottomTv.setLayoutParams(new LayoutParams(
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        ));
        bottomTv.setTextAlignment(TEXT_ALIGNMENT_TEXT_END);

        addView(finEntryView);
        addView(bottomTv);
    }
}
