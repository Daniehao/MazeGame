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
    maze1 = new Maze(3, 4, 11, true, false,
            0, 1);
    maze2 = new Maze(3, 4, 11, true, true,
            0, 0);
    maze3 = new Maze(3, 4, 3, false, false,
            0, 1);
    maze4 = new Maze(3, 4, 3, false, true,
            0, 1);
  }

  @Test
  public void testMazeConstructorValid() {
    assertEquals("The maze is 3 * 4, and it is non-wrapping perfect maze.",
            maze1.toString());
    assertEquals("The maze is 3 * 4, and it is a wrapping perfect maze.",
            maze2.toString());
    assertEquals("The maze is 3 * 4, and it is non-wrapping room maze.",
            maze3.toString());
    assertEquals("The maze is 3 * 4, and it is wrapping room maze.", maze4.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid1() {
    maze1 = new Maze(-3, 4, 11, true, false,
            0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid2() {
    maze1 = new Maze(3, -4, 11, true, false,
            0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid3() {
    maze3 = new Maze(3, 4, -3, false, false,
            0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMazeConstructorInvalid4() {
    maze3 = new Maze(3, 4, 12, false, false,
            0, 1);
  }

  @Test
  public void getLocationTest() {
    assertEquals(maze1.getPlayerPosX(), 0);
    assertEquals(maze1.getPlayerPosY(), 1);
    assertEquals(maze2.getPlayerPosX(), 0);
    assertEquals(maze2.getPlayerPosY(), 0);
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
    System.out.println(maze1.getPlayerGold());
    maze1.goDown();
    System.out.println(maze1.getPlayerGold());
    maze1.goRight();
    System.out.println(maze1.getPlayerGold());
    maze1.goRight();
    System.out.println(maze1.getPlayerGold());
  }

  @Test
  public void checkWrapPerfectMaze() {
    maze2.goUp();
    System.out.println(maze2.getPlayerPosX());
    System.out.println(maze2.getPlayerPosY());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkWrapPerfectMaze2() {
    MazeGame maze5 = new Maze(3, 4, 11, true, true,
            0, 1);
    maze5.goUp();
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkNonWrapRoomMaze() {
    maze3.goUp();
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkNonWrapRoomMaze2() {
    MazeGame maze6 = new Maze(3, 4, 3, false, false,
            0, 2);
    maze6.goUp();
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkNonWrapRoomMaze3() {
    MazeGame maze6 = new Maze(3, 4, 3, false, false,
            0, 2);
    maze6.goDown();
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkWrapRoomMaze() {
    maze4.goUp();
  }

  @Test
  public void checkWrapRoomMaze2() {
    maze4 = new Maze(3, 4, 3, false, true,
            0, 0);
    maze4.goUp();
  }

}
