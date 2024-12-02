package com.br.widgettest.ui.extensions.cli.widgets;

import android.content.Context;
import android.widget.LinearLayout;

import com.br.widgettest.ui.extensions.cli.CliBaseWidget;

import java.util.ArrayList;
import java.util.List;

abstract class CliBaseAggregateWidget extends CliBaseWidget<CliBaseAggregateWidget.V> {
    private List<CliBaseWidget> widgets = new ArrayList<>();

    public CliBaseAggregateWidget(Context context) {
        super(V.class, context);
        this.argsClasses.add(List.class);
        this.argsList.add(widgets);
    }

    protected List<CliBaseWidget> getWidgets() {
        return widgets;
    }

    @Override
    public String cliView() {
        String r = "";

        for (CliBaseWidget widget : getWidgets()) {
            r = r.concat(widget.cliView());
        }

        return r;
    }

    public static class V extends LinearLayout {
        public V(Context context, List<CliBaseWidget> widgets) {
            super(context);
            setOrientation(VERTICAL);
            for (CliBaseWidget widget : widgets) {
                addView(widget.getView());
            }
        }
    }

}
