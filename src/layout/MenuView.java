package layout;

import java.awt.event.ActionListener;
import java.util.function.Consumer;

import game.Cell;

public interface MenuView {
  /**
   * Make the view visible. This is usually called after the view is constructed
   */
  void makeVisible();

  void setCommandCallback(Consumer<String> callback);

  public void setListener(ActionListener listener);

  /**
   * Transmit an error message to the view, in case the command could not be
   * processed correctly.
   *
   * @param error the message
   */
  void showErrorMessage(String error);

}
