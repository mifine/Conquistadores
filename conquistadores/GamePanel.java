package conquistadores;

/**
 *
 * @author mifine
 */
import java.awt.*;
import java.awt.Color;
import javax.swing.*;

public class GamePanel extends JPanel {

    public static Color playerColor;

    public int[] selectedCase = new int[2];

    public JPanel board = new JPanel();
    public JPanel south = new JPanel();
    public JPanel north = new JPanel();
    public Case grid[][] = new Case[Game.BOARD_SIZE][Game.BOARD_SIZE];

    public Case caseOrigin;
    public Case caseDestination;
    private boolean isHuman;

    public GamePanel() {
        GridLayout g1 = new GridLayout(Game.BOARD_SIZE, Game.BOARD_SIZE);
        g1.setHgap(1);
        g1.setVgap(1);
        this.board.setLayout(g1);
    }

    /*
     * Initialize the token placement at the begining of a new Game. clan = 1 :
     * 10 Player 1, clan = 0 : 5 Indigeneous clan = 2 : 10 Player 2
     */
    public void initGamePanel() {

        this.populateCenterPanel();
        this.populateNorthPanel(1, Game.GM.gameMecanics.getWhoPlaysFirst());
        this.populateSouthPanel();
        int middle = (int) Game.BOARD_SIZE / 2;

        // Build the first grid to play
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                if (Game.PLACEMENT == 0) {
                    int r = this.getRandomNb(5);
                    this.placeGrid(r, i, j, 2);
                } else {
                    this.placeGrid(j, i, j, middle);
                }
            }
        }
    }

    public void placeGrid(int r, int i, int j, int sep) {
        if (r < sep) {
            this.grid[i][j].setClan(1);
            this.grid[i][j].setClanColor(this.playerColor);
        }
        if (r == sep) {
            this.grid[i][j].setClan(0);
            this.grid[i][j].setClanColor(new Color(218, 165, 32));
        }
        if (r > sep) {
            this.grid[i][j].setClan(2);
            this.grid[i][j].setClanColor(new Color(255, 51, 51));
        }
        int ground = this.getRandomNb(3);
        this.grid[i][j].setGround(ground);
        this.grid[i][j].repaint();
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
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                this.grid[i][j] = new Case(i, j);
                this.board.add(grid[i][j]);
            }
        }
    }

    /*
     * Populate North JPanel with Turn number - Player or Enemy Turn
     */
    public void populateNorthPanel(int turnNb, int whoPlays) {
        JLabel turnLabel = new JLabel("Tour " + turnNb + "/" + Game.MAX_TURN_NUMBER + " - " + (whoPlays == 1 ? Game.GM.getPlayerName() : "Ennemi"));
        turnLabel.setFont(new Font("Verdana", 1, 20));
        this.north.add(turnLabel);
        
    }

    /*
     * Update North JPanel with new Turn number - Player or Enemy Turn
     */
    public void updateNorthPanel(int turnNb, int whoPlays) {
        this.north.remove(this.north.getComponent(0));
        this.north.revalidate();
        populateNorthPanel(turnNb, whoPlays);
        this.north.repaint();
    }
    
    /*
     * Populate South JPanel with ATTACK and SEND TROOPS buttons
     */
    public void populateSouthPanel() {
        JButton buttonAttack = new JButton("ATTAQUER");
        JButton buttonSend = new JButton("ENVOYER DES RENFORTS");

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
            if (Game.GM.gameMecanics.resolveAttack(this.caseOrigin, this.caseDestination)) {
                Game.GM.gameMecanics.closeActionTurn(this.caseOrigin, this.caseDestination, false);
            }
        }

    }

    /*
     * South JPanel SEND TROOPS button method
     */
    private void buttonSendActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.canBeActioned(1)) {
            if (Game.GM.gameMecanics.resolveSend(this.caseOrigin, this.caseDestination)) {
                Game.GM.gameMecanics.closeActionTurn(this.caseOrigin, this.caseDestination, false);
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
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
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

        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                this.grid[i][j].setNbCasesSelected(0);
                this.grid[i][j].setCanBeSelected(true);
                for (int k = 0; k < Game.BOARD_SIZE; k++) {
                    for (int l = 0; l < Game.BOARD_SIZE; l++) {
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
        final JFrame alert = new JFrame();
        JLabel alertLabel = new JLabel(message);
        alert.setTitle("Conquistadors");
        alert.setSize(xsize, ysize);
        alert.add(alertLabel);
        alert.setLocationRelativeTo(null);
        alert.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
     * GET, SET
     */
    public Case getCaseOrigin() {
        return this.caseOrigin;
    }

    public Case getCaseDestination() {
        return this.caseDestination;
    }

    public void setIsHuman(boolean b) {
        this.isHuman = b;
    }

    /*
     * Returns a random number between 0 and max-1 @returns int
     */
    private int getRandomNb(int max) {
        return (int) (Math.random() * max);
    }
}
