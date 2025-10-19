package com.poo.dao;

import jakarta.persistence.EntityManager;
import java.util.List;
public class CharacterDAO extends GenericDAO<Character> {
    /**
     * Busca um personagem pelo seu ID.
     * @param id ID do personagem que se deseja buscar.
     * @return O personagem correspondente ao ID fornecido, ou null se não encontrado.
     */
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
    /**
     * Busca todos os personagens no banco de dados.
     * @return Uma lista contendo todos os personagens.
     */
    @Override
    public List<Character> findAll() {
        EntityManager em = getEntityManager();
        try{
            // Consulta JPQL para buscar todos os personagens
            return em.createQuery("SELECT c FROM Character c", Character.class).getResultList();
        }
        finally {
            em.close();
        }
    }
    /**
     * Busca personagens pelo seu nome.
     * @param name Nome do personagem que se deseja buscar.
     * @return Uma lista de personagens correspondentes ao nome fornecido.
     */
    public List<Character> findByName(String name) {
        EntityManager em = getEntityManager();
        try{
            // Usando o LOWER para tornar a busca case-insensitive
            return em.createQuery("SELECT c FROM Character c WHERE LOWER(c.name) = LOWER(:name) ", Character.class)
                    .setParameter("name", name)
                    .getResultList();
        }
        finally{
            em.close();
        }
    }
/**
     * Busca personagens pelo seu nível.
     * @param level Nível do personagem que se deseja buscar.
     * @return Uma lista de personagens correspondentes ao nível fornecido.
     */
    public List<Character> findByLevel(int level) {
        EntityManager em = getEntityManager();
        try{
            // Consulta JPQL para buscar personagens pelo nível
            return em.createQuery("SELECT c FROM Character c WHERE c.level = :level ", Character.class)
                    .setParameter("level", level)
                    .getResultList();
        }
        finally{
            em.close();
        }
    }

}
