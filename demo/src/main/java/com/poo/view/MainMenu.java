package com.poo.view;

import javax.swing.*;
import java.awt.*;
import com.poo.service.EntityService;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("RPG Manager - Menu Principal");
        setSize(300, 320); // Aumentei um pouco a altura
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10)); // Mudei para 4 linhas

        JButton btnCreate = new JButton("Cadastrar Entidades");
        JButton btnAbility = new JButton("Gerenciar Habilidades");
        JButton btnCombat = new JButton("Iniciar Combate");
        JButton btnGenData = new JButton("⚠️ Gerar 100k Inimigos");
        btnGenData.setForeground(Color.RED);

        btnCreate.addActionListener(e -> new CreateEntityWindow().setVisible(true));
        btnAbility.addActionListener(e -> new AbilityWindow().setVisible(true));
        btnCombat.addActionListener(e -> new SelectCombatWindow().setVisible(true));

        // Lógica do botão de carga
        btnGenData.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Isso vai inserir 100.000 inimigos no banco.\nPode demorar um pouco. Deseja continuar?",
                    "Teste de Carga", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                new Thread(() -> {
                    btnGenData.setEnabled(false);
                    btnGenData.setText("Processando...");

                    EntityService service = new EntityService();
                    long inicio = System.currentTimeMillis();

                    service.generateMassiveData(100000); // 100k

                    long fim = System.currentTimeMillis();
                    long tempo = (fim - inicio) / 1000;

                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Concluído em " + tempo + " segundos!");
                        btnGenData.setText("Gerar 100k Inimigos");
                        btnGenData.setEnabled(true);
                    });
                }).start();
            }
        });

        add(btnCreate);
        add(btnAbility);
        add(btnCombat);
        add(btnGenData);
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