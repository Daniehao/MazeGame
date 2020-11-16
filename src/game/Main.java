package game;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import controller.ControllerImpl;

/**
 * Game.Main class for generating two samples for Perfect maze and wrapped room maze. The player
 * visits each location in the room maze and reach to the goal point in both of the examples.
 */
public class Main {
  /**
   * main method to print gold numbers, player's location and whether he/she has reached goal.
   *
   * @param args The arguments for main method.
   */
  public static void main(String[] args) throws IOException {
//    MazeGame game = createMaze();
    MazeGame game = new MazeGameImpl(3, 4, 6, false, true,
            0.2, 0.2, 3);
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
    MazeGame game = new MazeGameImpl(rows, cols, remains, isPerfect, isWrapping, batPercent,
            pitPercent, arrows);
    return game;
  }
}
