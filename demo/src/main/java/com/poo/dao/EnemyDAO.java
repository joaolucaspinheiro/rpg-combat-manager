package com.poo.dao;

import com.poo.entity.Enemy;
import jakarta.persistence.EntityManager;
import java.util.List;
public class EnemyDAO extends GenericDAO<Enemy> {
/**
     * Busca um inimigo pelo seu ID.
     * @param id ID do inimigo que se deseja buscar.
     * @return O inimigo correspondente ao ID fornecido, ou null se não encontrado.
     */
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
    /**
     * Busca todos os inimigos no banco de dados.
     * @return Uma lista contendo todos os inimigos.
     */
    @Override
    public List<Enemy> findAll() {
        EntityManager em = getEntityManager();
        try{
            // Consulta JPQL para buscar todos os inimigos
            return em.createQuery("SELECT e FROM Enemy e", Enemy.class).getResultList();
        }
        finally {
            em.close();
        }
    }
    /**
     * Busca inimigos pelo seu nome.
     * @param name Nome do inimigo que se deseja buscar.
     * @return Uma lista de inimigos correspondentes ao nome fornecido.
     */
    public List<Enemy> findByName(String name) {
        EntityManager em = getEntityManager();
        try{
            // Usando o LOWER para tornar a busca case-insensitive
            return em.createQuery("SELECT e FROM Enemy e WHERE LOWER(e.name) = LOWER(:name) ", Enemy.class)
                    .setParameter("name", name)
                    .getResultList();
        }
        finally{
            em.close();
        }
    }
    /**
     * Busca inimigos pelo seu nível.
     * @param level Nível do inimigo que se deseja buscar.
     * @return Uma lista de inimigos correspondentes ao nível fornecido.
     */
    public List<Enemy> findByLevel(int level) {
        EntityManager em = getEntityManager();
        try {
            // Consulta JPQL para buscar inimigos pelo nível
            return em.createQuery("SELECT e FROM Enemy e WHERE e.level = :level ", Enemy.class)
                    .setParameter("level", level)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    /**
     * Insere uma grande quantidade de inimigos para testes de carga.
     * Usa batch processing para evitar estouro de memória.
     */
    public void populateInBulk(int quantity) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            String[] types = {"Orc", "Bandido", "Goblin", "Esqueleto", "Dragão"};
            java.util.Random random = new java.util.Random();

            for (int i = 1; i <= quantity; i++) {
                Enemy enemy = new Enemy();
                String type = types[random.nextInt(types.length)];

                enemy.setName(type + " " + i);
                enemy.setEnemyType(type);
                enemy.setLevel(random.nextInt(10) + 1);
                enemy.setHpMax(50 + random.nextInt(100));
                enemy.setHpCurrent(enemy.getHpMax());
                enemy.setMpMax(20);
                enemy.setMpCurrent(20);
                enemy.setInitiative(random.nextInt(20));

                em.persist(enemy);

                if (i % 100 == 0) {
                    em.flush();
                    em.clear();
                }
            }

            em.getTransaction().commit();
            System.out.println("Inserção de " + quantity + " inimigos concluída!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
