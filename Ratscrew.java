import java.util.List;
import java.util.ArrayList; 
/**
 * Write a description of class Ratscrew here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ratscrew
{
    private List<Card> cardPile;
    private List<List<Card>> playerPiles;
    private Deck deck;
    private boolean dealing = true;
    private int players = 0;
    private int currentPlayer = 0;
    private int faceCardPlayer = -1;
    private int numberToDraw = 0;
    private final String[] RANKS =
        {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    private final String[] SUITS =
        {"spades", "hearts", "diamonds", "clubs"};
    private final int[] POINT_VALUES =
        {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3};
    
    public Ratscrew(int players){
        this.players = players;
        deck = new Deck(RANKS, SUITS, POINT_VALUES);
        playerPiles = new ArrayList<List<Card>>();
        newGame(players);
        dealing = false;
    }
    
    public void switchTurn(){
        if (currentPlayer==players-1) currentPlayer = 0;
        else currentPlayer++;
        if (!dealing && playerPiles.get(currentPlayer).size()==0) switchTurn();
    }
    
    public void newGame(int players){
        deck.shuffle();
        for (int i=0; i < players; i++){
            playerPiles.add(new ArrayList<Card>());
        }
        for (int i=0; i < deck.getDeckSize(); i++){
            playerPiles.get(currentPlayer).add(deck.deal());
            switchTurn();
        }
        currentPlayer = 0;
        cardPile = new ArrayList<Card>();
    }
    
    private boolean pair(){
        if (cardPile.size() > 1 && 
            cardPile.get(cardPile.size()-1).rank()
            .equals(cardPile.get(cardPile.size()-2).rank()))
         return true;
        return false;
    }
    
    private boolean sandwich(){
        if (cardPile.size() > 2 &&
            cardPile.get(cardPile.size()-1).rank()
            .equals(cardPile.get(cardPile.size()-3).rank()))
         return true;
        return false;
    }
    
    private boolean faceCard(){
        return ((cardPile.get(cardPile.size()-1)).pointValue()>0);
    }
    
    private boolean legalClaim(int claimer){
        if (claimable() && faceCardPlayer==claimer) return true;
        if (pair() || sandwich()) return true;
        return false;
    }
    
    public boolean claim(int currentClaimer){
        if (!gameOver() && legalClaim(currentClaimer)){
            int cardPileSize = cardPile.size();
            while(cardPile.size()>0){
                playerPiles.get(currentClaimer).add(0, cardPile.remove(0));
            }
            currentPlayer = currentClaimer;
            faceCardPlayer = -1;
            numberToDraw = 0;
            return true;
        }
        else {
            cardPile.add(0, playerPiles.get(currentClaimer).remove(0));
            return false;
        }
    }
    
    public boolean claimable(){
        if (faceCardPlayer!=-1 && numberToDraw==0){
            int num = currentPlayer - faceCardPlayer;
            if (num == 1 || Math.abs(num)==players-1) return true;
        }
        return false;
    }
    
    public boolean gameOver(){
        if (!pair() && !sandwich() && !claimable()){
            for (List<Card> player : playerPiles){
              if (player.size()==deck.getDeckSize()) return true;
            }
            if (cardPile.size()==deck.getDeckSize()) return true;
        }
        return false;
    }
    
    public int gameWinner(){
        if (gameOver()){
            for (int i = 0; i < playerPiles.size(); i++){
                if (playerPiles.get(i).size()==deck.size()) return i;
            }
        }
        return -1;       
    }
    
    public boolean draw(int currentDrawer){    
        if (!gameOver() && currentPlayer==currentDrawer && !claimable()){
            int size = playerPiles.get(currentPlayer).size();
            cardPile.add(playerPiles.get(currentPlayer).remove(size-1));
            if (faceCard()) {
                numberToDraw = cardPile.get(cardPile.size()-1).pointValue();
                faceCardPlayer = currentPlayer;
                switchTurn();
            }
            else if (numberToDraw>0) numberToDraw--;
            else if (numberToDraw==0 && !claimable()) switchTurn();
            return true;
        }
        return false;
    }
    
    public int deckSize(int player){
        if (playerPiles.size()>player){
            return playerPiles.get(player).size();
        }
        else return 0;
    }
    
    public int getNumToDraw() { return numberToDraw;}    
    public List<Card> getCardPile(){
        return cardPile;
    }
}
