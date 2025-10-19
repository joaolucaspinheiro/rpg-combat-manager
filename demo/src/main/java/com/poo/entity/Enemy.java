package com.poo.entity;

import jakarta.persistence.Entity;

@Entity
public class Enemy extends CombatEntity {
    // Tipo específico para Inimigo
    private String EnemyType;


    // --- Getters and Setters ---
    public String getEnemyType() {
        return EnemyType;
    }
    public void setEnemyType(String enemyType) {
        EnemyType = enemyType;
    }
}
