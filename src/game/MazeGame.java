package game;

/**
 * The maze game that enables a player to move to four directions(left, right, up, down). The game
 * could also show the player's location and the gold amount he/she has.
 */
public interface MazeGame {

  /**
   * Get the current location of the player.
   */
  public int[] getPlayerLocation();

  public void shoot(String direction, int distance);

  public void move(String direction);

}
