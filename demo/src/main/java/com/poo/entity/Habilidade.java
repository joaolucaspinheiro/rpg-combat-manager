package com.poo.entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
@Entity
public class Habilidade {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private int custoMp;
    private int dano;
    private String tipo; //

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private EntidadeCombate dono;


    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getCustoMp() { return custoMp; }
    public void setCustoMp(int custoMp) { this.custoMp = custoMp; }
    public int getDano() { return dano; }
    public void setDano(int dano) { this.dano = dano; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public EntidadeCombate getDono() { return dono; }
    public void setDono(EntidadeCombate dono) { this.dono = dono; }


}
