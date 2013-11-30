package conquistadores;

/**
 *
 * @author mifine
 */
import conquistadores.ui.FrameStart;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Case extends JPanel implements MouseListener {

    public int posx, posy;
    private int nbCasesSelected;
    private int clan;
    private int troopsNumber;
    private boolean isHuman;
    private Color initialColor, clanColor;
    private boolean isSelected, canBeSelected;
    public int[][] caseSelected = new int[5][5];

    public Case(int x, int y) {
        super();
        this.posx = x;
        this.posy = y;
        this.initialize();
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        int xnb, ynb = 52;
        int x = 2, y = 2, width = 132, height = 74, arcWidth = 10, ArcHeight = 10;
        g.setColor(this.clanColor);
        g.fillRoundRect(x, y, width, height, arcWidth, ArcHeight);
        g.setColor(this.clanColor.darker().darker());
        g.setFont(new Font("Verdana", 1, 40));
        if (this.troopsNumber < 10) {
            xnb = 51;
        } else {
            xnb = 38;
        }
        g.drawString(Integer.toString(this.troopsNumber), xnb, ynb);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (this.isHuman) {
            int nbCasesSelectedBeforeClick = this.nbCasesSelected;

            if (!(this.clan != FrameStart.gameMecanics.getWhoIsPlayingInt() && nbCasesSelectedBeforeClick == 0)) {

                if (this.isSelected && this.caseSelected[this.posx][this.posy] == 1 && nbCasesSelectedBeforeClick == 1
                        || this.isSelected && this.caseSelected[this.posx][this.posy] == 2) {
                    this.isSelected = false;
                    this.setBackground(Color.DARK_GRAY);
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            FrameStart.boardGamePanel.grid[i][j].caseSelected[this.posx][this.posy] = 0;
                            if (nbCasesSelectedBeforeClick == 1) {
                                FrameStart.boardGamePanel.grid[i][j].canBeSelected = true;     // if the case we deselect is the only one that was selected, we can select any case
                            }
                            FrameStart.boardGamePanel.grid[i][j].nbCasesSelected--;            // indicate to all cases that one case is deselected
                        }
                    }
                } /*
                 * Case clicked is the first case that has been selected and no
                 * other case is selected : we can deselect it OR Case clicked
                 * is the second case that has been selected : we can deselect
                 * it
                 */ else if (!this.isSelected && this.canBeSelected && this.nbCasesSelected < 2) {
                    this.isSelected = true;
                    this.setBackground(Color.LIGHT_GRAY);
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (nbCasesSelectedBeforeClick == 0) {
                                FrameStart.boardGamePanel.grid[i][j].caseSelected[this.posx][this.posy] = 1;
                                FrameStart.boardGamePanel.grid[i][j].canBeSelected = this.isNeighbour(i, j);
                            }
                            if (nbCasesSelectedBeforeClick == 1) {
                                FrameStart.boardGamePanel.grid[i][j].caseSelected[this.posx][this.posy] = 2;
                            }
                            FrameStart.boardGamePanel.grid[i][j].nbCasesSelected++;         // indicate to all cases that one additional case is selected
                        }
                    }
                    this.canBeSelected = true;
                }  // Case clicked was not selected and can be selected : we can select it
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (this.isHuman) {
            // no case is selected yet : the player can select any of his cases
            if (this.nbCasesSelected == 0 && this.clan == FrameStart.gameMecanics.getWhoIsPlayingInt()) {
                this.setBackground(Color.LIGHT_GRAY);
                this.canBeSelected = true;
            } // one case is already selected, can be selected only if the neighbour case has been selected
            else if (this.nbCasesSelected == 1 && this.canBeSelected) {
                this.setBackground(Color.LIGHT_GRAY);
                if (!this.isSelected) {
                    boolean neighbour = false;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (this.isNeighbour(i, j) && this.caseSelected[i][j] == 1) {
                                neighbour = true;
                            }
                        }
                    }
                    if (neighbour) {
                        this.setBackground(Color.LIGHT_GRAY);
                        this.canBeSelected = true;
                    } else {
                        this.canBeSelected = false;
                    }
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (this.isHuman) {
            if (!this.isSelected && this.nbCasesSelected != 2) {
                this.setBackground(this.initialColor);
            }
            if (this.nbCasesSelected == 1) {
                //       this.canBeSelected = false;
            }
        }
    }

    public boolean isNeighbour(int i, int j) {
        boolean result = false;
        int x = this.posx;
        int y = this.posy;
        if ((i == (x - 1) && j == y) || (i == (x + 1) && j == y) || (i == x && j == (y - 1)) || (i == x && j == (y + 1))) {
            return true;
        } else {
            return false;
        }
    }

    public Case[] getNeighbours() {
        Case[] neighbour = new Case[4];
        int x = this.posx;
        int y = this.posy;

        neighbour[0] = (x > 0) ? FrameStart.boardGamePanel.grid[x - 1][y] : null;
        neighbour[1] = (y > 0) ? FrameStart.boardGamePanel.grid[x][y - 1] : null;
        neighbour[2] = (y < 4) ? FrameStart.boardGamePanel.grid[x][y + 1] : null;
        neighbour[3] = (x < 4) ? FrameStart.boardGamePanel.grid[x + 1][y] : null;

        return neighbour;
    }

    // GET , SET
    public void setClanColor(Color colorId) {
        this.clanColor = colorId;
    }

    public Color getClanColor() {
        return this.clanColor;
    }

    public void setClan(int clanId) {
        this.clan = clanId;
        if (clanId == 0) {
            this.troopsNumber = 5;
        } else {
            this.troopsNumber = 10;
        }
    }

    public int getClan() {
        return this.clan;
    }

    public void setTroopsNumber(int nb) {
        this.troopsNumber = nb;
    }

    public int getTroopsNumber() {
        return this.troopsNumber;
    }

    public void setIsSelected(boolean bool) {
        this.isSelected = bool;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setCanBeSelected(boolean bool) {
        this.canBeSelected = bool;
    }

    public boolean getCanBeSelected() {
        return this.canBeSelected;
    }

    public void setNbCasesSelected(int nb) {
        this.nbCasesSelected = nb;
    }

    public void setIsHuman(boolean b) {
        this.isHuman = b;
    }

    // END GET - SET

    /*
     * Initialize a Case with default values.
     */
    public void initialize() {
        this.isSelected = false;
        this.canBeSelected = true;
        this.nbCasesSelected = 0;
        this.initialColor = Color.DARK_GRAY;
        this.setBackground(this.initialColor);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                this.caseSelected[i][j] = 0;
            }
        }
    }

}
