
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

    int posx, posy;
    public int clan;
    public Color initialColor;
    public Color clanColor;
    public int troopsNumber;
    boolean isSelected;
    boolean canBeSelected;
    int nbCasesSelected;
    int[][] caseSelected = new int[5][5];

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
        int x = 2, y = 2, width = 128, height = 74, arcWidth = 10, ArcHeight = 10;
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

        int nbCasesSelectedBeforeClick = this.nbCasesSelected;

        if (!(this.clan != 1 && nbCasesSelectedBeforeClick == 0)) {
            // Case clicked is the first case that has been selected and no other case is selected : we can deselect it OR
            // Case clicked is the second case that has been selected : we can deselect it
            if (this.isSelected && this.caseSelected[this.posx][this.posy] == 1 && nbCasesSelectedBeforeClick == 1
                    || this.isSelected && this.caseSelected[this.posx][this.posy] == 2) {
                this.isSelected = false;
                this.setBackground(Color.DARK_GRAY);
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        Game.boardGame.grid[i][j].caseSelected[this.posx][this.posy] = 0;
                        if (nbCasesSelectedBeforeClick == 1) {
                            Game.boardGame.grid[i][j].canBeSelected = true;     // if the case we deselect is the only one that was selected, we can select any case 
                        }
                        Game.boardGame.grid[i][j].nbCasesSelected--;            // indicate to all cases that one case is deselected
                    }
                }
            } // Case clicked was not selected and can be selected : we can select it
            else if (!this.isSelected && this.canBeSelected && this.nbCasesSelected < 2) {
                this.isSelected = true;
                this.setBackground(Color.LIGHT_GRAY);
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (nbCasesSelectedBeforeClick == 0) {
                            Game.boardGame.grid[i][j].caseSelected[this.posx][this.posy] = 1;
                            Game.boardGame.grid[i][j].canBeSelected = this.isNeighbourg(i, j);
                        }
                        if (nbCasesSelectedBeforeClick == 1) {
                            Game.boardGame.grid[i][j].caseSelected[this.posx][this.posy] = 2;
                        }
                        Game.boardGame.grid[i][j].nbCasesSelected++;         // indicate to all cases that one additional case is selected
                    }
                }
                this.canBeSelected = true;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (this.nbCasesSelected == 0 && this.clan == 1) {
            System.out.println("Nb caseSelected = 0");
            this.setBackground(Color.LIGHT_GRAY);
            this.canBeSelected = true;
        } else if (this.nbCasesSelected == 1 && this.canBeSelected) {
            System.out.println("Nb caseSelected = 1 et this case can be selected");
            this.setBackground(Color.LIGHT_GRAY);
            if (!this.isSelected) {
                boolean neighbourg = false;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (this.isNeighbourg(i, j) && this.caseSelected[i][j] == 1);
                        neighbourg = true;
                    }
                }
                if (neighbourg) {
                    this.setBackground(Color.LIGHT_GRAY);
                    this.canBeSelected = true;
                } else {
                    this.canBeSelected = false;
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!this.isSelected && this.nbCasesSelected != 2) {
            this.setBackground(this.initialColor);
        }
        if (this.nbCasesSelected == 1) {
            //       this.canBeSelected = false;
        }
        System.out.println("Case is exited and canBeSelected = " + this.canBeSelected + " and isSelected = " + this.isSelected);
    }

    public boolean isNeighbourg(int i, int j) {
        boolean result = false;
        int x = this.posx;
        int y = this.posy;
        if ((i == (x - 1) && j == y) || (i == (x + 1) && j == y) || (i == x && j == (y - 1)) || (i == x && j == (y + 1))) {
            return true;
        } else {
            return false;
        }
    }

    // GET , SET
    public void setClanColor(Color colorId) {
        this.clanColor = colorId;
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

    public boolean getIsSelected() {
        return this.isSelected;
    }
    
    public void initialize(){
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
