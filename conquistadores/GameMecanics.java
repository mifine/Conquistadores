/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conquistadores;

/**
 *
 * @author mifine
 */
public class GameMecanics {

    private static String whoPlaysFirst;
    private static Player player1;
    private static Player player2;
    private Player currentPlayer;
    private String whoIsPlaying;
    private int whoIsPlayingInt;
    private int[] finalScore = new int[3];

    public int turnNumber = 1;
    public int actionNumber = 1;

    public GameMecanics() {
        double rand = Math.random();
        this.whoPlaysFirst = (rand < 0.5) ? "Player 1" : "Player 2";
        player1 = new PlayerHuman();
//        player1 = new PlayerAI();
//        player1.setLevel(Game.AI_LEVEL);
    }

    /*
     * Once everything is in place, we can start the game.
     */
    public void startGame() {
        if (this.whoPlaysFirst == "Player 1") {
            this.setCurrentPlayer(1);
        } else {
            this.setCurrentPlayer(2);
        }
        currentPlayer.playTurn();
    }
    /*
     * Resolve an Attack @params Case caseOrigin, Case caseDestination
     *
     * returns true if the attack was possible and has been done returns false
     * if the attack was not possible
     */

    public boolean resolveAttack(Case caseOrigin, Case caseDestination) {
        boolean attack;
        int attackTroops = caseOrigin.getTroopsNumber() - 1;
        if (attackTroops > 0) {
            int defendTroops = caseDestination.getTroopsNumber();
            int attackLvl = attackTroops + this.alea() + this.bonus("attack", caseOrigin.getGround());
            int defendLvl = defendTroops + this.alea() + this.bonus("defense", caseDestination.getGround());
            int diff = attackLvl - defendLvl;
            if (diff > 0) {
                caseOrigin.setTroopsNumber(1);
                caseDestination.setClan(caseOrigin.getClan());
                caseDestination.setClanColor(caseOrigin.getClanColor());
                caseDestination.setTroopsNumber(diff);
            } else if (diff == 0) {
                caseOrigin.setTroopsNumber(1);
                caseDestination.setTroopsNumber(Math.max(1, defendTroops - attackTroops));
            } else {
                caseOrigin.setTroopsNumber(1);
                caseDestination.setTroopsNumber(Math.min(defendTroops, Math.abs(diff)));
            }
            attack = true;
        } else {
            FrameStart.boardGamePanel.sendAlert("You do not have enough troops to send an attack. Please do another action.", 350, 90);
            attack = false;
        }
        return attack;
    }

    /*
     * Resolve a troop support move @params Case caseOrigin, Case
     * caseDestination returns true if the sendTroops was possible and has been
     * done return false if the sendTroops was not possible
     */
    public boolean resolveSend(Case caseOrigin, Case caseDestination) {
        boolean sendTroops;
        int destinationTroops = caseDestination.getTroopsNumber();
        int originTroops = caseOrigin.getTroopsNumber();
        int troopsSent = originTroops - 1;

        if (troopsSent > 0 && destinationTroops < (Game.MAX_TROOPS_PER_CASE + (int)(this.turnNumber-1)/2 - 1)) {
            if (troopsSent + destinationTroops <= Game.MAX_TROOPS_PER_CASE + (int)(this.turnNumber-1)/2) {
                caseOrigin.setTroopsNumber(originTroops - troopsSent);
                caseDestination.setTroopsNumber(destinationTroops + troopsSent);
            } else {
                caseOrigin.setTroopsNumber(originTroops - Game.MAX_TROOPS_PER_CASE - (int)(this.turnNumber-1)/2 + 2 + destinationTroops);
                caseDestination.setTroopsNumber(Game.MAX_TROOPS_PER_CASE + (int)(this.turnNumber-1)/2);
            }
            sendTroops = true;
        } else {
            if (currentPlayer.whoAmI() == "Human") {
                FrameStart.boardGamePanel.sendAlert("You can not perform this action. Please do another action.", 400, 90);
            }
            sendTroops = false;
        }
        return sendTroops;
    }

