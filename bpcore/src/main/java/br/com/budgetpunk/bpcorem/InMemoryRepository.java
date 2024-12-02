package br.com.budgetpunk.bpcorem;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository<T extends Entity> implements IRepository<T> {
    private final List<T> list;

    public InMemoryRepository(List<T> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
    }

    @Override
    public T get(Long id) {
        return list.get(id.intValue());
    }

    @Override
    public List<T> all() {
        return list;
    }

    @Override
    public void save(T entity) {
        if (entity.id() != null) {
            list.set(entity.id().intValue(), entity);
        } else {
            list.add(entity);
            entity.id((long) list.indexOf(entity));
        }
    }

    @Override
    public void delete(T entity) {
        if (entity.id() != null) {
            list.remove(entity.id().intValue());
        }
    }
}
