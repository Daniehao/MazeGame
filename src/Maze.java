import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The Maze class that implements the MazeGame, and which could generate wrapping or non-wrapping
 * perfect maze, wrapping or non-wrapping room maze.
 */
public class Maze implements MazeGame {
  List<Integer> savedWall;
  private int rows;
  private int cols;
  private int remains;
  private Cell[][] maze;
  private boolean isWrapping;
  private int playerGold;
  private int playerPosX;
  private int playerPosY;

  /**
   * Constructor for Maze class.
   *
   * @param rows       The number of rows in the maze.
   * @param cols       The number of columns in the maze.
   * @param remains    The number of walls that should remain.
   * @param isPerfect  The maze is perfect or not.
   * @param isWrapping The maze is wrapping or not.
   * @param playerPosX The horizontal location of the player.
   * @param playerPosY The vertical location of the player.
   */
  public Maze(int rows, int cols, int remains, boolean isPerfect, boolean isWrapping,
              int playerPosX, int playerPosY) {
    this.rows = rows;
    this.cols = cols;
    this.remains = remains;
    this.isWrapping = isWrapping;
    this.playerPosX = playerPosX;
    this.playerPosY = playerPosY;
    playerGold = 0;
    savedWall = new ArrayList<>();
    if (rows < 0) {
      throw new IllegalArgumentException("rows input cannot be negative!");
    }
    if (cols < 0) {
      throw new IllegalArgumentException("columns input cannot be negative!");
    }
    generatePerfectMaze(isWrapping);
    if (!isPerfect) {
      if (remains < rows * cols - 1 && remains >= 0) {
        generateRoomMaze();
      } else {
        throw new IllegalArgumentException("The remains input is invalid since it needs to be 0 " +
                "to rows*columns - 1");
      }
    }
  }

  /**
   * Generate a Wrapping or a Non- wrapping perfect maze.
   *
   * @param isWrapping The maze is wrapping or not.
   */
  private void generatePerfectMaze(boolean isWrapping) {
    int[][] cellToUnion = new int[rows][cols];
    List<Integer> walls = new ArrayList<>();
    Map<Integer, List<Integer>> unionToCells = new HashMap<>();
    generateCells(rows, cols);
    int totalWalls = 0;
    if (isWrapping) {
      totalWalls = cols * rows + rows * cols;
    } else {
      totalWalls = (cols - 1) * rows + (rows - 1) * cols;
    }

    setUnionCellRelationship(cellToUnion, unionToCells);

    for (int i = 0; i < totalWalls; i++) {
      walls.add(i);
    }
    int removedCount = kruskalOnWalls(cellToUnion, walls, unionToCells, totalWalls, isWrapping);
    if (removedCount == rows * cols - 1) {
      savedWall.addAll(walls);
    } else {
      for (int wall : walls) {
        int[][] cellsPositions = new int[2][2];
        if (!isWrapping) {
          cellsPositions = getCellsPositionByWall(wall);
        } else {
          cellsPositions = getCellsPositionByWallWrapping(wall);
        }
        int cell1X = cellsPositions[0][0];
        int cell1Y = cellsPositions[0][1];
        int cell2X = cellsPositions[1][0];
        int cell2Y = cellsPositions[1][1];
        linkCells(cell1X, cell1Y, cell2X, cell2Y);
        setUnionNum(unionToCells, cellToUnion, cellToUnion[cell1X][cell1Y],
                cellToUnion[cell2X][cell2Y]);
      }
    }
  }

