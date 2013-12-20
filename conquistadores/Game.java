package conquistadores;

/**
 *
 * @author mifine
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game {

    final static FrameGame FG = new FrameGame();
    final static ScoreManager SM = new ScoreManager();
    final static GameManager GM = new GameManager();

    //declare game constants 
    static int MAX_TURN_NUMBER = 1;
    static int MAX_ACTIONS_PER_TURN = 3;
    static int MAX_TROOPS_PER_CASE = 17;
    static int ADD_TROOPS_PER_TURN = 1;
    static int BOARD_SIZE = 7;
    static int INITIAL_TROOPS_NATIVE = 7;
    static int INITIAL_TROOPS_PLAYER = 11;
    static int AI_LEVEL = 0;
    static int GROUND_TYPE = 1;
    static int PLACEMENT = 1;                                   // random=0 or normal=1
    final static Color[] GROUND_COLOR = new Color[5];
    final static String SCORE_FILE = "scores.txt";
    final static String PARAMS_FILE = "gameParameters.dat";

    public static void main(String[] arg) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                FG.setTitle("Conquistadors");
                FG.setSize(600, 400);
                FG.setLocationRelativeTo(null);
                FG.setBackground(Color.lightGray);
                FG.setLayout(new BorderLayout());
                FG.setResizable(false);
                FG.show();
                 
            }
        });
    }

}
