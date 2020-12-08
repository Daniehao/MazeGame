package game.view;

import game.model.Cell;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;


/**
 * The GameView interface is the graphical interface of the maze game and it includes all of the
 * operations on moving and shooting that the controller would need to invoke.
 */
public interface GameView {

  /**
   * Show the graphical interface of the maze game.
   */
  void createAndShowGui();

  /**
   * Add panels into the game frame.
   *
   * @param rows      The number of rows of the maze.
   * @param cols      The number of columns of the maze.
   * @param playerNum The number of players in the game.
   */
  void addComponents(int rows, int cols, int playerNum);

  /**
   * Get the shooting distance.
   *
   * @return The shooting distance.
   */
  String getShootDistance();

  /**
   * Set the move direction.
   *
   * @param direction Move direction.
   */
  void setMoveDirection(String direction);

  /**
   * Get the move direction.
   *
   * @return The move direction.
   */
  String getMoveDirection();

  /**
   * Set the shoot direction.
   */
  void setShootDirection(String direction);

  /**
   * Get the shoot direction.
   *
   * @return The shoot direction.
   */
  String getShootDirection();

  /**
   * Set the alert message in the alert panel with the player indicator of the next round.
   *
   * @param alert The alert message.
   * @param flag  The player indicator.
   */
  void setAlertPanel(String alert, int flag);

  /**
   * Change the alert message.
   *
   * @param alert The alert message.
   * @param location The location message.
   * @param flag  The player in the following
   */
  void changeAlertPanel(String alert, String location, int flag);

  /**
   * Set listeners for all of the components in the frame.
   *
   * @param listener    The Actionlistener.
   * @param keyListener The KeyListener.
   */
  public void setListener(ActionListener listener, KeyListener keyListener);

  /**
   * Show the player or players on the maze game at the beginning.
   *
   * @param flag The flag of player.
   * @param x    The init x position of current player.
   * @param y    The init y position of current player.
   */
  public void showPlayer(int flag, int x, int y);

  /**
   * Update the game view when player is moving.
   *
   * @param getWalkedCells The cells that player has passed.
   * @param newPos         The stopped position after player's moving.
   * @param flag           The indicator of the player.
   */
  public void changeViewByMove(List<Cell> getWalkedCells, int[] newPos, int flag);

  /**
   * Clear all of the existing images to empty images in the maze panel.
   */
  public void clearPanels();

  /**
   * Transfer focus for keyboard input.
   */
  public void resetFocus();
}
