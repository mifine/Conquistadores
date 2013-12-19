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

    final static FrameGame F = new FrameGame();

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
    static String PLACEMENT = "random";                                   // random or normal
    final static Color[] GROUND_COLOR = new Color[5];
    static public ScoreManager sm;

    public static void main(String[] arg) {

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game();
                //new NewJFramegger().setVisible(true);
            }
        });

    }

    public Game() {
      //  new GameEngine();
        sm = new ScoreManager();
        
        F.setTitle("Conquistadors");
        F.setSize(600, 400);
        F.setLocationRelativeTo(null);
        F.setBackground(Color.lightGray);
        F.setLayout(new BorderLayout());
        F.setResizable(false);
        F.show();
        
    }

}
