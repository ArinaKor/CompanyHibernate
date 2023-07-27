package org.company.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    List<T> findAll();
    Optional<T> findById(long id);
    boolean delete(long id);
    boolean save(T t);
    boolean update(long id, T t);
}
