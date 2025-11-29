package org.example.jpa.dao;

import org.example.jpa.ClienteJpa;
import org.example.jpa.dao.generics.GenericDaoJpa;

public class ClienteDaoJpa extends GenericDaoJpa<ClienteJpa, Long> {

    public ClienteDaoJpa() {
        super(ClienteJpa.class);
    }
}
