package com.br.widgettest.core.dao;

import android.content.Context;

import com.br.widgettest.core.Entry;
import com.br.widgettest.core.exceptions.NotImplementedException;

/**
 * Created by Breno on 2/9/2016.
 */
public class EntryDao extends AbstractDao<Entry> {
    private static final DB.Entity ENTITY = DB.Entity.Entry;

    public EntryDao(Context context) {
        super(context, ENTITY);
    }

    @Override
    public Entry find(String id) {
        throw new NotImplementedException();
    }

    @Override
    DB.Entity getEntity() {
        return ENTITY;
    }
}
