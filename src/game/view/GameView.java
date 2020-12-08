package game.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;

import game.model.Cell;

/**
 * The game.view interface for the maze game and it includes all of the operations that the game.controller
 * would need to invoke on the game.view.
 *
 */
public interface GameView {

  void createAndShowGUI();

  void addComponents(int rows, int cols, int playerNum);

  /**
   * Transmit an error message to the game.view, in case the command could not be
   * processed correctly.
   *
   * @param error the message
   */
  void showErrorMessage(String error);

  String getShootDistance();

  void setMoveDirection(String direction);

  String getMoveDirection();

  void setShootDirection(String direction);

  String getShootDirection();

  void setAlertPanel(String alert, int flag);

  void changeAlertPanel(String alert, int flag);

  public void setListener(ActionListener listener, KeyListener keyListener);

  public void showPlayer(int flag, int x, int y);

  public void changeViewByMove(List<Cell> getWalkedCells, int[] newPos, int flag);

  public void clearPanels();

  public void resetFocus();
}
