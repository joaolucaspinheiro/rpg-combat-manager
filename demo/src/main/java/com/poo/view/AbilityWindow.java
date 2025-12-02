package com.poo.view;

import com.poo.entity.Ability;
import com.poo.entity.Character;
import com.poo.entity.CombatEntity;
import com.poo.entity.Enemy;
import com.poo.enums.AbilityType;
import com.poo.service.EntityService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AbilityWindow extends JFrame {
    private EntityService entityService;
    private JComboBox<CombatEntity> cmbOwner;
    private JTextField txtName, txtMpCost, txtDamage;
    private JComboBox<AbilityType> cmbType;

    public AbilityWindow() {
        entityService = new EntityService();
        setTitle("Gerenciador de Habilidades (GM)");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Painel Superior: Escolher Dono ---
        JPanel pnlOwner = new JPanel();
        List<Character> chars = entityService.findAllCharacters();
        List<Enemy> enemies = entityService.findAllEnemies();

        cmbOwner = new JComboBox<>();
        chars.forEach(cmbOwner::addItem);
        enemies.forEach(cmbOwner::addItem);

        // Renderizador para mostrar nomes bonitos no combo
        cmbOwner.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof CombatEntity) {
                    setText(((CombatEntity) value).getName() + " (ID: " + ((CombatEntity) value).getId() + ")");
                }
                return this;
            }
        });

        pnlOwner.add(new JLabel("Adicionar Habilidade para:"));
        pnlOwner.add(cmbOwner);
        add(pnlOwner, BorderLayout.NORTH);

        // --- Painel Central: Dados da Habilidade ---
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Nova Habilidade"));

        txtName = new JTextField();
        txtMpCost = new JTextField("0");
        txtDamage = new JTextField("0");
        cmbType = new JComboBox<>(AbilityType.values());

        pnlForm.add(new JLabel("Nome da Habilidade:")); pnlForm.add(txtName);
        pnlForm.add(new JLabel("Custo de MP:")); pnlForm.add(txtMpCost);
        pnlForm.add(new JLabel("Potência (Dano/Cura):")); pnlForm.add(txtDamage);
        pnlForm.add(new JLabel("Tipo:")); pnlForm.add(cmbType);

        add(pnlForm, BorderLayout.CENTER);

        // --- Painel Inferior: Botão Salvar ---
        JButton btnSave = new JButton("Aprender Habilidade");
        btnSave.addActionListener(e -> saveAbility());
        add(btnSave, BorderLayout.SOUTH);
    }

    private void saveAbility() {
        try {
            CombatEntity owner = (CombatEntity) cmbOwner.getSelectedItem();
            if (owner == null) return;

            Ability ability = new Ability();
            ability.setName(txtName.getText());
            ability.setMpCost(Integer.parseInt(txtMpCost.getText()));
            ability.setBaseDamage(Integer.parseInt(txtDamage.getText()));
            ability.setType((AbilityType) cmbType.getSelectedItem());
            ability.setOwner(owner); // Vínculo importante!

            // Adiciona na lista da entidade e atualiza no banco
            owner.getAbilities().add(ability);

            if (owner instanceof Character) {
                entityService.updateCharacter((Character) owner);
            } else {
                entityService.updateEnemy((Enemy) owner);
            }

            JOptionPane.showMessageDialog(this, "Habilidade aprendida com sucesso!");
            txtName.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
}