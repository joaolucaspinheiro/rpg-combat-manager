package com.poo.view;

import com.poo.entity.Character;
import com.poo.entity.CombatEntity;
import com.poo.entity.Enemy;
import com.poo.service.EntityService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectCombatWindow extends JFrame {

    private EntityService entityService;

    // Listas visuais
    private DefaultListModel<CombatEntity> availableModel;
    private DefaultListModel<CombatEntity> selectedModel;
    private JList<CombatEntity> lstAvailable;
    private JList<CombatEntity> lstSelected;

    public SelectCombatWindow() {
        entityService = new EntityService();
        setTitle("Montar Mesa de Combate (GM)");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Modelos de Dados ---
        availableModel = new DefaultListModel<>();
        selectedModel = new DefaultListModel<>();

        // Carrega tudo do banco
        List<Character> chars = entityService.findAllCharacters();
        List<Enemy> enemies = entityService.findAllEnemies();
        chars.forEach(availableModel::addElement);
        enemies.forEach(availableModel::addElement);

        // --- Painel Central (Duas Listas) ---
        JPanel pnlLists = new JPanel(new GridLayout(1, 3, 10, 10));

        // Lista Esquerda (Disponíveis)
        lstAvailable = new JList<>(availableModel);
        lstAvailable.setCellRenderer(new EntityListRenderer());
        JScrollPane scrollAvail = new JScrollPane(lstAvailable);
        scrollAvail.setBorder(BorderFactory.createTitledBorder("Banco de Entidades"));

        // Painel Meio (Botões)
        JPanel pnlButtons = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnAdd = new JButton("Adicionar >>");
        JButton btnRemove = new JButton("<< Remover");
        pnlButtons.add(new JLabel("")); // Espaço
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnRemove);
        pnlButtons.add(new JLabel(""));

        // Lista Direita (Selecionados para Luta)
        lstSelected = new JList<>(selectedModel);
        lstSelected.setCellRenderer(new EntityListRenderer());
        JScrollPane scrollSel = new JScrollPane(lstSelected);
        scrollSel.setBorder(BorderFactory.createTitledBorder("Participantes do Combate"));

        pnlLists.add(scrollAvail);
        pnlLists.add(pnlButtons);
        pnlLists.add(scrollSel);
        add(pnlLists, BorderLayout.CENTER);

        // --- Botão Iniciar ---
        JButton btnStart = new JButton("INICIAR SESSÃO DE COMBATE");
        btnStart.setFont(new Font("Arial", Font.BOLD, 16));
        btnStart.addActionListener(e -> startCombat());
        add(btnStart, BorderLayout.SOUTH);

        // --- Ações ---
        btnAdd.addActionListener(e -> {
            List<CombatEntity> selected = lstAvailable.getSelectedValuesList();
            for (CombatEntity ent : selected) {
                selectedModel.addElement(ent);
                availableModel.removeElement(ent);
            }
        });

        btnRemove.addActionListener(e -> {
            List<CombatEntity> selected = lstSelected.getSelectedValuesList();
            for (CombatEntity ent : selected) {
                availableModel.addElement(ent);
                selectedModel.removeElement(ent);
            }
        });
    }

    private void startCombat() {
        if (selectedModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione pelo menos um participante.");
            return;
        }

        List<CombatEntity> participants = new ArrayList<>();
        for (int i = 0; i < selectedModel.size(); i++) {
            participants.add(selectedModel.getElementAt(i));
        }

        // AGORA SIM: Passa apenas 1 argumento (a lista) para a BattleWindow
        new BattleWindow(participants).setVisible(true);
        dispose();
    }

    // Renderizador para mostrar Herois e Monstros de forma diferente
    static class EntityListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof CombatEntity) {
                CombatEntity e = (CombatEntity) value;
                String type = (e instanceof Character) ? "[HERÓI]" : "[INIMIGO]";
                setText(type + " " + e.getName() + " (Inic: " + e.getInitiative() + ")");
                if (e instanceof Enemy) setForeground(Color.RED);
                else setForeground(Color.BLUE);
            }
            return this;
        }
    }
}