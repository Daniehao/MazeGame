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
        int rows = info[0];
        int cols = info[1];
        int walls = info[2];
        int player = info[3];
        boolean isWrapping = menuView.getWrapping();
        String difficulty = menuView.getDifficulty();

        if (isWrapping && walls > cols * rows + rows * cols - rows * cols + 1) {
          menuView.msgbox();
        } else if (!isWrapping && walls > (cols - 1) * rows + (rows - 1) * cols - rows * cols + 1) {
          menuView.msgbox();
        } else {
          boolean isPerfect = false;
          if (isWrapping && walls == cols * rows + rows * cols - rows * cols + 1 || !isWrapping &&
                  walls == (cols - 1) * rows + (rows - 1) * cols - rows * cols + 1) {
            isPerfect = true;
          }
          double batPercent = 0;
          double pitPercent = 0;
          int arrows = 0;
          if (difficulty == "easy") {
            batPercent = 0.1;
            pitPercent = 0.1;
            arrows = 10;
          }
          if (difficulty == "medium") {
            batPercent = 0.2;
            pitPercent = 0.2;
            arrows = 5;
          }
          if (difficulty == "hard") {
            batPercent = 0.3;
            pitPercent = 0.3;
            arrows = 3;
          }
          game = new MazeGameImpl(rows, cols, walls, isPerfect, isWrapping, batPercent,
                  pitPercent, arrows);
        }

//        if (walls )
//        game = new MazeGameImpl()
//        break;
//      case "Start New Game Button":
//        String text = view.getInputString();
//        // send text to the model
//        model.setString(text);
//
//        // clear input text field
//        view.clearInputString();
//        // finally echo the string in view
//        text = model.getString();
//        view.setEchoOutput(text);
//
//        break;
//      case "Exit Button":
//        System.exit(0);
//        break;

      default:
        throw new IllegalStateException("Error: unknown button");
    }
  }
}

