package game;

import java.util.Scanner;

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
  public static void main(String[] args) {
    createMaze();

  }

  /**
   * Generate the maze by input the dimension of the maze, maze type, and player's start position.
   */
  public static void createMaze(){
    Scanner scan = new Scanner(System.in);
    int rows = scan.nextInt();
    int cols = scan.nextInt();
    int remains = scan.nextInt();
    boolean isPerfect = scan.next() == "true"? true: false;
    boolean isWrapping = scan.next() == "true"?true: false;
    int playerPosX = scan.nextInt();
    int playerPosY = scan.nextInt();
    MazeGame game = new MazeGameImpl(rows, cols, remains, isPerfect, isWrapping,
            playerPosX, playerPosY);
  }
}