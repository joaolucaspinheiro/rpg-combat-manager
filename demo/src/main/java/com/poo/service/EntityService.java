package com.poo.service;
import com.poo.dao.CharacterDAO;
import com.poo.dao.EnemyDAO;
import com.poo.entity.CombatEntity;
import com.poo.entity.Enemy;
import com.poo.entity.Character;
import java.util.List;

/**
 * Serviço genérico para operações relacionadas a entidades de combate.
 * Implementa lógicas comuns que podem ser compartilhadas entre diferentes tipos de entidades.
 */
public class EntityService {
    private final CharacterDAO characterDAO;
    private final EnemyDAO enemyDAO;
    public EntityService() {
        this.characterDAO = new CharacterDAO();
        this.enemyDAO = new EnemyDAO();
    }
    // --- Métodos CRUD para character ---
    public void saveCharacter(Character character) {
        characterDAO.save(character);
    }
    public Character findCharacterById(Long id) {
        return characterDAO.findById(id);
    }
    public void updateCharacter(Character character) {
        characterDAO.update(character);
    }
    public List<Character> findAllCharacters() {
        return characterDAO.findAll();
    }
    public void deleteCharacter(Character character) {
        characterDAO.delete(character);
    }
    // --- Métodos CRUD para enemy ---
    public void saveEnemy(Enemy enemy) {
        enemyDAO.save(enemy);
    }
    public Enemy findEnemyById(Long id) {
        return enemyDAO.findById(id);
    }
    public void updateEnemy(Enemy enemy) {
        enemyDAO.update(enemy);
    }
    public List<Enemy> findAllEnemies() {
        return enemyDAO.findAll();
    }
    public void deleteEnemy(Enemy enemy) {
        enemyDAO.delete(enemy);
    }
}
