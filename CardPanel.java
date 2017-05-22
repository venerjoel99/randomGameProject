// 3.7
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.net.URL;

public class CardPanel extends JPanel{
    private static final int CARD_WIDTH = 73;
    private static final int CARD_HEIGHT = 97;
    private JLabel example;
    private RatscrewGUI frame;
    private Ratscrew game;
    private JLabel[] previousCards;
    private JLabel[] playerCards;
    private JLabel[] playerData;
    private JLabel statusMsg;
    private JLabel winMsg;
    private int x = 0;
    private int y = 0;
    public CardPanel(RatscrewGUI frame){
        this.frame = frame;
        this.game = this.frame.getGame();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        previousCards[0].setBounds(getWidth()/2 + (CARD_WIDTH/2),  getHeight()/2 - (CARD_HEIGHT/2), CARD_WIDTH, CARD_HEIGHT);
        previousCards[1].setBounds(getWidth()/2, getHeight()/2 - (CARD_HEIGHT/2), CARD_WIDTH, CARD_HEIGHT);
        previousCards[2].setBounds(getWidth()/2 - (CARD_WIDTH/2),  getHeight()/2 - (CARD_HEIGHT/2), CARD_WIDTH, CARD_HEIGHT);
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
        int players = playerCards.length;
        switch(players){
            case 1:
                playerCards[0].setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - CARD_HEIGHT - 20, CARD_WIDTH, CARD_HEIGHT);
                playerData[0].setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - (4*CARD_HEIGHT/3) - 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
                break;
            case 2:
                playerCards[0].setBounds(20, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
                playerData[0].setBounds(20, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
                playerCards[1].setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
                playerData[1].setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
                break;
            case 3:
                playerCards[0].setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - CARD_HEIGHT - 20, CARD_WIDTH, CARD_HEIGHT);
                playerData[0].setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - (4*CARD_HEIGHT/3) - 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
                playerCards[1].setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
                playerData[1].setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
                playerCards[2].setBounds(getWidth()/2 - CARD_WIDTH/2, 20 + CARD_HEIGHT / 3, CARD_WIDTH, CARD_HEIGHT);
                playerData[2].setBounds(getWidth()/2 - CARD_WIDTH/2, 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
                break;
            case 4:
                playerCards[0].setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - CARD_HEIGHT - 20, CARD_WIDTH, CARD_HEIGHT);
                playerData[0].setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - (4*CARD_HEIGHT/3) - 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
                playerCards[1].setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
                playerData[1].setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
                playerCards[2].setBounds(getWidth()/2 - CARD_WIDTH/2, 20 + CARD_HEIGHT / 3, CARD_WIDTH, CARD_HEIGHT);
                playerData[2].setBounds(getWidth()/2 - CARD_WIDTH/2, 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
                playerCards[3].setBounds(20, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
                playerData[3].setBounds(20, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
                break;
            default:
                if (players>0){
                    for (int i = 0; i < players; i++){
                        playerCards[i].setBounds(getWidth()/2 - 300 + (i * 100), getHeight() / 2 + 100, 
                        CARD_WIDTH, CARD_HEIGHT); 
                        playerData[i].setBounds(getWidth()/2 -300 + (i * 100), getHeight() / 2 + 100 + CARD_HEIGHT,
                        CARD_WIDTH, CARD_HEIGHT/3);
                    }
                }
                break;
        }
        for (int i = 0; i < playerCards.length; i++){
           playerData[i].setText("P" + (i+1) + ": " + game.deckSize(i) + " cards");
           playerCards[i].setIcon(new ImageIcon(getClass().getResource(imageFileName(null))));
           playerData[i].setVisible(true);
           playerCards[i].setVisible(game.deckSize(i)>0);
        }
        statusMsg.setBounds(getWidth()/2, getHeight()/2 - CARD_HEIGHT/2 - 30, 100, 30);
        winMsg.setBounds(getWidth()/2, getHeight()/2 + CARD_HEIGHT/2 + 30, 100, 30);
    }
    public void initPanel(){
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
            playerData[i].setFont(new Font("SansSerif", Font.BOLD, 10));
            this.add(playerCards[i]);
            this.add(playerData[i]);
        }
        statusMsg = new JLabel();
        winMsg = new JLabel();
        this.add(statusMsg);
        statusMsg.setVisible(false);
        winMsg.setFont(new Font("SansSerif", Font.BOLD, 10));
        winMsg.setForeground(Color.GREEN);
        this.add(winMsg);
        winMsg.setVisible(false);
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
    public JLabel getStatus(){ return statusMsg;}
    public JLabel getWinMsg(){ return winMsg;}
}