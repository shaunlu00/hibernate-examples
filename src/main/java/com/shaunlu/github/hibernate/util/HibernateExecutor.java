package com.shaunlu.github.hibernate.util;

import javax.persistence.EntityManager;

public interface HibernateExecutor {

    void execute(EntityManager entityManager);

}
