import java.util.Scanner;

import game.controller.GameGuiController;
import game.model.MazeGame;
import game.model.MazeGameImpl;
import game.view.GameView;
import game.view.MazeLayout;
import game.view.MazeMenu;
import game.view.MenuView;

/**
 * The driver class for running two versions of the maze games. One version is text input only, and
 * the other version includes a graphical interface.
 */
public class Main {
  /**
   * The main method is used to start either version of the maze game by input arguments.
   *
   * @param args The input type of game version.
   */
  public static void main(String[] args) {
//    List<String> lst = Arrays.asList(args);
//    if (lst.stream().anyMatch("--gui"::equals)) {
      // Create the game.model
      MazeGame model = new MazeGameImpl();
      // Create the game.view
      MenuView view = new MazeMenu();
      GameView mazeView = new MazeLayout();
      // Create the game.controller with the game.model and the game.view
      new GameGuiController(model, view, mazeView);
//    } else if (lst.stream().anyMatch("--text"::equals)) {
//      MazeGame game = createMaze();
//      System.out.println(game.getPlayerLocation());
//      try {
//        new ControllerImpl(new InputStreamReader(System.in), System.out).start(game);
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
  }

  /**
   * Generate the maze by input the dimension of the maze, maze type, and player's start position.
   */
  public static MazeGame createMaze() {
    Scanner scan = new Scanner(System.in);
    System.out.println("Input the rows number of the maze: ");
    int rows = scan.nextInt();
    System.out.println("Input the columns number of the maze: ");
    int cols = scan.nextInt();
    System.out.println("Input the wall number of the maze: ");
    int remains = scan.nextInt();
    System.out.println("The maze is Perfect(true/false)?");
    boolean isPerfect = scan.nextBoolean();
    System.out.println("The maze is Wrappping(true/false)?");
    boolean isWrapping = scan.nextBoolean();
    System.out.println("Input the percentage of bats: ");
    double batPercent = Double.parseDouble(scan.next());
    System.out.println("Input the percentage of pits: ");
    double pitPercent = Double.parseDouble(scan.next());
    System.out.println("Input the number of arrows: ");
    int arrows = scan.nextInt();
    System.out.println("Input the number of players(1 or 2): ");
    int players = scan.nextInt();
    MazeGame game = new MazeGameImpl(rows, cols, remains, isPerfect, isWrapping, batPercent,
            pitPercent, arrows, players);
    return game;
  }
}
