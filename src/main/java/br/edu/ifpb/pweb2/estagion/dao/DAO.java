package br.edu.ifpb.pweb2.estagion.dao;

import java.util.List;

public interface DAO<T> {
    void create(T t);
    void update(T t);
    void delete(Long id);
    T find(Long id);
    List<T> findAll();
}
