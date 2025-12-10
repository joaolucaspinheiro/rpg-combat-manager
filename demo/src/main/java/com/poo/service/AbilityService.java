package com.poo.service;

import com.poo.dao.AbilityDAO;
import com.poo.entity.Ability;
import java.util.List;

public class AbilityService {

    private final AbilityDAO abilityDAO;

    public AbilityService() {
        this.abilityDAO = new AbilityDAO();
    }

    // Salvar diretamente uma habilidade
    public void save(Ability ability) {
        if (ability.getMpCost() < 0) {
            throw new IllegalArgumentException("Custo de MP não pode ser negativo.");
        }
        abilityDAO.save(ability);
    }

    // Listar todas as habilidades do jogo
    public List<Ability> findAll() {
        return abilityDAO.findAll();
    }

    // Buscar habilidades de um personagem específico
    public List<Ability> findByOwner(Long ownerId) {
        return abilityDAO.findByOwnerId(ownerId);
    }

    // Excluir uma habilidade pelo ID
    public void delete(Ability ability) {
        abilityDAO.delete(ability);
    }
}