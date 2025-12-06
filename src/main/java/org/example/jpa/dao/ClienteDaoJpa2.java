package org.example.jpa.dao;

import org.example.jpa.ClienteJpa;
import org.example.jpa.dao.generics.GenericDaoJpa;

public class ClienteDaoJpa2 extends GenericDaoJpa<ClienteJpa, Long> implements IClienteDaoJpa<ClienteJpa> {
    public ClienteDaoJpa2() {
        super(ClienteJpa.class, "postgres2");
    }
}
