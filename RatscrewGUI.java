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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Write a description of class RatscrewGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class RatscrewGUI extends JFrame
{
    protected static final int DEFAULT_HEIGHT = 500 * 3;
    protected static final int DEFAULT_WIDTH = 800 * 3;
    protected static final int CARD_WIDTH = 73;
    protected static final int CARD_HEIGHT = 97;    

    /** The board (Board subclass). */
    protected Ratscrew game;

    /** The main panel containing the game components. */
    protected CardPanel panel;

    /**
     * Initialize the GUI.
     * @param gameBoard is a <code>Board</code> subclass.
     */
    public RatscrewGUI(int players) {
        game = new Ratscrew(players);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    /**
     * Initialize the display.
     */
    protected void initDisplay()  {
        this.setTitle("Egyptian Ratscrew");
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));        
        panel.setLayout(null);
        panel.setPreferredSize(
            new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
        panel.initPanel();
        getContentPane().add(panel);
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.setVisible(true);
    }       
    /**
     * Draw the display (cards and messages).
     */
    public void repaint() {
        if (game.gameOver()){
            panel.getWinMsg().setText("Player " + game.gameWinner() + " wins");
            panel.getWinMsg().setVisible(true);
        }
        for (int i = 0; i < panel.previousCards.length; i++){
            panel.previousCards[i].setBounds(getWidth()/2 + (CARD_WIDTH/2) - (CARD_WIDTH*i/2),  getHeight()/2 - (CARD_HEIGHT/2), CARD_WIDTH, CARD_HEIGHT);
        }
        panel.statusMsg.setBounds(getWidth()/2, getHeight()/2 - CARD_HEIGHT/2 - 30, 100, 30);
        panel.winMsg.setBounds(getWidth()/2, getHeight()/2 + CARD_HEIGHT/2 + 30, 100, 30);
        panel.refresh();
        panel.repaint();
    }    
    /**
     * Deal with the user clicking on something other than a button or a card.
     */
    protected void signalError() {
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
    /**
     * Attempt to put down a card of a player
     * @param player the index of the player who is attempting to draw
     */
    protected void draw(int player){
        if (!game.draw(player)){
           if (game.claimable()){
               panel.getStatusMsg().setText("P" + (game.getFaceCardPlayer() + 1) + " claims");
           } else {
               panel.getStatusMsg().setText("P" + (game.getCurrentPlayer() + 1) + " draws");
           }
           panel.getStatusMsg().setVisible(true);  
        } else panel.getStatusMsg().setVisible(false);
    }
    /**
     * Attempt to give all center pile cards to a player
     * @param player the index of the player attempting a claim
     */
    protected void claim(int player){
        if (!game.claim(player)){
            if (game.claimable()) panel.getStatusMsg().setText("P" + (game.getFaceCardPlayer() + 1) + " claims");
            else panel.getStatusMsg().setText("False Claim by P" + (player+1));
            panel.getStatusMsg().setVisible(true);
        }
        else panel.getStatusMsg().setVisible(false);
    }
    /**
     * An abstract panel class for the abstract GUI class
     */
    protected abstract class CardPanel extends JPanel{
        private Ratscrew game;
        private JLabel[] previousCards;
        private JLabel[] playerCards;
        private JLabel[] playerData;
        private JLabel statusMsg;
        private JLabel winMsg;
        /**
         * Constructor for the card panel class
         */
        protected CardPanel(RatscrewGUI frame){
            this.game = frame.game;
        }
        /**
         * Draws all player cards and data labels and 
         * the labels to display the last 3 cards of the center pile
         */
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            
        }
        /**
         * Initializes the panel components
         */
        protected void initPanel(){
            previousCards = new JLabel[3];
            playerCards = new JLabel[game.getPlayers()];
            playerData  = new JLabel[game.getPlayers()];
            for (int j = 0; j < previousCards.length ; j++){
                previousCards[j] = new JLabel();
                this.add(previousCards[j]);
            }
            for (int i = 0; i < playerCards.length; i++){
                playerCards[i] = new JLabel();
                playerData[i] = new JLabel();
                playerData[i].setFont(new Font("SansSerif", Font.BOLD, 20));
                this.add(playerCards[i]);
                this.add(playerData[i]);
            }
            statusMsg = new JLabel();
            winMsg = new JLabel();
            this.add(statusMsg);
            statusMsg.setVisible(false);
            winMsg.setFont(new Font("SansSerif", Font.BOLD, 20));
            winMsg.setForeground(Color.BLACK);
            this.add(winMsg);
            winMsg.setVisible(false);
        }
        public void refresh(){
            for (int i = 0; i < previousCards.length; i++){
                String cardImageFileName;
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
                playerData[i].setText("P" + (i+1) + ": " + game.deckSize(i) + " cards");
                playerCards[i].setIcon(new ImageIcon(getClass().getResource(imageFileName(null))));
                playerData[i].setVisible(true);
                playerCards[i].setVisible(game.deckSize(i)>0 && game.getCurrentPlayer()==i);
            }
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
        /**
         * Returns the image file name corresponding to the card,
         */
        private String imageFileName(Card c){
            return imageFileName(c, false);
        }
        /**
         * Getter for the game object
         */
        protected Ratscrew getGame(){ 
            return game;
        }
        /**
         * Getter for a previous card on the center
         */
        protected JLabel getPreviousCard(int index){
            if (previousCards.length > index && !(index<0)) return previousCards[index];
            return null;
        }
        /**
         * Getter for the deck of a specific player
         * @param player the player whose card label is to be returned
         */
        protected JLabel getPlayerCards(int index){
            if (playerCards.length > index && !(index<0)) return playerCards[index];
            return null;
        }
        /**
         * Getter for the data label of a specific player
         */
        protected JLabel getPlayerData(int index){
            if (playerData.length > index && !(index<0)) return playerData[index];
            return null;
        }
        /**
         * Getter for the win message label
         */
        protected JLabel getWinMsg(){ return winMsg;}
        /**
         * Getter for the status message label
         */
        protected JLabel getStatusMsg(){return statusMsg;}
        /**
         * Return the number of cards on the center
         */
        protected int pileSize(){return previousCards.length;}
        /**
         * Return the number of players playing the game
         */
        protected int players(){return playerCards.length;}
    }
    /**
     * Getter for the gui's game member object
     */
    public Ratscrew getGame(){return game;}
    /**
     * Return the panel member of the gui
     */
    public CardPanel getPanel(){return panel;}
}
