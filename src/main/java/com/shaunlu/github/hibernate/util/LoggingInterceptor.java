package com.shaunlu.github.hibernate.util;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;

@Slf4j
public class LoggingInterceptor extends EmptyInterceptor {
    @Override
    public boolean onSave(
            Object entity,
            Serializable id,
            Object[] state,
            String[] propertyNames,
            Type[] types) {

        log.info("-------------Entity {}-{} saved with state{}-------------",
                entity.getClass().getSimpleName(),id, state);
        return super.onSave(entity, id, state, propertyNames, types);
    }
}
