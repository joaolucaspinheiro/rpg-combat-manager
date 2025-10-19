package com.poo.service;
import com.poo.dao.CharacterDAO;
import com.poo.dao.EnemyDAO;
import com.poo.entity.CombatEntity;
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
    private void validateEntityState(CombatEntity combatEntity) {
        // Para não permitir pontos de vida ou mana negativos setamos como 0
        if(combatEntity.getHpCurrent() < 0) {
            combatEntity.setHpCurrent(0);
        }
        if(combatEntity.getMpCurrent() < 0) {
            combatEntity.setMpCurrent(0);
        }
        // --- Logica para cura/Recuperação: garantindoq que hp e mp não ultrapassem o máximo ---
        if(combatEntity.getHpCurrent() > combatEntity.getHpMax()) {
            combatEntity.setHpCurrent(combatEntity.getHpMax());
        }
        if(combatEntity.getMpCurrent() > combatEntity.getMpMax()) {
            combatEntity.setMpCurrent(combatEntity.getMpMax());
        }
    }
}