  /**
   * Assign the union number for each cell as well as the cells included for each union.
   *
   * @param cellToUnion  The union number of the cell.
   * @param unionToCells The cells in a union.
   */
  private void setUnionCellRelationship(int[][] cellToUnion, Map<Integer,
          List<Integer>> unionToCells) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        cellToUnion[i][j] = i * cols + j;
        List<Integer> temp = new ArrayList<>();
        temp.add(i * cols + j);
        unionToCells.put(i * cols + j, temp);
      }
    }
  }

  /**
   * Use the Kruskal Algorithm to implement the wall removal process.
   *
   * @param cellToUnion  The union number of the cell.
   * @param walls        The walls that still remains in the maze.
   * @param unionToCells The cells in a union.
   * @param totalWalls   The initial nubmer of walls at the beginning.
   * @return The number of walls that has been removed.
   */
  private int kruskalOnWalls(int[][] cellToUnion, List<Integer> walls, Map<Integer,
          List<Integer>> unionToCells, int totalWalls, boolean isWrapping) {
    Random random = new Random();
    random.setSeed(1000);
    int removedCount = 0;
    while (removedCount < rows * cols - 1 && savedWall.size() < totalWalls - rows * cols + 1) {
      int randomInt = random.nextInt(walls.size());
      int[][] cellsPositions = new int[2][2];
      if (isWrapping) {
        cellsPositions = getCellsPositionByWallWrapping(walls.get(randomInt));
      } else {
        cellsPositions = getCellsPositionByWall(walls.get(randomInt));
      }
      int cell1X = cellsPositions[0][0];
      int cell1Y = cellsPositions[0][1];
      int cell2X = cellsPositions[1][0];
      int cell2Y = cellsPositions[1][1];
      if (cellToUnion[cell1X][cell1Y] == cellToUnion[cell2X][cell2Y]) {
        savedWall.add(walls.get(randomInt));
      } else {
        linkCells(cell1X, cell1Y, cell2X, cell2Y);
        removedCount++;
        setUnionNum(unionToCells, cellToUnion, cellToUnion[cell1X][cell1Y],
                cellToUnion[cell2X][cell2Y]);
      }
      walls.remove(randomInt);
    }
    return removedCount;
  }

  /**
   * Combine the two unions into one.
   *
   * @param unionToCells The cells in a union.
   * @param cellToUnion  The union number of the cell.
   * @param unionNum1    The index of the one cell by the wall.
   * @param unionNum2    The index of the other cell by the wall.
   */
  private void setUnionNum(Map<Integer, List<Integer>> unionToCells, int[][] cellToUnion,
                           int unionNum1, int unionNum2) {
    for (int cellIndex : unionToCells.get(unionNum1)) {
      cellToUnion[cellIndex / cols][cellIndex % cols] = unionNum2;
    }
    unionToCells.get(unionNum2).addAll(unionToCells.get(unionNum1));
    unionToCells.remove(unionNum1);
  }

  /**
   * Link two cells by their locations.
   *
   * @param cell1X The horizontal index of cell 1.
   * @param cell1Y The vertical index of cell 1.
   * @param cell2X The horizontal index of cell 2.
   * @param cell2Y The vertical index of cell 2.
   */
  private void linkCells(int cell1X, int cell1Y, int cell2X, int cell2Y) {
    Cell cell1 = maze[cell1X][cell1Y];
    Cell cell2 = maze[cell2X][cell2Y];
    if (cell1X == cell2X) {
      cell1.setNextCell(cell2, "right");
      cell2.setNextCell(cell1, "left");
    } else {
      cell1.setNextCell(cell2, "down");
      cell2.setNextCell(cell1, "up");
    }
  }

  /**
   * Initialize the maze by generating m * n empty cells.
   *
   * @param rows The number of rows in the maze.
   * @param cols The number of columns in the maze.
   */
  private void generateCells(int rows, int cols) {
    maze = new Cell[rows][cols];
    int goldTotal = (int) (rows * cols * .2);
    int thiefTotal = (int) (rows * cols * .1);
    Random random = new Random();
    random.setSeed(1000);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        maze[i][j] = new Cell();
      }
    }

    for (int i = 0; i < goldTotal; i++) {
      int indexX = random.nextInt(rows);
      int indexY = random.nextInt(cols);
      maze[indexX][indexY].setGold(true, 50);
    }

    for (int i = 0; i < thiefTotal; i++) {
      int indexX = random.nextInt(rows);
      int indexY = random.nextInt(cols);
      maze[indexX][indexY].setThief();
    }
  }

  /**
   * Get the location of the two cells by a input wall index for non wrapping maze.
   *
   * @param wallIndex The wall index.
   * @return The location array of the two cells.
   */
  private int[][] getCellsPositionByWall(int wallIndex) {
    if (wallIndex < rows * (cols - 1)) {
      int colIndex = wallIndex % (cols - 1);
      int rowIndex = wallIndex / (cols - 1);
      return new int[][]{{rowIndex, colIndex}, {rowIndex, colIndex + 1}};
    } else {
      wallIndex -= rows * (cols - 1);
      int colIndex = wallIndex % cols;
      int rowIndex = wallIndex / cols;
      return new int[][]{{rowIndex, colIndex}, {rowIndex + 1, colIndex}};
    }
  }

  /**
   * @param wallIndex The wall index.
   * @return The location array of the two cells.
   */
  private int[][] getCellsPositionByWallWrapping(int wallIndex) {
    if (wallIndex < rows * cols) {
      int colIndex = wallIndex % cols;
      int rowIndex = wallIndex / cols;
      if (colIndex == 0) {
        return new int[][]{{rowIndex, colIndex}, {rowIndex, cols - 1}};
      } else {
        return new int[][]{{rowIndex, colIndex - 1}, {rowIndex, colIndex}};
      }
    } else {
      wallIndex -= rows * cols;
      int colIndex = wallIndex % cols;
      int rowIndex = wallIndex / cols;
      if (rowIndex == 0) {
        return new int[][]{{rowIndex, colIndex}, {rows - 1, colIndex}};
      } else {
        return new int[][]{{rowIndex - 1, colIndex}, {rowIndex, colIndex}};
      }
    }
  }

  /**
   * Generate a Non-wrapping room maze.
   */
  private void generateRoomMaze() {
    int numToDelete = savedWall.size() - remains;
    Random random = new Random();
    random.setSeed(1000);
    for (int i = 0; i < numToDelete; i++) {
      int randomInt = random.nextInt(savedWall.size());
      int[][] cellsPositions = getCellsPositionByWall(randomInt);
      int cell1X = cellsPositions[0][0];
      int cell1Y = cellsPositions[0][1];
      int cell2X = cellsPositions[1][0];
      int cell2Y = cellsPositions[1][1];
      linkCells(cell1X, cell1Y, cell2X, cell2Y);
    }
  }

  @Override
  public void goLeft() {
    if (!checkOutOfBound()) {
      throw new IllegalArgumentException("You cannot go this direction!");
    }
    if (playerPosY - 1 >= 0) {
      playerPosY--;
      checkGoldThief();
    } else {
      throw new IllegalArgumentException("Player's move is out of bound!");
    }
  }

  @Override
  public void goRight() {
    if (!checkOutOfBound()) {
      throw new IllegalArgumentException("You cannot go this direction!");
    }
    if (playerPosY + 1 < cols) {
      playerPosY++;
      checkGoldThief();
    } else {
      throw new IllegalArgumentException("Player's move is out of bound!");
    }
  }

  @Override
  public void goUp() {
    if (!checkOutOfBound()) {
      throw new IllegalArgumentException("You cannot go this direction!");
    }
    if (playerPosX - 1 >= 0) {
      playerPosX--;
      checkGoldThief();
    } else {
      throw new IllegalArgumentException("Player's move is out of bound!");
    }
  }

  @Override
  public void goDown() {
    if (!checkOutOfBound()) {
      throw new IllegalArgumentException("You cannot go this direction!");
    }
    if (playerPosX + 1 < rows) {
      playerPosX++;
      checkGoldThief();
    } else {
      throw new IllegalArgumentException("Player's move is out of bound!");
    }
  }

  @Override
  public int getPlayerPosX() {
    return playerPosX;
  }

  @Override
  public int getPlayerPosY() {
    return playerPosY;
  }

  @Override
  public int getPlayerGold() {
    return playerGold;
  }

  /**
   * Check if the location of the player is out of bound.
   *
   * @return True/False.
   */
  public boolean checkOutOfBound() {
    if (playerPosX < 0 || playerPosY < 0 || playerPosX >= rows || playerPosY >= cols) {
      return false;
    }
    return true;
  }


  /**
   * Update the gold amount of player as well as update the gold amount after the player take the
   * gold in the current location.
   */
  private void checkGoldThief() {
    if (maze[playerPosX][playerPosY].hasGold()) {
      playerGold += maze[playerPosX][playerPosY].getGoldNum();
      maze[playerPosX][playerPosY].setGold(false, 0);
    }
    if (maze[playerPosX][playerPosY].hasThief()) {
      playerGold -= (int) (0.1 * playerGold);
    }
  }
}
