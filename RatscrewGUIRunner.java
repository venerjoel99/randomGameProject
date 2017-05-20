/**
 * This is a class that plays the GUI version of the Elevens game.
 * See accompanying documents for a description of how Elevens is played.
 */
public class RatscrewGUIRunner {

	/**
	 * Plays the GUI version of Elevens.
	 * @param args is not used.
	 */
	public static void main(String[] args) {
		Ratscrew game = new Ratscrew(2);
		RatscrewGUI gui = new RatscrewGUI(game);
		gui.displayGame();
	}
}
