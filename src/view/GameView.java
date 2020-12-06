package view;

import java.awt.event.ActionListener;
import java.util.function.Consumer;

import model.Cell;

/**
 * The view interface for the maze game and it includes all of the operations that the controller
 * would need to invoke on the view.
 *
 */
public interface GameView {

  void createAndShowGUI();

  void addComponents(int rows, int cols, int playerNum);

  /**
   * Transmit an error message to the view, in case the command could not be
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

  public void setListener(ActionListener listener);

  public void showPlayer(int flag, int x, int y);

  public void changeViewByMove(int[] prevPos, String prevCellStatus, int[] newPos);
}
