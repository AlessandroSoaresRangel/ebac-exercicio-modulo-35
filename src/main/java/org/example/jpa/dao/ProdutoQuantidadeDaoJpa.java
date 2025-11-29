package org.example.jpa.dao;

import org.example.jpa.ProdutoQuantidadeJpa;
import org.example.jpa.dao.generics.GenericDaoJpa;

public class ProdutoQuantidadeDaoJpa extends GenericDaoJpa<ProdutoQuantidadeJpa, Long> {
    public ProdutoQuantidadeDaoJpa() {
        super(ProdutoQuantidadeJpa.class);
    }
}
