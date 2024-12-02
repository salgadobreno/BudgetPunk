package br.com.budgetpunk.bpcore.dao;

import java.util.List;

/**
 * Created by Breno on 2/7/2016.
 */
interface DB {
    enum Entity {
        Category,
        Entry
    }

    void persist(Entity entity, List list);

    <T> List<T> get(Entity entity);
}
