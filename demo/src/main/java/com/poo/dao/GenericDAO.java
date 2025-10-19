package com.poo.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
/**
 * Classe genérica de Data Access Object (DAO) para operações CRUD.
 * @param <T> Tipo da entidade que este DAO gerencia.
 */
public abstract class GenericDAO<T> {
    // Fábrica de EntityManager compartilhada para todas as instâncias de DAO
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencePU");
    // Método protegido para obter um novo EntityManager
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
/**
     * Salva uma nova entidade no banco de dados.
     * @param entity A entidade a ser salva.
     */
    public void save(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();

        }
    }
    /**
     *Atualiza uma entidade existente no banco de dados.
     * @param entity A entidade a ser atualizada.
     */
    public void update(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }
    /**
     * Deleta uma entidade do banco de dados.
     * @param entity A entidade a ser deletada.
     */
    public void delete(T entity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public abstract T findById(Long id);
    public abstract List<T> findAll();
}
