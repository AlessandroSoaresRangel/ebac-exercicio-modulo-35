package org.example.jpa.dao.generics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.exceptions.DAOException;
import org.example.exceptions.TipoChaveNaoEncontradaException;

import java.util.Collection;
import java.util.List;

public class GenericDaoJpa <T, E> implements IGenericDaoJpa <T, E> {
    private EntityManagerFactory factory;
    protected EntityManager manager;
    private Class<T> persistentClass;

    public GenericDaoJpa (Class<T> persistentClass) {
            this.persistentClass = persistentClass;
    }

    @Override
    public T cadastrarJpa(T entity) throws DAOException {
        this.abrirConeccao();
        this.manager.persist(entity);
        this.fecharConeccao();
        return entity;
    }

    @Override
    public void excluirJpa(T entity) throws DAOException {
        this.abrirConeccao();
        entity = this.manager.merge(entity);
        this.manager.remove(entity);
        this.fecharConeccao();

    }

    @Override
    public T alterarJpa(T entity) throws TipoChaveNaoEncontradaException, DAOException {
        this.abrirConeccao();
        entity = this.manager.merge(entity);
        this.fecharConeccao();
        return entity;
    }

    @Override
    public T consultarJpa(E valor) throws DAOException {
        this.abrirConeccao();
        T entity = this.manager.find(this.persistentClass, valor);
        this.fecharConeccao();
        return entity;
    }

    @Override
    public Collection<T> buscarTodosJpa() throws DAOException {
        this.abrirConeccao();
        TypedQuery<T> query = this.manager.createQuery(this.getSelectSql(), this.persistentClass);
        List<T> resultado = query.getResultList();
        this.fecharConeccao();
        return resultado;
    }

    protected void abrirConeccao() {
        this.factory = Persistence.createEntityManagerFactory("projeto4");
        this.manager = this.factory.createEntityManager();
        manager.getTransaction().begin();
    }
    protected void fecharConeccao() {
        this.manager.getTransaction().commit();
        this.manager.close();
        this.factory.close();
    }

    private String getSelectSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT obj FROM ");
        sb.append(this.persistentClass.getSimpleName());
        sb.append(" obj");
        return sb.toString();
    }
}
