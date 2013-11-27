
/**
 *
 * @author mifine
 */
import java.awt.*;
import java.awt.Color;
import javax.swing.*;

public class Board extends JPanel {

    public Case grid[][] = new Case[5][5];
    public JPanel board = new JPanel();
    public Color playerColor;
    public int[] selectedCase = new int[2];

    public JPanel east = new JPanel();
    public JPanel west = new JPanel();
    public JPanel south = new JPanel();
    public JPanel north = new JPanel();
    int i, j;

    public void Board() {

        this.board.setLayout(new GridLayout(5, 5));
     //   this.board.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                this.grid[i][j] = new Case(i, j);
                if (i % 2 == j % 2) {
                    this.grid[i][j].setBackground(Color.DARK_GRAY);
                } else {
                    this.grid[i][j].setBackground(Color.DARK_GRAY);
                }
                this.board.add(grid[i][j]);
            }
        }
    }

    /*
     Initialize the token placement at the begining of a new Game. 
     clan = 1 : 10 Player 1, 
     clan = 0 : 5 Indigeneous
     clan = 2 : 10 Player 2
     */
    public void InitPlacement() {

        for (i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                if (j < 2) {
                    this.grid[i][j].setClan(1);
                    this.grid[i][j].setClanColor(this.playerColor);
                }
                if (j == 2) {
                    this.grid[i][j].setClan(0);
                    this.grid[i][j].setClanColor(new Color(218, 165, 32));
                }
                if (j > 2) {
                    this.grid[i][j].setClan(2);
                    this.grid[i][j].setClanColor(new Color(255, 51, 51));
                }
                this.grid[i][j].repaint();
            }
        }
    }

    /*
     Set Player Color
     */
    public void setPlayerColor(Color colorToSet) {
        this.playerColor = colorToSet;
    }

    public Case getCase(int x, int y) {
        return this.grid[x][y];
    }

}
