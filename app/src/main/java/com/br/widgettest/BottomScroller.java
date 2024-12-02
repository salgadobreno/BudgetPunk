package com.br.widgettest;

import android.widget.ListView;

public class BottomScroller {
    private ListView listView;

    public BottomScroller(ListView listView) {
        this.listView = listView;
    }

    public void run() {
        listView.smoothScrollToPosition(listView.getCount() - 1);
    }
}
