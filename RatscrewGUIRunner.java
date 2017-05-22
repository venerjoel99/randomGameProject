/**
 * A simple commit testing comment
 * This is a class that plays the GUI version of the Elevens game.
 * See accompanying documents for a description of how Elevens is played.
 */
public class RatscrewGUIRunner {

    /**
     * Plays the GUI version of Elevens.
     * @param args is not used.
     */
    public static void main(String[] args) {
        RatscrewGUI gui = new RatscrewGUI(4);
        gui.displayGame();
    }
}
