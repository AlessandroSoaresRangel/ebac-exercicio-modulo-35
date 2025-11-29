package org.example.jpa.services.generics;

import org.example.exceptions.DAOException;
import org.example.exceptions.TipoChaveNaoEncontradaException;
import org.example.jpa.dao.generics.IGenericDaoJpa;

import java.util.Collection;

public class GenericServiceJpa<T,E> implements IGenericServiceJpa<T,E> {

    protected IGenericDaoJpa<T,E> dao;

    public GenericServiceJpa(IGenericDaoJpa<T,E> dao) {
        this.dao = dao;
    }

    @Override
    public T cadastrar(T entity) throws DAOException {
         return this.dao.cadastrarJpa(entity);
    }

    @Override
    public T consultar(E valor) throws DAOException {
        return this.dao.consultarJpa(valor);
    }

    @Override
    public void excluir(T entity) throws DAOException {
        this.dao.excluirJpa(entity);
    }

    @Override
    public T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
        return this.dao.alterarJpa(entity);
    }

    @Override
    public Collection<T> buscarTodos() throws DAOException {
        return this.dao.buscarTodosJpa();
    }
}
