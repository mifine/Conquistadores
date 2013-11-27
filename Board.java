
/**
 *
 * @author mifine
 */
import java.awt.*;
import java.awt.Color;
import javax.swing.*;

public class Board extends JPanel {

    public Case grid[][] = new Case[5][5];
    public JPanel board = new JPanel();
    public Color playerColor;
    public int[] selectedCase = new int[2];

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
    }

    /*
     Set Player Color
     @param Color colorToSet
     */
    public void setPlayerColor(Color colorToSet) {
        this.playerColor = colorToSet;
    }

    /*
     Populate Board Grids with Cases
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

    private void buttonAttackActionPerformed(java.awt.event.ActionEvent evt) {
        this.canBeActioned("attack");
        System.out.println("caseOrigin.clan = " + caseOrigin.clan + " - caseDestination.clan = " + caseDestination.clan);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(caseOrigin.caseSelected[i][j]);
            }
            System.out.println("");
        }

        if (this.canBeActioned("attack")) {
            System.out.println(caseOrigin.posx + " " + caseOrigin.posy + " ATTACKS " + caseDestination.posx + " " + caseDestination.posy);

        }
    }

    private void buttonSendActionPerformed(java.awt.event.ActionEvent evt) {
        this.canBeActioned("send");
        System.out.println("caseOrigin.clan = " + caseOrigin.clan + " - caseDestination.clan = " + caseDestination.clan);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(caseOrigin.caseSelected[i][j]);
            }
            System.out.println("");
        }

        if (this.canBeActioned("send")) {
            System.out.println(caseOrigin.posx + " " + caseOrigin.posy + " SENDS TROOPS TO " + caseDestination.posx + " " + caseDestination.posy);

        }
    }

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
     Returns an Array of Cases, 
     array[0] = case origine
     array[1] = case destination
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

}
