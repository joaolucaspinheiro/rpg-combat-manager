package com.poo.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class EntidadeCombate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nivel;
    private String nome;
    private int hpMaximo;
    private int mpMaximo;
    private int hpAtual;
    private int mpAtual;
    private int iniciativa;

    @OneToMany(mappedBy = "dono", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Habilidade> habilidades;

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public int getHpAtual() { return hpAtual; }
    public void setHpAtual(int hpAtual) { this.hpAtual = hpAtual; }
    public int getHpMaximo() { return hpMaximo; }
    public void setHpMaximo(int hpMaximo) { this.hpMaximo = hpMaximo; }
    public int getMpAtual() { return mpAtual; }
    public void setMpAtual(int mpAtual) { this.mpAtual = mpAtual; }
    public int getMpMaximo() { return mpMaximo; }
    public void setMpMaximo(int mpMaximo) { this.mpMaximo = mpMaximo; }
    public int getIniciativa() { return iniciativa; }
    public void setIniciativa(int iniciativa) { this.iniciativa = iniciativa; }
    public List<Habilidade> getHabilidades() { return habilidades; }
    public void setHabilidades(List<Habilidade> habilidades) { this.habilidades = habilidades; }
}
