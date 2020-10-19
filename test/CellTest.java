import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    cell1.setGold(true, 10);
    cell2.setThief();
    cell1.setNextCell(cell2, "right");
    cell1.setNextCell(cell3, "left");
    cell1.setNextCell(cell4, "up");
    cell1.setNextCell(cell5, "down");
  }

  @Test
  public void testConstructor() {
    assertEquals("The left cell of current cell is: null, the right cell of current " +
                    "cell is: The left cell of current cell is: null, the right cell of current " +
                    "cell is: null, the up cell of current cell is: null, The down cell of current" +
                    " cell is: null, the total number of gold in the cell: 0, if the cell include" +
                    " a thief: false, the up cell of current cell is: null, The down cell of " +
                    "current cell is: null, the total number of gold in the cell: 0, if the cell" +
                    " include a thief: false", cell1.toString());
  }

  @Test
  public void testSetNextCell() {
    assertEquals(cell1.getRightCell(), cell2);
    assertEquals(cell1.getLeftCell(), cell3);
    assertEquals(cell1.getUpCell(), cell4);
    assertEquals(cell1.getDownCell(), cell5);
  }

  @Test
  public void testGetGold() {
    assertEquals(10, cell1.getGoldNum());
    assertEquals(0, cell2.getGoldNum());
  }

  @Test
  public void testThief() {
    assertEquals(false, cell1.hasThief());
    assertEquals(true, cell2.hasThief());
  }
}
