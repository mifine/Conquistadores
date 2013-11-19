
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

    public final static int HT = 500;
    public final static int LG = 500;
    static Board board = new Board();
    JFrame Game;
    JPanel GamePano;
    JButton NPBouton, Inform, Quitter, Video;
    JLabel Version;

    public static void main(String[] arg) {
        new Game();
    }

    public Game() {
        Frame F = new Frame();
        F.setTitle("Conquistadors");
        F.setSize(LG, HT);
        F.setLocationRelativeTo(null);
        F.setBackground(Color.lightGray);
        F.setLayout(new BorderLayout());
        F.setResizable(false);
   //     F.setIconImage(new ImageIcon("icone.gif").getImage());

        Image icon = Toolkit.getDefaultToolkit().getImage("icone.gif");
        F.setIconImage(icon);
        F.show();

        MenuBar menuBar = new MenuBar();
        Menu menuGame = new Menu("Game");
        Menu menuOptions = new Menu("Options");
        Menu menuInformations = new Menu("Informations");

        MenuItem menuNewGame = new MenuItem("New Game");
        MenuItem menuScores = new MenuItem("Scores");
        MenuItem menuExit = new MenuItem("Exit");
        MenuItem menuColorPlayer = new MenuItem("Player color");
        MenuItem menuRules = new MenuItem("Rules");
        MenuItem menuAbout = new MenuItem("About");

        menuGame.add(menuNewGame);
        menuGame.add(menuScores);
        menuGame.add(menuExit);
        menuOptions.add(menuColorPlayer);
        menuInformations.add(menuRules);
        menuInformations.add(menuAbout);

        menuBar.add(menuGame);
        menuBar.add(menuOptions);
        menuBar.add(menuInformations);

        menuScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    System.out.println("Scores");
                    //      Scores s = new Scores();
                    //     s.VisuScores();
                } catch (Exception e) {
                }
            }
        });

        menuNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    System.out.println("New Game");
                    //       NouvellePartie n = new NouvellePartie();
                    //      n.NewPartie();
                } catch (Exception e) {
                }
            }
        });

        menuAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    System.out.println("About");
//                    Projet p = new Projet();
//                    p.InfoProjet();
                } catch (Exception e) {
                }
            }
        });

        menuRules.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    System.out.println("Rules");
//                    Regles r = new Regles();
//                    r.visuRegles();
                } catch (Exception e) {
                }
            }
        });

        menuExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    System.exit(0);
                } catch (Exception e) {
                }
            }
        });

        menuColorPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    ColorPlayerFrame ColorPlayerFrame = new ColorPlayerFrame();
                    ColorPlayerFrame.setVisible(true);
                    ColorPlayerFrame.setLocationRelativeTo(null);
                } catch (Exception e) {
                }
            }
        });

        F.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        F.setMenuBar(menuBar);

        board.Board();

        F.add(board.board, BorderLayout.CENTER);
        F.add(board.east, BorderLayout.EAST);
        F.add(board.west, BorderLayout.SOUTH);
        F.add(board.south, BorderLayout.WEST);
        F.add(board.north, BorderLayout.NORTH);

        F.show();
    }

    /*
     Initialize the token placement at the begining of a new Game. 
     10 Player 1, 
     5 Indigeneous
     10 Player 2
     */ public static void NewGame() {
        board.InitPlacement();
    }
}
