/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conquistadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * Inspired by http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
 */
public class ScoreManager {

    private static final String SCORE_FILE = "scores.txt";

    public ScoreManager() {
        
    }

    
    public String getHighscoreString() {
        String highscoreString = "";
	// 
   ArrayList<Score> scores;
        scores = getScores();
        int max = scores.size();
     System.out.println(max);
        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
          Date date = new Date(scores.get(i).getTime());
           highscoreString += (i + 1) + ". " + date + " - Player 1: " + scores.get(i).getScore1() + " - Player 2: " + scores.get(i).getScore2() + " - Board Size: " + scores.get(i).getBoardSize() + "\n";
           //highscoreString += (i + 1) + " - Player 1: " + scores.get(i).getScore1() + " - Player 2: " + scores.get(i).getScore2() + " - Board Size: " + scores.get(i).getBoardSize() + "\n";
           
            i++;
        }
        return highscoreString;
}
    
    public ArrayList<Score> getScores() {
        ArrayList<Score> scores = new ArrayList();
        scores = loadScoreFile();
        scores = sort(scores);
        return scores;
    }

    /*
     * Build the scores Array List of scores from the read SCORE_FILE
     */
    public ArrayList<Score> loadScoreFile() {
        ArrayList<Score> scores = new ArrayList();
        ArrayList scoresString = this.readScore();
        String scoreLine;
        int max = scoresString.size();
        for(int i = 0; i < max; i++) {
            scoreLine = scoresString.get(i).toString();
            String[] elts = scoreLine.split(" ");
          
            Score sc = new Score(
                    Long.parseLong(elts[0]),
                    Integer.parseInt(elts[1]),
                    Integer.parseInt(elts[2]),
                    Integer.parseInt(elts[3]),
                    Integer.parseInt(elts[4])
            );
            scores.add(sc);
        }
        return scores;
    }

    /*
     * Read the SCORE_FILE and return a String Array List
     */
    static ArrayList readScore() {
        ArrayList scores = new ArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(SCORE_FILE));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                scores.add(line);
                line = br.readLine();
            }
            return scores;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Write the new score line to the SCORE_FILE
     */
    public void writeScore(int player1Score, int player2Score, boolean isEnemyAI) {
        File file = new File(SCORE_FILE);
        Date today = new Date();
        String score = today.getTime() + " " + player1Score + " " + player2Score + " " + (isEnemyAI ? "1" : "0") + " " + Game.BOARD_SIZE;
        PrintWriter pw = null;
        try {
            FileWriter fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            pw.println(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (pw != null) {
            pw.close();

        }
    }

    /*
     * Sort the Score Array List based on the comparator
     */
    private ArrayList<Score> sort(ArrayList<Score> scores) {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
        return scores;
    }
}

/*
 * ScoreComparator class to help sort the scores Array List
 */
class ScoreComparator implements Comparator<Score> {

    public int compare(Score score1, Score score2) {

        int sc1 = score1.getScore1();
        int sc2 = score2.getScore1();

        if (sc1 > sc2) {
            return -1;
        } else if (sc1 < sc2) {
            return +1;
        } else {
            return 0;
        }
    }

}
