package service;

import java.util.List;

public interface IService <T>{

    void add(T t);
    void delete(int idProduit);

    void update(T t);

    List<T> readAll();

    T readById(int idProduit);
}
