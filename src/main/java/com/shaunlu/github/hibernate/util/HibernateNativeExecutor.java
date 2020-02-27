package com.shaunlu.github.hibernate.util;

import org.hibernate.Session;

public interface HibernateNativeExecutor {

    void execute(Session session);

}
