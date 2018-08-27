package com.demo.reservation.web.dao.custom;

import javax.persistence.EntityManager;

public class BaseCustomDAO {

    protected EntityManager entityManager;

    public BaseCustomDAO(EntityManager entityManager) {

        this.entityManager = entityManager;
    }
}
