
/**
 *
 * @author mifine
 */
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.applet.*;
import java.awt.Panel.*;
import java.awt.event.ActionListener.*;

public class Game {

    public final static int HT = 400;
    public final static int LG = 600;
    static GamePanel boardGame = new GamePanel();
    static Frame F = new FrameStart(boardGame);
    public static void main(String[] arg) {
        new Game();
    }

    public Game() {
        
        F.setTitle("Conquistadors");
        F.setSize(LG, HT);
        F.setLocationRelativeTo(null);
        F.setBackground(Color.lightGray);
        F.setLayout(new BorderLayout());
        F.setResizable(false);
        F.show();
    }

}
