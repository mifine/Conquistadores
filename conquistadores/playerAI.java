package conquistadores;

import conquistadores.ui.FrameStart;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author mifine
 */
public class PlayerAI extends Player {

    private int level;                       // level = 0 : "random", 1 : "easy", 2 : "normal" or 3 : "difficult"

    @Override
    public void playTurn() {

        Thread t = new Thread() {
            public void run() {
                System.out.println("IA Start Turn");
                setCasesClickPossibility(false);
                for (int action = 0; action < Game.MAX_ACTIONS_PER_TURN; action++) {
                    System.out.println("IA Start Action");
                    play();
                }
            }
        };
        t.start();

    }

    /*
     * Determine which action the IA will play
     */
    public void play() {
        Case[] cases = new Case[2];
        switch (this.level) {
            case 0:
                cases = this.playRandom();
                break;
            case 1:
                this.playEasy();
                break;
            case 2:
                this.playNormal();
                break;
            case 3:
                this.playAdvanced();
                break;
            default:
        }
        this.closeActionTurn(cases[0], cases[1]);
        System.out.println("IA close Action Turn OK");
    }

    /*
     * Gameplay for an random IA // to test the IA implementation
     */
    public Case[] playRandom() {

        /*
         * Random algorithm:
         *
         * 0 - If an IA case exists do: 1 to 4: 1 - Take a case randomly within
         * the IA case, check if this case has more than 1 troop otherwise redo
         * 1. 2 - Pick a neighbour case randomly 3 - If neighbour case.clan is
         * the same then send troops else attack. 4 - Resolve action and play
         * the next action
         *
         * http://www.tutorialspoint.com/java/java_arraylist_class.htm
         */
        Case boardCase[][] = new Case[5][5];
        ArrayList<Case> aiCases = new ArrayList<Case>();
        Case caseOrigin;
        Case caseDestination;
        Case[] neighbours = new Case[4];
        Case[] casesToReturn = new Case[2];

        for (int i = 0;
                i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardCase[i][j] = FrameStart.boardGamePanel.grid[i][j];
                if (boardCase[i][j].getClan() == 2) {
                    aiCases.add(boardCase[i][j]);
                }
            }
        }

        System.out.println(
                "IA get all board cases OK");

        int sizeAI = aiCases.size();
        boolean result = false;

        // 0 - If an IA case exists do: 1 to 4
        do {
            if (sizeAI != 0) {
                int rand;
                int origin = this.getRandomNb(sizeAI);
                caseOrigin = aiCases.get(origin - 1);
                neighbours = caseOrigin.getNeighbours();
                if (neighbours[1] == null) {
                    do {
                        rand = this.getRandomNb(4);
                    } while (neighbours[rand - 1] == null);
                    caseDestination = neighbours[rand - 1];
                } else {
                    caseDestination = neighbours[1];
                }
                caseOrigin.updateBackgroundDisplay();
                caseDestination.updateBackgroundDisplay();
                if (caseOrigin.getTroopsNumber() > 1) {
                    if (caseOrigin.getClan() == caseDestination.getClan()) {
                        result = FrameStart.gameMecanics.resolveSend(caseOrigin, caseDestination);
                    } else {
                        result = FrameStart.gameMecanics.resolveAttack(caseOrigin, caseDestination);
                    }
                    if (result) {
                        casesToReturn[0] = caseOrigin;
                        casesToReturn[1] = caseDestination;
                    }
                }
            } else {
                result = true;
            }
        } while (result
                == false);

        return casesToReturn;
    }

    /*
     * Gameplay for an easy IA
     */
    public void playEasy() {

        /*
         * easy algorithm : Check for the case that has the max number of troops
         *
         */
    }

    /*
     * Gameplay for a normal IA
     */
    public void playNormal() {

    }

    /*
     * Gameplay for an advanced IA
     */
    public void playAdvanced() {

    }

    /*
     * Close the action turn for IA
     */
    public void closeActionTurn(Case caseOrigin, Case caseDestination) {
        FrameStart.gameMecanics.closeActionTurn(caseOrigin, caseDestination, false);
    }

    /*
     * Returns a random number between 1 and max @returns int
     */
    public int getRandomNb(int max) {
        return (int) (Math.random() * max) + 1;
    }
}
