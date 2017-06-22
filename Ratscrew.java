import java.util.List;
import java.util.ArrayList; 
/**
 * The class that handles the game's rules and the deck to be used
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ratscrew
{
    //The pile where players put their cards down
    private List<Card> cardPile;
    //Players' piles of cards during the game.  Number of piles depend on number of players
    private List<List<Card>> playerPiles;
    //The object that has the deck of cards
    private Deck deck;
    //Is the deck being dealt out?
    private boolean dealing = true; 
    //Number of players
    private int players = 0;
    //Index of current player drawing
    private int currentPlayer = 0;
    //Player who has put down a face card, if any
    private int faceCardPlayer = -1;
    //Number of cards the next player has to put down after previous plyaer puts down face card
    private int numberToDraw = 0;
    //All card ranks ace to king
    private final String[] RANKS =
        {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    //All four suit
    private final String[] SUITS =
        {"spades", "hearts", "diamonds", "clubs"};
    //Number of cards next player has to put down for each rank    
    private final int[] POINT_VALUES =
        {4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3};
    
    /**
     * Constructor for the game class
     * @param players the number of players playing the game
     */
    public Ratscrew(int players){
        this.players = players;
        deck = new Deck(RANKS, SUITS, POINT_VALUES);
        playerPiles = new ArrayList<List<Card>>();
        for (int i=0; i < players; i++){
            playerPiles.add(new ArrayList<Card>());
        }
        newGame();
    }
    
    /**
     * Switches the turn to the next player with at least 1 card after
     * previous player puts down a card
     */
    public void switchTurn(){
        if (currentPlayer==players-1) currentPlayer = 0;
        else currentPlayer++;
        if (!dealing && playerPiles.get(currentPlayer).size()==0) switchTurn();
    }
    
    /**
     * Starts a new game, shuffles the deck, and deals out all the cards
     */
    public void newGame(){
        dealing = true;
        deck.shuffle();
        currentPlayer = 0;
        for (List<Card> pile : playerPiles){
            pile.clear();
        }
        for (int i=0; i < deck.getDeckSize(); i++){
            playerPiles.get(currentPlayer).add(deck.deal());
            switchTurn();
        }
        currentPlayer = 0;
        cardPile = new ArrayList<Card>();
        dealing = false;
    }
    
    /**
     * True if the last two cards on the center pile
     * have matching ranks, false otherwise
     */
    public boolean pair(){
        if (cardPile.size() > 1 && cardPile.get(cardPile.size()-1)!=null
            && cardPile.get(cardPile.size()-2)!=null){
            return cardPile.get(cardPile.size()-1).rank()
            .equals(cardPile.get(cardPile.size()-2).rank());
        }
        return false;
    }
    
    /**
     * True if the last card and the third to last card
     * have matching ranks
     */
    public boolean sandwich(){
        if (cardPile.size() > 2 && cardPile.get(cardPile.size()-1)!=null
            && cardPile.get(cardPile.size()-3)!=null){
            return cardPile.get(cardPile.size()-1).rank()
            .equals(cardPile.get(cardPile.size()-3).rank());
        }
        return false;
    }
    
    /**
     * True if the last card put down was a face card
     */
    private boolean faceCard(){
        return ((cardPile.get(cardPile.size()-1)).pointValue()>0);
    }
    
    /**
     * True if the claiming player is able to take the pile of cards on the center
     */
    private boolean legalClaim(int claimer){
        if (claimable() && faceCardPlayer==claimer) return true;
        if (pair() || sandwich()) return true;
        return false;
    }
    
    /**
     * Gives a player all cards on the center if it is legal; otherwise, player loses a card
     * @return whether the pile has been claimed by the player
     */
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
            if (playerPiles.get(currentClaimer).size()>0)
            cardPile.add(0, playerPiles.get(currentClaimer).remove(0));
            return false;
        }
    }
    
    /**
     * True if the pile is claimable by any or a specific player
     */
    public boolean claimable(){
        return (faceCardPlayer!=-1 && numberToDraw==0);
    }
    
    /**
     * True if the game is over
     */
    public boolean gameOver(){
        if (!pair() && !sandwich() && !claimable()){
            if (gameWinner()!=-1) return true;
            if (cardPile.size()==deck.getDeckSize()) return true;
        }
        return false;
    }
    
    /**
     * Gives the index of the winning player, not the number(player number - 1)
     */
    public int gameWinner(){
        int winner = -1;
        for (int i = 0; i < players; i++){
            int cards = 0;
            for (int j=0; j< players; j++){
                 if (i!=j) cards+=playerPiles.get(j).size();
            }
            if (cards==0){     
                winner = i;
            }
        }
        if (cardPile.size() > 0 && 
        cardPile.get(cardPile.size()-1).pointValue()>0 && faceCardPlayer!=winner) return -1;
        return winner;       
    }
    
    /**
     * Puts down a card on the center pile for a player
     * @param currentDrawer the player that attempts to put down the card
     * @return whether the player has successfully put down the card
     */
    public boolean draw(int currentDrawer){    
        if (!gameOver() && currentPlayer==currentDrawer && !claimable()){
            int size = playerPiles.get(currentPlayer).size();
            if (size>0){
                cardPile.add(playerPiles.get(currentPlayer).remove(size-1));
                if (faceCard()) {
                    numberToDraw = cardPile.get(cardPile.size()-1).pointValue();
                    faceCardPlayer = currentPlayer;
                    switchTurn();
                }
                else if (numberToDraw>0) numberToDraw--;
                else if (numberToDraw==0 && !claimable()) switchTurn();
            }
            else switchTurn();
            return true;
        }
        return false;
    }
    
    /**
     * Returns the number of cards of a specific player
     * @param player the chosen player whose data is to be displayed
     */
    public int deckSize(int player){
        if (playerPiles.size()>player){
            return playerPiles.get(player).size();
        }
        else return 0;
    }
    
    /**
     * Getter for the number of cards a player has to draw in a row
     */
    public int getNumToDraw(){ 
        return numberToDraw;
    }    
    /**
     * Getter for the current player to draw
     */
    public int getCurrentPlayer(){ 
        return currentPlayer;
    }
    /**
     * Returns the last person to put down a face card
     */
    public int getFaceCardPlayer(){
        return faceCardPlayer;
    }
    /**
     * Returns the number of players
     */
    public int getPlayers(){
        return players;
    }
    /**
     * Returns the list of cards a specific player has
     * @param player the index of the player to get the list
     */
    public List<Card> getPlayerPile(int player){
        return playerPiles.get(player);
    }
    /**
     * Returns the center pile of cards
     */
    public List<Card> getCardPile(){
        return cardPile;
    }
}
