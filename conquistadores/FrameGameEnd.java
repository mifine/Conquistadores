/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conquistadores;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mifine
 */
public class FrameGameEnd extends JFrame {
   
    /*
     * Frame that will display the end level to the player, with scores, and let him choose the next action
     */
    public FrameGameEnd(int score1, int score2) {

        JPanel centerP = new JPanel();
        JPanel southP = new JPanel();
        JLabel messageSpace, messageScore, message1, message2;
        JButton button1, button2;

        this.setTitle("Conquistadors - Fin du niveau");
        this.setSize(500, 250);

        centerP.setLayout(new GridLayout(5, 1));

        if (score1 >= score2) {
            message1 = new JLabel("BRAVO ! Vous avez gagné et terminé ce niveau !");
            message2 = new JLabel("Voulez vous continuer et passer au niveau suivant ?");
            button1 = new JButton("Continuer au niveau " + (int)(Game.GM.getCurrentGameLevel() + 1));
            button1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    buttonNextLevelActionPerformed(evt);
                }
            });
        } else {
            message1 = new JLabel("Vous avez perdu !");
            message2 = new JLabel("Voulez vous rejouer ?");
            button1 = new JButton("Rejouer");
            button1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    buttonReplayLevelActionPerformed(evt);
                }
            });
        }

        button2 = new JButton("Arrêter de jouer");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStopGameActionPerformed(evt);
            }
        });

        messageSpace = new JLabel("");
        messageScore = new JLabel("Scores: Vous = " + score1 + " points  / Ennemi = " + score2 + " points.");

        centerP.add(message2, JLabel.CENTER_ALIGNMENT);
        centerP.add(messageSpace, JLabel.CENTER);
        centerP.add(message1, JLabel.CENTER);
        centerP.add(messageSpace, JLabel.CENTER);

        centerP.add(messageScore, JLabel.CENTER);
        // centerP.add(messageSpace, JLabel.CENTER);
        centerP.add(new JLabel("***********************************     Fin du niveau     ***********************************"), JLabel.CENTER);

        southP.add(button1);
        southP.add(button2);

        this.add(new JLabel("    "), BorderLayout.WEST);
        this.add(centerP, BorderLayout.CENTER);
        this.add(southP, BorderLayout.SOUTH);
        
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e
            ) {
                Game.FG.endGameFrame.dispose();
                Game.FG.frameGame.dispose();
            }
        }
        );
        this.show();
    }
    
    private void buttonNextLevelActionPerformed(java.awt.event.ActionEvent evt) {
        Game.FG.endGameFrame.dispose();
        Game.FG.frameGame.dispose();
        Game.GM.launchLevel(Game.GM.getCurrentGameLevel() + 1);
        
    }
    
    private void buttonReplayLevelActionPerformed(java.awt.event.ActionEvent evt) {
        Game.FG.endGameFrame.dispose();
        Game.FG.frameGame.dispose();
        Game.GM.launchLevel(Game.GM.getCurrentGameLevel());
        
    }
    
    private void buttonStopGameActionPerformed(java.awt.event.ActionEvent evt) {
       Game.FG.endGameFrame.dispose();
       Game.FG.frameGame.dispose();
                
    }
}
