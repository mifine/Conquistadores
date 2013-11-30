package conquistadores;

/**
 *
 * @author mifine
 */
import java.awt.*;
import java.awt.Color;
import java.awt.Panel.*;
import java.awt.event.ActionListener.*;

public class Game {

    final static Frame F = new FrameStart();

    //declare game constants 
    final static int MAX_TURN_NUMBER = 5;
    final static int MAX_ACTIONS_PER_TURN = 3;
    final static int MAX_TROOPS_PER_CASE = 13;

    public static void main(String[] arg) {
        new Game();
    }

    public Game() {
        F.setTitle("Conquistadors");
        F.setSize(600, 400);
        F.setLocationRelativeTo(null);
        F.setBackground(Color.lightGray);
        F.setLayout(new BorderLayout());
        F.setResizable(false);
        F.show();
    }

}
