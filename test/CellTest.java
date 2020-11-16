import org.junit.Before;
import org.junit.Test;

import game.Cell;

import static org.junit.Assert.assertEquals;

/**
 * Test for the Game.Cell class, which checks if all cell has a correct link with the cell next to
 * it.
 */
public class CellTest {
  private Cell cell1;
  private Cell cell2;
  private Cell cell3;
  private Cell cell4;
  private Cell cell5;

  @Before
  public void setup() {
    cell1 = new Cell();
    cell2 = new Cell();
    cell1.setNextCell(cell2, "right");
    cell1.setNextCell(cell3, "left");
    cell1.setNextCell(cell4, "up");
    cell1.setNextCell(cell5, "down");
  }

  @Test
  public void testConstructor() {
    assertEquals("class Game.Cell",
            cell1.getClass().toString());
  }

  @Test
  public void testSetNextCell() {
    assertEquals(cell1.getRightCell(), cell2);
    assertEquals(cell1.getLeftCell(), cell3);
    assertEquals(cell1.getUpCell(), cell4);
    assertEquals(cell1.getDownCell(), cell5);
  }

}
