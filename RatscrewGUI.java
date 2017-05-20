import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
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
public class RatscrewGUI extends JFrame implements ActionListener
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
    /** Buttons in the game*/
    private JButton player1Draw;
    private JButton player2Draw;
    private JButton player1Claim;
    private JButton player2Claim;
    /** Game Labels*/
    private JLabel player1Cards;
    private JLabel player2Cards;
    private JLabel previousCards[] = new JLabel[3];
    private JLabel winMsg;
    private JLabel statusMsg;

    /**
     * Initialize the GUI.
     * @param gameBoard is a <code>Board</code> subclass.
     */
    public RatscrewGUI(Ratscrew gameBoard) {
        game = gameBoard;
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
        
        previousCards[0] = new JLabel();
        previousCards[1] = new JLabel();
        previousCards[2] = new JLabel();
        player1Cards = new JLabel(game.deckSize(0) + "cards");
        player2Cards = new JLabel(game.deckSize(1) + "cards");
        player1Cards.setFont(new Font("SansSerif", Font.BOLD, 10));
        player2Cards.setFont(new Font("SansSerif", Font.BOLD, 10));
        panel.add(previousCards[0]);
        panel.add(previousCards[1]);
        panel.add(previousCards[2]);
        panel.add(player1Cards);
        panel.add(player2Cards);
        previousCards[0].setBounds(pileLeft - CARD_WIDTH, pileTop, CARD_WIDTH, CARD_HEIGHT);
        previousCards[1].setBounds(pileLeft, pileTop, CARD_WIDTH, CARD_HEIGHT);
        previousCards[2].setBounds(pileLeft + CARD_WIDTH, pileTop, CARD_WIDTH, CARD_HEIGHT);
        player1Cards.setBounds(player1Left, player1Top, CARD_WIDTH, CARD_HEIGHT);
        player2Cards.setBounds(player2Left, player2Top, CARD_WIDTH, CARD_HEIGHT);
        
        player1Draw = new JButton();
        player2Draw = new JButton();
        player1Claim = new JButton();
        player2Claim = new JButton();
        
        player1Draw.setText("Draw");
        player2Draw.setText("Draw");
        player1Claim.setText("Claim");
        player2Claim.setText("Claim");
        
        panel.add(player1Draw);
        panel.add(player2Draw);
        panel.add(player1Claim);
        panel.add(player2Claim);
       
        player1Draw.setBounds(player1Left, player1Top + 105, 73, 30);
        player2Draw.setBounds(player2Left, player2Top + 105, 73, 30);
        player1Claim.setBounds(player1Left, player1Top + 135, 73, 30);
        player2Claim.setBounds(player2Left, player2Top + 135, 73, 30);
        
        player1Draw.addActionListener(this);
        player2Draw.addActionListener(this);
        player1Claim.addActionListener(this);
        player2Claim.addActionListener(this);

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
            previousCards[i].setVisible(true);
           }else {
				throw new RuntimeException(
					"Card image not found: \"" + cardImageFileName + "\"");
		   }
        }
		player1Cards.setText(game.deckSize(0) + "cards");
        player2Cards.setText(game.deckSize(1) + "cards");
        if (game.claimable()){
            winMsg.setText("claim");
            winMsg.setVisible(true);
        }
        else winMsg.setVisible(false);
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
    
    /**
     * Respond to a button click (on either the "Replace" button
     * or the "Restart" button).
     * @param e the button click action event
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(player1Draw)){
            if (!game.draw(0)){
                statusMsg.setText("Player 1 cannot draw right now");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getSource().equals(player2Draw)){
            if (!game.draw(1)){
                statusMsg.setText("Player 2 cannot draw right now");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getSource().equals(player1Claim)){
            if (!game.claim(0)){
                statusMsg.setText("Illegal claim by player 1");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else if (e.getSource().equals(player2Claim)){
            if (!game.claim(1)){
                statusMsg.setText("Illegal claim by player 2");
                statusMsg.setVisible(true);
            }
            else statusMsg.setVisible(false);
            //repaint();
        }
        else {
            signalError();
            return;
        }
        if (game.gameOver()){
            winMsg.setText("Player " + (game.gameWinner()+1) + " wins");
            winMsg.setVisible(true);
        }
        repaint();
    }
}
