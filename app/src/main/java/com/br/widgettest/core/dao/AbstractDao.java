package com.br.widgettest.core.dao;

import android.content.Context;

import java.util.List;

/**
 * Created by Breno on 2/9/2016.
 */
public abstract class AbstractDao<T> implements Dao<T> {
    protected final CacheStorageDB db;

    public AbstractDao(Context context, DB.Entity entity) {
        db = new CacheStorageDB(new InternalStorageDB(context));
    }

    abstract DB.Entity getEntity();

    @Override
    public List<T> list() {
        return db.get(getEntity());
    }

    @Override
    public void add(T t) {
        List<T> list = db.get(getEntity());
        if (!list.contains(t)) {
            list.add(t);
            db.persist(getEntity(), list);
        }
    }

    @Override
    public void remove(T t) {
        List<T> list = db.get(getEntity());
        if (list.contains(t)) {
            list.remove(t);
            db.persist(getEntity(), list);
        } else {
            throw new RuntimeException("not found");
        }
    }

    @Override
    public void store(List<T> list) {
        db.persist(getEntity(), list);
    }
}
