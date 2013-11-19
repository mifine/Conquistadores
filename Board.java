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

public class Board extends Canvas {

    public Token grille[][] = new Token[5][5];
    Panel board = new Panel();
    Panel east = new Panel();
    Panel west = new Panel();
    Panel south = new Panel();
    Panel north = new Panel();
    int i, j;

    public void Board() {

        board.setLayout(new GridLayout(5, 5));
        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                grille[i][j] = new Token(i, j);
                board.add(grille[i][j]);
            }
        }
    }

/*
    Initialize the token placement at the begining of a new Game. 
    10 Player 1, 
    5 Indigeneous
    10 Player 2
    */ 	 

    public void InitPlacement() {

        boolean pairLigne = true;
        for (i = 0; i < 4; i++) {
            boolean pairColonne = true;
            for (j = 0; j < 10; j++) {
                if ((pairLigne && (!pairColonne)) || ((!pairLigne) && pairColonne)) {
                    grille[i][j].pion = -1;
                }
                grille[i][j].repaint();
                pairColonne = !pairColonne;
            }
            pairLigne = !pairLigne;
        }
        //boucle pour avoir les pions rouges: 
        boolean pairLigneProuge = true;
        for (i = 6; i < 10; i++) {
            boolean pairColonneProuge = true;
            for (j = 0; j < 10; j++) {
                if ((pairLigneProuge && !pairColonneProuge) || ((!pairLigneProuge) && (pairColonneProuge))) {
                    grille[i][j].pion = 1;
                }
                grille[i][j].repaint();
                pairColonneProuge = !pairColonneProuge;
            }
            pairLigneProuge = !pairLigneProuge;
        }
    }

}