    /*
     * Determine the alea that is added to the ATT or DEF level during an attack
     */
    private int alea() {
        int alea = 0;
        Double rand = Math.random();
        if (rand < 0.5) {
            alea = 1;
        } else if (rand >= 0.5 && rand < 0.85) {
            alea = 2;
        } else if (rand >= 0.85 && rand < 0.90) {
            alea = 3;
        } 
        return alea;
    }

    /*
     * Determine the alea that is added to the ATT or DEF level during an attack
     */
    private int bonus(String type, int ground) {
        int bonus;
        switch (ground) {
            case 0:
                bonus = (type == "defense") ? 3 : 0;
                break;
            case 2:
                bonus = (type == "attack") ? 3 : 0;
                break;
            default:
                bonus = 0;
                break;
        }
        return bonus;
    }

    /*
     * Close an action turn
     */
    public void closeActionTurn(Case caseOrigin, Case caseDestination, boolean isAI ) {
        if (!isAI) {
            FrameStart.boardGamePanel.closeActionTurn(caseOrigin, caseDestination);
        } else {

        }
        this.actionNumber++;
        if (this.actionNumber > Game.MAX_ACTIONS_PER_TURN) {
            this.closeTurn();
        }
    }

    /*
     * Close a turn if no more actions are possible then check if the game can
     * continue with the next player or if the game is finished if Game can
     * continue, change the player who plays and do
     * currentPlayer.playTurn(turnNb)
     */
    public void closeTurn() {
        this.setCurrentPlayer((this.whoIsPlaying == "Player 1") ? 2 : 1);

        if (this.turnNumber < Game.MAX_TURN_NUMBER * 2) {
            FrameStart.boardGamePanel.updateNorthPanel((int) (this.turnNumber) / 2 + 1, this.whoIsPlaying);

        }
        this.turnNumber++;
        this.actionNumber = 1;

        if (this.turnNumber > Game.MAX_TURN_NUMBER * 2 && this.whoIsPlaying == this.whoPlaysFirst) {
            try {
                Thread.sleep(500);
                finalScore = this.countScores();
                FrameStart.boardGamePanel.showFinalScore(finalScore[1], finalScore[2]);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        // at the end of each turn, increase the number of troops per case. +X for Players, +2X for Natives. 
        if (this.whoIsPlaying == this.whoPlaysFirst) {
            for (int i = 0; i < Game.BOARD_SIZE; i++) {
                for (int j = 0; j < Game.BOARD_SIZE; j++) {
                    Case cas = FrameStart.boardGamePanel.grid[i][j];
                    if (cas.getClan() == 0) {
                        cas.setTroopsNumber(3 * Game.ADD_TROOPS_PER_TURN + cas.getTroopsNumber());
                    } else {
                        cas.setTroopsNumber(Game.ADD_TROOPS_PER_TURN + cas.getTroopsNumber());
                    }
                    cas.repaint();
                }
            }
        }

        this.currentPlayer.playTurn();
    }

    public void setCurrentPlayer(int player) {
        if (player == 1) {
            this.currentPlayer = player1;
            this.whoIsPlaying = "Player 1";
        } else {
            this.currentPlayer = player2;
            this.whoIsPlaying = "Player 2";
        }
        this.whoIsPlayingInt = player;
    }

    public void changePlayer() {
        this.setCurrentPlayer((this.whoIsPlaying == "Player 1") ? 2 : 1);
    }

    /*
     * CountScores
     */
    public int[] countScores() {
        int[] scores = new int[3];
        scores[0] = 0;
        scores[1] = 0;
        scores[2] = 0;
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                scores[FrameStart.boardGamePanel.grid[i][j].getClan()]
                        += (10 + FrameStart.boardGamePanel.grid[i][j].getTroopsNumber());
            }
        }
        return scores;
    }

    public void setSecondPlayer(boolean isEnemyAI) {
        if (isEnemyAI) {
            player2 = new PlayerAI();
            player2.setLevel(Game.AI_LEVEL);
        } else {
            player2 = new PlayerHuman();
        }
    }

    // GET, SET
    public String getWhoPlaysFirst() {
        return this.whoPlaysFirst;
    }

    public int getWhoIsPlayingInt() {
        return this.whoIsPlayingInt;
    }

}
