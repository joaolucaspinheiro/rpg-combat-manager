package com.poo.view;

import com.poo.entity.Character;
import com.poo.entity.Enemy;
import com.poo.service.EntityService;

import javax.swing.*;
import java.awt.*;

public class CreateEntityWindow extends JFrame {

    private EntityService entityService;
    private JTabbedPane tabbedPane;

    public CreateEntityWindow() {
        entityService = new EntityService();

        setTitle("Criar Entidades");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Criar Herói", createHeroPanel());
        tabbedPane.addTab("Criar Inimigo", createEnemyPanel());

        add(tabbedPane);
    }

    private JPanel createHeroPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtName = new JTextField();
        JTextField txtHp = new JTextField("100");
        JTextField txtMp = new JTextField("50");
        JTextField txtClass = new JTextField("Guerreiro");
        JTextField txtLevel = new JTextField("1");
        JTextField txtInitiative = new JTextField("10");

        panel.add(new JLabel("Nome:")); panel.add(txtName);
        panel.add(new JLabel("HP Max:")); panel.add(txtHp);
        panel.add(new JLabel("MP Max:")); panel.add(txtMp);
        panel.add(new JLabel("Classe:")); panel.add(txtClass);
        panel.add(new JLabel("Nível:")); panel.add(txtLevel);
        panel.add(new JLabel("Iniciativa:")); panel.add(txtInitiative);

        JButton btnSave = new JButton("Salvar Herói");
        btnSave.addActionListener(e -> {
            try {
                Character c = new Character();
                c.setName(txtName.getText());
                c.setHpMax(Integer.parseInt(txtHp.getText()));
                c.setHpCurrent(c.getHpMax()); // Começa cheio
                c.setMpMax(Integer.parseInt(txtMp.getText()));
                c.setMpCurrent(c.getMpMax());
                c.setLevel(Integer.parseInt(txtLevel.getText()));
                c.setCharacterClass(txtClass.getText());
                c.setInitiative(Integer.parseInt(txtInitiative.getText()));

                entityService.saveCharacter(c);
                JOptionPane.showMessageDialog(this, "Herói salvo com sucesso!");
                txtName.setText(""); // Limpa campo
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        panel.add(new JLabel(""));
        panel.add(btnSave);
        return panel;
    }

    private JPanel createEnemyPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtName = new JTextField();
        JTextField txtHp = new JTextField("80");
        JTextField txtType = new JTextField("Orc");
        JTextField txtLevel = new JTextField("1");
        JTextField txtInitiative = new JTextField("10");

        panel.add(new JLabel("Nome:")); panel.add(txtName);
        panel.add(new JLabel("HP Max:")); panel.add(txtHp);
        panel.add(new JLabel("Tipo:")); panel.add(txtType);
        panel.add(new JLabel("Nível:")); panel.add(txtLevel);
        panel.add(new JLabel("Iniciativa:")); panel.add(txtInitiative);

        JButton btnSave = new JButton("Salvar Inimigo");
        btnSave.addActionListener(e -> {
            try {
                Enemy en = new Enemy();
                en.setName(txtName.getText());
                en.setHpMax(Integer.parseInt(txtHp.getText()));
                en.setHpCurrent(en.getHpMax());
                en.setMpMax(0); en.setMpCurrent(0);
                en.setEnemyType(txtType.getText());
                en.setLevel(Integer.parseInt(txtLevel.getText()));
                en.setInitiative(Integer.parseInt(txtInitiative.getText()));

                entityService.saveEnemy(en);
                JOptionPane.showMessageDialog(this, "Inimigo salvo com sucesso!");
                txtName.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        });

        panel.add(new JLabel(""));
        panel.add(btnSave);
        return panel;
    }
}