package org.example.jpa.services.generics;

import org.example.exceptions.DAOException;
import org.example.exceptions.TipoChaveNaoEncontradaException;

import java.util.Collection;

public interface IGenericServiceJpa <T, E> {
    T cadastrar(T entity) throws DAOException;

    T consultar(E valor) throws DAOException;

    void excluir(T entity) throws DAOException;

    T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    Collection<T> buscarTodos() throws DAOException;
}
