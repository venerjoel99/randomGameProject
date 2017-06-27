import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
/**
 * Write a description of class ThreePlayerGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ThreePlayerGUI extends RatscrewGUI implements KeyListener
{
    /**
     * Constructor for objects of class ThreePlayerGUI
     */
    public ThreePlayerGUI()
    {
        super(3);
        panel = new ThreePlayerPanel(this);
        initDisplay();
        panel.addKeyListener(this);
        repaint();
    }
    public void repaint(){
        super.repaint();
        panel.getPlayerCards(0).setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - (3*CARD_HEIGHT/2) - 40, CARD_WIDTH, CARD_HEIGHT);
        panel.getPlayerData(0).setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - (14*CARD_HEIGHT/6) - 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
        panel.getPlayerCards(1).setBounds(getWidth() - (3*CARD_WIDTH/2), getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
        panel.getPlayerData(1).setBounds(getWidth() - CARD_WIDTH*2, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
        panel.getPlayerCards(2).setBounds(getWidth()/2 - CARD_WIDTH/2, 20 + CARD_HEIGHT / 3, CARD_WIDTH, CARD_HEIGHT);
        panel.getPlayerData(2).setBounds(getWidth()/2 - CARD_WIDTH/2, 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
    }
    public void keyPressed(KeyEvent e){
        char key = e.getKeyChar();
        if (key=='r'){
            game.newGame();
            repaint();
            return;
        }
        switch (key){
            case 'a':
                draw(0);
                break;
            case 's':
                claim(0);
                break;
            case 'd':
                draw(1);
                break;
            case 'f':
                claim(1);
                break;
            case 'g':
                draw(2);
                break;
            case 'h':
                claim(2);
                break;
            case 'j':
                draw(3);
                break;
            case 'k':
                claim(3);
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
    private class ThreePlayerPanel extends CardPanel{
        public ThreePlayerPanel(RatscrewGUI frame){
            super(frame);
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            getPlayerCards(0).setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - CARD_HEIGHT - 20, CARD_WIDTH, CARD_HEIGHT);
            getPlayerData(0).setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - (4*CARD_HEIGHT/3) - 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
            getPlayerCards(1).setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
            getPlayerData(1).setBounds(getWidth() - CARD_WIDTH*2, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
            getPlayerCards(2).setBounds(getWidth()/2 - CARD_WIDTH/2, 20 + CARD_HEIGHT / 3, CARD_WIDTH, CARD_HEIGHT);
            getPlayerData(2).setBounds(getWidth()/2 - CARD_WIDTH/2, 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
        }
    }
}
