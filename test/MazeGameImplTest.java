import org.junit.Before;
import org.junit.Test;

import model.MazeGame;
import model.MazeGameImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test for the Game.Maze class, which checks if four types of maze works fine with user's input
 * directions.
 */
public class MazeGameImplTest {
  private MazeGame game1;
  private MazeGame game2;
  private MazeGame game3;
  private MazeGame game4;

  /**
   * Create objects for the following tests.
   */
  @Before
  public void setup() {
    game1 = new MazeGameImpl(3, 4, 6, true, false,
            0.2, 0.3, 3, 1);
    game2 = new MazeGameImpl(3, 4, 13, true, true,
            0.2, 0.2, 3, 1);
    game3 = new MazeGameImpl(3, 4, 3, false, false,
            0.2, 0.3, 3, 1);
    game4 = new MazeGameImpl(3, 4, 6, false, true,
            0.2, 0.2, 3, 1);
  }

  @Test
  public void testMazeConstructorValid() {
    assertEquals("The maze is 3 * 4, and it is a non-wrapping perfect maze. " +
                    "The start point of player is (2, 1). The saved walls are numbered by: " +
                    "9 2 11 13 15 16 ",
            game1.toString());
    assertEquals("The maze is 3 * 4, and it is a wrapping perfect maze. The start point " +
                    "of player is (2, 0). The saved walls are numbered by: 0 19 2 3 4 5 11 13 16 " +
                    "17 18 21 23 ",
            game2.toString());
    assertEquals("The maze is 3 * 4, and it is a non-wrapping room maze. The start " +
                    "point of player is (2, 2). The saved walls are numbered by: 11 13 16 ",
            game3.toString());
    assertEquals("The maze is 3 * 4, and it is a wrapping room maze. The start point " +
                    "of player is (0, 2). The saved walls are numbered by: 19 2 11 13 21 23 ",
            game4.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid1() {
    game1 = new MazeGameImpl(-3, 4, 11, true, false,
            0.2, 0.2, 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid2() {
    game1 = new MazeGameImpl(3, -4, 11, true, false,
            0.2, 0.2, 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid3() {
    game3 = new MazeGameImpl(3, 4, -3, false, false,
            0.2, 0.2, 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid4() {
    game3 = new MazeGameImpl(3, 4, 7, false, false,
            0.2, 0.2, 3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid5() {
    game3 = new MazeGameImpl(3, 4, 4, false, false,
            -0.2, 0.2, 3, 1);
    game3 = new MazeGameImpl(3, 4, 4, false, false,
            0.2, -0.2, 3, 1);
    game3 = new MazeGameImpl(3, 4, 4, false, false,
            0.2, 0.2, -3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoDownInvalid() {
    game1.move("down");
    game1.move("down");
    game1.move("down");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveEmptyString() {
    game1.move("");
    game4.move("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidString() {
    game1.move("North");
    game4.move("abc");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyShoot() {
    game1.shoot("", 1);
    game4.shoot("", 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidShoot() {
    game1.shoot("North", 1);
    game4.shoot("abc", 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootZero() {
    game1.shoot("S", 0);
    game4.shoot("N", 0);
  }

  @Test
  public void testMoveNorthAndSouth() {
    game1.move("N");
    assertEquals("You are in cave (1, 1). Tunnels lead to the E, W, N, S",
            game1.getPlayerLocation());
    game1.move("S");
    assertEquals("You are in cave (2, 1). Tunnels lead to the E, W, N",
            game1.getPlayerLocation());
  }

  @Test
  public void testMoveEastAndWest() {
    game1.move("E");
    assertEquals("You are in cave (2, 3). Tunnels lead to the W",
            game1.getPlayerLocation());
    game1.move("W");
    assertEquals("You are in cave (2, 1). Tunnels lead to the E, W, N",
            game1.getPlayerLocation());
    game4.move("E");
    assertEquals("You are in cave (0, 3). Tunnels lead to the E, W, N",
            game4.getPlayerLocation());
    game4.move("W");
    assertEquals("You are in cave (0, 2). Tunnels lead to the E, N, S",
            game4.getPlayerLocation());
  }

  @Test
  public void testMoveToWall() {
    game1.move("N");
    game1.move("W");
    String beforeMoveToWall = game1.getPlayerLocation();
    game1.move("S");
    String afterMoveToWall = game1.getPlayerLocation();
    assertEquals(beforeMoveToWall, afterMoveToWall);

    String beforeMoveToWall4 = game4.getPlayerLocation();
    game4.move("W");
    String afterMoveToWall4 = game4.getPlayerLocation();
    assertEquals(beforeMoveToWall4, afterMoveToWall4);
  }

  @Test
  public void testMoveTunnel() {
    game1.move("N");
    game1.move("E");
    assertEquals("You are in cave (0, 3). Tunnels lead to the S",
            game1.getPlayerLocation());
    game4.move("E");
    game4.move("E");
    game4.move("E");
    assertEquals("You are in cave (1, 1). Tunnels lead to the E, W, N",
            game4.getPlayerLocation());
  }

  @Test
  public void testMoveTunnel2() {
    game1.move("E");
    assertEquals("You are in cave (2, 3). Tunnels lead to the W",
            game1.getPlayerLocation());
    game4.move("E");
    game4.move("N");
    assertEquals("You are in cave (2, 0). Tunnels lead to the E, W, N, S",
            game4.getPlayerLocation());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootDistanceZero() {
    game1.shoot("N", 0);
    game2.shoot("W", 0);
    game3.shoot("N", 0);
    game4.shoot("E", 0);
    assertEquals(false, game4.checkShootSuccess());
  }

  @Test
  public void testShootDistanceWrong() {
    game4.shoot("N", 3);
    assertEquals(false, game4.checkShootSuccess());
    game4.setPlayerLocation(0, 0);
    game4.shoot("S", 1);
    assertEquals(false, game4.checkShootSuccess());
    game4.setPlayerLocation(0, 0);
    game4.shoot("S", 3);
    assertEquals(false, game4.checkShootSuccess());
  }

  @Test
  public void testShootDistanceOverWumpus() {
    game1.move("N");
    game1.shoot("E", 3);
    assertEquals(false, game1.checkShootSuccess());
    game1.shoot("E", 1);
    assertEquals(true, game1.checkShootSuccess());
  }

  @Test
  public void testShootThroughTunnel() {
    game1.move("N");
    game1.shoot("E", 2);
    assertEquals(false, game1.checkShootSuccess());
    game1.shoot("E", 1);
    assertEquals(true, game1.checkShootSuccess());
  }

  @Test
  public void testShootToWall() {
    game4.shoot("W", 1);
    assertEquals(false, game4.checkShootSuccess());
    game1.shoot("E", 2);
    assertEquals(false, game4.checkShootSuccess());
  }

  @Test
  public void testOutOfArrows() {
    game1.shoot("N", 1);
    assertEquals(false, game1.checkShootSuccess());
    game1.shoot("N", 2);
    assertEquals(false, game1.checkShootSuccess());
    game1.shoot("W", 1);
    assertEquals(false, game1.checkShootSuccess());
    game1.shoot("E", 1);
    assertEquals(false, game1.checkShootSuccess());
  }

  @Test
  public void testShootSuccess() {
    game4.setPlayerLocation(0, 0);
    game4.shoot("S", 2);
    assertEquals(true, game4.checkShootSuccess());
    assertEquals(true, game4.getGameEnd());
  }

  @Test
  public void testPlayerMoveToBat() {
    game1.setPlayerStartLocation(0, 2);
    assertNotEquals(game1.getPlayerLocation(), "You are in cave (0, 2). " +
            "Tunnels lead to the W ");
  }

  @Test
  public void testMoveToPit() {
    game1.move("N");
    game1.move("N");
    assertEquals(true, game1.getCurrentCell().getIsPit());
    assertEquals(true, game1.getGameEnd());
    assertEquals(true, game4.getCurrentCell().getDownCell().getIsPit());
  }

  @Test
  public void testMoveCloseToPit() {
    game1.move("N");
    assertEquals(true, game1.getCurrentCell().getCloseToPit());
    game1.move("S");
    game1.move("W");
    assertEquals(false, game1.getCurrentCell().getCloseToPit());
    game4.move("N");
    assertEquals(true, game4.getCurrentCell().getCloseToPit());
    game4.move("W");
    assertEquals(false, game4.getCurrentCell().getCloseToPit());
  }

  @Test
  public void testMoveCloseToPitByTunnel() {
    game4.setPlayerStartLocation(1, 0);
    assertEquals(true, game4.getCurrentCell().getCloseToPit());
  }

  @Test
  public void testMoveCloseToWumpus() {
    //Smell a wumpus through a two cells tunnel.
    game1.move("N");
    assertEquals(true, game1.getCurrentCell().getCloseToWumpus());
    game1.move("W");
    assertEquals(false, game1.getCurrentCell().getCloseToWumpus());
    //Smell a wumpus through a one cell tunnel.
    game4.move("N");
    assertEquals(true, game4.getCurrentCell().getCloseToWumpus());
    game4.move("S");
    assertEquals(false, game4.getCurrentCell().getCloseToWumpus());
  }

  @Test
  public void testEatenByWumpus() {
    game1.move("N");
    game1.move("E");
    assertEquals(true, game1.getGameEnd());
    game4.move("E");
    game4.move("E");
    game4.move("S");
    game4.move("S");
    assertEquals(true, game4.getGameEnd());
  }

  @Test
  public void testKillWumpusWithTunnel() {
    game1.move("N");
    game1.shoot("E", 1);
    assertEquals(true, game1.checkShootSuccess());
  }

  @Test
  public void testKillWumpusWithoutTunnel() {
    game4.move("N");
    game4.shoot("W", 1);
    assertEquals(true, game4.checkShootSuccess());
  }

  @Test
  public void testUnWinnable() {
    game1.setPlayerStartLocation(0, 0);
    assertEquals(true, game1.checkUnwinnable());
    assertEquals(false, game4.checkUnwinnable());
  }
}
