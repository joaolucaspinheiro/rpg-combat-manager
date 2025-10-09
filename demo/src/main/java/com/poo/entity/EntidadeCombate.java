package com.poo.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class EntidadeCombate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nivel;
    private String nome;
    private int hpMax;
    private int mpMax;
    private int hpAtual;
    private int mpAtual;
    private int iniciativa;

    


}
