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
        boolean playing = true;
        FourPlayerGUI gui = new FourPlayerGUI();
        Ratscrew game = gui.getGame();
        gui.getPanel().removeKeyListener(gui);
        gui.displayGame();
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
    public void keyPressed(KeyEvent e){return;}
}
