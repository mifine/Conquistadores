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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * Inspired by
 * http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
 */
public class ScoreManager {

    public ScoreManager() {
    }

    public String[] getHighscores() {

        int maxHighScore = 9;
        ArrayList<Score> scores;
        scores = getScores();
        if (scores != null) {
        int max = Math.min(maxHighScore, scores.size());
        int i = 0;
        String[] highscores = new String[max];
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        while (i < max) {
            Date date = new Date(scores.get(i).getTime());
            highscores[i] = (i + 1) + ".          " + scores.get(i).getScore1() 
                    + "   -   Niveau :  " + scores.get(i).getLevel() 
                    + "   -   " + dateFormat.format(date) 
                    + "   -   " + scores.get(i).getName() +   "\n";
            i++;
        }
        return highscores;}
        else return null;
    }

    public ArrayList<Score> getScores() {
        ArrayList<Score> scores = new ArrayList();
        scores = loadScoreFile();
        if (scores != null) {
            scores = sort(scores);
            return scores;
        } else {
            return null;
        }
    }

    /*
     * Build the scores Array List of scores from the read SCORE_FILE
     */
    public ArrayList<Score> loadScoreFile() {
        ArrayList<Score> scores = new ArrayList();
        ArrayList scoresString = this.readScore();
        String scoreLine;
        if (scoresString != null) {
            int max = scoresString.size();
            for (int i = 0; i < max; i++) {
                scoreLine = scoresString.get(i).toString();
                String[] elts = scoreLine.split(" ");

                Score sc = new Score(
                        elts[0],
                        Long.parseLong(elts[1]),
                        Integer.parseInt(elts[2]),
                        Integer.parseInt(elts[3])
                );
                scores.add(sc);
            }
            return scores;
        } else {
            return null;
        }
    }

    /*
     * Read the SCORE_FILE and return a String Array List
     */
    static ArrayList readScore() {
        ArrayList scores = new ArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(Game.SCORE_FILE));
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
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Write the new score line to the SCORE_FILE
     */
    //public void writeScore(int player1Score, int player2Score, boolean isEnemyAI, int gameLevel) {
    public void writeScore() {
        File file = new File(Game.SCORE_FILE);
        Date today = new Date();
     //   String score = today.getTime() + " " + Game.GM.getPlayerName() + " " + player1Score + " " + player2Score + " " + (isEnemyAI ? "1" : "0") + " " + gameLevel;
       String score = Game.GM.getPlayerName()  + " " +  today.getTime() + " " + Game.GM.getScoreTotal() + " " + Game.GM.getCurrentGameLevel();
       
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
