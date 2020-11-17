import org.junit.Before;
import org.junit.Test;

import game.MazeGame;
import game.MazeGameImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test for the Game.Maze class, which checks if four types of maze works fine with user's input
 * directions.
 */
public class MazeGameImplTest {
  private MazeGame maze1;
  private MazeGame maze2;
  private MazeGame maze3;
  private MazeGame game4;

  /**
   * Create objects for the following tests.
   */
  @Before
  public void setup() {
    maze1 = new MazeGameImpl(3, 4, 11, true, false,
            0.2, 0.3, 3);
    maze2 = new MazeGameImpl(3, 4, 11, true, true,
            0.2, 0.2, 3);
    maze3 = new MazeGameImpl(3, 4, 3, false, false,
            0.2, 0.3, 3);
    game4 = new MazeGameImpl(3, 4, 6, false, true,
            0.2, 0.2, 3);
  }

  @Test
  public void testMazeConstructorValid() {
    assertEquals("The maze is 3 * 4, and it is non-wrapping perfect maze.",
            maze1.toString());
    assertEquals("The maze is 3 * 4, and it is a wrapping perfect maze.",
            maze2.toString());
    assertEquals("The maze is 3 * 4, and it is non-wrapping room maze.",
            maze3.toString());
    assertEquals("The maze is 3 * 4, and it is wrapping room maze.", game4.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid1() {
    maze1 = new MazeGameImpl(-3, 4, 11, true, false,
            0.2, 0.2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid2() {
    maze1 = new MazeGameImpl(3, -4, 11, true, false,
            0.2, 0.2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid3() {
    maze3 = new MazeGameImpl(3, 4, -3, false, false,
            0.2, 0.2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid4() {
    maze3 = new MazeGameImpl(3, 4, 7, false, false,
            0.2, 0.2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoDownInvalid() {
    maze1.move("down");
    maze1.move("down");
    maze1.move("down");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveEmptyString() {
    game4.move("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidString() {
    game4.move("abc");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyShoot() {
    game4.shoot("", 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidShoot() {
    game4.shoot("abc", 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootZero() {
    game4.shoot("N", 0);
  }

  @Test
  public void testMoveNorthAndSouth() {
    game4.move("N");
    assertEquals("You are in cave (2, 2). Tunnels lead to the W, N, S",
            game4.getPlayerLocation());
    game4.move("S");
    assertEquals("You are in cave (0, 2). Tunnels lead to the E, N, S",
            game4.getPlayerLocation());
  }

  @Test
  public void testMoveEastAndWest() {
    game4.move("E");
    assertEquals("You are in cave (0, 3). Tunnels lead to the E, W, N",
            game4.getPlayerLocation());
    game4.move("W");
    assertEquals("You are in cave (0, 2). Tunnels lead to the E, N, S",
            game4.getPlayerLocation());
  }

  @Test
  public void testMoveTunnel() {
    game4.move("E");
    game4.move("E");
    game4.move("E");
    assertEquals("You are in cave (1, 1). Tunnels lead to the E, W, N",
            game4.getPlayerLocation());
  }

  @Test
  public void testMoveTunnel2() {
    game4.move("E");
    game4.move("N");
    assertEquals("You are in cave (2, 0). Tunnels lead to the E, W, N, S",
            game4.getPlayerLocation());
  }

  @Test
  public void testShootUnsuccess() {
    game4.shoot("N", 3);
    assertEquals(false, game4.checkShootSuccess());
  }

  @Test
  public void testShootSuccess() {
    game4.shoot("N", 3);
    assertEquals(false, game4.checkShootSuccess());
  }

  @Test
  public void testHasBat() {
    assertEquals(true, game4.getCurrentCell().getDownCell().getHasBat());
  }

  @Test
  public void testNotHasBat() {
    game4.move("N");
    assertEquals(false, game4.getCurrentCell().getDownCell().getHasBat());
  }

  @Test
  public void testHasPit() {
    assertEquals(true, game4.getCurrentCell().getDownCell().getIsPit());
  }

  @Test
  public void testCloseToPit() {
    game4.move("N");
    assertEquals(true, game4.getCurrentCell().getCloseToPit());
  }

  @Test
  public void testNotCloseToPit() {
    game4.move("E");
    assertEquals(false, game4.getCurrentCell().getCloseToPit());
  }

  @Test
  public void testCloseToWumpus() {
    game4.move("N");
    assertEquals(true, game4.getCurrentCell().getCloseToWumpus());
    game4.move("S");
    assertEquals(false, game4.getCurrentCell().getCloseToWumpus());
  }

  @Test
  public void testHasWumpus() {
    game4.move("N");
    assertEquals(false, game4.getCurrentCell().getIsWumpus());
    assertEquals(true, game4.getCurrentCell().getLeftCell().getIsWumpus());
  }

  @Test
  public void testGameOver() {
    game4.move("N");
    game4.move("W");
    assertEquals(true, game4.getGameOver());
  }

  @Test
  public void testUnWinnable() {
    game4.checkUnwinnable();
    assertEquals(false, game4.checkUnwinnable());
  }
}
