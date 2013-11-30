package conquistadores;

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
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            for (int j = 0; j < Game.BOARD_SIZE; j++) {
                FrameStart.boardGamePanel.setIsHuman(bool);
                FrameStart.boardGamePanel.grid[i][j].setIsHuman(bool);
                
            }
        }
    }
    
   // GET, SET
    public void setLevel(int lvl) {
        this.level = lvl;
    }
   
    public String whoAmI() {
        return "Player";
    }
   
}
