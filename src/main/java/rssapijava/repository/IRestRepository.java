package rssapijava.repository;

import rssapijava.entity.BaseEntity;

public interface IRestRepository<T extends BaseEntity> {
    T[] select();
    T select(Integer id);
    T insert(T entity);
    T update(Integer id, T entity);
    T delete(Integer id);
}