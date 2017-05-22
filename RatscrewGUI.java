import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class RatscrewGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RatscrewGUI extends JFrame implements KeyListener
{
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 800;
    private static final int CARD_WIDTH = 73;
    private static final int CARD_HEIGHT = 97;
    //Layout of the pile
    private static final int pileTop = 100;
    private static final int pileLeft = 350;
    //Layout of the two player piles
    private static final int player1Top = 160;
    private static final int player1Left = 250;
    private static final int player2Top = 160;
    private static final int player2Left = 450;
    private static final int BUTTON_TOP = 30;
    /** x coord of the "Replace" button. */
    private static final int BUTTON_LEFT = 570;
    /** Distance between the tops of the "Replace" and "Restart" buttons. */
    private static final int BUTTON_HEIGHT_INC = 50;
    /** y coord of the "n undealt cards remain" label. */
    private static final int LABEL_TOP = 160;
    /** x coord of the "n undealt cards remain" label. */
    private static final int LABEL_LEFT = 540;
    /** Distance between the tops of the "n undealt cards" and
     *  the "You lose/win" labels. */
    private static final int LABEL_HEIGHT_INC = 35;

    /** The board (Board subclass). */
    private Ratscrew game;
    
    private JLabel win;
    private JLabel status;

    /** The main panel containing the game components. */
    private CardPanel panel;

    /**
     * Initialize the GUI.
     * @param gameBoard is a <code>Board</code> subclass.
     */
    public RatscrewGUI(int players) {
        game = new Ratscrew(players);
        initDisplay();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
    /**
     * Initialize the display.
     */
    private void initDisplay()  {
        panel = new CardPanel(this);

        this.setTitle("Egyptian Ratscrew");
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        
        panel.setLayout(new GridLayout(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        panel.setPreferredSize(
            new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
        panel.initPanel();
        panel.setPreferredSize(
            new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
        getContentPane().add(panel);
        panel.addKeyListener(this);
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        //getRootPane().setDefaultButton(replaceButton);
        panel.setVisible(true);
        win = panel.getWinMsg();
        status = panel.getStatus();
    }
    
    /**
     * Draw the display (cards and messages).
     */
    public void repaint() {
        if (game.gameOver()){
            win.setText("Player " + game.gameWinner() + " wins");
            win.setVisible(true);
        }
        panel.setPreferredSize(
            new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
        panel.repaint();
    }
    
    /**
     * Deal with the user clicking on something other than a button or a card.
     */
    private void signalError() {
        Toolkit t = panel.getToolkit();
        t.beep();
    }
    
    /**
     * Run the game.
     */
    public void displayGame() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }
    
    public void keyPressed(KeyEvent e){
        if (e.getKeyChar()=='a'){
            if (!game.draw(0)){
                status.setText((game.getCurrentPlayer() + 1) + " draws");
                status.setVisible(true);
            }
            else status.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='s'){
            if (!game.claim(0)){
                if (game.claimable()) status.setText((game.getFaceCardPlayer() + 1) + " claims");
                else status.setText("illegal");
                status.setVisible(true);
            }
            else status.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='d'){
            if (!game.draw(1)){
                status.setText((game.getCurrentPlayer() + 1) + " draws");
                status.setVisible(true);
            }
            else status.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='f'){
            if (!game.claim(1)){
                if (game.claimable()) panel.getStatus().setText((game.getFaceCardPlayer() + 1) + " claims");
                else panel.getStatus().setText("illegal");
                panel.getStatus().setVisible(true);
            }
            else panel.getStatus().setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='g'){
            if (!game.draw(2)){
                status.setText((game.getCurrentPlayer() + 1) + " draws");
                status.setVisible(true);
            }
            else panel.getStatus().setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='h'){
            if (!game.claim(2)){
                if (game.claimable()) status.setText((game.getFaceCardPlayer() + 1) + " claims");
                else status.setText("illegal");
                status.setVisible(true);
            }
            else panel.getStatus().setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='j'){
            if (!game.draw(3)){
                panel.getStatus().setText((game.getCurrentPlayer() + 1) + " draws");
                panel.getStatus().setVisible(true);
            }
            else status.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='k'){
            if (!game.claim(3)){
                if (game.claimable()) status.setText((game.getFaceCardPlayer() + 1) + " claims");
                else status.setText("illegal");
                status.setVisible(true);
            }
            else status.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='r'){
            game.newGame();
        }
        else {
            signalError();
            return;
        }
        repaint();
    }
    public void keyTyped(KeyEvent e){
    }
    public void keyReleased(KeyEvent e){
    }
    public Ratscrew getGame(){ return game;}
}
