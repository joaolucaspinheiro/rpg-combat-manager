package com.poo.view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("RPG Manager - Menu Principal");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnCreate = new JButton("Cadastrar Entidades");
        JButton btnAbility = new JButton("Gerenciar Habilidades");
        JButton btnCombat = new JButton("Iniciar Combate");

        btnCreate.addActionListener(e -> new CreateEntityWindow().setVisible(true));
        btnAbility.addActionListener(e -> new AbilityWindow().setVisible(true));
        btnCombat.addActionListener(e -> new SelectCombatWindow().setVisible(true));

        add(btnCreate);
        add(btnAbility);
        add(btnCombat);
    }

    public static void main(String[] args) {
        // Configura visual nativo do Windows
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}