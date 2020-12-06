package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.MazeGame;
import model.MazeGameImpl;
import view.GameView;
import view.MenuView;

/**
 * Implementation of the controller for the basic MVC.
 */
public class ActionController implements ActionListener {
  private MazeGame game;
  private MenuView menuView;
  private GameView mazeView;

  /**
   * Constructor.
   *
   * @param game
   * @param menuView
   * @param mazeView
   */
  public ActionController(MazeGame game, MenuView menuView, GameView mazeView) {
    this.game = game;
    this.menuView = menuView;
    this.mazeView = mazeView;
    menuView.setListener(this);
    mazeView.setListener(this);
    menuView.display();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      // read from the input text field
      case "Start New":
        int[] info = menuView.getMazeInput();
        int rows = info[0];
        int cols = info[1];
        int walls = info[2];
        int players = info[3];
        boolean isWrapping = menuView.getWrapping();
        String difficulty = menuView.getDifficulty();
        System.out.println(difficulty);
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
          if (difficulty.equals("easy mode")) {
            batPercent = 0.1;
            pitPercent = 0.1;
            arrows = 10;
          }
          if (difficulty.equals("medium mode")) {
            batPercent = 0.2;
            pitPercent = 0.2;
            arrows = 5;
          }
          if (difficulty.equals("hard mode")) {
            batPercent = 0.3;
            pitPercent = 0.3;
            arrows = 3;
          }
          game = new MazeGameImpl(rows, cols, walls, isPerfect, isWrapping, batPercent,
                  pitPercent, arrows, players);
          mazeView.setAlertPanel(game.getAlert(), 1);
          mazeView.addComponents(rows,cols,players);
          mazeView.createAndShowGUI();
        }
        break;
      case "Move up":
        mazeView.setMoveDirection("up");
        System.out.println(mazeView.getMoveDirection());
        break;
      case "Move down":
        mazeView.setMoveDirection("down");
        break;
      case "Move left":
        mazeView.setMoveDirection("left");
        break;
      case "Move right":
        mazeView.setMoveDirection("right");
        break;
      case "Move":
        game.changePlayerFlag();
        System.out.println(game.getPlayerRound());
        mazeView.changeAlertPanel(game.getAlert(), game.getPlayerRound());
        break;
      default:
        throw new IllegalStateException("Error: unknown button");
    }
  }
}

