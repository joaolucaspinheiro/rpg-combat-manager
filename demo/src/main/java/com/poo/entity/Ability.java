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
    private int baseDamage;
    private String description;
    private String type;
    /**
     * Mapeamento de 'muitos para um': Muitas Habilidades pertencem a UMA Entidade de Combate.
     * Esta é a ponta DONA do relacionamento, pois contém a chave estrangeira (Foreign Key).
     *
     * @JoinColumn(name = "owner_id"): Especifica o nome da coluna física
     * que será criada na tabela 'Ability' para armazenar o ID do dono da habilidade
     * (a Entidade de Combate).
     */
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private CombatEntity owner;

    // --- Getters and Setters ---
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getMpCost() { return mpCost; }
    public void setMpCost(int mpCost) { this.mpCost = mpCost; }

    public int getBaseDamage() { return baseDamage; }
    public void setBaseDamage(int baseDamage) { this.baseDamage = baseDamage; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public CombatEntity getOwner() { return owner; }
    public void setOwner(CombatEntity owner) { this.owner = owner; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
