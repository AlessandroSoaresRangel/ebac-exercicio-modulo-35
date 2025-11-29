package org.example.jpa.dao;

import org.example.exceptions.DAOException;
import org.example.exceptions.TipoChaveNaoEncontradaException;
import org.example.jpa.VendaJpa;
import org.example.jpa.dao.generics.IGenericDaoJpa;

import java.util.Collection;

public interface IVendaDaoJpa<t, e> extends IGenericDaoJpa<t, e> {

    void finalizarVenda(t venda) throws DAOException, TipoChaveNaoEncontradaException;
    void cancelarVenda(t venda) throws DAOException, TipoChaveNaoEncontradaException;

    t consultarComCollection(e id);

}
