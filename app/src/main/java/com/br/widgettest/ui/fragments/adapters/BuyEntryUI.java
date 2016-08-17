package com.br.widgettest.ui.fragments.adapters;

import com.br.widgettest.core.Entry;

/**
 * Created by Breno on 8/11/2016.
 */
public interface BuyEntryUI {
    boolean delete(Entry entry);

    boolean edit(Entry entry);

    boolean toggleActive(Entry entry);
}
