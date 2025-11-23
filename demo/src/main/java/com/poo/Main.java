package com.poo;

import com.poo.entity.Ability;
import com.poo.entity.Character;
import com.poo.entity.CombatEntity;
import com.poo.entity.Enemy;
import com.poo.enums.AbilityType;
import com.poo.service.CombatService;
import com.poo.service.EntityService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciando os Serviços
        EntityService entityService = new EntityService();
        CombatService combatService = new CombatService();

        System.out.println("--- CRIANDO PERSONAGENS E INIMIGOS ---");

        // 2. Criando um Personagem (Mago)
        Character hero = new Character();
        hero.setName("Gandalf");
        hero.setLevel(5);
        hero.setHpMax(100);
        hero.setHpCurrent(100);
        hero.setMpMax(200); // Bastante mana para testar
        hero.setMpCurrent(200);
        hero.setInitiative(15);
        hero.setCharacterClass("Mago");
        hero.setExperiencePoints(0);
        hero.setAbilities(new ArrayList<>()); // Inicializa a lista para evitar NullPointerException

        // Criando uma Habilidade de Dano (Bola de Fogo)
        Ability fireball = new Ability();
        fireball.setName("Bola de Fogo");
        fireball.setDescription("Lança uma bola de fogo no inimigo.");
        fireball.setMpCost(20);
        fireball.setBaseDamage(40); // Dano alto
        fireball.setType(AbilityType.DAMAGE); // Ou DANO, dependendo de como escreveu no Enum
        fireball.setOwner(hero); // Vincula ao dono (Importante para o Banco!)

        // Adiciona habilidade ao herói
        hero.getAbilities().add(fireball);

        // Criando uma Habilidade de Cura (Cura Rápida)
        Ability heal = new Ability();
        heal.setName("Cura Rápida");
        heal.setMpCost(10);
        heal.setBaseDamage(30); // Na lógica de cura, isso será o valor recuperado
        heal.setType(AbilityType.HEAL); // Ou CURA
        heal.setOwner(hero);

        hero.getAbilities().add(heal);

        // 3. Salvando o Herói no Banco de Dados
        // Graças ao CascadeType.ALL, isso salvará o herói E as habilidades automaticamente
        entityService.saveCharacter(hero);
        System.out.println("Personagem salvo: " + hero.getName() + " (ID: " + hero.getId() + ")");

        // 4. Criando um Inimigo (Orc)
        Enemy monster = new Enemy();
        monster.setName("Orc Guerreiro");
        monster.setLevel(3);
        monster.setHpMax(150);
        monster.setHpCurrent(150);
        monster.setMpMax(20);
        monster.setMpCurrent(20);
        monster.setInitiative(10); // Menor que o herói, então o herói ataca primeiro
        monster.setEnemyType("Humanoide");

        // Salvando o Inimigo
        entityService.saveEnemy(monster);
        System.out.println("Inimigo salvo: " + monster.getName() + " (ID: " + monster.getId() + ")");

        System.out.println("\n--- INICIANDO COMBATE ---");

        // 5. Setup do Combate
        List<CombatEntity> participants = new ArrayList<>();
        participants.add(hero);
        participants.add(monster);

        combatService.startCombat(participants);

        // --- TURNO 1 (Gandalf - Iniciativa 15) ---
        CombatEntity currentTurn = combatService.nextTurn();
        System.out.println("Turno de: " + currentTurn.getName());

        if (currentTurn == hero) {
            // Gandalf usa Bola de Fogo no Orc
            // Passamos '40' como valor do efeito (dano base da habilidade)
            String result = combatService.useAbility(hero, monster, fireball, fireball.getBaseDamage());
            System.out.println(result);
        }

        System.out.println("Status Orc: HP " + monster.getHpCurrent() + "/" + monster.getHpMax());
        System.out.println("Status Gandalf: MP " + hero.getMpCurrent() + "/" + hero.getMpMax());

        // --- TURNO 2 (Orc - Iniciativa 10) ---
        currentTurn = combatService.nextTurn();
        System.out.println("\nTurno de: " + currentTurn.getName());

        if (currentTurn == monster) {
            // Orc ataca Gandalf fisicamente
            String result = combatService.performAttack(monster, hero, 15); // 15 de dano físico
            System.out.println(result);
        }
        System.out.println("Status Gandalf: HP " + hero.getHpCurrent() + "/" + hero.getHpMax());

        // --- TURNO 3 (Gandalf se Cura) ---
        currentTurn = combatService.nextTurn(); // Volta para o Gandalf (Lista Circular)
        System.out.println("\nTurno de: " + currentTurn.getName());

        if (currentTurn == hero) {
            // Gandalf usa Cura em si mesmo
            String result = combatService.useAbility(hero, hero, heal, heal.getBaseDamage());
            System.out.println(result);
        }
        System.out.println("Status Gandalf: HP " + hero.getHpCurrent() + "/" + hero.getHpMax());

        // Atualiza os estados finais no Banco de Dados
        entityService.updateCharacter(hero);
        entityService.updateEnemy(monster);
        System.out.println("\n--- ESTADO FINAL SALVO NO BANCO ---");
    }
}