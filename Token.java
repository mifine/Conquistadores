/**
 *
 * @author mifine
 */

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Token extends Canvas implements MouseListener {

    int posx, posy;
    int pion = 0;
    static int[][] position = new int[5][5];

    public Token(int x, int y) {
        super();
        posx = x;//ligne
        posy = y;//colonne
        addMouseListener(this);
        position[posx][posy] = 0;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
