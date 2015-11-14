package com.acme.services;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by toto on 14/11/15.
 */
public class ExampleDao {

    private final EntityManager entityManager;

    @Inject
    public ExampleDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public String create(ExampleResourceCreateRequest request) {
        return null;
    }

    public String getNameById(String id) {
        return null;
    }
}
