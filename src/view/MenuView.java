package view;

import java.awt.event.ActionListener;
import java.util.function.Consumer;

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

  /**
   * Display this view.
   */
  void display();

  int[] getMazeInput();

  boolean getWrapping();

  String getDifficulty();

  void msgbox();

  public void setString(String s);
}
