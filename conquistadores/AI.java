package conquistadores;

/**
 *
 * @author mifine
 */
public class AI extends Player {

    private static String level;                       // level = "easy", "normal" or "difficult"
    private Case caseToPlayOrigin;
    private Case caseToPlayDestination;
    private String action;                            // action = "attack" or "send"

    public void playTurn(int turnNb) {
        this.determineAction();

    }

    /*
     determine which action the IA will play, based on an analysis of the board.
     */ 
    public void determineAction() {
        switch (this.level) {
            case "easy":
                this.playEasyAction();
                break;
            case "normal":
                this.playNormalAction();
                break;
            case "difficult":
                this.playDifficultAction();
                break;
            default:
        }
    }

    /*
     Determine the cases for an easy IA
     */
    public void playEasyAction() {

        /* easy algorith :
         Check for the case that has the max number of troops
            
         */
        int iOr, jOr, iDest, jDest;
//        this.caseToPlayOrigin = FrameStart.boardGamePanel.grid[iOr][jOr];
//        this.caseToPlayDestination = FrameStart.boardGamePanel.grid[iDest][jDest];

    }

    /*
     Determine the cases for an easy IA
     */
    public void playNormalAction() {

    }

    /*
     Determine the cases for an easy IA
     */
    public void playDifficultAction() {

    }

}
