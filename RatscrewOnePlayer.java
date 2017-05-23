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
        OnePlayerGUI gui = new OnePlayerGUI(4);
        Ratscrew game = gui.getGame();
        gui.displayGame();
        
        while (playing){
            while (!game.gameOver()){
                if (game.getCurrentPlayer()!=0) game.draw(game.getCurrentPlayer());
                autoClaim(game, gui);
                gui.repaint();
                sleep(waitTime);
            }
        }
        
    }
    public static void autoClaim(Ratscrew game, OnePlayerGUI gui){
        if (game.pair() || game.sandwich()){
            sleep(waitTime);
            if (game.pair() || game.sandwich()){
                int claimer=0; 
                do{
                    claimer = (int) (Math.random()*game.getPlayers());
                }while (game.getPlayerPile(claimer).size()==0 && claimer==0);
                game.claim(claimer);                
                System.out.println("Claimed by:" + claimer);
                
            }                                 
            gui.repaint();
        }
        else if (game.claimable() && game.getFaceCardPlayer()!=0){
                sleep(waitTime);
                game.claim(game.getFaceCardPlayer());
                System.out.println("Claimed by:" + game.getFaceCardPlayer());
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
