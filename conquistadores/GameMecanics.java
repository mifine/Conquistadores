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

    private static int whoPlaysFirst;
    private static Player player1;
    private static Player player2;
    private Player currentPlayer;
    private int whoIsPlaying;
    private int whoIsPlayingInt;
    private int[] finalScore = new int[3];
    public boolean isEnemyAI;
    public int turnNumber = 1;
    public int actionNumber = 1;

    public GameMecanics(int level) {
        double rand = Math.random();
        this.whoPlaysFirst = (rand < 0.5) ? 1 : 2;
        player1 = new PlayerHuman();
        Game.GM.loadGameParameters();
    }

    /*
     * Once everything is in place, this method will be called from the
     * GameManager to start the game.
     */
    public void startGame() {
        this.setCurrentPlayer(this.whoPlaysFirst);
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
            caseOrigin.displayAttack(true, attackLvl);
            caseDestination.displayAttack(true, defendLvl);
            if (diff > 0) {
                caseOrigin.setTroopsNumber(1);
                caseDestination.setClan(caseOrigin.getClan());
                caseDestination.setClanColor(caseOrigin.getInitialClanColor());
                caseDestination.setInitialClanColor(caseOrigin.getInitialClanColor());
                caseDestination.setInitialBrightClanColor(caseOrigin.getClanColor());
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
            FrameGame.boardGamePanel.sendAlert("Vous n'avez pas assez de troupes pour attaquer. Choississez une autre action.", 350, 90);
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

        // By default, we send all troops but one from the origin case
        int troopsSent = originTroops - 1;

        if (troopsSent > 0 && destinationTroops < (Game.MAX_TROOPS_PER_CASE - 1)) {
            if (troopsSent + destinationTroops <= Game.MAX_TROOPS_PER_CASE) {
                caseOrigin.setTroopsNumber(originTroops - troopsSent);
                caseDestination.setTroopsNumber(destinationTroops + troopsSent);
            } else {
                caseOrigin.setTroopsNumber(originTroops - Game.MAX_TROOPS_PER_CASE + destinationTroops);
                caseDestination.setTroopsNumber(Game.MAX_TROOPS_PER_CASE);
            }
            sendTroops = true;
        } else {
            if (currentPlayer.whoAmI() == "Human") {
                FrameGame.boardGamePanel.sendAlert("Vous ne pouvez pas faire cette action. Choississez autre chose.", 400, 90);
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
        if (rand < 0.3) {
            alea = 0;
        } else if (rand >= 0.3 && rand < 0.6) {
            alea = 1;
        } else if (rand >= 0.6 && rand < 0.8) {
            alea = 2;
        } else if (rand >= 0.8 && rand < 0.95) {
            alea = 3;
        } else if (rand >= 95) {
            alea = 4;
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
     * Close an action turn Check if the AI can still play (ex: all cases have
     * only 1 troops) If AI does not have any more troops, then EndGame.
     */
    public void closeActionTurn(Case caseOrigin, Case caseDestination, boolean isAI) {
        if (!isAI) {
            FrameGame.boardGamePanel.closeActionTurn(caseOrigin, caseDestination);
        }
        this.actionNumber++;

        if (this.actionNumber > Game.MAX_ACTIONS_PER_TURN) {
            this.closeTurn();
        }
        caseOrigin.displayAttack(false, 0);
        caseDestination.displayAttack(false, 0);
        if (this.turnNumber == 1) {
            FrameGame.boardGamePanel.updateNorthPanel(1, this.whoIsPlaying);
        } else {
            FrameGame.boardGamePanel.updateNorthPanel((int) (this.turnNumber + 1) / 2, this.whoIsPlaying);
        }

    }

    /*
     * Close a turn if no more actions are possible then check if the game can
     * continue with the next player or if the game is finished if Game can
     * continue, change the player who plays and do
     * currentPlayer.playTurn(turnNb)
     */
    public void closeTurn() {
        this.setCurrentPlayer((this.whoIsPlaying == 1) ? 2 : 1);
        this.actionNumber = 1;
        // update the Game panel with the indication of who's turn it is
        if (this.turnNumber < Game.MAX_TURN_NUMBER * 2) {
            FrameGame.boardGamePanel.updateNorthPanel((int) (this.turnNumber) / 2 + 1, this.whoIsPlaying);
        }
        this.turnNumber++;

        // END GAME
        if (this.turnNumber > Game.MAX_TURN_NUMBER * 2 && this.whoIsPlaying == this.whoPlaysFirst) {
            endGame();
        }

        // END TURN
        if (this.whoIsPlaying == this.whoPlaysFirst) {
            endTurn();
        }

        this.currentPlayer.playTurn();
    }

    /*
     * the level is finished, we can close the game Window and update the total
     * score of the player in the gameManager Then we save the score. we update
     * the score only if the player has won the level
     */
    public void endGame() {
        try {
            Thread.sleep(500);
            finalScore = this.countScores();
            // we update the score only if the player has won the level
            if (finalScore[1] >= finalScore[2]) {
                Game.GM.setScoreTotal(Game.GM.getScoreTotal() + finalScore[1]);
            }
            Game.FG.showEndLevel(finalScore[1], finalScore[2]);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }

    /*
     * the turn is finished, increase the number of troops per case. +X for
     * Players, +2X for Natives.
     */
    public void endTurn() {
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                Case cas = FrameGame.boardGamePanel.grid[i][j];
                if (cas.getClan() == 0) {
                    cas.setTroopsNumber(3 * Game.ADD_TROOPS_PER_TURN + cas.getTroopsNumber());
                } else {
                    cas.setTroopsNumber(Game.ADD_TROOPS_PER_TURN + cas.getTroopsNumber());
                }
                cas.repaint();
            }
        }
    }

    /*
     * Method called to set the current player when changing action turn
     */
    public void setCurrentPlayer(int player) {
        if (player == 1) {
            this.currentPlayer = player1;
            this.whoIsPlaying = 1;
        } else {
            this.currentPlayer = player2;
            this.whoIsPlaying = 2;
        }
        this.whoIsPlayingInt = player;
    }

    /*
     * Method called to vhange the current player when changing action turn
     */
    public void changePlayer() {
        this.setCurrentPlayer((this.whoIsPlaying == 1) ? 2 : 1);
    }

    /*
     * CountScores Return scores array with Player 1 and Player 2 scores.
     */
    public int[] countScores() {
        int[] scores = new int[3];
        scores[0] = 0;
        scores[1] = 0;
        scores[2] = 0;
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                scores[FrameGame.boardGamePanel.grid[i][j].getClan()]
                        += (10 + FrameGame.boardGamePanel.grid[i][j].getTroopsNumber());
            }
        }
        return scores;
    }

    /*
     * Set the second player. Will vary if second player is Human or Computer
     */
    public void setSecondPlayer(boolean isEnemyAI) {
        if (isEnemyAI) {
            player2 = new PlayerAI();
            player2.setLevel(Game.AI_LEVEL);
        } else {
            player2 = new PlayerHuman();
        }
    }

    /*
     * Set the game parameters according to the values store in the table coming
     * from the GameManager.
     */
    public void setGameParameters(String params) {
        String[] p = params.split(" ");
        Game.BOARD_SIZE = Integer.parseInt(p[0]);
        Game.MAX_TURN_NUMBER = Integer.parseInt(p[1]);
        Game.MAX_ACTIONS_PER_TURN = Integer.parseInt(p[2]);
        Game.MAX_TROOPS_PER_CASE = Integer.parseInt(p[3]);
        Game.ADD_TROOPS_PER_TURN = Integer.parseInt(p[4]);
        Game.INITIAL_TROOPS_NATIVE = Integer.parseInt(p[5]);
        Game.INITIAL_TROOPS_PLAYER = Integer.parseInt(p[6]);
        Game.AI_LEVEL = Integer.parseInt(p[7]);
        Game.GROUND_TYPE = Integer.parseInt(p[8]);
        Game.PLACEMENT = Integer.parseInt(p[9]);
    }

    // GET, SET
    public int getWhoPlaysFirst() {
        return this.whoPlaysFirst;
    }

    public int getWhoIsPlayingInt() {
        return this.whoIsPlayingInt;
    }

    public void setIsEnemyAi(boolean bool) {
        this.isEnemyAI = bool;
    }

    public int getActionNumber() {
        return this.actionNumber;
    }

    // END GET, SET
}
