package view;

import java.util.function.Consumer;

import model.Cell;

/**
 * The view interface for the maze game and it includes all of the operations that the controller
 * would need to invoke on the view.
 *
 */
public interface GameView {

  void buildMaze(Cell[] cells);

  void createAndShowGUI();

  void addComponents(int rows, int cols, String alert, int playerNum);

  /**
   * Transmit an error message to the view, in case the command could not be
   * processed correctly.
   *
   * @param error the message
   */
  void showErrorMessage(String error);
}
