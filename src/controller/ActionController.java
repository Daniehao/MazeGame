package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.MazeGame;
import model.MazeGameImpl;
import view.MazeLayout;
import view.MazeMenu;

/**
 * Implementation of the controller for the basic MVC.
 */
public class ActionController implements ActionListener {
  private MazeGame game;
  private MazeMenu menuView;
  private MazeLayout mazeView;

  /**
   * Constructor.
   *
   * @param game
   * @param menuView
   * @param mazeView
   */
  public ActionController(MazeGame game, MazeMenu menuView, MazeLayout mazeView) {
    this.game = game;
    this.menuView = menuView;
    this.mazeView = mazeView;
    menuView.setListener(this);
    menuView.display();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      // read from the input text field
      case "Start Same Game":
        int[] info = menuView.getMazeInput();
        String difficulty = menuView.getDifficulty();
        game = new MazeGameImpl()
        break;
      case "Start New Game Button":
        String text = view.getInputString();
        // send text to the model
        model.setString(text);

        // clear input text field
        view.clearInputString();
        // finally echo the string in view
        text = model.getString();
        view.setEchoOutput(text);

        break;
      case "Exit Button":
        System.exit(0);
        break;

      default:
        throw new IllegalStateException("Error: unknown button");
    }
  }
}

