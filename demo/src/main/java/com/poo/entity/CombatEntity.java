package com.poo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CombatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;
    private String name;

    private int hpMax;
    private int mpMax;
    private int hpCurrent;
    private int mpCurrent;
    private int initiative;
    /**
     * Mapeamento de 'um para muitos': Uma Entidade de Combate possui uma lista de Habilidades.
     *
     * mappedBy="owner": Indica que esta é a ponta INVERSA (não-dona) do relacionamento.
     * O mapeamento da chave estrangeira (a coluna 'owner_id') é gerenciado pelo campo 'owner'
     * definido na classe Ability.
     *
     * cascade=CascadeType.ALL: Propaga todas as operações (salvar, atualizar, deletar)
     * desta Entidade para suas Habilidades associadas.
     *
     * fetch=FetchType.LAZY: Carrega a lista de habilidades apenas quando for solicitada,
     * otimizando a performance.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ability> abilities;

    // --- Getters and Setters ---
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getHpCurrent() { return hpCurrent; }
    public void setHpCurrent(int hpCurrent) { this.hpCurrent = hpCurrent; }

    public int getHpMax() { return hpMax; }
    public void setHpMax(int hpMax) { this.hpMax = hpMax; }

    public int getMpCurrent() { return mpCurrent; }
    public void setMpCurrent(int mpCurrent) { this.mpCurrent = mpCurrent; }

    public int getMpMax() { return mpMax; }
    public void setMpMax(int mpMax) { this.mpMax = mpMax; }

    public int getInitiative() { return initiative; }
    public void setInitiative(int initiative) { this.initiative = initiative; }

    public List<Ability> getAbilities() { return abilities; }
    public void setAbilities(List<Ability> abilities) { this.abilities = abilities; }

    }
}
