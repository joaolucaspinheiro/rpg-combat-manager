package com.poo.dao;

import com.poo.entity.Enemy;
import jakarta.persistence.EntityManager;
import java.util.List;
public class EnemyDAO extends GenericDAO<Enemy> {

    @Override
    public Enemy findById(Long id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Enemy.class, id);
        }
        finally {
            em.close();
        }
    }
    @Override
    public List<Enemy> findAll() {
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT e FROM Enemy e", Enemy.class).getResultList();
        }
        finally {
            em.close();
        }
    }
    public List<Enemy> findByName(String name) {
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT e FROM Enemy e WHERE LOWER(e.name) = LOWER(:name) ", Enemy.class)
                    .setParameter("name", name)
                    .getResultList();
        }
        finally{
            em.close();
        }
    }
    public List<Enemy> findByLevel(int level) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Enemy e WHERE e.level = :level ", Enemy.class)
                    .setParameter("level", level)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
