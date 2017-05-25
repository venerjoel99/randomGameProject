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
        RatscrewGUI gui = new OnePlayerGUI();
        Ratscrew game = gui.getGame();
        gui.displayGame();
        
        while (playing){
            while (!game.gameOver()){                
                if (game.claimable() || game.pair() || game.sandwich()){                    
                    sleep(waitTime/2);
                    autoClaim(game, gui);
                }
                else if (game.getCurrentPlayer()!=0) {
                    sleep(waitTime/2);
                    game.draw(game.getCurrentPlayer());  
                    gui.repaint();
                }
                else if (game.getCurrentPlayer()==0 && game.deckSize(0)==0) {
                    game.switchTurn();
                    gui.repaint();
                }
                else {
                    sleep(waitTime/2);
                }
                sleep(waitTime/2);
            }
        }
        
    }
    public static void autoClaim(Ratscrew game, RatscrewGUI gui){
        //sleep(waitTime/2);
        if (game.claimable() && game.getFaceCardPlayer()!=0){
            //sleep(waitTime);
            gui.claim(game.getFaceCardPlayer());
            gui.repaint();
        }  
        else if (game.pair() || game.sandwich()){
            int claimer=0; 
            do{
                claimer = (int) (Math.random()*game.getPlayers());
            }while (claimer==0 || game.getPlayerPile(claimer).size()==0);
            gui.claim(claimer);                                 
            gui.repaint();
        }
    }
    public static void sleep(int waitTime){
        try{
            Thread.sleep(waitTime);
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
