package org.company.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    List<T> findAll();
    Optional<T> findById(long id);
    boolean delete(long id);
    boolean update(long id, T t);
    boolean save(T t);
}
