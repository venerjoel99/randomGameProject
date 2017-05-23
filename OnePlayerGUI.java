import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
/**
 * Write a description of class OnePlayerGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OnePlayerGUI extends RatscrewGUI implements MouseListener
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class OnePlayerGUI
     */
    public OnePlayerGUI(int players)
    {
        super(players);
        // initialise instance variables
        for (int i = 0; i < getPanel().cardPileSize(); i++){
            getPanel().getPreviousCard(i).addMouseListener(this);
        }
        getPanel().getPlayerCards(0).addMouseListener(this);
    }
    @Override
    public void keyPressed(KeyEvent e){
        
    }
    public void mouseExited(MouseEvent e){
        }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseReleased(MouseEvent e){
    }
    public void mousePressed(MouseEvent e){
    }
    public void mouseClicked(MouseEvent e){
        if (e.getSource().equals(getPanel().getPlayerCards(0))){
            draw(0);
        }
        else {
            boolean pileClicked = false;
            for (int i = 0; i < getPanel().cardPileSize(); i++){
                if(e.getSource().equals(getPanel().getPreviousCard(i)))  pileClicked = true;
            }
            if (pileClicked){
                claim(0);
            }
        }
    }
    @Override
    public void repaint(){
        super.repaint();
        getPanel().getPlayerData(0).setText("You: " + getGame().deckSize(0));
    }
}

