
/**
 * Write a description of class RatscrewSim here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RatscrewOnePlayer
{
    public static int waitTime = 1000;
    public static void main(String[] args){
        boolean currentlyPlaying = true;
        boolean playing = true;
        RatscrewGUI gui = new RatscrewGUI(4);
        Ratscrew game = gui.getGame();
        gui.displayGame();
        while (playing){
            while (!game.gameOver()){
                if (game.getCurrentPlayer()!=0) game.draw(game.getCurrentPlayer());
                else while (game.getCurrentPlayer()==0);
                gui.repaint();
                if (game.pair() || game.sandwich()){
                    try{
                        Thread.sleep(waitTime);
                    }
                    catch(Exception e){
                        System.out.println(e.toString());
                    }
                    if (game.pair() || game.sandwich()){
                        int claimer; 
                        do{
                            claimer = (int) (Math.random()*game.getPlayers());
                        }while (game.getPlayerPile(claimer).size()==0 && claimer==0);
                        game.claim(claimer);
                    }
                    gui.repaint();
                }
                else if (game.claimable() && game.getFaceCardPlayer()!=0){
                    try{
                        Thread.sleep(waitTime);
                    }
                    catch(Exception e){
                        System.out.println(e.toString());
                    }
                    game.claim(game.getFaceCardPlayer());
                    gui.repaint();
                }
                try{
                    Thread.sleep(waitTime);
                }
                catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        }
    }
}
