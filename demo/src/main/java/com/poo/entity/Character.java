package com.poo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_character") //  evita o conflito com a palavra reservada do SQL
public class Character extends CombatEntity {
    // atributos especificos para o personagem
    private String characterClass; // classe ex: guerreiro, mago, arqueiro
    private int experiencePoints; // pontos de experiencia acumulados

    // --- Getters and Setters ---
    public String getCharacterClass() {
        return characterClass;
    }
    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }
    public int getExperiencePoints() {
        return experiencePoints;
    }
    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }



}
