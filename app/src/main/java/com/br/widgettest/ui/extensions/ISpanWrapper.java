package com.br.widgettest.ui.extensions;

import android.text.Spannable;

public interface ISpanWrapper {
    void setSpan(Spannable target, Object what, int start, int end, int flags);
}
