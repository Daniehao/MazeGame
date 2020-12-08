package game.view;

import java.awt.event.ActionListener;

/**
 * The interface to get and wrap up the user's optimized settings about the maze game, and it also
 * includes method to display the graphical interface of the game menu.
 */
public interface MenuView {

  /**
   * Set listeners on the buttons of the menu.
   * @param listener The ActionListener.
   */
  public void setListener(ActionListener listener);

  /**
   * Display the game menu's graphical interface.
   */
  void display();

  /**
   * Fetch the user's input on the maze game.
   * @return The array of user's input.
   */
  int[] getMazeInput();

  /**
   * Get if the maze if wrapping or not.
   * @return True/False.
   */
  boolean getWrapping();

  /**
   * Get the difficulty option.
   * @return The difficulty option.
   */
  String getDifficulty();

  /**
   * The pop out message to indicate the input number of walls is invalid.
   */
  void msgbox();
}
