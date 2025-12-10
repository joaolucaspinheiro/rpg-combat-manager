package com.poo.view;

import com.poo.entity.Ability;
import com.poo.entity.CombatEntity;
import com.poo.entity.Character;
import com.poo.enums.AbilityType;
import com.poo.service.CombatService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BattleWindow extends JFrame {

    private CombatService combatService;
    private CombatEntity currentEntity; // Quem está agindo agora
    private List<CombatEntity> allParticipants; // Referência para escolha de alvos

    // Componentes UI
    private JLabel lblTurno;
    private JTextArea txtLog;
    private JPanel pnlParticipants; // Grid visual
    private JButton btnAttack, btnAbility, btnPass;

    public BattleWindow(List<CombatEntity> participants) {
        this.allParticipants = participants;
        this.combatService = new CombatService();

        setTitle("Mesa de Combate - Game Master Mode");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Inicializa o serviço e ordena a lista circular
        combatService.startCombat(participants);

        // 2. Painel Topo (Quem está agindo)
        JPanel pnlHeader = new JPanel(new FlowLayout());
        pnlHeader.setBackground(Color.DARK_GRAY);
        lblTurno = new JLabel("Aguardando início...");
        lblTurno.setForeground(Color.WHITE);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 24));
        pnlHeader.add(lblTurno);
        add(pnlHeader, BorderLayout.NORTH);

        // 3. Painel Central (Visualização de Status em Grid)
        pnlParticipants = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 colunas, N linhas
        pnlParticipants.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(new JScrollPane(pnlParticipants), BorderLayout.CENTER);

        // 4. Painel Lateral (Log)
        txtLog = new JTextArea(20, 30);
        txtLog.setEditable(false);
        add(new JScrollPane(txtLog), BorderLayout.EAST);

        // 5. Painel Inferior (Controles do GM)
        JPanel pnlControls = new JPanel(new FlowLayout());
        btnAttack = new JButton("Ataque Básico");
        btnAbility = new JButton("Usar Habilidade");
        btnPass = new JButton("Passar Turno / Próximo");

        Dimension btnSz = new Dimension(160, 50);
        btnAttack.setPreferredSize(btnSz);
        btnAbility.setPreferredSize(btnSz);
        btnPass.setPreferredSize(btnSz);

        pnlControls.add(btnAttack);
        pnlControls.add(btnAbility);
        pnlControls.add(btnPass);
        add(pnlControls, BorderLayout.SOUTH);

        // 6. Ações
        btnPass.addActionListener(e -> nextTurn());
        btnAttack.addActionListener(e -> gmPerformAttack());
        btnAbility.addActionListener(e -> gmPerformAbility());

        // Inicia o primeiro turno
        updateParticipantGrid();
        nextTurn();
    }

    private void nextTurn() {
        // Verifica se o combate acabou
        int livingCount = 0;
        for(CombatEntity c : allParticipants) if(c.getHpCurrent() > 0) livingCount++;

        if(livingCount <= 1) {
            JOptionPane.showMessageDialog(this, "Combate Encerrado!");
            dispose();
            return;
        }

        // Pega o próximo da Lista Circular
        currentEntity = combatService.nextTurn();

        // Se pegou alguém morto, pula automaticamente
        while(currentEntity.getHpCurrent() <= 0) {
            currentEntity = combatService.nextTurn();
        }

        // Atualiza UI
        String type = (currentEntity instanceof Character) ? "Herói" : "Inimigo";
        lblTurno.setText("Turno de: " + currentEntity.getName() + " (" + type + ")");
        log("--- Turno de " + currentEntity.getName() + " ---");

        // Destaca visualmente na grid
        updateParticipantGrid();
    }

    private void gmPerformAttack() {
        CombatEntity target = pickTarget();
        if (target != null) {
            String res = combatService.performAttack(currentEntity, target, 10);
            log(res);
            updateParticipantGrid();
        }
    }

    private void gmPerformAbility() {
        if (currentEntity.getAbilities().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Esta entidade não possui habilidades!");
            return;
        }

        // 1. Escolher Habilidade
        Ability[] abilities = currentEntity.getAbilities().toArray(new Ability[0]);
        String[] options = new String[abilities.length];
        for (int i=0; i<abilities.length; i++) options[i] = abilities[i].getName() + " ("+abilities[i].getMpCost()+" MP)";

        int choice = JOptionPane.showOptionDialog(this, "Escolha a Habilidade:", "Grimório",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice < 0) return;
        Ability selectedAbility = abilities[choice];

        // 2. Escolher Alvo
        CombatEntity target = pickTarget();
        if (target != null) {
            String res = combatService.useAbility(currentEntity, target, selectedAbility, selectedAbility.getBaseDamage());
            log(res);
            updateParticipantGrid();
        }
    }

    private CombatEntity pickTarget() {
        // Filtra apenas vivos para serem alvos
        List<CombatEntity> livingTargets = allParticipants.stream()
                .filter(e -> e.getHpCurrent() > 0)
                .toList();

        CombatEntity[] options = livingTargets.toArray(new CombatEntity[0]);

        // Mostra popup para o GM escolher quem vai sofrer a ação
        CombatEntity selected = (CombatEntity) JOptionPane.showInputDialog(
                this,
                "Selecione o Alvo da ação de " + currentEntity.getName() + ":",
                "Escolha de Alvo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        return selected;
    }

    // Atualiza os cartões visuais de todos os participantes
    private void updateParticipantGrid() {
        pnlParticipants.removeAll();

        for (CombatEntity e : allParticipants) {
            JPanel card = new JPanel(new GridLayout(4, 1));
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

            // Destaca se for o turno dele
            if (e == currentEntity) {
                card.setBackground(new Color(255, 255, 200)); // Amarelo claro
                card.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4));
            } else if (e.getHpCurrent() <= 0) {
                card.setBackground(Color.LIGHT_GRAY); // Morto
                card.setEnabled(false);
            }

            JLabel lblName = new JLabel(e.getName());
            lblName.setFont(new Font("Arial", Font.BOLD, 14));
            if (e instanceof Character) lblName.setForeground(Color.BLUE);
            else lblName.setForeground(Color.RED);

            JProgressBar hpBar = new JProgressBar(0, e.getHpMax());
            hpBar.setValue(e.getHpCurrent());
            hpBar.setString("HP: " + e.getHpCurrent());
            hpBar.setStringPainted(true);
            hpBar.setForeground(Color.RED);

            JProgressBar mpBar = new JProgressBar(0, e.getMpMax());
            mpBar.setValue(e.getMpCurrent());
            mpBar.setString("MP: " + e.getMpCurrent());
            mpBar.setStringPainted(true);
            mpBar.setForeground(Color.BLUE);

            card.add(lblName);
            card.add(hpBar);
            card.add(mpBar);


            card.add(new JLabel("Inic: " + e.getInitiative()));

            pnlParticipants.add(card);
        }

        pnlParticipants.revalidate();
        pnlParticipants.repaint();
    }

    private void log(String msg) {
        txtLog.append(msg + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }
}