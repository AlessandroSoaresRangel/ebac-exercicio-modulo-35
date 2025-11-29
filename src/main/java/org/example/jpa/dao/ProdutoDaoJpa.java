package org.example.jpa.dao;

import org.example.jpa.ProdutoJpa;
import org.example.jpa.dao.generics.GenericDaoJpa;

public class ProdutoDaoJpa extends GenericDaoJpa<ProdutoJpa, Long> {
    public ProdutoDaoJpa() {
        super(ProdutoJpa.class);
    }
}
