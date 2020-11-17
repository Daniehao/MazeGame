package game;

/**
 * The maze game that enables a player to move to four directions(left, right, up, down). The game
 * could also show the player's location and the gold amount he/she has.
 */
public interface MazeGame {
  /**
   * Get the current location of the player.
   *
   * @return The string of player's location.
   */
  public String getPlayerLocation();

  /**
   * Shoot towards to direction with a specified distance by the number of caves.
   *
   * @param direction The direction that player wish to shoot.
   * @param distance  The shooting distance by the number of caves.
   */
  public void shoot(String direction, int distance) throws IllegalArgumentException;

  /**
   * The player's move inside the maze.
   *
   * @param direction Move towards to a specific direction.
   */
  public void move(String direction);

  /**
   * Return if the game is ended.
   *
   * @return True/False.
   */
  public boolean getGameOver();

  /**
   * Check if the game is unwinnable and it's impossible to kill the wumpus from a safe cave.
   * @return True/False.
   */
  public boolean checkUnwinnable();

  public boolean checkShootSuccess();

  /**
   * Get the current cell of that the player is located.
   * @return The current cell.
   */
  public Cell getCurrentCell();

  /**
   * Set Player's location to a specific cell for testing.
   */
  public void setPlayerLocation(int x, int y);
}
