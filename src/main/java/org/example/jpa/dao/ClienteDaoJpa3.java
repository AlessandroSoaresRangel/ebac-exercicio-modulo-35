package org.example.jpa.dao;

import org.example.jpa.ClienteJpa;
import org.example.jpa.dao.generics.GenericDaoJpa;

public class ClienteDaoJpa3 extends GenericDaoJpa<ClienteJpa, Long> implements IClienteDaoJpa<ClienteJpa> {

    public ClienteDaoJpa3() {
        super(ClienteJpa.class, "mysql");
    }
}
