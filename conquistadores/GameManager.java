package conquistadores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mifine
 */
public class GameManager {

    private int currentGameLevel = 1;
    private boolean isEnemyAI;
    public static GameMecanics gameMecanics;
    private static String[] gameParameters;
    private static String playerName = "VotreNom";
    private static int scoreTotal;

    public GameManager() {
    }

    /*
     * not yet implemented. Will be used to check of the next level exists or if it is the end of the game.
     */
    public boolean checkIfLevelExists(int level) {
        boolean bool = false;
        return bool;
    }
    
    /*
     * Launch a new game, starting at level 1, reseting the total score. 
     * Game againt the computer
     */
    public void launchNewGame() {
        this.scoreTotal = 0;
        this.launchLevel(1);
    }

    /*
     * Control all tasks needed to launch a new level.
     * Initiate gameMecanics & frameGame
     */
    public void launchLevel(int level) {
        gameMecanics = new GameMecanics(level);
        gameMecanics.setIsEnemyAi(true);
        gameMecanics.setSecondPlayer(true);
        gameMecanics.setGameParameters(gameParameters[level-1]);
        this.currentGameLevel = level;
        Game.FG.startNewLevel(level);
        gameMecanics.startGame();
    }

    /*
     * method called to disable all cases when displaying the score (impossible to click)
     */
    public void disableAllCases(boolean bool){
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                FrameGame.boardGamePanel.grid[i][j].setCanBeSelected(!bool);
                FrameGame.boardGamePanel.grid[i][j].setNbCasesSelected(2);
            }
        }
    }
    
    /*
     * Set the game parameters depending on the level
     *
     * MAX_TURN_NUMBER MAX_ACTIONS_PER_TURN MAX_TROOPS_PER_CASE
     * ADD_TROOPS_PER_TURN BOARD_SIZE INITIAL_TROOPS_NATIVE
     * INITIAL_TROOPS_PLAYER AI_LEVEL GROUND_TYPE PLACEMENT // random=0 or
     * normal=1
     */
    public void loadGameParameters() {
//        gameParameters = new String[10];
//        gameParameters[0] = "1 1 1 1 1 1 1 1 1 1";
//        gameParameters[1] = "3 3 11 1 5 5 9 0 1 1";
//        gameParameters[2] = "3 3 11 1 5 5 9 0 1 0";
//        gameParameters[3] = "3 3 11 1 7 7 9 0 1 1";
//        gameParameters[4] = "3 3 11 1 7 7 9 0 1 0";
//        gameParameters[5] = "3 3 11 1 7 7 9 0 1 1";
//        gameParameters[6] = "3 3 11 1 5 5 9 0 2 1";
//        gameParameters[7] = "3 3 11 1 5 5 9 0 2 0";
//        gameParameters[8] = "3 3 11 1 7 7 9 0 2 1";
//        gameParameters[9] = "3 3 11 1 7 7 9 0 2 0";
//        
        
        ArrayList params = new ArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Game.PARAMS_FILE));
            String line = br.readLine();
            while (line != null) {
                params.add(line);
                line = br.readLine();
            }
            int max = params.size();
            gameParameters = new String[max];
            for (int i = 0; i < max; i++) {
                gameParameters[i] = params.get(i).toString();
                System.out.println(gameParameters[i]);
                }
            
        } catch (IOException e) {
            
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
     // GET, SET
    public int getCurrentGameLevel() {
        return this.currentGameLevel;
    }
    
    public void setCurrentGameLevel(int lvl){
        this.currentGameLevel = lvl;
    }
    
    public void setPlayerName(String name) {
        this.playerName = name;
    }
    
    public String getPlayerName() {
        return this.playerName;
    }

    public void setScoreTotal(int t) {
        this.scoreTotal = t;
    }
    
    public int getScoreTotal() {
        return this.scoreTotal;
    }
    
    // END GET, SET

}
