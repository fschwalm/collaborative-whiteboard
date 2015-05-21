package br.org.tutty.collaborative_whiteboard;

/**
 * Created by drferreira on 29/01/15.
 */
public interface Dao {
    void persist(Object entity);

    Long count(Class<?> clazz);

    void update(Object entity);

    void remove(Object entity);
}
