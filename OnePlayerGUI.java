import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
/**
 * Write a description of class OnePlayerGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OnePlayerGUI extends RatscrewGUI implements MouseListener
{
    /**
     * Constructor for objects of class OnePlayerGUI
     */
    public OnePlayerGUI()
    {
        super(4);
        panel = new OnePlayerPanel(this);
        initDisplay();
        // initialise instance variables
        for (int i = 0; i < panel.pileSize(); i++){
            panel.getPreviousCard(i).addMouseListener(this);
        }
        panel.getPlayerCards(0).addMouseListener(this);
        panel.getWinMsg().addMouseListener(this);
        repaint();
    }
    public void displayGame(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);              
            }
        });
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
        if (e.getSource().equals(panel.getPlayerCards(0))){
            draw(0);      
            repaint();
        }
        else if (e.getSource().equals(panel.getWinMsg())){
            if (getGame().gameOver()) getGame().newGame();
		 	panel.getWinMsg().setVisible(false);
        }
        else {
            boolean pileClicked = false;
            for (int i = 0; i < panel.pileSize(); i++){
                if(e.getSource().equals(panel.getPreviousCard(i)))  pileClicked = true;
            }
            if (pileClicked){
                if (getGame().deckSize(0)>0)claim(0);
            }
            repaint();
        }
    }
    private class OnePlayerPanel extends CardPanel{
        public OnePlayerPanel(RatscrewGUI frame){
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
            getPlayerCards(3).setBounds(20, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
            getPlayerData(3).setBounds(20, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
            getPlayerData(0).setText("You: " + getGame().deckSize(0));
        }
    }
}

