/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conquistadores;

import java.io.Serializable;

/**
 *
 * @author mifine
 */
public class Score implements Serializable {

    private long time;
    private int score1;
    private int score2;
    private int isEnemyAI;
    private int boardSize;

    public Score(long t, int S1, int S2, int isAI, int b) {
        this.time = t; 
        this.score1 = S1;
        this.score2 = S2;
        this.isEnemyAI = isAI;
        this.boardSize = b;

    }

    public long getTime() {
        return time;
    }

    public int getScore1() {
        return score1;
    }
    
    public int getScore2() {
        return score2;
    }
    
    public int getIsAI() {
        return isEnemyAI;
    }
    
    public int getBoardSize() {
        return boardSize;
    }
    
}
