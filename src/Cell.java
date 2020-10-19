import java.util.HashMap;
import java.util.Map;

/**
 * Cell class to store the pointer as well as the gold number, and whether tbere is a thief inside
 * the cell.
 */
public class Cell {
  //string is the direction, cell is the next cell.
  Map<String, Cell> cellMap;
  boolean hasGold;
  boolean hasThief;
  int goldNum;

  /**
   * Constructor for Cell class.
   */
  public Cell() {
    cellMap = new HashMap<>();
    cellMap.put("left", null);
    cellMap.put("right", null);
    cellMap.put("up", null);
    cellMap.put("down", null);
    hasGold = false;
    hasThief = false;
    goldNum = 0;
  }

  /**
   * Set the pointer to the next cell.
   *
   * @param nextCell      The cell next to the current cell.
   * @param directionSide The direction pointer to the next cell.
   */
  public void setNextCell(Cell nextCell, String directionSide) {
    switch (directionSide) {
      case "left":
        cellMap.put("left", nextCell);
        break;
      case "right":
        cellMap.put("right", nextCell);
        break;
      case "up":
        cellMap.put("up", nextCell);
        break;
      case "down":
        cellMap.put("down", nextCell);
        break;
    }
  }

  /**
   * Set if the current cell has gold and the count of the gold.
   *
   * @param hasGold Whether the current cell has gold.
   * @param goldNum The count of gold in the cell.
   */
  public void setGold(boolean hasGold, int goldNum) {
    this.hasGold = hasGold;
    this.goldNum = goldNum;
  }

  /**
   * Set the hasThief value to true.
   */
  public void setThief() {
    hasThief = true;
  }

  /**
   * Set the hasGold value for the cell.
   *
   * @return If the cell has gold.
   */
  public boolean hasGold() {
    return hasGold;
  }

  /**
   * Set the hasThief value for the cell.
   *
   * @return If the cell has thief.
   */
  public boolean hasThief() {
    return hasThief;
  }

  /**
   * Get the amount of gold int the cell.
   *
   * @return The amout of gold in the cell.
   */
  public int getGoldNum() {
    return goldNum;
  }

  public String toString() {
    return String.format("The left cell of current cell is: %s, the right cell of current cell " +
            "is: %s, the up cell of current cell is: %s, The down cell of current cell is: %s, " +
            "the total number of gold in the cell: %d, if the cell include a thief: %s",
            cellMap.get("left"), cellMap.get("right"), cellMap.get("up"), cellMap.get("down"),
            goldNum, "" + hasThief);
  }

  /**
   * Get the cell on the left side of the current cell.
   *
   * @return The cell on the left side of current.
   */
  public Cell getLeftCell() {
    return cellMap.get("left");
  }

  /**
   * Get the cell on the right side of the current cell.
   *
   * @return The cell on the right side of current.
   */
  public Cell getRightCell() {
    return cellMap.get("right");
  }

  /**
   * Get the cell on the up side of the current cell.
   *
   * @return The cell on the up side of current.
   */
  public Cell getUpCell() {
    return cellMap.get("up");
  }

  /**
   * Get the cell on the down side of the current cell.
   *
   * @return The cell on the down side of current.
   */
  public Cell getDownCell() {
    return cellMap.get("down");
  }
}
