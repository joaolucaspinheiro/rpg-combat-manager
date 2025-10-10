package com.poo.dao;
import com.poo.entity.Ability;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AbilityDAO extends GenericDAO<Ability> {
    @Override
    public Ability findById(Long id) {
        EntityManager em = getEntityManager();
        try{
        return em.find(Ability.class, id);
    }finally{
        em.close();
        }
    }
    @Override
    public List<Ability> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Ability a", Ability.class).getResultList();
        }finally{
        em.close();}
    }

    public List<Ability> findByOwnerId(Long ownerId) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Ability a WHERE a.owner.id = :ownerId", Ability.class)
                     .setParameter("ownerId", ownerId)
                     .getResultList();
        } finally {
            em.close();
        }
    }
    public Ability findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Ability a WHERE LOWER(a.name) = LOWER(:name)", Ability.class)
                     .setParameter("name", name)
                     .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

}
