import java.util.Scanner;

/**
 * Main class for generating two samples for Perfect maze and wrapped room maze. The player visits
 * each location in the room maze and reach to the goal point in both of the examples.
 */
public class Main {
  /**
   * main method to print gold numbers, player's location and whether he/she has reached goal.
   *
   * @param args The arguments for main method.
   */
  public static void main(String[] args) {
    //    int rows = 3;
    //    int cols = 4;
    //    int remains = 6;
    //    boolean isPerfect = true;
    //    boolean isWrapping = false;
    //    int startX = 0;
    //    int startY = 1;
    //    int targetX = 2;
    //    int targetY = 3;
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter rows: ");
    int rows = Integer.valueOf(sc.nextLine());
    if (rows < 0) {
      throw new IllegalArgumentException("Invalid rows!");
    }
    System.out.println("Enter columns: ");
    int cols = Integer.valueOf(sc.nextLine());
    if (cols < 0) {
      throw new IllegalArgumentException("Invalid columns!");
    }
    System.out.println("Enter remains wall number: ");
    int remains = Integer.valueOf(sc.nextLine());
    if (remains < 0) {
      throw new IllegalArgumentException("Invalid remains!");
    }
    System.out.println("Enter the x of the start point: ");
    int startX = Integer.valueOf(sc.nextLine());
    if (startX < 0) {
      throw new IllegalArgumentException("Invalid start!");
    }
    System.out.println("Enter the y of the start point: ");
    int startY = Integer.valueOf(sc.nextLine());
    if (startY < 0) {
      throw new IllegalArgumentException("Invalid start!");
    }
    System.out.println("Enter the x of the target point: ");
    int targetX = Integer.valueOf(sc.nextLine());
    if (targetX < 0) {
      throw new IllegalArgumentException("Invalid target!");
    }
    System.out.println("Enter the y of the target point: ");
    int targetY = Integer.valueOf(sc.nextLine());
    if (targetY < 0) {
      throw new IllegalArgumentException("Invalid target!");
    }
    System.out.println("Enter whether it is a perfect maze: ");
    boolean isPerfect = sc.nextLine().equals("true") ? true : false;
    System.out.println("Enter whether it is a wrapping maze: ");
    boolean isWrapping = sc.nextLine().equals("true") ? true : false;

    //Sample 1 : Show a perfect Maze.
    if (startX < 0 || startX >= rows || startY < 0 || startY >= cols) {
      throw new IllegalArgumentException("The start point is invalid!");
    } else if (targetX < 0 || targetX >= rows || targetY < 0 || targetY >= cols) {
      throw new IllegalArgumentException("The target point is invalid!");
    } else if (isWrapping && remains >= rows * cols - 1) {
      throw new IllegalArgumentException("The remains has to be less than rows * cols - 1 for "
              +
              "non-wrapping maze!");
    } else {
      Maze maze = new Maze(rows, cols, remains, isPerfect, isWrapping, startX, startY);
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goDown();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goDown();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goRight();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goRight();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      System.out.println("If the player has reached to the destination: "
              + checkReachToTarget(maze.getPlayerPosX(), maze.getPlayerPosY(), targetX, targetY));
    }

    //Sample 2: Show a wrapping room maze.
    //    int rows2 = 3;
    //    int cols2 = 3;
    //    int remains2 = 3;
    //    boolean isPerfect2 = false;
    //    boolean isWrapping2 = true;
    //    int startX2 = 0;
    //    int startY2 = 0;
    //    int targetX2 = 1;
    //    int targetY2 = 2;
    System.out.println("Enter rows: ");
    int rows2 = Integer.valueOf(sc.nextLine());
    if (rows2 < 0) {
      throw new IllegalArgumentException("Invalid rows!");
    }
    System.out.println("Enter columns: ");
    int cols2 = Integer.valueOf(sc.nextLine());
    if (cols2 < 0) {
      throw new IllegalArgumentException("Invalid columns!");
    }
    System.out.println("Enter remains wall number: ");
    int remains2 = Integer.valueOf(sc.nextLine());
    if (remains2 < 0) {
      throw new IllegalArgumentException("Invalid remains!");
    }
    System.out.println("Enter the x of the start point: ");
    int startX2 = Integer.valueOf(sc.nextLine());
    if (startX2 < 0) {
      throw new IllegalArgumentException("Invalid start!");
    }
    System.out.println("Enter the y of the start point: ");
    int startY2 = Integer.valueOf(sc.nextLine());
    if (startY2 < 0) {
      throw new IllegalArgumentException("Invalid start!");
    }
    System.out.println("Enter the x of the target point: ");
    int targetX2 = Integer.valueOf(sc.nextLine());
    if (targetX2 < 0) {
      throw new IllegalArgumentException("Invalid target!");
    }
    System.out.println("Enter the y of the target point: ");
    int targetY2 = Integer.valueOf(sc.nextLine());
    if (targetY2 < 0) {
      throw new IllegalArgumentException("Invalid target!");
    }
    System.out.println("Enter whether it is a perfect maze: ");
    boolean isPerfect2 = sc.nextLine().equals("true") ? true : false;
    System.out.println("Enter whether it is a wrapping maze: ");
    boolean isWrapping2 = sc.nextLine().equals("true") ? true : false;

    if (startX2 < 0 || startX2 >= rows2 || startY2 < 0 || startY2 >= cols2) {
      throw new IllegalArgumentException("The start point is invalid!");
    } else if (targetX2 < 0 || targetX2 >= rows2 || targetY2 < 0 || targetY2 >= cols2) {
      throw new IllegalArgumentException("The target point is invalid!");
    } else if (isWrapping2 && remains2 >= rows2 * cols2 - 1) {
      throw new IllegalArgumentException("The remains has to be less than rows * cols - 1 for "
              +
              "non-wrapping maze!");
    } else {
      Maze maze = new Maze(rows2, cols2, remains2, isPerfect2, isWrapping2, startX2, startY2);
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goRight();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goRight();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goUp();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goLeft();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goLeft();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goUp();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goRight();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      maze.goRight();
      System.out.println(String.format("Player's current location: (%d, %d)",
              maze.getPlayerPosX(), maze.getPlayerPosY()));
      System.out.println(String.format("Player's current gold amount: %d", maze.getPlayerGold()));
      System.out.println("If the player has reached to the destination: "
              + checkReachToTarget(maze.getPlayerPosX(), maze.getPlayerPosY(), targetX2, targetY2));
    }
  }

  /**
   * Check if the player has reach to the target point.
   *
   * @param playerX Horizontal location of player.
   * @param playerY Vertical location of player.
   * @param targetX Horizontal target of player.
   * @param targetY Vertical target of player.
   * @return True/False.
   */
  public static boolean checkReachToTarget(int playerX, int playerY, int targetX, int targetY) {
    return targetX == playerX && targetY == playerY;
  }
}
