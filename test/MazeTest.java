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
  public void testMazeConstructor() {
    maze1 = new Maze(4, 3, 11, true, false,
            0, 1);
    maze2 = new Maze(4, 3, 11, true, true,
            0, 1);
    maze3 = new Maze(4, 3, 3, false, false,
            0, 1);
    maze4 = new Maze(4, 3, 3, false, true,
            0, 1);
    assertEquals(maze1.getPlayerPosX(), 0);
    assertEquals(maze1.getPlayerPosY(), 1);
    assertEquals(maze2.getPlayerPosX(), 0);
    assertEquals(maze2.getPlayerPosY(), 1);
    assertEquals(maze3.getPlayerPosX(), 0);
    assertEquals(maze3.getPlayerPosY(), 1);
    assertEquals(maze4.getPlayerPosX(), 0);
    assertEquals(maze4.getPlayerPosY(), 1);
  }

  @Test
  public void testGenerateCells() {
    generateCells
  }
}
