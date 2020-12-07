package model;

import java.util.List;

/**
 * The maze game that enables a player to move to four directions(left, right, up, down). The player
 * may also shoot to a direction by a distance, and the interface supports the user to get the
 * current location of the player, get the current cell of the player, and whether the shoot is
 * success, and whether it is an unwinnable game.
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
  public boolean getGameEnd();

  /**
   * Check if the game is unwinnable and it's impossible to kill the wumpus from a safe cave.
   *
   * @return True/False.
   */
  public boolean checkUnwinnable();

  /**
   * Get if the player successfully shooted the wumpus.
   *
   * @return True/False.
   */
  public boolean checkShootSuccess();

  /**
   * Get the current cell of that the player is located.
   *
   * @return The current cell.
   */
  public Cell getCurrentCell();

  /**
   * Set the player's location to a specific cell for testing.
   *
   * @param x The player's horizontal location.
   * @param y The player's vertical location.
   */
  public void setPlayerLocation(int x, int y);

  /**
   * Reset the player's starting point for testing.
   *
   * @param x The horizontal location of the starting point.
   * @param y The vertical location of the starting point.
//   */
  public void setPlayerStartLocation(int x, int y);

  /**
   * Get the shooting result by if the player shooted to the wumpus and how many arrows are left.
   *
   * @return The result of shoot is successful or not.
   */
  public String getShootRes();

  /**
   * Return the alert message.
   * @return The alert message of the game.
   */
  public String getAlert();

  public void changePlayerFlag();

  public int getPlayerRound();

  public int[] getPlayerPosition(int flag);

  public Cell getCurrCell();

  public List<Cell> getWalkedCells();
}
