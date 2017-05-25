import java.awt.event.KeyEvent;
/**
 * Write a description of class RatscrewSim here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RatscrewSim
{
    public static int waitTime = 200;
    public static void main(String[] args){
        boolean currentlyPlaying = true;
        FourPlayerGUI gui = new FourPlayerGUI();
        Ratscrew game = gui.getGame();
        gui.getPanel().removeKeyListener(gui);
        //gui.displayGame();
        simulate(1000, gui);
    }
    public static void simulate(int trials, RatscrewGUI gui){
        int[] players = new int[gui.getGame().getPlayers()];
        for (int k = 0; k < players.length; k++) {
            players[k] = 0;
        }
        for (int i = 0; i < trials; i++){
            int winner = playRound(gui);
            for (int j = 0; j < players.length; j++){
                if (winner==j) players[j]++;
            }
        }
        for (int l = 0; l < players.length; l++){
            System.out.println("P" + (l+1) + ": " + players[l] + " wins");
        }
    }
    public static int playRound(RatscrewGUI gui){
        Ratscrew game = gui.getGame();
        boolean playing = true;
        game.newGame();
        while (playing){
            while (!game.gameOver()){
                game.draw(game.getCurrentPlayer());
                gui.repaint();
                if (game.pair() || game.sandwich()){
                    int claimer;
                    do{
                        claimer = (int) (Math.random()*game.getPlayers());
                    }while (game.getPlayerPile(claimer).size()==0);
                    game.claim(claimer);
                }
                else if (game.claimable()){
                    game.claim(game.getFaceCardPlayer());
                }
            }
            playing = false;
        }
        return game.gameWinner();
    }
    public static void displayedSimulation(RatscrewGUI gui){
        Ratscrew game = gui.getGame();
        boolean playing = true;
        game.newGame();
        while (playing){
            while (!game.gameOver()){
                game.draw(game.getCurrentPlayer());
                try{
                    Thread.sleep(waitTime);
                }
                catch(Exception e){
                    System.out.println(e.toString());
                }
                gui.repaint();
                if (game.pair() || game.sandwich()){
                    int claimer;
                    do{
                        claimer = (int) (Math.random()*game.getPlayers());
                    }while (game.getPlayerPile(claimer).size()==0);
                    game.claim(claimer);
                }
                else if (game.claimable()){
                    game.claim(game.getFaceCardPlayer());
                }
                try{
                    Thread.sleep(waitTime);
                }
                catch(Exception e){
                    System.out.println(e.toString());
                }
                gui.repaint();
            }
        }
    }
}
