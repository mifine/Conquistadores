
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

    public Case grid[][] = new Case[5][5];
    public JPanel board = new JPanel();
    public Color playerColor;
    public int[] selectedCase = new int[2];
    public int turnNumber = 1;
    public int actionNumber = 1;
    static int maxTurnNumber = 3;
    static int maxActionsPerTurn = 3;
    static int maxTroopsPerCase = 13;
    public int[] finalScore = new int[3];

    public JPanel east = new JPanel();
    public JPanel west = new JPanel();
    public JPanel south = new JPanel();
    public JPanel north = new JPanel();
    int i, j;

    private Case caseOrigin;
    private Case caseDestination;

    public void Board() {
        this.board.setLayout(new GridLayout(5, 5));
        this.populateCenterPanel();
        this.populateNorthPanel();
        this.populateSouthPanel();
        this.west.setLayout(new GridLayout(5, 3));
        this.populateWestPanel();

    }

    /*
     Initialize the token placement at the begining of a new Game. 
     clan = 1 : 10 Player 1, 
     clan = 0 : 5 Indigeneous
     clan = 2 : 10 Player 2
     */
    public void InitPlacement() {

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

        //   this.startGame();
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
     Populate North JPanel with Player VS IA
     */
    public void populateNorthPanel() {
        JLabel label = new JLabel("Player VS IA");
        label.setFont(new Font("Verdana", 1, 20));
        this.north.add(label);
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
            if (this.resolveAttack()) {
                this.closeActionTurn();
            }
        }

    }

    /*
     South JPanel SEND TROOPS button method
     */
    private void buttonSendActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.canBeActioned("send")) {
            //    System.out.println(caseOrigin.posx + " " + caseOrigin.posy + " SENDS TROOPS TO " + caseDestination.posx + " " + caseDestination.posy);
            if (this.resolveSendTroops()) {
                this.closeActionTurn();
            }
        }
    }

    /*
     * South JPanel Check if a button action can be performed
     */
    private boolean canBeActioned(String action) {
        boolean canBeActioned = false;
        Case[] casesPlayed;
        casesPlayed = this.determineOriginDestination();
        this.caseOrigin = casesPlayed[0];
        this.caseDestination = casesPlayed[1];
        switch (action) {
            case "attack":
                if (caseOrigin.clan != caseDestination.clan) {
                    canBeActioned = true;
                } else {
                    canBeActioned = false;
                }
                break;
            case "send":
                if (caseOrigin.clan == caseDestination.clan) {
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
     * Close an action turn
     */
    private void closeActionTurn() {
        this.caseOrigin.initialize();
        this.caseDestination.initialize();
        this.resetAllCases();
        this.caseOrigin.repaint();
        this.caseDestination.repaint();
        this.actionNumber++;

        if (this.actionNumber > this.maxActionsPerTurn) {
            try {
                Thread.sleep(500);
                if (this.turnNumber < this.maxTurnNumber) {
                    this.sendAlert(" Turn" + this.turnNumber + " is finished. ", 130, 90);
                }
                this.turnNumber++;
                this.actionNumber = 1;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

        }

        if (this.turnNumber > this.maxTurnNumber) {
            try {
                Thread.sleep(500);
                this.sendAlert(" The game is finished. ", 150, 90);

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /*
     * Determine the cases origin and destination for a player action
     *
     * Returns an Array of Cases, 
     * array[0] = case origine
     * array[1] = case destination
     */
    private Case[] determineOriginDestination() {
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
     * Populate West JPanel with Turn number, Player Points, Enemy Points
     */
    public void populateWestPanel() {
//        JLabel turnLabel = new JLabel("Turn");
//        JLabel turnNumberLabel = new JLabel("" + this.turnNumber);
//        turnLabel.setFont(new Font("Verdana", 1, 20));
//        turnNumberLabel.setFont(new Font("Verdana", 1, 20));
//
//        this.west.add(turnLabel);
//        this.west.add(turnNumberLabel);
    }

    /*
     * Start Game method after BoardPanel is Initiated
     */
    public void resetAllCases() {

        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                this.grid[i][j].nbCasesSelected = 0;
                this.grid[i][j].canBeSelected = true;
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        this.grid[i][j].caseSelected[k][l] = 0;
                    }
                }
            }
        }
    }


    /*
     * Resolve an Attack  
     * returns true if the attack was possible and has been done
     * return false if the attach was not possible
     */
    private boolean resolveAttack() {
        boolean attack;
        int attackTroops = this.caseOrigin.troopsNumber - 1;
        if (attackTroops > 0) {
            int defendTroops = this.caseDestination.troopsNumber;
            int attackLvl = attackTroops + this.alea();
            int defendLvl = defendTroops + this.alea();
            int diff = attackLvl - defendLvl;
            if (diff > 0) {
                this.caseOrigin.troopsNumber = 1;
                this.caseDestination.setClan(this.caseOrigin.clan);
                this.caseDestination.setClanColor(this.caseOrigin.clanColor);
                this.caseDestination.troopsNumber = diff;
            } else if (diff == 0) {
                this.caseOrigin.troopsNumber = 1;
                this.caseDestination.troopsNumber = Math.max(1, defendTroops - attackTroops);
            } else {
                this.caseOrigin.troopsNumber = 1;
                this.caseDestination.troopsNumber = Math.min(defendTroops, Math.abs(diff));
            }
            attack = true;
        } else {
            this.sendAlert("You do not have enough troops to send an attack. Please do another action.", 350, 90);
            attack = false;
        }
        return attack;
    }

    /*
     * Resolve a troop support move
     * returns true if the sendTroops was possible and has been done
     * return false if the sendTroops was not possible
     */
    private boolean resolveSendTroops() {
        boolean sendTroops;
        int destinationTroops = this.caseDestination.troopsNumber;
        int troopsSent = this.caseOrigin.troopsNumber - 1;

        if (troopsSent > 0 && destinationTroops < 12) {
            if (troopsSent + destinationTroops <= this.maxTroopsPerCase) {
                this.caseOrigin.troopsNumber -= troopsSent;
                this.caseDestination.troopsNumber += troopsSent;
            } else {
                this.caseOrigin.troopsNumber -= this.maxTroopsPerCase - destinationTroops;
                this.caseDestination.troopsNumber = this.maxTroopsPerCase;
            }
            sendTroops = true;
        } else {
            this.sendAlert("You can not perform this action. Please do another action.", 400, 90);
            sendTroops = false;
        }
        return sendTroops;
    }

    /*
     * Send a message alert to the player if an action can not be performed due to a lack of troops, ...
     */
    private void sendAlert(String message, int xsize, int ysize) {
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
                if (Game.boardGame.turnNumber > Game.boardGame.maxTurnNumber) {
                    try {
                        Thread.sleep(500);
                        Game.boardGame.finalScore = Game.boardGame.countScores();
                        Game.boardGame.showFinalScore();
                    } catch (InterruptedException exep) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        alert.show();
    }

    /*
     * Determine the alea that is added to the ATT or DEF level during an attack
     */
    private int alea() {
        int alea = 0;
        Double rand = Math.random();
        if (rand < 0.5) {
            alea = 1;
        } else if (rand >= 0.5 && rand < 0.8) {
            alea = 2;
        } else if (rand >= 0.8 && rand < 0.95) {
            alea = 3;
        } else {
            alea = 4;
        }
        return alea;
    }

    /*
     * CountScores
     */
    public int[] countScores() {
        int[] scores = new int[3];
        scores[0] = 0;
        scores[1] = 0;
        scores[2] = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                scores[this.grid[i][j].clan] += (10 + this.grid[i][j].troopsNumber);
            }
        }
        return scores;
    }


    /*
     * Send a message alert to the player if an action can not be performed due to a lack of troops, ...
     */
    private void showFinalScore() {
        final Frame alert = new Frame();
        JLabel playerScoreLabel = new JLabel("Scores: Player = " + this.finalScore[1] + " / Enemy = " + this.finalScore[2]);
        // JLabel enemyScoreLabel = new JLabel("Enemy score = " + this.finalScore[2]);
        // JLabel nativeScoreLabel = new JLabel("Enemy score = " + this.finalScore[0]);
        alert.setTitle("Conquistadors");
        alert.setSize(230, 90);
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

}
