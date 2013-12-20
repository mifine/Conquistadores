package conquistadores;

import java.util.ArrayList;

/**
 *
 * @author mifine
 */
public class PlayerAI extends Player {

    private int level;                       // level = 0 : "random", 1 : "easy", 2 : "normal" or 3 : "difficult"
    private Case caseOrigin;
    private Case caseDestination;

    @Override
    public void playTurn() {

        Thread t = new Thread() {
            public void run() {
                setCasesClickPossibility(false);
                for (int action = 0; action < Game.MAX_ACTIONS_PER_TURN; action++) {
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
        switch (this.level) {
            case 0:
                this.playRandom();
                break;
            case 1:
                this.playEasy();
                break;
            case 2:
                this.playMedium();
                break;
            case 3:
                this.playAdvanced();
                break;
            default:
        }
        this.closeActionTurn();
    }

    /*
     * Gameplay for an random IA // to test the IA implementation
     */
    public void playRandom() {

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
        Case boardCase[][] = new Case[Game.BOARD_SIZE][Game.BOARD_SIZE];
        ArrayList<Case> aiCases = new ArrayList<Case>();

        Case[] neighbours = new Case[4];

        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                boardCase[i][j] = FrameGame.boardGamePanel.grid[i][j];
                if (boardCase[i][j].getClan() == 2) {
                    aiCases.add(boardCase[i][j]);
                }
            }
        }

        int sizeAI = aiCases.size();
        boolean result = false;

        // 0 - If an IA case exists do: 1 to 4
        do {
            if (sizeAI != 0) {
                int rand;
                do {
                    int origin = this.getRandomNb(sizeAI);
                    caseOrigin = aiCases.get(origin);
                } while (caseOrigin.getTroopsNumber() == 1);
                neighbours = caseOrigin.getNeighbours();
                if (Game.PLACEMENT == 1) {
                    if (neighbours[1] == null) {
                        do {
                            rand = this.getRandomNb(4);
                        } while (neighbours[rand] == null);
                        caseDestination = neighbours[rand];
                    } else {
                        caseDestination = neighbours[1];
                    }
                } else {
                    do {
                        rand = this.getRandomNb(4);
                    } while (neighbours[rand] == null);
                    caseDestination = neighbours[rand];
                }

                caseOrigin.updateBackgroundDisplay();
                caseDestination.updateBackgroundDisplay();
                if (caseOrigin.getTroopsNumber() > 1) {
                    if (caseOrigin.getClan() == caseDestination.getClan()) {
                        result = Game.GM.gameMecanics.resolveSend(caseOrigin, caseDestination);
                    } else {
                        result = Game.GM.gameMecanics.resolveAttack(caseOrigin, caseDestination);
                    }
                    if (!result) {
                        this.caseOrigin.initialize();
                        this.caseDestination.initialize();
                        this.caseOrigin.repaint();
                        this.caseDestination.repaint();
                    }
                }
            } else {
                result = true;
            }
        } while (result == false);

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
    public void playMedium() {

    }

    /*
     * Gameplay for an advanced IA
     */
    public void playAdvanced() {

    }

    /*
     * Close the action turn for IA
     */
    public void closeActionTurn() {
        Game.GM.gameMecanics.closeActionTurn(this.caseOrigin, this.caseDestination, false);
    }

    /*
     * Returns a random number between 0 and max-1 @returns int
     */
    private int getRandomNb(int max) {
        return (int) (Math.random() * max);
    }

    @Override
    public String whoAmI() {
        return "AI";
    }
    
       // GET, SET
    public void setLevel(int lvl) {
        this.level = lvl;
    }
}
