package com.br.widgettest.ui.extensions;

import android.text.Spannable;

public class SpanWrapper implements ISpanWrapper {
    @Override
    public void setSpan(Spannable target, Object what, int start, int end, int flags) {
        target.setSpan(what, start, end, flags);
    }
}
