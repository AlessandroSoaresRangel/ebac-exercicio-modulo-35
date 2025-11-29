package org.example.jpa.dao;

import org.example.exceptions.DAOException;
import org.example.exceptions.TipoChaveNaoEncontradaException;
import org.example.jpa.VendaJpa;
import org.example.jpa.dao.generics.GenericDaoJpa;

public class VendaExcluirDaoJpa extends GenericDaoJpa<VendaJpa, Long> implements IVendaDaoJpa<VendaJpa, Long>{

    public VendaExcluirDaoJpa() {
        super(VendaJpa.class);
    }

    @Override
    public void finalizarVenda(VendaJpa venda) throws DAOException, TipoChaveNaoEncontradaException {
        throw new UnsupportedOperationException("Operação não permitida");
    }

    @Override
    public void cancelarVenda(VendaJpa venda) throws DAOException, TipoChaveNaoEncontradaException {
        throw new UnsupportedOperationException("Operação não permitida");
    }

    @Override
    public VendaJpa consultarComCollection(Long id) {
        throw new UnsupportedOperationException("Operação não permitida");
    }
}
