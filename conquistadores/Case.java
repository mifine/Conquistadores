package conquistadores;

/**
 *
 * @author mifine
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Case extends JPanel implements MouseListener {

    private int posx, posy;
    private int nbCasesSelected;
    private int clan;
    private int troopsNumber;
    private int ground;
    private boolean isHuman;
    private Color initialColor, clanColor;
    private boolean isSelected, canBeSelected;
    public int[][] caseSelected = new int[Game.BOARD_SIZE][Game.BOARD_SIZE];

    public Case(int x, int y) {
        super();
        this.posx = x;
        this.posy = y;
        this.initialize();
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        int xnb, ynb = 52 * 5 / Game.BOARD_SIZE;
        int x = 26 * 5 / Game.BOARD_SIZE;
        int y = 15 * 5 / Game.BOARD_SIZE;
        int width = 76 * 5 / Game.BOARD_SIZE;
        int height = 48 * 5 / Game.BOARD_SIZE;
        int arcWidth = 10;
        int arcHeight = 10;
        g.setColor(this.clanColor);
        g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
        g.setColor(this.clanColor.darker().darker());
        g.setFont(new Font("Verdana", 1, 40 * 5 / Game.BOARD_SIZE));
        if (this.troopsNumber < 10) {
            xnb = 51 * 5 / Game.BOARD_SIZE;
        } else {
            xnb = 38 * 5 / Game.BOARD_SIZE;
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

            if (!(this.clan != Game.GM.gameMecanics.getWhoIsPlayingInt() && nbCasesSelectedBeforeClick == 0)) {

                if (this.isSelected && this.caseSelected[this.posx][this.posy] == 1 && nbCasesSelectedBeforeClick == 1
                        || this.isSelected && this.caseSelected[this.posx][this.posy] == 2) {
                    this.isSelected = false;
                    this.setBackground(this.initialColor);
                    for (int i = 0; i < Game.BOARD_SIZE; i++) {
                        for (int j = 0; j < Game.BOARD_SIZE; j++) {
                            FrameGame.boardGamePanel.grid[i][j].caseSelected[this.posx][this.posy] = 0;
                            if (nbCasesSelectedBeforeClick == 1) {
                                FrameGame.boardGamePanel.grid[i][j].canBeSelected = true;     // if the case we deselect is the only one that was selected, we can select any case
                            }
                            FrameGame.boardGamePanel.grid[i][j].nbCasesSelected--;            // indicate to all cases that one case is deselected
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
                    for (int i = 0; i < Game.BOARD_SIZE; i++) {
                        for (int j = 0; j < Game.BOARD_SIZE; j++) {
                            if (nbCasesSelectedBeforeClick == 0) {
                                FrameGame.boardGamePanel.grid[i][j].caseSelected[this.posx][this.posy] = 1;
                                FrameGame.boardGamePanel.grid[i][j].canBeSelected = this.isNeighbour(i, j);
                            }
                            if (nbCasesSelectedBeforeClick == 1) {
                                FrameGame.boardGamePanel.grid[i][j].caseSelected[this.posx][this.posy] = 2;
                            }
                            FrameGame.boardGamePanel.grid[i][j].nbCasesSelected++;         // indicate to all cases that one additional case is selected
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
            if (this.nbCasesSelected == 0 && this.clan == Game.GM.gameMecanics.getWhoIsPlayingInt()) {
                this.setBackground(Color.LIGHT_GRAY);
                this.canBeSelected = true;
            } // one case is already selected, can be selected only if the neighbour case has been selected
            else if (this.nbCasesSelected == 1 && this.canBeSelected) {
                this.setBackground(Color.LIGHT_GRAY);
                if (!this.isSelected) {
                    boolean neighbour = false;
                    for (int i = 0; i < Game.BOARD_SIZE; i++) {
                        for (int j = 0; j < Game.BOARD_SIZE; j++) {
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

        neighbour[0] = (x > 0) ? FrameGame.boardGamePanel.grid[x - 1][y] : null;
        neighbour[1] = (y > 0) ? FrameGame.boardGamePanel.grid[x][y - 1] : null;
        neighbour[2] = (y < (Game.BOARD_SIZE - 1)) ? FrameGame.boardGamePanel.grid[x][y + 1] : null;
        neighbour[3] = (x < (Game.BOARD_SIZE - 1)) ? FrameGame.boardGamePanel.grid[x + 1][y] : null;

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
            this.troopsNumber = Game.INITIAL_TROOPS_NATIVE;
        } else {
            this.troopsNumber = Game.INITIAL_TROOPS_PLAYER;
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

    public void setIsHuman(boolean bool) {
        this.isHuman = bool;
    }

    public void setGround(int gd) {
        this.ground = gd;
        this.initialColor = Game.GROUND_COLOR[gd];
        this.setBackground(this.initialColor);
    }

    public int getGround() {
        return this.ground;
    }

    // END GET - SET

    /*
     * Initialize a Case with default values.
     */
    public void initialize() {
        this.isSelected = false;
        this.canBeSelected = true;
        this.nbCasesSelected = 0;
        this.setBackground(this.initialColor);
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                this.caseSelected[i][j] = 0;
            }
        }
    }

    /*
     * Update case background display when played by the AI
     */
    public void updateBackgroundDisplay() {
        try {
            this.setBackground(Color.LIGHT_GRAY);
            Thread.sleep(500);
            // Thread.sleep(10);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        this.repaint();
    }

}
