package conquistadores;

import conquistadores.ui.FrameStart;

/**
 *
 * @author mifine
 */
public abstract class Player {
    
    private int numberTurnsPlayed = 0;
    private int level;                       // level = 0 : "random", 1 : "easy", 2 : "normal" or 3 : "difficult"

    
    
    public Player(){
        
    }
    
    public void playTurn() {
              
        
    }
    
    public void closeTurn() {
        
    }

    public void setCasesClickPossibility(boolean bool){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                FrameStart.boardGamePanel.setIsHuman(bool);
                FrameStart.boardGamePanel.grid[i][j].setIsHuman(bool);
                
            }
        }
    }
    
   // GET, SET
    public void setLevel(int lvl) {
        this.level = lvl;
    }
   
   
}
