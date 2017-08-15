package com.br.widgettest.core.dao;

import java.util.List;

/**
 * Created by Breno on 2/9/2016.
 */
public interface Dao<T> {
    T find(Long id);

    List<T> list();

    void save(T t);

    void remove(T t);

    void store(List<T> ts); //TODO: remove?
}
