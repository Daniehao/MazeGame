package view;

import java.util.function.Consumer;

import model.Cell;

/**
 * The view interface for the maze game and it includes all of the operations that the controller
 * would need to invoke on the view.
 *
 */
public interface GameView {
  /**
   * Make the view visible. This is usually called after the view is constructed
   */
  void makeVisible();

  void setCommandCallback(Consumer<String> callback);

  /**
   * Transmit an error message to the view, in case the command could not be
   * processed correctly.
   *
   * @param error the message
   */
  void showErrorMessage(String error);

  void buildMaze(Cell[] cells);

//  void setCellHasPlayer(int x, int y);


}
