package com.br.widgettest.core.dao;

import java.util.List;

/**
 * Created by Breno on 2/9/2016.
 */
public interface Dao<T> {
    T find(String id);

    List<T> list();

    void add(T t);

    void remove(T t);

    void store(List<T> ts); //TODO: remove?
}
