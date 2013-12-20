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

    final private long time;
    final private int score1;
    final private int score2;
    final private int isEnemyAI;
    final private int level;

    public Score(long t, int S1, int S2, int isAI, int l) {
        this.time = t; 
        this.score1 = S1;
        this.score2 = S2;
        this.isEnemyAI = isAI;
        this.level = l;

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
    
    public int getLevel() {
        return level;
    }
    
}
