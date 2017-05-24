
/**
 * Write a description of class ThreePlayerGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ThreePlayerGUI extends RatscrewGUI
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class ThreePlayerGUI
     */
    public ThreePlayerGUI()
    {
        // initialise instance variables
        x = 0;
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public int sampleMethod(int y)
    {
        playerCards[0].setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - CARD_HEIGHT - 20, CARD_WIDTH, CARD_HEIGHT);
        playerData[0].setBounds(getWidth()/2 - CARD_WIDTH/2, getHeight() - (4*CARD_HEIGHT/3) - 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
        playerCards[1].setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - CARD_HEIGHT/2, CARD_WIDTH, CARD_HEIGHT);
        playerData[1].setBounds(getWidth() - CARD_WIDTH, getHeight()/2 - (3*CARD_HEIGHT/4), CARD_WIDTH*2, CARD_HEIGHT/3);
        playerCards[2].setBounds(getWidth()/2 - CARD_WIDTH/2, 20 + CARD_HEIGHT / 3, CARD_WIDTH, CARD_HEIGHT);
        playerData[2].setBounds(getWidth()/2 - CARD_WIDTH/2, 20, CARD_WIDTH * 2, CARD_HEIGHT/3);
    }
}
