package conquistadores;

/**
 *
 * @author mifine
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.*;

public class Case extends JPanel implements MouseListener {

    private int posx, posy;
    private int nbCasesSelected;
    private int clan;
    private int troopsNumber;
    private int ground;
    private boolean isHuman;
    private Color initialColor, clanColor;
    private Color initialClanColor, initialBrightClanColor;
    private boolean isSelected, canBeSelected;
    private boolean displayAttack;
    private int attack = 0;
    public int[][] caseSelected = new int[Game.BOARD_SIZE][Game.BOARD_SIZE];

    public Case(int x, int y) {
        super();
        this.posx = x;
        this.posy = y;
        this.initialize();
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        double coeff = 1;

        int x = 10;
        int y = 10;
        int width = 114;
        int height = 56;
//         int x = 2;
//        int y = 2;
//        int width = 130;
//        int height = 72;
        int arc = 10;
        int xOval = 0, yOval = 0;
        int xMiddle = 10 - 2 + (114 - 8) / 2;
        int yMiddle = 10 + 21;
        int sizePion = 10;

//        if (Game.BOARD_SIZE == 7 || Game.BOARD_SIZE == 11) {
//            x = 10 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//            y = 10 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//            width = 114 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//            height = 56 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//            sizePion = sizePion * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//        }

        super.paint(g);
        g.setColor(this.clanColor.darker().darker());
        g.fillRoundRect(x, y, width, height, arc, arc);
        g.setColor(this.clanColor);
        g.fillRoundRect((int) (x + 4 * coeff), (int) (y + 4 * coeff), (int) (width - 8 * coeff), (int) (height - 8 * coeff), arc, arc);
        g.setColor(this.clanColor.darker().darker());

        if (!this.displayAttack) {
            for (int i = 0;
                    i < this.troopsNumber; i++) {
                if (i < 7) {
                    xOval = xMiddle + (i % 2 == 1 ? (14 * (i + 1) / 2) : (-14 * i / 2));
                    yOval = yMiddle;
                } else if (i < 13) {
                    xOval = xMiddle - 7 + (i % 2 == 1 ? (14 * (i + 1 - 7 + 1) / 2) : (-14 * (i - 7) / 2) + 7);
                    yOval = yMiddle - 14;
                } else if (i < 19) {
                    xOval = xMiddle - 7 + (i % 2 == 1 ? (14 * (i + 1 - 13 + 1) / 2) : (-14 * (i - 13) / 2) + 7);
                    yOval = yMiddle + 14;
                }

//                if (Game.BOARD_SIZE == 7 || Game.BOARD_SIZE == 11) {
//                    xOval = xOval * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//                    yOval = yOval * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//                }
                g.fillOval(xOval, yOval, sizePion, sizePion);
            }
        }

        //to display the attack total score
        if (this.displayAttack) {
            xOval = xMiddle - 21;
            yOval = yMiddle - 8;
            int xWidth = 4 * 14;
            int yHeight = 2 * 14;
            g.setFont(new Font("Verdana", 1, 15));
//            if (Game.BOARD_SIZE == 7 || Game.BOARD_SIZE == 11) {
//                xOval = xOval * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//                yOval = yOval * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//                xWidth = xWidth * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//                yHeight = yHeight * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//                xMiddle = xMiddle * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//                yMiddle = yMiddle * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE;
//                g.setFont(new Font("Verdana", 1, 15 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE));
//            }
            g.setColor(Color.LIGHT_GRAY);
            g.fillRoundRect(xOval, yOval, xWidth, yHeight, arc, arc);
            g.setColor(this.clanColor.darker().darker());
            g.fillRoundRect(xOval + 2, yOval + 2, xWidth - 4, yHeight - 4, arc, arc);
            g.setColor(this.clanColor);
            if (this.attack < 10) {
                g.drawString(Integer.toString(this.attack), xMiddle + 3 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE, yMiddle + 17 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE);
            } else {
                g.drawString(Integer.toString(this.attack), xMiddle - 7 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE, yMiddle + 17 * (Game.BOARD_SIZE - 2) / Game.BOARD_SIZE);
            }

        }
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
//                    this.setBackground(Color.LIGHT_GRAY);
                    this.clanColor = this.initialBrightClanColor;
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
//                this.setBackground(Color.LIGHT_GRAY);
                this.clanColor = this.initialBrightClanColor;
                this.canBeSelected = true;
            } // one case is already selected, can be selected only if the neighbour case has been selected
            else if (this.nbCasesSelected == 1 && this.canBeSelected) {
//                this.setBackground(Color.LIGHT_GRAY);
                this.clanColor = this.initialBrightClanColor;
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
//                        this.setBackground(Color.LIGHT_GRAY);
                        this.clanColor = this.initialBrightClanColor;
                        this.canBeSelected = true;
                    } else {
                        this.canBeSelected = false;
                    }
                }
            }
        }
        this.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (this.isHuman) {
            if (!this.isSelected && this.nbCasesSelected != 2) {
                this.clanColor = this.initialClanColor;
                this.setBackground(this.initialColor);
            }
            if (this.nbCasesSelected == 1) {
                //       this.canBeSelected = false;
            }
        }
        this.repaint();
    }

    public boolean isNeighbour(int i, int j) {
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

    public void setInitialClanColor(Color colorId) {
        this.initialClanColor = colorId;
    }

    public Color getInitialClanColor() {
        return this.initialClanColor;
    }

    public void setInitialBrightClanColor(Color colorId) {
        this.initialBrightClanColor = colorId;
    }

    public Color getInitialBrightClanColor() {
        return this.initialBrightClanColor;
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
        //  this.initialClanColor = 
        this.setBackground(this.initialColor);
    }

    public int getGround() {
        return this.ground;
    }

    public void setDisplayAttack(boolean bool) {
        this.displayAttack = bool;
    }

    public boolean getDisplayAttack() {
        return this.displayAttack;
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
        this.clanColor = this.initialClanColor;
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
//            this.setBackground(Color.LIGHT_GRAY);
            this.clanColor = this.initialBrightClanColor;
            this.repaint();
            Thread.sleep(500);
            // Thread.sleep(10);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        this.repaint();
    }

    /*
     * Display the attack score if bool = true, remove if bool = false
     */
    public void displayAttack(boolean bool, int att) {
        this.displayAttack = bool;
        this.attack = att;
        this.repaint();
        if (bool) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
