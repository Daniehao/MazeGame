import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MazeTest {
  private MazeGame maze1;
  private MazeGame maze2;
  private MazeGame maze3;
  private MazeGame maze4;

  /**
   * Create objects for the following tests.
   */
  @Before
  public void setup() {
    maze1 = new Maze(4, 3, 11, true, false,
            0, 1);
    maze2 = new Maze(4, 3, 11, true, true,
            0, 1);
    maze3 = new Maze(4, 3, 3, false, false,
            0, 1);
    maze4 = new Maze(4, 3, 3, false, true,
            0, 1);
  }

  @Test
  public void testMazeConstructorValid() {
    maze1 = new Maze(4, 3, 11, true, false,
            0, 1);
    maze2 = new Maze(4, 3, 11, true, true,
            0, 1);
    maze3 = new Maze(4, 3, 3, false, false,
            0, 1);
    maze4 = new Maze(4, 3, 3, false, true,
            0, 1);
    assertEquals("The maze is 4 * 3, and it is non-wrapping perfect maze.",
            maze1.toString());
    assertEquals("The maze is 4 * 3, and it is a wrapping perfect maze.",
            maze2.toString());
    assertEquals("The maze is 4 * 3, and it is non-wrapping room maze.",
            maze3.toString());
    assertEquals("The maze is 4 * 3, and it is wrapping room maze.", maze4.toString());
  }

  @Test
  public void getLocationTest() {
    assertEquals(maze1.getPlayerPosX(), 0);
    assertEquals(maze1.getPlayerPosY(), 1);
    assertEquals(maze2.getPlayerPosX(), 0);
    assertEquals(maze2.getPlayerPosY(), 1);
    assertEquals(maze3.getPlayerPosX(), 0);
    assertEquals(maze3.getPlayerPosY(), 1);
    assertEquals(maze4.getPlayerPosX(), 0);
    assertEquals(maze4.getPlayerPosY(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoDownInvalid() {
    maze1.goDown();
    maze1.goDown();
    maze1.goDown();
    maze1.goDown();
  }

  @Test
  public void testGoDownValid() {
    maze1.goDown();
    assertEquals(1, maze1.getPlayerPosX());
    assertEquals(1, maze1.getPlayerPosY());
  }

  @Test
  public void testGoLeftValid() {
    maze1.goLeft();
    assertEquals(0, maze1.getPlayerPosX());
    assertEquals(0, maze1.getPlayerPosY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoLeftInValid() {
    maze1.goLeft();
    maze1.goLeft();
    assertEquals(0, maze1.getPlayerPosX());
    assertEquals(0, maze1.getPlayerPosY());
  }

  @Test
  public void testGoRightValid() {
    maze1.goRight();
    assertEquals(0, maze1.getPlayerPosX());
    assertEquals(2, maze1.getPlayerPosY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoRightInValid() {
    maze1.goRight();
    maze1.goRight();
    maze1.goRight();
    maze1.goRight();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGoUpInValid() {
    maze1.goUp();
  }

  @Test
  public void testGoUpValid() {
    maze1.goDown();
    maze1.goUp();
    assertEquals(maze1.getPlayerPosX(), 0);
    assertEquals(maze1.getPlayerPosY(), 1);
  }

  @Test
  public void checkNonWrapPerfectMaze() {
    maze1.goDown();
    maze1.goDown();
    maze1.goRight();
    maze1.goRight();
  }

}
