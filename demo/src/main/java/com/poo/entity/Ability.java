package com.poo.entity;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Ability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int mpCost;
    private int damage;
    private String type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private CombatEntity owner;

    // --- Getters and Setters ---
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMpCost() { return mpCost; }
    public void setMpCost(int mpCost) { this.mpCost = mpCost; }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public CombatEntity getOwner() { return owner; }
    public void setOwner(CombatEntity owner) { this.owner = owner; }
}
