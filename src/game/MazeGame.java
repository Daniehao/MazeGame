package game;

/**
 * The maze game that enables a player to move to four directions(left, right, up, down). The game
 * could also show the player's location and the gold amount he/she has.
 */
public interface MazeGame {
  /**
   * Turn left operation.
   */
  public void goLeft();

  /**
   * Turn right operation.
   */
  public void goRight();

  /**
   * Turn up operation.
   */
  public void goUp();

  /**
   * Turn down operation.
   */
  public void goDown();

  /**
   * Get the horizontal location of the player.
   */
  public int getPlayerPosX();

  /**
   * Get the vertical location of the player.
   */
  public int getPlayerPosY();

  /**
   * Get the total number of gold of the player.
   */
  public int getPlayerGold();

  public void shoot(String direction, int distance);

  public void move(String direction);

}
