package br.com.budgetpunk.bpcorem;

import java.util.List;

public interface IRepository<T extends Entity> {

    T get(Long id);

    List<T> all();

    void save(T entity);

    void delete(T entity);

}
