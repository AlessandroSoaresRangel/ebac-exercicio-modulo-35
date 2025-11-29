package org.example.jpa.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.dao.IVendaDao;
import org.example.exceptions.DAOException;
import org.example.exceptions.TipoChaveNaoEncontradaException;
import org.example.jpa.ClienteJpa;
import org.example.jpa.ProdutoJpa;
import org.example.jpa.VendaJpa;
import org.example.jpa.dao.generics.GenericDaoJpa;

public class VendaDaoJpa extends GenericDaoJpa<VendaJpa, Long> implements IVendaDaoJpa<VendaJpa, Long> {

    public VendaDaoJpa() {
        super(VendaJpa.class);
    }

    @Override
    public void finalizarVenda(VendaJpa venda) throws DAOException, TipoChaveNaoEncontradaException {
            super.alterarJpa(venda);
    }

    @Override
    public void cancelarVenda(VendaJpa venda) throws DAOException, TipoChaveNaoEncontradaException {
        super.alterarJpa(venda);
    }

    @Override
    public void excluirJpa(VendaJpa venda) {
        throw new UnsupportedOperationException("Operação não permitida");
    }
    @Override
    public VendaJpa cadastrarJpa(VendaJpa venda) throws DAOException {
        try {
            super.abrirConeccao();
            venda.getProdutos().forEach(prod -> {
                ProdutoJpa produtoJpa = super.manager.merge(prod.getProduto());
                prod.setProduto(produtoJpa);
            });
            ClienteJpa cliente = super.manager.merge(venda.getCliente());
            venda.setCliente(cliente);
            super.manager.persist(venda);
            super.fecharConeccao();
            return venda;
        } catch (Exception e) {
            throw new DAOException("Erro Salvando Venda", e);
        }
    }

    @Override
    public VendaJpa consultarComCollection(Long id) {
        super.abrirConeccao();

        CriteriaBuilder builder = super.manager.getCriteriaBuilder();
        CriteriaQuery<VendaJpa> query = builder.createQuery(VendaJpa.class);
        Root<VendaJpa> root = query.from(VendaJpa.class);
        root.fetch("cliente");
        root.fetch("produtos");
        query.select(root).where(builder.equal(root.get("id"),id));

        TypedQuery<VendaJpa> query1 = super.manager.createQuery(query);
        VendaJpa venda = query1.getSingleResult();

        super.fecharConeccao();

        return venda;
    }
}
