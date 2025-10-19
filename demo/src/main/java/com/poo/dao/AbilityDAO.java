package com.poo.dao;
import com.poo.entity.Ability;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AbilityDAO extends GenericDAO<Ability> {
    /**
     * Busca uma habilidade pelo seu ID.
     * @param id ID da habilidade que se deseja buscar.
     * @return A habilidade correspondente ao ID fornecido, ou null se não encontrada.
     */
    @Override
    public Ability findById(Long id) {
        EntityManager em = getEntityManager();
        try{
        return em.find(Ability.class, id);
    }finally{
        em.close();
        }
    }
    /**
     * Busca todas as habilidades no banco de dados.
     * @return Uma lista contendo todas as habilidades.
     */
    @Override
    public List<Ability> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT a FROM Ability a", Ability.class).getResultList();
        }finally{
        em.close();}
    }
    /**
        * Busca todas as habilidades pertencentes a um determinado dono (owner).
        * @param ownerId ID do dono cujas habilidades se deseja buscar.
        * @return Uma lista de habilidades pertencentes ao dono especificado.
    */
    public List<Ability> findByOwnerId(Long ownerId) {
        EntityManager em = getEntityManager();
        try {
            // Consulta JPQL para buscar habilidades pelo ID do dono
            return em.createQuery("SELECT a FROM Ability a WHERE a.owner.id = :ownerId", Ability.class)
                     .setParameter("ownerId", ownerId)
                     .getResultList();
        } finally {
            em.close();
        }
    }
    /**
     * Busca uma habilidade pelo seu nome.
     * @param name Nome da habilidade que se deseja buscar.
     * @return A habilidade correspondente ao nome fornecido, ou null se não encontrada.
     */
    public Ability findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            // Usando o LOWER para tornar a busca case-insensitive
            return em.createQuery("SELECT a FROM Ability a WHERE LOWER(a.name) = LOWER(:name)", Ability.class)
                     .setParameter("name", name)
                     .getSingleResult();
        } catch (Exception e) {
            // Retorna null se nenhuma habilidade for encontrada com o nome fornecido
            return null;
        } finally {
            em.close();
        }
    }

}
