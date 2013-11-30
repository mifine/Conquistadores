package conquistadores;

/**
 *
 * @author mifine
 */
import java.awt.*;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class GamePanel extends JPanel {

    public static Color playerColor;

    public int[] selectedCase = new int[2];

    public JPanel board = new JPanel();
    public JPanel south = new JPanel();
    public JPanel north = new JPanel();
    public Case grid[][] = new Case[5][5];

    private int i, j;
    public Case caseOrigin;
    public Case caseDestination;

    public GamePanel() {
        this.board.setLayout(new GridLayout(5, 5));
    }

    /*
     Initialize the token placement at the begining of a new Game. 
     clan = 1 : 10 Player 1, 
     clan = 0 : 5 Indigeneous
     clan = 2 : 10 Player 2
     */
    public void InitGamePanel() {

        this.populateCenterPanel();
        this.populateNorthPanel();
        this.populateSouthPanel();

        // Build the first grid to play
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (j < 2) {
                    this.grid[i][j].setClan(1);
                    this.grid[i][j].setClanColor(this.playerColor);
                }
                if (j == 2) {
                    this.grid[i][j].setClan(0);
                    this.grid[i][j].setClanColor(new Color(218, 165, 32));
                }
                if (j > 2) {
                    this.grid[i][j].setClan(2);
                    this.grid[i][j].setClanColor(new Color(255, 51, 51));
                }
                this.grid[i][j].repaint();
            }
        }
    }

    /*
     Set Player Color
     @param Color colorToSet
     */
    public void setPlayerColor(Color colorToSet) {
        this.playerColor = colorToSet;
    }

    /*
     Populate GamePanel Grids with Cases
     */
    public void populateCenterPanel() {
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                this.grid[i][j] = new Case(i, j);
                this.grid[i][j].setBackground(Color.DARK_GRAY);
                this.grid[i][j].setBackground(Color.DARK_GRAY);
                this.board.add(grid[i][j]);
            }
        }
    }

    /*
     Populate North JPanel with Turn number - Player or Enemy Turn
     */
    public void populateNorthPanel() {
        String whoPlays = FrameStart.gameMecanics.getWhoPlaysFirst();
        JLabel turnLabel = new JLabel("Turn 1 - " + whoPlays + "'s turn");
        turnLabel.setFont(new Font("Verdana", 1, 20));
        this.north.add(turnLabel);
    }

    /*
     Update North JPanel with new Turn number - Player or Enemy Turn
     */
    public void updateNorthPanel(int turnNb, String whoPlays) {
        this.north.remove(this.north.getComponent(0));
        this.north.revalidate();
        this.north.repaint();
        JLabel turnLabel = new JLabel("Turn " + turnNb + " - " + whoPlays + "'s turn");
        turnLabel.setFont(new Font("Verdana", 1, 20));
        this.north.add(turnLabel);
    }

    /*
     Populate South JPanel with ATTACK and SEND TROOPS buttons
     */
    public void populateSouthPanel() {
        JButton buttonAttack = new JButton("ATTACK");
        JButton buttonSend = new JButton("SEND TROOPS");

        buttonAttack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAttackActionPerformed(evt);
            }
        });

        buttonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendActionPerformed(evt);
            }
        });

        this.south.add(buttonAttack);
        this.south.add(buttonSend);
    }

    /*
     South JPanel ATTACK button method
     */
    private void buttonAttackActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.canBeActioned("attack")) {
            //    System.out.println(caseOrigin.posx + " " + caseOrigin.posy + " ATTACKS " + caseDestination.posx + " " + caseDestination.posy);
            if (FrameStart.gameMecanics.resolveAttack()) {
                FrameStart.gameMecanics.closeActionTurn();
            }
        }

    }

    /*
     South JPanel SEND TROOPS button method
     */
    private void buttonSendActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.canBeActioned("send")) {
            //    System.out.println(caseOrigin.posx + " " + caseOrigin.posy + " SENDS TROOPS TO " + caseDestination.posx + " " + caseDestination.posy);
            if (FrameStart.gameMecanics.resolveSendTroops()) {
                FrameStart.gameMecanics.closeActionTurn();
            }
        }
    }

    /*
     * South JPanel Check if a button action can be performed
     */
    public boolean canBeActioned(String action) {
        boolean canBeActioned = false;
        Case[] casesPlayed;
        casesPlayed = this.determineOriginDestination();
        this.caseOrigin = casesPlayed[0];
        this.caseDestination = casesPlayed[1];
        switch (action) {
            case "attack":
                if (this.caseOrigin.getClan() != this.caseDestination.getClan()) {
                    canBeActioned = true;
                } else {
                    canBeActioned = false;
                }
                break;
            case "send":
                if (this.caseOrigin.getClan() == this.caseDestination.getClan()) {
                    canBeActioned = true;
                } else {
                    canBeActioned = false;
                }
                break;
            default:
        }
        return canBeActioned;
    }

    /*
     * Determine the cases origin and destination for a player action
     *
     * Returns an Array of Cases, 
     * array[0] = case origine
     * array[1] = case destination
     */
    public Case[] determineOriginDestination() {
        Case[] casesPlayed = new Case[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.grid[0][0].caseSelected[i][j] == 1) {
                    casesPlayed[0] = this.grid[i][j];
                }
                if (this.grid[0][0].caseSelected[i][j] == 2) {
                    casesPlayed[1] = this.grid[i][j];
                }
            }
        }
        return casesPlayed;
    }

    /*
     * Reset all Cases after an action.
     */
    public void resetAllCases() {

        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                this.grid[i][j].setNbCasesSelected(0);
                this.grid[i][j].setCanBeSelected(true);
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        this.grid[i][j].caseSelected[k][l] = 0;
                    }
                }
            }
        }
    }


    /*
     * Send a message alert to the player if an action can not be performed due to a lack of troops, ...
     */
    public void sendAlert(String message, int xsize, int ysize) {
        final Frame alert = new Frame();
        JLabel alertLabel = new JLabel(message);
        alert.setTitle("Conquistadors");
        alert.setSize(xsize, ysize);
        alert.add(alertLabel);
        alert.setLocationRelativeTo(null);
        alert.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                alert.dispose();
            }
        });

        alert.show();
    }
    /*
     * Close an action turn
     */

    public void closeActionTurn() {
        this.caseOrigin.initialize();
        this.caseDestination.initialize();
        this.resetAllCases();
        this.caseOrigin.repaint();
        this.caseDestination.repaint();
    }

    /*
     * Send a message alert to the player if an action can not be performed due to a lack of troops, ...
     */
    public void showFinalScore(int score1, int score2) {
        final Frame alert = new Frame();
        JLabel playerScoreLabel = new JLabel("Scores: Player 1 = " + score1 + " / Player 2 = " + score2);
        // JLabel enemyScoreLabel = new JLabel("Enemy score = " + this.finalScore[2]);
        // JLabel nativeScoreLabel = new JLabel("Enemy score = " + this.finalScore[0]);
        alert.setTitle("Conquistadors");
        alert.setSize(260, 90);
        alert.add(playerScoreLabel);
        //   alert.add(enemyScoreLabel);
        // alert.add(nativeScoreLabel);
        alert.setLocationRelativeTo(null);
        alert.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                alert.dispose();
                FrameStart.frameGame.dispose();
            }
        });

//        try {
//            this.finalize(); 
//                    } catch (InterruptedException exep) {
//                        Thread.currentThread().interrupt();
//                    }
        alert.show();

    }

    // GET, SET
    public Case getCaseOrigin() {
        return this.caseOrigin;
    }

    public Case getCaseDestination() {
        return this.caseDestination;
    }
}
