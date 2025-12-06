package org.example.jpa.dao;

import org.example.jpa.ClienteJpa;
import org.example.jpa.dao.generics.GenericDaoJpa;

public class ClienteDaoJpa extends GenericDaoJpa<ClienteJpa, Long> implements IClienteDaoJpa<ClienteJpa>{

    public ClienteDaoJpa() {
        super(ClienteJpa.class, "postgres1");
    }
}
