package model;

public class Player {
  private int player1x;
  private int player1y;
  private int player2x;
  private int player2y;
  private int flag;

  public Player(int flag) {
    this.flag = flag;
    player1x = 0;
    player1y = 0;
    player2x = 0;
    player2y = 0;
  }

  public void setPlayerLocation(int x, int y) {
    if (flag == 1) {
      player1x = x;
      player1x = y;
    } else {
      player2x = x;
      player2y = y;
    }
  }
  public int[] getPlayerLocation(int flag) {
    int[] location = new int[2];
    if (flag == 1) {
      location[0] = player1x;
      location[1] = player1y;
    } else {
      location[0] = player2x;
      location[1] = player2y;
    }
    return location;
  }
}
