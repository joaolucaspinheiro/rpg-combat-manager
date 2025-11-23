package com.poo.view;

import com.poo.entity.Ability;
import com.poo.entity.Character;
import com.poo.entity.CombatEntity;
import com.poo.entity.Enemy;
import com.poo.enums.AbilityType;
import com.poo.service.CombatService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BattleWindow extends JFrame {

    // Serviços
    private CombatService combatService;

    // Entidades
    private Character player;
    private Enemy enemy;

    // Componentes da Tela
    private JLabel lblPlayerName;
    private JProgressBar barPlayerHP;
    private JProgressBar barPlayerMP;

    private JLabel lblEnemyName;
    private JProgressBar barEnemyHP;

    private JTextArea txtLog;
    private JButton btnAttack;
    private JButton btnAbility;

    public BattleWindow() {
        // 1. Configuração da Janela
        setTitle("RPG Combat Manager - Batalha");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Espaçamento entre componentes
        setLocationRelativeTo(null); // Centraliza na tela

        // 2. Inicializa Serviços e Dados (Mockados para teste rápido)
        initGameData();

        // 3. Painel Superior (Área Visual dos Lutadores)
        JPanel pnlArena = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlArena.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- LADO DO JOGADOR ---
        JPanel pnlPlayer = new JPanel(new GridLayout(0, 1, 5, 5));
        pnlPlayer.setBorder(BorderFactory.createTitledBorder("Herói"));

        lblPlayerName = new JLabel(player.getName());
        lblPlayerName.setFont(new Font("Arial", Font.BOLD, 18));
        lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);

        barPlayerHP = createProgressBar(Color.RED, player.getHpMax());
        barPlayerMP = createProgressBar(Color.BLUE, player.getMpMax());

        pnlPlayer.add(lblPlayerName);
        pnlPlayer.add(new JLabel("HP:"));
        pnlPlayer.add(barPlayerHP);
        pnlPlayer.add(new JLabel("MP:"));
        pnlPlayer.add(barPlayerMP);

        // --- LADO DO INIMIGO ---
        JPanel pnlEnemy = new JPanel(new GridLayout(0, 1, 5, 5));
        pnlEnemy.setBorder(BorderFactory.createTitledBorder("Inimigo"));

        lblEnemyName = new JLabel(enemy.getName());
        lblEnemyName.setFont(new Font("Arial", Font.BOLD, 18));
        lblEnemyName.setHorizontalAlignment(SwingConstants.CENTER);
        lblEnemyName.setForeground(Color.RED);

        barEnemyHP = createProgressBar(Color.RED, enemy.getHpMax());

        pnlEnemy.add(lblEnemyName);
        pnlEnemy.add(new JLabel("HP:"));
        pnlEnemy.add(barEnemyHP);
        // Inimigo visualmente não precisa mostrar MP agora

        pnlArena.add(pnlPlayer);
        pnlArena.add(pnlEnemy);
        add(pnlArena, BorderLayout.NORTH);

        // 4. Painel Central (Log de Combate)
        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Registro de Combate"));
        add(scrollLog, BorderLayout.CENTER);

        // 5. Painel Inferior (Botões)
        JPanel pnlActions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        btnAttack = new JButton("ATAQUE FÍSICO");
        btnAbility = new JButton("USAR HABILIDADE");

        // Estilizando botões
        Dimension btnSize = new Dimension(180, 50);
        btnAttack.setPreferredSize(btnSize);
        btnAbility.setPreferredSize(btnSize);

        pnlActions.add(btnAttack);
        pnlActions.add(btnAbility);
        add(pnlActions, BorderLayout.SOUTH);

        // 6. Configurando Ações (Eventos)
        btnAttack.addActionListener(e -> performPlayerAttack());
        btnAbility.addActionListener(e -> showAbilityDialog());

        // Atualiza a tela inicial
        updateUI();
        log("Combate iniciado! " + player.getName() + " (Lvl " + player.getLevel() + ") encontrou " + enemy.getName() + "!");
    }

    // Cria uma barra de progresso colorida
    private JProgressBar createProgressBar(Color color, int max) {
        JProgressBar bar = new JProgressBar(0, max);
        bar.setValue(max);
        bar.setStringPainted(true); // Mostra o numero ex: 100/100
        bar.setForeground(color);
        return bar;
    }

    private void initGameData() {
        combatService = new CombatService();

        // Criando dados na memória para testar a interface sem depender do banco agora
        player = new Character();
        player.setName("Lucas, o Mago");
        player.setHpMax(100);
        player.setHpCurrent(100);
        player.setMpMax(100);
        player.setMpCurrent(100);
        player.setLevel(1);
        player.setAbilities(new ArrayList<>());

        Ability fireball = new Ability();
        fireball.setName("Bola de Fogo");
        fireball.setMpCost(20);
        fireball.setBaseDamage(35);
        fireball.setType(AbilityType.DAMAGE);
        player.getAbilities().add(fireball);

        Ability heal = new Ability();
        heal.setName("Cura Menor");
        heal.setMpCost(15);
        heal.setBaseDamage(25); // Valor da cura
        heal.setType(AbilityType.HEAL);
        player.getAbilities().add(heal);

        enemy = new Enemy();
        enemy.setName("Goblin Saqueador");
        enemy.setHpMax(80);
        enemy.setHpCurrent(80);

        // Configura o serviço de combate
        List<CombatEntity> participants = new ArrayList<>();
        participants.add(player);
        participants.add(enemy);
        combatService.startCombat(participants);
    }

    private void performPlayerAttack() {
        // Jogador Ataca
        String result = combatService.performAttack(player, enemy, 10); // Dano base da arma
        log("[JOGADOR] " + result);

        updateUI();
        if (checkCombatEnd()) return;

        // Turno do Inimigo (pequeno delay para parecer natural)
        enemyTurn();
    }

    private void enemyTurn() {
        // Simulação simples: inimigo ataca de volta
        SwingUtilities.invokeLater(() -> {
            String result = combatService.performAttack(enemy, player, 8); // Dano do inimigo
            log("[INIMIGO] " + result);
            updateUI();
            checkCombatEnd();
        });
    }

    private void showAbilityDialog() {
        List<Ability> abilities = player.getAbilities();
        if (abilities.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Você não tem habilidades!");
            return;
        }

        // Cria array de opções para o popup
        String[] options = new String[abilities.size()];
        for (int i = 0; i < abilities.size(); i++) {
            Ability a = abilities.get(i);
            options[i] = a.getName() + " (" + a.getMpCost() + " MP)";
        }

        int choice = JOptionPane.showOptionDialog(this,
                "Escolha uma habilidade:",
                "Grimório de Habilidades",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (choice >= 0) {
            Ability selected = abilities.get(choice);

            // Verifica MP antes de chamar o serviço para dar feedback visual rápido
            if (player.getMpCurrent() < selected.getMpCost()) {
                JOptionPane.showMessageDialog(this, "Mana insuficiente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Define o alvo (se for cura, alvo é si mesmo; se dano, é o inimigo)
            CombatEntity target = (selected.getType() == AbilityType.HEAL) ? player : enemy;

            String result = combatService.useAbility(player, target, selected, selected.getBaseDamage());
            log("[MAGIA] " + result);

            updateUI();
            if (checkCombatEnd()) return;

            enemyTurn();
        }
    }

    private void updateUI() {
        // Atualiza Jogador
        barPlayerHP.setValue(player.getHpCurrent());
        barPlayerHP.setString(player.getHpCurrent() + "/" + player.getHpMax());

        barPlayerMP.setValue(player.getMpCurrent());
        barPlayerMP.setString(player.getMpCurrent() + "/" + player.getMpMax());

        // Atualiza Inimigo
        barEnemyHP.setValue(enemy.getHpCurrent());
        barEnemyHP.setString(enemy.getHpCurrent() + "/" + enemy.getHpMax());
    }

    private void log(String message) {
        txtLog.append(message + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength()); // Rola log para baixo
    }

    private boolean checkCombatEnd() {
        if (player.getHpCurrent() <= 0) {
            JOptionPane.showMessageDialog(this, "GAME OVER! Você caiu em combate.");
            System.exit(0); // Fecha o jogo
            return true;
        } else if (enemy.getHpCurrent() <= 0) {
            JOptionPane.showMessageDialog(this, "VITÓRIA! O inimigo foi derrotado.");
            System.exit(0); // Fecha o jogo
            return true;
        }
        return false;
    }

    // Método main para rodar direto a janela
    public static void main(String[] args) {
        // LookVerify and Feel do sistema operacional (fica mais bonito)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new BattleWindow().setVisible(true);
        });
    }
}