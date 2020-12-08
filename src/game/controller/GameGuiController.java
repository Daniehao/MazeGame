package game.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.model.MazeGame;
import game.model.MazeGameImpl;
import game.view.GameView;
import game.view.MenuView;

/**
 * Implementation of the ActionListener and KeyListener for the Maze Game. The controller enables
 * users to move in the maze as well as the shoot operation to shoot towards to a cave.
 */
public class GameGuiController implements ActionListener, KeyListener {
  private MazeGame game;
  private MenuView menuView;
  private GameView mazeView;

  /**
   * Constructor.
   *
   * @param game     The model of maze game.
   * @param menuView The menu view of maze game.
   * @param mazeView The view of the maze, alert board and control board.
   */
  public GameGuiController(MazeGame game, MenuView menuView, GameView mazeView) {
    this.game = game;
    this.menuView = menuView;
    this.mazeView = mazeView;
    menuView.setListener(this);
    mazeView.setListener(this, this);
    menuView.display();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String s = e.getActionCommand();
    if (s.equals("Start New")) {
      int[] info = menuView.getMazeInput();
      int rows = info[0];
      int cols = info[1];
      int walls = info[2];
      int players = info[3];
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
        mazeView.setAlertPanel(game.getPlayerLocation(), 1);
        mazeView.addComponents(rows, cols, players);
        int[] pos1 = game.getPlayerPosition(1);
        mazeView.showPlayer(1, pos1[0], pos1[1]);
        if (players == 2) {
          int[] pos2 = game.getPlayerPosition(2);
          mazeView.showPlayer(2, pos2[0], pos2[1]);
        }
        mazeView.createAndShowGui();
      }
      mazeView.createAndShowGui();
      mazeView.resetFocus();
      return;
    } else if (s.equals("Start Same Game")) {
      int[] info = menuView.getMazeInput();
      int rows = info[0];
      int cols = info[1];
      int walls = info[2];
      int players = info[3];
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
        mazeView.changeAlertPanel(game.getPlayerLocation(), "", 1);
        mazeView.clearPanels();
        int[] pos1 = game.getPlayerPosition(1);
        mazeView.showPlayer(1, pos1[0], pos1[1]);
        if (players == 2) {
          int[] pos2 = game.getPlayerPosition(2);
          mazeView.showPlayer(2, pos2[0], pos2[1]);
        }
      }
      mazeView.resetFocus();
      return;
    }

    if (!game.getGameEnd()) {
      switch (e.getActionCommand()) {
        case "Move up":
          mazeView.setMoveDirection("N");
          break;
        case "Move down":
          mazeView.setMoveDirection("S");
          break;
        case "Move left":
          mazeView.setMoveDirection("W");
          break;
        case "Move right":
          mazeView.setMoveDirection("E");
          break;
        case "Move":
          game.move(mazeView.getMoveDirection());
          int[] newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        case "Shoot up":
          mazeView.setShootDirection("N");
          break;
        case "Shoot down":
          mazeView.setShootDirection("S");
          break;
        case "Shoot left":
          mazeView.setShootDirection("W");
          break;
        case "Shoot right":
          mazeView.setShootDirection("E");
          break;
        case "Shoot":
          int distance = Integer.valueOf(mazeView.getShootDistance());
          game.shoot(mazeView.getShootDirection(), distance);
          game.changePlayerFlag();
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          break;
        default:
          break;
      }
    }
    mazeView.resetFocus();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (!game.getGameEnd()) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
          game.move("N");
          int[] newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        case KeyEvent.VK_DOWN: //caps
          game.move("S");
          newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        case KeyEvent.VK_LEFT: //caps
          game.move("W");
          newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        case KeyEvent.VK_RIGHT: //caps
          game.move("E");
          newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (!game.getGameEnd()) {
      switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
          game.move("N");
          int[] newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        case KeyEvent.VK_DOWN: //caps
          game.move("S");
          newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        case KeyEvent.VK_LEFT: //caps
          game.move("W");
          newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        case KeyEvent.VK_RIGHT: //caps
          game.move("E");
          newPos = game.getPlayerPosition(game.getPlayerRound());
          mazeView.changeViewByMove(game.getWalkedCells(), newPos, game.getPlayerRound());
          mazeView.changeAlertPanel(game.getAlert(), game.getPlayerLocation(),
                  game.getPlayerRound());
          game.changePlayerFlag();
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //Nothing needs to do.
  }
}

