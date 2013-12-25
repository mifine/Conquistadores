package conquistadores;

/**
 *
 * @author mifine
 */
public class PlayerHuman extends Player {
    
    @Override
    public void playTurn() {
        System.out.println("Human Plays");
        this.setCasesClickPossibility(true);
    }

    @Override
    public String whoAmI() {
        return "Human";
    }
    
}
