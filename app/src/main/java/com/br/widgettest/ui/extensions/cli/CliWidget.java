package com.br.widgettest.ui.extensions.cli;

import android.view.View;

interface CliWidget<T extends View> {
    String toStringRep();

    T getView();
}
