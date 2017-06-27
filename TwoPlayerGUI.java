import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
/**
 * Write a description of class TwoPlayerGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TwoPlayerGUI extends RatscrewGUI implements KeyListener
{
    /**
     * Constructor for objects of class TwoPlayerGUI
     */
    public TwoPlayerGUI()
    {
       super(2);
       panel = new TwoPlayerPanel(this);
       initDisplay();
       panel.addKeyListener(this);
       repaint();
    }
    public void keyPressed(KeyEvent e){
        char key = e.getKeyChar();
        if (key=='r'){
            game.newGame();
            repaint();
            return;
        }
        switch (key){
            case 'f':
                draw(0);
                break;
            case 'j':
                draw(1);
                break;
            case 'd':
                claim(0);
                break;
            case 'k':
                claim(1);
                break;
            default:
                signalError();
                return;
            }
        repaint();
    }
    public void keyTyped(KeyEvent e){
    }
    public void keyReleased(KeyEvent e){
    }
    public void repaint(){
        super.repaint();
        panel.getPlayerCards(0).setBounds(20, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
        panel.getPlayerData(0).setBounds(20, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
        panel.getPlayerCards(1).setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
        panel.getPlayerData(1).setBounds(getWidth() - CARD_WIDTH*2, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
    }
    private class TwoPlayerPanel extends CardPanel{
        public TwoPlayerPanel(RatscrewGUI frame){
            super(frame);
        }
    }
}
