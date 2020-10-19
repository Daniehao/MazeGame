import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

  private void generatePerfectMaze(boolean isWrapping) {
    int[][] cellToUnion = new int[rows][cols];
    List<Integer> walls = new ArrayList<>();
    Map<Integer, List<Integer>> unionToCells = new HashMap<>();
    generateCells(rows, cols);
    int totalWalls = 0;
    if (isWrapping) {
      totalWalls = (cols + 1) * rows + (rows + 1) * cols;
    } else {
      totalWalls = (cols - 1) * rows + (rows - 1) * cols;
    }

    setUnionCellRelationship(cellToUnion, unionToCells);

    for (int i = 0; i < totalWalls; i++) {
      walls.add(i);
    }
    int removedCount = kruskalOnWalls(cellToUnion, walls, unionToCells, totalWalls);
    if (removedCount == rows * cols - 1) {
      savedWall.addAll(walls);
    } else {
      for (int wall : walls) {
        int[][] cellsPositions = getCellsPositionByWall(wall);
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

  private void setUnionCellRelationship(int[][] cellToUnion, Map<Integer, List<Integer>> unionToCells) {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        cellToUnion[i][j] = i * cols + j;
        List<Integer> temp = new ArrayList<>();
        temp.add(i * cols + j);
        unionToCells.put(i * cols + j, temp);
      }
    }
  }

  private int kruskalOnWalls(int[][] cellToUnion, List<Integer> walls, Map<Integer, List<Integer>> unionToCells, int totalWalls) {
    Random random = new Random();
    int removedCount = 0;
    while (removedCount < rows * cols - 1 && savedWall.size() < totalWalls - rows * cols + 1) {
      int randomInt = random.nextInt(walls.size());
      int[][] cellsPositions = getCellsPositionByWall(walls.get(randomInt));
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
      System.out.println(removedCount + ", " + savedWall.size());
    }
    return removedCount;
  }

  private void setUnionNum(Map<Integer, List<Integer>> unionToCells, int[][] cellToUnion,
                           int unionNum1, int unionNum2) {
    for (int cellIndex : unionToCells.get(unionNum1)) {
      cellToUnion[cellIndex / cols][cellIndex % cols] = unionNum2;
    }
    unionToCells.get(unionNum2).addAll(unionToCells.get(unionNum1));
    unionToCells.remove(unionNum1);
  }

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

  private void generateCells(int rows, int cols) {
    maze = new Cell[rows][cols];
    int goldTotal = (int) (rows * cols * .2);
    int thiefTotal = (int) (rows * cols * .1);
    Random random = new Random();
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

  private void generateRoomMaze() {
    int numToDelete = savedWall.size() - remains;
    Random random = new Random();
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

  private void changeToWrappingMaze() {
    for (int j = 0; j < cols; j++) {
      linkCells(0, j, rows - 1, j);
    }
    for (int i = 0; i < rows; i++) {
      linkCells(i, 0, i, cols - 1);
    }
  }

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

  private void checkGoldThief() {
    if (maze[playerPosX][playerPosY].hasGold()) {
      playerGold++;
      maze[playerPosX][playerPosY].setGold(false, 0);
    }
    if (maze[playerPosX][playerPosY].hasThief()) {
      playerGold -= (int) (0.1 * playerGold);
    }
  }

  public int getPlayerPosX() {
    return playerPosX;
  }

  public int getPlayerPosY() {
    return playerPosY;
  }

  public int getPlayerGold() {
    return playerGold;
  }

  public boolean checkOutOfBound() {
    if (playerPosX < 0 || playerPosY < 0 || playerPosX >= rows || playerPosY >= cols) {
      return false;
    }
    return true;
  }
}
