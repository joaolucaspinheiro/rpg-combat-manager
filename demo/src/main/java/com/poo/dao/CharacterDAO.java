package com.poo.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
public class CharacterDAO extends GenericDAO<Character> {
    @Override
    public Character findById(Long id) {
        EntityManager em = getEntityManager();
        try{
            return em.find(Character.class, id);
        }
        finally {
            em.close();
        }
    }
    @Override
    public List<Character> findAll() {
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT c FROM Character c", Character.class).getResultList();
        }
        finally {
            em.close();
        }
    }
    public List<Character> findByName(String name) {
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT c FROM Character c WHERE LOWER(c.name) = LOWER(:name) ", Character.class)
                    .setParameter("name", name)
                    .getResultList();
        }
        finally{
            em.close();
        }
    }

    public List<Character> findByLevel(int level) {
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT c FROM Character c WHERE c.level = :level ", Character.class)
                    .setParameter("level", level)
                    .getResultList();
        }
        finally{
            em.close();
        }
    }

}
