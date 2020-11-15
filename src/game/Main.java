package game;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import controller.ControllerImpl;

/**
 * Game.Main class for generating two samples for Perfect maze and wrapped room maze. The player visits
 * each location in the room maze and reach to the goal point in both of the examples.
 */
public class Main {
  /**
   * main method to print gold numbers, player's location and whether he/she has reached goal.
   *
   * @param args The arguments for main method.
   */
  public static void main(String[] args) throws IOException {
    MazeGame game = createMaze();
    game.getPlayerLocation();
    try {
      new ControllerImpl(new InputStreamReader(System.in), System.out).start(game);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generate the maze by input the dimension of the maze, maze type, and player's start position.
   */
  public static MazeGame createMaze(){
    Scanner scan = new Scanner(System.in);
    int rows = scan.nextInt();
    int cols = scan.nextInt();
    int remains = scan.nextInt();
    boolean isPerfect = scan.next() == "true"? true: false;
    boolean isWrapping = scan.next() == "true"?true: false;
    double batPercent = Double.parseDouble(scan.next());
    double pitPercent = Double.parseDouble(scan.next());
    int arrows = scan.nextInt();
    MazeGame game = new MazeGameImpl(rows, cols, remains, isPerfect, isWrapping, batPercent,
            pitPercent, arrows);
    return game;
  }
}
