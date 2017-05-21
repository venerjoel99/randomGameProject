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

    /** The main panel containing the game components. */
    private JPanel panel;
    /** Game Labels*/
    private JLabel playerCards[];
    private JLabel previousCards[] = new JLabel[3];
    private JLabel winMsg;
    private JLabel statusMsg;

    /**
     * Initialize the GUI.
     * @param gameBoard is a <code>Board</code> subclass.
     */
    public RatscrewGUI(int players) {
        game = new Ratscrew(players);
        playerCards = new JLabel[players];
        initDisplay();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
    
    /**
     * Returns the image that corresponds to the input card.
     * Image names have the format "[Rank][Suit].GIF" or "[Rank][Suit]S.GIF",
     * for example "aceclubs.GIF" or "8heartsS.GIF". The "S" indicates that
     * the card is selected.
     *
     * @param c Card to get the image for
     * @param isSelected flag that indicates if the card is selected
     * @return String representation of the image
     */
    private String imageFileName(Card c, boolean isSelected) {
        String str = "cards/";
        if (c == null) {
            return "cards/back1.GIF";
        }
        str += c.rank() + c.suit();
        if (isSelected) {
            str += "S";
        }
        str += ".GIF";
        return str;
    }
    
    private String imageFileName(Card c){
        return imageFileName(c, false);
    }
    
    /**
     * Initialize the display.
     */
    private void initDisplay()  {
        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };

        this.setTitle("Egyptian Ratscrew");
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        
        panel.setLayout(null);
        panel.setPreferredSize(
            new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
            
        for (int j = 0; j < previousCards.length ; j++){
            previousCards[j] = new JLabel();
            panel.add(previousCards[j]);
        }
        for (int i = 0; i < playerCards.length; i++){
            playerCards[i] = new JLabel();
            playerCards[i].setFont(new Font("SansSerif", Font.BOLD, 10));
            panel.add(playerCards[i]);
            playerCards[i].setBounds(player1Left + (i * 100), player1Top, CARD_WIDTH, CARD_HEIGHT);
        }
        
        previousCards[0].setBounds(pileLeft + (CARD_WIDTH/2), pileTop, CARD_WIDTH, CARD_HEIGHT);
        previousCards[1].setBounds(pileLeft, pileTop, CARD_WIDTH, CARD_HEIGHT);
        previousCards[2].setBounds(pileLeft - (CARD_WIDTH/2), pileTop, CARD_WIDTH, CARD_HEIGHT);

        statusMsg = new JLabel();
        statusMsg.setBounds(pileLeft, pileTop - 40, 100, 30);
        panel.add(statusMsg);
        statusMsg.setVisible(false);

        winMsg = new JLabel();
        winMsg.setBounds(pileLeft, pileTop + 120, 100, 30);
        winMsg.setFont(new Font("SansSerif", Font.BOLD, 10));
        winMsg.setForeground(Color.GREEN);
        panel.add(winMsg);
        winMsg.setVisible(false);

        panel.setPreferredSize(
            new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
        getContentPane().add(panel);
        panel.addKeyListener(this);
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        //getRootPane().setDefaultButton(replaceButton);
        panel.setVisible(true);
    }
    
    /**
     * Draw the display (cards and messages).
     */
    public void repaint() {
        String cardImageFileName;
        for (int i = 0; i < previousCards.length; i++){
           if (game.getCardPile().size()>i){
                cardImageFileName = imageFileName(game.getCardPile().get(game.getCardPile().size()-(1+i)));
           }
           else cardImageFileName = imageFileName(null);
           URL imageURL = getClass().getResource(cardImageFileName);
           if (imageURL !=null){
            ImageIcon icon = new ImageIcon(imageURL);
            previousCards[i].setIcon(icon);
            previousCards[i].setVisible(cardImageFileName!=imageFileName(null));
           }else {
                throw new RuntimeException(
                    "Card image not found: \"" + cardImageFileName + "\"");
           }
        }
        for (int i = 0; i < playerCards.length; i++){
            playerCards[i].setText(game.deckSize(i) + "cards");
        }
        if (game.claimable()){
            winMsg.setText("claim");
            winMsg.setVisible(true);
        }
        else winMsg.setVisible(false);
        if (game.gameOver()){
            winMsg.setText("Player " + game.gameWinner() + " wins");
            winMsg.setVisible(true);
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
                statusMsg.setText((game.getCurrentPlayer() + 1) + " draws");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='s'){
            if (!game.claim(0)){
                if (game.claimable()) statusMsg.setText((game.getFaceCardPlayer() + 1) + " claims");
                else statusMsg.setText("illegal");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='d'){
            if (!game.draw(1)){
                statusMsg.setText((game.getCurrentPlayer() + 1) + " draws");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='f'){
            if (!game.claim(1)){
                if (game.claimable()) statusMsg.setText((game.getFaceCardPlayer() + 1) + " claims");
                else statusMsg.setText("illegal");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='g'){
            if (!game.draw(2)){
                statusMsg.setText((game.getCurrentPlayer() + 1) + " draws");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='h'){
            if (!game.claim(2)){
                if (game.claimable()) statusMsg.setText((game.getFaceCardPlayer() + 1) + " claims");
                else statusMsg.setText("illegal");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='j'){
            if (!game.draw(3)){
                statusMsg.setText((game.getCurrentPlayer() + 1) + " draws");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='k'){
            if (!game.claim(3)){
                if (game.claimable()) statusMsg.setText((game.getFaceCardPlayer() + 1) + " claims");
                else statusMsg.setText("illegal");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getKeyChar()=='r'){
            game.newGame();
            repaint();
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
    public JLabel getWinMsg(){ return winMsg;}
}
