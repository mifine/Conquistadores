package conquistadores;

/**
 *
 * @author mifine
 */
import conquistadores.ui.FrameStart;
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
    private boolean isHuman;

    public GamePanel() {
        this.board.setLayout(new GridLayout(5, 5));
    }

    /*
     * Initialize the token placement at the begining of a new Game. clan = 1 :
     * 10 Player 1, clan = 0 : 5 Indigeneous clan = 2 : 10 Player 2
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
     * Set Player Color @param Color colorToSet
     */
    public void setPlayerColor(Color colorToSet) {
        this.playerColor = colorToSet;
    }

    /*
     * Populate GamePanel Grids with Cases
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
     * Populate North JPanel with Turn number - Player or Enemy Turn
     */
    public void populateNorthPanel() {
        String whoPlays = FrameStart.gameMecanics.getWhoPlaysFirst();
        JLabel turnLabel = new JLabel("Turn 1 - " + whoPlays + "'s turn");
        turnLabel.setFont(new Font("Verdana", 1, 20));
        this.north.add(turnLabel);
    }

    /*
     * Update North JPanel with new Turn number - Player or Enemy Turn
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
     * Populate South JPanel with ATTACK and SEND TROOPS buttons
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
     * South JPanel ATTACK button method
     */
    private void buttonAttackActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.canBeActioned(0)) {
            //    System.out.println(caseOrigin.posx + " " + caseOrigin.posy + " ATTACKS " + caseDestination.posx + " " + caseDestination.posy);
            if (FrameStart.gameMecanics.resolveAttack(this.caseOrigin, this.caseDestination)) {
                FrameStart.gameMecanics.closeActionTurn(this.caseOrigin, this.caseDestination, false);
            }
        }

    }

    /*
     * South JPanel SEND TROOPS button method
     */
    private void buttonSendActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.canBeActioned(1)) {
            //    System.out.println(caseOrigin.posx + " " + caseOrigin.posy + " SENDS TROOPS TO " + caseDestination.posx + " " + caseDestination.posy);
            if (FrameStart.gameMecanics.resolveSend(this.caseOrigin, this.caseDestination)) {
                FrameStart.gameMecanics.closeActionTurn(this.caseOrigin, this.caseDestination, false);
            }
        }
    }

    /*
     * South JPanel Check if a button action can be performed @param int action
     * = 0 if attack, = 1 if send
     */
    public boolean canBeActioned(int action) {
        boolean canBeActioned = false;
        if (this.isHuman) {
            Case[] casesPlayed;
            casesPlayed = this.determineOriginDestination();
            this.caseOrigin = casesPlayed[0];
            this.caseDestination = casesPlayed[1];
            switch (action) {
                case 0:
                    if (this.caseOrigin.getClan() != this.caseDestination.getClan()) {
                        canBeActioned = true;
                    } else {
                        canBeActioned = false;
                    }
                    break;
                case 1:
                    if (this.caseOrigin.getClan() == this.caseDestination.getClan()) {
                        canBeActioned = true;
                    } else {
                        canBeActioned = false;
                    }
                    break;
                default:
            }
        } else {
            canBeActioned = false;
        }
        return canBeActioned;
    }

    /*
     * Determine the cases origin and destination for a player action
     *
     * Returns an Array of Cases, array[0] = case origine array[1] = case
     * destination
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
     * Send a message alert to the player if an action can not be performed due
     * to a lack of troops, ...
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

    public void closeActionTurn(Case origin, Case destination) {
        origin.initialize();
        destination.initialize();
        this.resetAllCases();
        origin.repaint();
        destination.repaint();
    }

    /*
     * Update board display after IA action
     */
    public void updateDisplayIA(Case caseOrigin, Case caseDestination) {

        try {
            caseOrigin.setBackground(Color.LIGHT_GRAY);
            System.out.println("caseOrigin.setBackground(Color.LIGHT_GRAY);");
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();

        }

        try {
            caseDestination.setBackground(Color.LIGHT_GRAY);
            System.out.println("caseDestination.setBackground(Color.LIGHT_GRAY);");
            Thread.sleep(500);
        } catch (InterruptedException ex2) {
            Thread.currentThread().interrupt();
        }
        
        try {
            caseOrigin.repaint();
            caseDestination.repaint();
            System.out.println("Repaint");
            Thread.sleep(500);
        } catch (InterruptedException ex3) {
            Thread.currentThread().interrupt();
        }

    }

    /*
     * Send a message alert to the player if an action can not be performed due
     * to a lack of troops, ...
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
        alert.show();
    }

    // GET, SET
    public Case getCaseOrigin() {
        return this.caseOrigin;
    }

    public Case getCaseDestination() {
        return this.caseDestination;
    }

    public void setIsHuman(boolean b) {
        this.isHuman = b;
    }
}
