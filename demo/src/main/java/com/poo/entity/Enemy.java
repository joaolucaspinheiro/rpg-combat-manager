package com.poo.entity;

import jakarta.persistence.Entity;

@Entity
public class Enemy extends CombatEntity {
    // Tipo específico para Inimigo
    private String enemyType;


    // --- Getters and Setters ---
    public String getEnemyType() {
        return enemyType;
    }
    public void setEnemyType(String enemyType) {
        enemyType = enemyType;
    }
}
