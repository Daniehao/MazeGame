package game.model;

/**
 * Class to save player's location, dead status and other details.
 */
public class Player {
  private int playerX;
  private int playerY;
  private int[] start;
  private int arrows;
  private boolean isDead;

  /**
   * Constructor.
   *
   * @param flag Indicator of player.
   */
  public Player(int flag) {
    playerX = 0;
    playerY = 0;
    start = new int[2];
    arrows = 0;
    isDead = false;
  }

  /**
   * Set the start location for player.
   *
   * @param x Horizontal position.
   * @param y Vertical position.
   */
  public void setPlayerStartLocation(int x, int y) {
    start[0] = x;
    start[1] = y;
    setPlayerLocation(x, y);
  }

  /**
   * Get the integer array of the player's start location.
   *
   * @return The array of player start position.
   */
  public int[] getPlayerStartLocation() {
    return start;
  }

  /**
   * Set the player to a specific position.
   *
   * @param x Horizontal position.
   * @param y Vertical position.
   */
  public void setPlayerLocation(int x, int y) {
    playerX = x;
    playerY = y;
  }

  /**
   * Get the player's current location.
   *
   * @return The array of player's current location.
   */
  public int[] getPlayerLocation() {
    int[] location = new int[2];
    location[0] = playerX;
    location[1] = playerY;
    return location;
  }

  /**
   * Set the player's status to dead.
   */
  public void setDead() {
    isDead = true;
  }

  /**
   * Get whether the player is dead or not.
   *
   * @return
   */
  public boolean getDead() {
    return isDead;
  }

  /**
   * Assign the number of remaining arrows for the player.
   *
   * @param arrows Number of remaining arrows.
   */
  public void setArrows(int arrows) {
    this.arrows = arrows;
  }

  /**
   * Get the number of remaining arrows for the player.
   *
   * @return The number of remaining arrows.
   */
  public int getArrows() {
    return arrows;
  }
}
