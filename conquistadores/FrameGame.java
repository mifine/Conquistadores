package conquistadores;

/**
 *
 * @author mifine
 */
import static conquistadores.Game.GROUND_TYPE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Panel.*;
import java.awt.event.ActionListener.*;
import javax.swing.*;

public class FrameGame extends JFrame {

    private Color playerColor;
    private boolean isEnemyAI;
    public static JFrame frameGame;
    public static GamePanel boardGamePanel;
    public static GameMecanics gameMecanics;

    /**
     * Creates new form NewJFrame
     */
    public FrameGame() {
        this.playerColor = new Color(102, 102, 255);                            // This is the default color: blue
        this.isEnemyAI = true;                                                  // By default, play against IA

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                initComponents();
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        buttonNewGame = new JButton();
        buttonConsultScores = new JButton();
        buttonExit = new JButton();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuNewGame = new javax.swing.JMenuItem();
        jMenuExit = new javax.swing.JMenuItem();
        jMenuOptions = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jCheckBoxBlue = new JCheckBoxMenuItem();
        jCheckBoxOrange = new JCheckBoxMenuItem();
        jCheckBoxGreen = new JCheckBoxMenuItem();
        jCheckBoxPurple = new JCheckBoxMenuItem();
        jCheckBoxYellow = new JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuHuman = new JCheckBoxMenuItem();
        jCheckBoxMenuIA = new JCheckBoxMenuItem();
        jMenuInformations = new javax.swing.JMenu();
        jMenuScores = new javax.swing.JMenuItem();
        jMenuRules = new javax.swing.JMenuItem();
        jMenuAbout = new javax.swing.JMenuItem();
        jMenuOptionsConstants = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Welcome to Conquistadores, an epic conquest game. ");

        jLabel4.setText("You arrive on a new island at the same time as your worst ennemy.");

        jLabel3.setText("The island is already inhabited by some pacifist native people.");

        jLabel5.setText("Your goal is to prevent your opponent to expand and conquier more areas.");

        jLabel6.setText("If you can spare some native people lives, that will be nice as well ");

        jLabel7.setText("(but you will not gain any bonus for that).");

        buttonNewGame.setText("Start new game");
        buttonNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewGameActionPerformed(evt);
            }
        });

        buttonConsultScores.setText("Consult scores");
        buttonConsultScores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConsultScoresActionPerformed(evt);
            }
        });

        buttonExit.setText("EXIT");
        buttonExit.setPreferredSize(new java.awt.Dimension(109, 23));
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        jLabel2.setText("Copyright mifine @2013-2014");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(buttonNewGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonConsultScores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(19, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel5)
                                                .addComponent(jLabel3)
                                                .addComponent(jLabel4))
                                        .addGap(84, 84, 84))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel2))
                                        .addGap(35, 35, 35))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonNewGame)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonConsultScores)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2)
                        .addContainerGap())
        );

        jMenuFile.setText("File");

        jMenuNewGame.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuNewGame.setText("New Game");
        jMenuNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuNewGameActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuNewGame);

        jMenuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuExit.setText("Exit game");
        jMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuExit);

        jMenuBar.add(jMenuFile);

        jMenuOptions.setText("Options");

        jMenu1.setText("Change player color");

        jCheckBoxBlue.setSelected(true);
        jCheckBoxBlue.setText("Blue");
        jCheckBoxBlue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/blue.jpg"))); // NOI18N
        jCheckBoxBlue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxBlueActionPerformed(evt);
            }
        });
        jMenu1.add(jCheckBoxBlue);

        jCheckBoxOrange.setText("Orange");
        jCheckBoxOrange.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/orange.jpg"))); // NOI18N
        jCheckBoxOrange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOrangeActionPerformed(evt);
            }
        });
        jMenu1.add(jCheckBoxOrange);

        jCheckBoxGreen.setText("Green");
        jCheckBoxGreen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/green.jpg"))); // NOI18N
        jCheckBoxGreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxGreenActionPerformed(evt);
            }
        });
        jMenu1.add(jCheckBoxGreen);

        jCheckBoxPurple.setText("Purple");
        jCheckBoxPurple.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/purple.jpg"))); // NOI18N
        jCheckBoxPurple.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPurpleActionPerformed(evt);
            }
        });
        jMenu1.add(jCheckBoxPurple);

        jCheckBoxYellow.setText("Yellow");
        jCheckBoxYellow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/yellow.jpg"))); // NOI18N
        jCheckBoxYellow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxYellowActionPerformed(evt);
            }
        });
        jMenu1.add(jCheckBoxYellow);

        jMenuOptions.add(jMenu1);
        jMenuOptions.add(jSeparator1);

        jCheckBoxMenuHuman.setText("Play against Human");
        jCheckBoxMenuHuman.setToolTipText("");
        jCheckBoxMenuHuman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuHumanActionPerformed(evt);
            }
        });
        jMenuOptions.add(jCheckBoxMenuHuman);

        jCheckBoxMenuIA.setSelected(true);
        jCheckBoxMenuIA.setText("Play against IA");
        jCheckBoxMenuIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuIAActionPerformed(evt);
            }
        });
        jMenuOptions.add(jCheckBoxMenuIA);

        jMenuBar.add(jMenuOptions);
        jMenuOptions.add(jSeparator2);

        jMenuOptionsConstants.setText("Options");
        jMenuOptionsConstants.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOptionsConstantsActionPerformed(evt);
            }
        });
        jMenuOptions.add(jMenuOptionsConstants);

        jMenuInformations.setText("Informations");

        jMenuScores.setText("Scores");
        jMenuScores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuScoresActionPerformed(evt);
            }
        });
        jMenuInformations.add(jMenuScores);

        jMenuRules.setText("Rules");
        jMenuRules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuRulesActionPerformed(evt);
            }
        });
        jMenuInformations.add(jMenuRules);

        jMenuAbout.setText("About ...");
        jMenuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuAboutActionPerformed(evt);
            }
        });
        jMenuInformations.add(jMenuAbout);

        jMenuBar.add(jMenuInformations);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 592, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 415, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuExitActionPerformed

    private void jMenuNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuNewGameActionPerformed
        this.launchNewGame();
    }//GEN-LAST:event_jMenuNewGameActionPerformed

    private void jMenuScoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuScoresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuScoresActionPerformed

    private void jCheckBoxMenuHumanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuHumanActionPerformed
        jCheckBoxMenuIA.setSelected(false);
        this.isEnemyAI = false;
    }//GEN-LAST:event_jCheckBoxMenuHumanActionPerformed

    private void jCheckBoxMenuIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuIAActionPerformed
        jCheckBoxMenuHuman.setSelected(false);
        this.isEnemyAI = true;
    }//GEN-LAST:event_jCheckBoxMenuIAActionPerformed

    private void jMenuRulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRulesActionPerformed
        FrameRules frameRules = new FrameRules();
        frameRules.setTitle("Rules");
        frameRules.setSize(730, 500);
        frameRules.setLocationRelativeTo(null);
        frameRules.show();
    }//GEN-LAST:event_jMenuRulesActionPerformed

    private void jMenuOptionsConstantsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuRulesActionPerformed
        FrameOptions frameOptions = new FrameOptions();
        frameOptions.setTitle("Options");
        frameOptions.setSize(450, 370);
        frameOptions.setLocationRelativeTo(null);
        frameOptions.show();
    }//GEN-LAST:event_jMenuOptionsConstantsActionPerformed

    private void jMenuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuAboutActionPerformed
        FrameAbout frameAbout = new FrameAbout();
        frameAbout.setTitle("About");
        frameAbout.setSize(310, 280);
        frameAbout.setLocationRelativeTo(null);
        frameAbout.show();
    }//GEN-LAST:event_jMenuAboutActionPerformed

    private void buttonNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNewGameActionPerformed
        this.launchNewGame();
    }//GEN-LAST:event_buttonNewGameActionPerformed

    private void buttonConsultScoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonConsultScoresActionPerformed
        String[] highScores = Game.sm.getHighscores();
        this.showHighScores(highScores, 500, 400);

    }//GEN-LAST:event_buttonConsultScoresActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonExitActionPerformed

    private void jCheckBoxOrangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOrangeActionPerformed
        this.changePlayerColor("orange");
    }//GEN-LAST:event_jCheckBoxOrangeActionPerformed

    private void jCheckBoxBlueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxBlueActionPerformed
        this.changePlayerColor("blue");
    }//GEN-LAST:event_jCheckBoxBlueActionPerformed

    private void jCheckBoxGreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxGreenActionPerformed
        this.changePlayerColor("green");
    }//GEN-LAST:event_jCheckBoxGreenActionPerformed

    private void jCheckBoxPurpleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPurpleActionPerformed
        this.changePlayerColor("purple");
    }//GEN-LAST:event_jCheckBoxPurpleActionPerformed

    private void jCheckBoxYellowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxYellowActionPerformed
        this.changePlayerColor("yellow");
    }//GEN-LAST:event_jCheckBoxYellowActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton buttonConsultScores;
    private JButton buttonExit;
    private JButton buttonNewGame;
    private JCheckBoxMenuItem jCheckBoxBlue;
    private JCheckBoxMenuItem jCheckBoxGreen;
    private JCheckBoxMenuItem jCheckBoxMenuHuman;
    private JCheckBoxMenuItem jCheckBoxMenuIA;
    private JCheckBoxMenuItem jCheckBoxOrange;
    private JCheckBoxMenuItem jCheckBoxPurple;
    private JCheckBoxMenuItem jCheckBoxYellow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuAbout;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuExit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuInformations;
    private javax.swing.JMenuItem jMenuNewGame;
    private javax.swing.JMenu jMenuOptions;
    private javax.swing.JMenuItem jMenuRules;
    private javax.swing.JMenuItem jMenuScores;
    private javax.swing.JMenuItem jMenuOptionsConstants;
    private JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables

    private void changePlayerColor(String color) {
        if (color != "blue") {
            this.jCheckBoxBlue.setSelected(false);
        }
        if (color != "orange") {
            this.jCheckBoxOrange.setSelected(false);
        }
        if (color != "yellow") {
            this.jCheckBoxYellow.setSelected(false);
        }
        if (color != "purple") {
            this.jCheckBoxPurple.setSelected(false);
        }
        if (color != "green") {
            this.jCheckBoxGreen.setSelected(false);
        }
        switch (color) {
            case "blue":
                this.playerColor = new Color(102, 102, 255);
                break;
            case "orange":
                this.playerColor = new Color(255, 153, 102);
                break;
            case "yellow":
                this.playerColor = new Color(255, 255, 102);
                break;
            case "purple":
                this.playerColor = new Color(255, 102, 255);
                break;
            case "green":
                this.playerColor = new Color(102, 255, 102);
                break;
            default:
        }

    }

    private void launchNewGame() {

        frameGame = new JFrame();
        gameMecanics = new GameMecanics();
        gameMecanics.setIsEnemyAi(this.isEnemyAI);
        boardGamePanel = new GamePanel();

        frameGame.setTitle("Conquistadors");
        frameGame.setSize(700, 500);
        frameGame.setLocationRelativeTo(null);
        frameGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        boardGamePanel.setPlayerColor(this.playerColor);

        frameGame.add(boardGamePanel.board, BorderLayout.CENTER);
        frameGame.add(boardGamePanel.south, BorderLayout.SOUTH);
        frameGame.add(boardGamePanel.north, BorderLayout.NORTH);

        gameMecanics.setSecondPlayer(this.isEnemyAI);
        this.setGroundColors();

        boardGamePanel.initGamePanel();

        frameGame.show();
        gameMecanics.startGame();

    }

    private static void setGroundColors() {
        if (Game.GROUND_TYPE == 1) {
            Game.GROUND_COLOR[0] = Game.GROUND_COLOR[1] = Game.GROUND_COLOR[2] = Color.DARK_GRAY;
        } else if (GROUND_TYPE == 2) {
            Game.GROUND_COLOR[0] = new Color(154, 205, 50);                              // Forest : +2 DEF
            Game.GROUND_COLOR[1] = new Color(238, 221, 130);                             // Plain : NO BONUS
            Game.GROUND_COLOR[2] = new Color(119, 136, 153);                             // Mountain : +2 ATT
            Game.GROUND_COLOR[3] = new Color(161, 218, 28);                              // Swamp : not used yet
            Game.GROUND_COLOR[4] = new Color(161, 218, 28);                              // Desert : not used yet
        }
    }

    /*
     * Send a message alert to the player if an action can not be performed due
     * to a lack of troops, ...
     */
    public void showHighScores(String[] highscores, int xsize, int ysize) {
        JFrame alert = new JFrame();
        int size = highscores.length;

        alert.setTitle("Conquistadors - High scores");
        alert.setSize(xsize, ysize);

        JPanel centerP = new JPanel();
        centerP.setLayout(new GridLayout(size + 2, 1));
        for (int i = 0; i < size; i++) {
            centerP.add(new JLabel(highscores[size - i - 1]), JLabel.CENTER);
        }
        centerP.add(new JLabel("************************************     High Scores     ************************************"), JLabel.CENTER);

        alert.add(new JLabel("    "), BorderLayout.WEST);
        alert.add(centerP, BorderLayout.CENTER);

        alert.setLocationRelativeTo(null);
        alert.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        alert.show();
    }

}
