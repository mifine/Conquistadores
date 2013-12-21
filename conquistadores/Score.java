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
    final private String name;
    final private int score1;
    final private int level;

    public Score(String n, long t, int S1, int l) {
        this.time = t; 
        this.name = n;
        this.score1 = S1;
        this.level = l;
    }

    public long getTime() {
        return time;
    }
    
    public String getName() {
        return name;
    }

    public int getScore1() {
        return score1;
    }
    
    public int getLevel() {
        return level;
    }
    
}
