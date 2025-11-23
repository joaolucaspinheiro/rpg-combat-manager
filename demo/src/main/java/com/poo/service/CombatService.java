package com.poo.service;

import com.poo.entity.Ability;
import com.poo.entity.CombatEntity;
import com.poo.enums.AbilityType;
import com.poo.util.DoubleCircularLinkedList;
import java.util.List;

/**
 * Lógica de combate genérica pode ser implementada aqui.
 * Exemplo: calcular dano, aplicar efeitos, gerenciar turnos, etc.
 */
public class CombatService {
    private DoubleCircularLinkedList turnOrder;
    public CombatService() {
        this.turnOrder = new DoubleCircularLinkedList();
    }
    // Inicia o combate com todos na lista, ordenando por iniciativa
    public void startCombat(List<CombatEntity> participants){
        for (CombatEntity entity : participants) {
            turnOrder.addParticipants(entity);
        }
        turnOrder.sortParticipantsByInitiative();

    }
    // Retorna a próxima entidade a agir no turno
    public CombatEntity nextTurn(){
        return turnOrder.nextTurn();
    }
    // Lógica para ataque físico
    public String performAttack(CombatEntity attacker, CombatEntity defender, int damage){
        int newHp = defender.getHpCurrent() - damage;
        defender.setHpCurrent(newHp);
        return attacker.getName() + " atacou " + defender.getName() + " causando " + damage + " de dano.";
    }
    // Lógica para usar habilidade
    // O parâmetro 'damage' aqui representa o valor do efeito (seja dano ou cura)
    public String useAbility(CombatEntity user, CombatEntity target, Ability ability, int effectValue){
        if (user.getMpCurrent() < ability.getMpCost()) {
            return user.getName() + " não tem MP suficiente para usar " + ability.getName() + ".";
        }

        // Consome a Mana
        user.setMpCurrent(user.getMpCurrent() - ability.getMpCost());
        // Verifica se é CURA (HEAL) ou DANO (DAMAGE)
        if(ability.getType() == AbilityType.HEAL) {
            int newHp = target.getHpCurrent() + effectValue;
            target.setHpCurrent(newHp);
            return user.getName() + " curou " + target.getName() + " recuperando " + effectValue + " de vida.";
        } else {
            // Assume DAMAGE, BUFF ou DEBUFF como hostil por enquanto (causa dano)
            int newHp = target.getHpCurrent() - effectValue;
            target.setHpCurrent(newHp);
            return user.getName() + " usou " + ability.getName() + " em " + target.getName() + " causando " + effectValue + " de dano.";
        }
    }

}
