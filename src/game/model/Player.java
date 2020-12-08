package game.model;

public class Player {
  private int playerX;
  private int playerY;
  private int flag;
  private int[] start;
  private int arrows;
  private boolean isDead;

  public Player(int flag) {
    this.flag = flag;
    playerX = 0;
    playerY = 0;
    start = new int[2];
    arrows = 0;
    isDead = false;
  }
  public void setPlayerStartLocation(int x, int y) {
    start[0] = x;
    start[1] = y;
    setPlayerLocation(x, y);
  }

  public int[] getPlayerStartLocation() {
    return start;
  }

  public void setPlayerLocation(int x, int y) {
    playerX = x;
    playerY = y;
  }

  public int[] getPlayerLocation() {
    int[] location = new int[2];
    location[0] = playerX;
    location[1] = playerY;
    return location;
  }

  public void setDead() {
    isDead = true;
  }

  public boolean getDead() {
    return isDead;
  }
}
