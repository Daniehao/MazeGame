package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 * The MazeGameImpl class that implements the MazeGame interface, and which could generate wrapping
 * or non-wrapping perfect maze, wrapping or non-wrapping room maze. There is a player who could go
 * to four directions, but chances are the player was eaten by the wumpus, fallen into a pit, drop
 * by the bats, geting into a hallway, or shoot to a wumpus successfully.
 */
public class MazeGameImpl implements MazeGame {
  private List<Integer> savedWall;
  private int rows;
  private int cols;
  private int remains;
  private Cell[][] maze;
  private boolean isWrapping;
//  private int playerPosX;
//  private int playerPosY;
  private boolean isPerfect;
  private List<int[]> caveLst;
  private double batPercent;
  private double pitPercent;
  private int arrows;
  private boolean isGameOver;
  private boolean isShootSuccess;
  private Cell wumpus;
//  private int[] start;
  private String alert;
  private int playerNum;
  private int flag;
  private Player player1;
  private Player player2;
  private Player currPlayer;
  private Player otherPlayer;

  public MazeGameImpl() {

  }

  /**
   * Constructor for Game.Maze class.
   *
   * @param rows       The number of rows in the maze.
   * @param cols       The number of columns in the maze.
   * @param remains    The number of walls that should remain.
   * @param isPerfect  The maze is perfect or not.
   * @param isWrapping The maze is wrapping or not.
   * @param playerNum The total number of players.
   */
  public MazeGameImpl(int rows, int cols, int remains, boolean isPerfect, boolean isWrapping,
                      double batPercent, double pitPercent, int arrows, int playerNum)
          throws IllegalArgumentException {
    this.rows = rows;
    this.cols = cols;
    this.remains = remains;
    this.isWrapping = isWrapping;
    this.isPerfect = isPerfect;
    savedWall = new ArrayList<>();
    caveLst = new ArrayList<>();
    this.batPercent = batPercent;
    this.pitPercent = pitPercent;
    this.arrows = arrows;
    this.playerNum = playerNum;
    isGameOver = false;
    isShootSuccess = false;
    flag = 1;
    wumpus = null;
    alert = "";
    player1 = new Player(1);
    player2 = null;
    if (playerNum == 2) {
      player2 = new Player(2);
    }
    if (rows < 0) {
      throw new IllegalArgumentException("rows input cannot be negative!");
    }
    if (cols < 0) {
      throw new IllegalArgumentException("columns input cannot be negative!");
    }
    if (batPercent < 0) {
      throw new IllegalArgumentException("The bat percentage is invalid!");
    }
    if (pitPercent < 0) {
      throw new IllegalArgumentException("The pit percentage is invalid!");
    }
    if (arrows <= 0) {
      throw new IllegalArgumentException("The arrow number is invalid!");
    }
    generatePerfectMaze();
    if (!isPerfect) {
      if (isWrapping && remains < cols * rows + rows * cols - rows * cols + 1 &&
              remains >= 0) {
        generateRoomMaze();
      } else if (!isWrapping && remains < (cols - 1) * rows + (rows - 1) * cols - rows * cols + 1
              && remains >= 0) {
        generateRoomMaze();
      } else {
        throw new IllegalArgumentException("The remains input is invalid!");
      }
    }

    assignCaveTunnel();
    linkTunnel();
    assignWumpus();
    assignPits();
    assignSuperBats();
    setStartPosition(1);
    if (playerNum == 2) {
      setStartPosition(2);
    }
    currPlayer = player1;
  }

  public void changePlayerFlag() {
    if (playerNum == 1) {
      flag = 1;
      currPlayer = player1;
    } else {
      if (flag == 1) {
        flag = 2;
        currPlayer = player2;
      } else {
        flag = 1;
        currPlayer = player1;
      }
    }
  }

  @Override
  public int getPlayerRound() {
    return flag;
  }

  private void setStartPosition(int flag) {
    Random random = new Random();
    if (flag == 1) {
      random.setSeed(1000);
    } else {
      random.setSeed(50);
    }
    int randomInt = random.nextInt(caveLst.size());
    int x = caveLst.get(randomInt)[0];
    int y = caveLst.get(randomInt)[1];
    Cell temp = maze[x][y];
    while (temp.isWumpus || temp.isPit || temp.hasBat) {
      randomInt = random.nextInt(caveLst.size());
      x = caveLst.get(randomInt)[0];
      y = caveLst.get(randomInt)[1];
      temp = maze[x][y];
    }
    if (flag == 1) {
      player1.setPlayerStartLocation(x, y);
    } else {
      player2.setPlayerStartLocation(x, y);
    }
  }

  /**
   * Generate a Wrapping or a Non-wrapping perfect maze.
   */
  private void generatePerfectMaze() {
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
    //Initialize the cellToUnion and unionToCells
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
    Random random = new Random();
    random.setSeed(1000);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        maze[i][j] = new Cell();
      }
    }
  }

  /**
   * Get the location of the two cells by a input wall index for non wrapping maze.
   *
   * @param wallIndex The wall index.
   * @return The location array of the two cells.
   */
  private int[][] getCellsPositionByWall(int wallIndex) {
    //The wall index is ordering by vertical first and then horizontal.
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
   * Get the two cells positions by the wall.
   *
   * @param wallIndex The wall index.
   * @return The location array of the two cells.
   */
  private int[][] getCellsPositionByWallWrapping(int wallIndex) {
    if (wallIndex < rows * cols) {
      int colIndex = wallIndex % cols;
      int rowIndex = wallIndex / cols;
      if (colIndex == 0) {
        return new int[][]{{rowIndex, cols - 1}, {rowIndex, colIndex}};
      } else {
        return new int[][]{{rowIndex, colIndex - 1}, {rowIndex, colIndex}};
      }
    } else {
      wallIndex -= rows * cols;
      int colIndex = wallIndex % cols;
      int rowIndex = wallIndex / cols;
      if (rowIndex == 0) {
        return new int[][]{{rows - 1, colIndex}, {rowIndex, colIndex}};
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
      int[][] cellsPositions = new int[2][2];
      if (isWrapping) {
        cellsPositions = getCellsPositionByWallWrapping(savedWall.get(randomInt));
      } else {
        cellsPositions = getCellsPositionByWall(savedWall.get(randomInt));
      }
      int cell1X = cellsPositions[0][0];
      int cell1Y = cellsPositions[0][1];
      int cell2X = cellsPositions[1][0];
      int cell2Y = cellsPositions[1][1];
      linkCells(cell1X, cell1Y, cell2X, cell2Y);
      savedWall.remove(randomInt);
    }
  }

  /**
   * Turn left operation.
   */
  public void goLeft() {
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    if (playerPosY - 1 >= 0 && maze[playerPosX][playerPosY].getLeftCell() != null) {
      playerPosY--;
      currPlayer.setPlayerLocation(playerPosX, playerPosY);
    } else {
      if (isWrapping && playerPosY - 1 < 0 && maze[playerPosX][playerPosY].getLeftCell() != null) {
        playerPosY = cols - playerPosY - 1;
        currPlayer.setPlayerLocation(playerPosX, playerPosY);
      }
    }
  }

  /**
   * Turn right operation.
   */
  public void goRight() {
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    if (playerPosY + 1 < cols && maze[playerPosX][playerPosY].getRightCell() != null) {
      playerPosY++;
      currPlayer.setPlayerLocation(playerPosX, playerPosY);
    } else {
      if (isWrapping && playerPosY + 1 >= cols
              && maze[playerPosX][playerPosY].getRightCell() != null) {
        playerPosY = 0;
        currPlayer.setPlayerLocation(playerPosX, playerPosY);
      }
    }
  }

  /**
   * Turn up operation.
   */
  public void goUp() {
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    if (playerPosX - 1 >= 0 && maze[playerPosX][playerPosY].getUpCell() != null) {
      playerPosX--;
      currPlayer.setPlayerLocation(playerPosX, playerPosY);
    } else {
      if (isWrapping && playerPosX - 1 < 0 && maze[playerPosX][playerPosY].getUpCell() != null) {
        playerPosX = rows - 1;
        currPlayer.setPlayerLocation(playerPosX, playerPosY);
      }
    }
  }

  /**
   * Turn down operation.
   */
  public void goDown() {
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    if (playerPosX + 1 < rows && maze[playerPosX][playerPosY].getDownCell() != null) {
      playerPosX++;
      currPlayer.setPlayerLocation(playerPosX, playerPosY);
    } else {
      if (isWrapping && playerPosX + 1 >= rows
              && maze[playerPosX][playerPosY].getDownCell() != null) {
        playerPosX = 0;
        currPlayer.setPlayerLocation(playerPosX, playerPosY);
      }
    }
  }


  @Override
  public String getPlayerLocation() {
    StringBuilder sb = new StringBuilder();
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    sb.append("You are in cave (" + playerPosX + ", " + playerPosY + "). ");
    sb.append("Tunnels lead to the ");
    Cell curr = maze[playerPosX][playerPosY];
    if (curr.getRightCell() != null) {
      sb.append("E, ");
    }
    if (curr.getLeftCell() != null) {
      sb.append("W, ");
    }
    if (curr.getUpCell() != null) {
      sb.append("N, ");
    }
    if (curr.getDownCell() != null) {
      sb.append("S, ");
    }
    sb.delete(sb.length() - 2, sb.length());
    return sb.toString();
  }

  private String convertWallsToString() {
    String s = "";
    for (int i = 0; i < savedWall.size(); i++) {
      s += savedWall.get(i);
      s += " ";
    }
    return s;
  }

  @Override
  public String toString() {
    String s = "";
    int[] start = currPlayer.getPlayerStartLocation();
    if (isPerfect && isWrapping) {
      s += String.format("The maze is %d * %d, and it is a wrapping perfect maze. " +
              "The start point of player is (%d, %d). The saved walls are numbered by: " +
              convertWallsToString(), rows, cols, start[0], start[1]);
    }
    if (isPerfect && !isWrapping) {
      s += String.format("The maze is %d * %d, and it is a non-wrapping perfect maze. " +
              "The start point of player is (%d, %d). The saved walls are numbered by: " +
              convertWallsToString(), rows, cols, start[0], start[1]);
    }
    if (!isPerfect && isWrapping) {
      s += String.format("The maze is %d * %d, and it is a wrapping room maze. " +
              "The start point of player is (%d, %d). The saved walls are numbered by: " +
              convertWallsToString(), rows, cols, start[0], start[1]);
    }
    if (!isPerfect && !isWrapping) {
      s += String.format("The maze is %d * %d, and it is a non-wrapping room maze. " +
              "The start point of player is (%d, %d). The saved walls are numbered by: " +
              convertWallsToString(), rows, cols, start[0], start[1]);
    }
    return s;
  }

  /**
   * Traverse the whole maze, and assign each cell as a cave or a tunnel.
   */
  private void assignCaveTunnel() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Cell curr = maze[i][j];
        if (getWallNums(curr) == 2) {
          curr.setIsRoom(false);
          curr.setIsTunnel(true);
        } else {
          curr.setIsRoom(true);
          curr.setIsTunnel(false);
          int[] temp = new int[2];
          temp[0] = i;
          temp[1] = j;
          caveLst.add(temp);
        }
      }
    }
  }

  /**
   * Link the two rooms if there is a tunnel or tunnels between them.
   */
  private void linkTunnel() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Cell curr = maze[i][j];
        if (curr.isRoom) {
          if (curr.getUpCell() != null && curr.getUpCell().isTunnel) {
            linkTunnelHelper(curr, curr.getUpCell(), 0);
          }
          if (curr.getDownCell() != null && curr.getDownCell().isTunnel) {
            linkTunnelHelper(curr, curr.getDownCell(), 1);
          }
          if (curr.getLeftCell() != null && curr.getLeftCell().isTunnel) {
            linkTunnelHelper(curr, curr.getLeftCell(), 2);
          }
          if (curr.getRightCell() != null && curr.getRightCell().isTunnel) {
            linkTunnelHelper(curr, curr.getRightCell(), 3);
          }
        }
      }
    }
  }

  /**
   * The player runs into the tunnel and update the player's location by the tunnel's exit.
   */
  private void linkTunnelHelper(Cell curr, Cell next, int flag) {
    int originFlag = flag;
    while (next.isTunnel) {
      if (next.getDownCell() != null && flag != 0) {
        next = curr.getDownCell();
        flag = 1;
      } else if (next.getUpCell() != null && flag != 1) {
        next = next.getUpCell();
        flag = 0;
      } else if (next.getLeftCell() != null && flag != 3) {
        next = next.getLeftCell();
        flag = 2;
      } else if (next.getRightCell() != null && flag != 2) {
        next = next.getRightCell();
        flag = 3;
      } else {
        break;
      }
    }
    if (originFlag == 0) {
      curr.setNextCell(next, "up");
    }
    if (originFlag == 1) {
      curr.setNextCell(next, "down");
    }
    if (originFlag == 2) {
      curr.setNextCell(next, "left");
    }
    if (originFlag == 3) {
      curr.setNextCell(next, "right");
    }

    if (flag == 0) {
      next.setNextCell(curr, "down");
    } else if (flag == 1) {
      next.setNextCell(curr, "up");
    } else if (flag == 2) {
      next.setNextCell(curr, "right");
    } else if (flag == 3) {
      next.setNextCell(curr, "left");
    }
  }

  /**
   * Get the number of walls surrounded by the current cell.
   *
   * @param curr The current cell.
   * @return The number of walls that the current cell has.
   */
  private int getWallNums(Cell curr) {
    int count = 0;
    if (curr.getDownCell() == null) {
      count++;
    }
    if (curr.getUpCell() == null) {
      count++;
    }
    if (curr.getLeftCell() == null) {
      count++;
    }
    if (curr.getRightCell() == null) {
      count++;
    }
    return count;
  }

  /**
   * Assign a random cave to have the wumpus. There is only one wumpus in the game.
   */
  private void assignWumpus() {
    Random random = new Random();
    random.setSeed(500);
    int index = random.nextInt(caveLst.size());
    int[] wumpusLocation = caveLst.get(index);
    wumpus = maze[wumpusLocation[0]][wumpusLocation[1]];
    wumpus.setIsWumpus();
    if (wumpus.getRightCell() != null) {
      wumpus.getRightCell().setCloseToWumpus();
    }
    if (wumpus.getLeftCell() != null) {
      wumpus.getLeftCell().setCloseToWumpus();
    }
    if (wumpus.getUpCell() != null) {
      wumpus.getUpCell().setCloseToWumpus();
    }
    if (wumpus.getDownCell() != null) {
      wumpus.getDownCell().setCloseToWumpus();
    }
  }

  /**
   * Assign some random caves as bottomless pits.
   */
  private void assignPits() {
    int caveNum = caveLst.size();
    int pitNum = (int) (caveNum * pitPercent);
    Random random = new Random();
    random.setSeed(300);
    for (int i = 0; i < pitNum; i++) {
      int index = random.nextInt(caveLst.size());
      int[] pitPos = caveLst.get(index);
      Cell pit = maze[pitPos[0]][pitPos[1]];
      pit.setIsPit();
      if (pit.getRightCell() != null) {
        pit.getRightCell().setCloseToPit();
      }
      if (pit.getLeftCell() != null) {
        pit.getLeftCell().setCloseToPit();
      }
      if (pit.getUpCell() != null) {
        pit.getUpCell().setCloseToPit();
      }
      if (pit.getDownCell() != null) {
        pit.getDownCell().setCloseToPit();
      }
    }
  }

  /**
   * Assign some random caves to have superbats.
   */
  private void assignSuperBats() {
    int caveNum = caveLst.size();
    int batNum = (int) (caveNum * batPercent);
    Random random = new Random();
    random.setSeed(200);
    for (int i = 0; i < batNum; i++) {
      int index = random.nextInt(caveLst.size());
      int[] batPos = caveLst.get(index);
      Cell bat = maze[batPos[0]][batPos[1]];
      bat.setHasBat();
    }
  }

  /**
   * Get into the cave that have superbats.
   */
  private void getInBats() {
    Random random = new Random();
    random.setSeed(50);
    int num = random.nextInt(2);
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    //both bats and pits
    if (maze[playerPosX][playerPosY].getIsPit()) {
      //50% possibility to pits
      if (num == 0) {
        getInPits();
      } else {
        //50% possiblity to drop random cave
        dropToRandomCave();
      }
    } else {
      if (num == 1) {
        dropToRandomCave();
      }
    }
  }

  /**
   * The superbats drop the player to a random cave.
   */
  private void dropToRandomCave() {
    alert = "Whoa -- you successfully duck superbats that try to grab you!";
    System.out.println(alert);
    Random random = new Random();
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    int index = random.nextInt(caveLst.size());
    playerPosX = caveLst.get(index)[0];
    playerPosY = caveLst.get(index)[1];
    if (maze[playerPosX][playerPosY].isWumpus) {
      getInWumpus();
    } else if (maze[playerPosX][playerPosY].hasBat) {
      getInBats();
    } else if (maze[playerPosX][playerPosY].isPit) {
      getInPits();
    }
  }

  /**
   * The player get into the cave that has wumpus.
   */
  private void getInWumpus() {
    isGameOver = true;
    alert = "Chomp, chomp, chomp, thanks for feeding the Wumpus! Better luck next time.";
    System.out.println(alert);
  }

  /**
   * The player get into the cave that is a bottomless pit.
   */
  private void getInPits() {
    isGameOver = true;
    alert = "You fell into the bottomless pit! Better luck next time.";
    System.out.println(alert);
  }

  @Override
  public void move(String direction) throws IllegalArgumentException {
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    Cell curr = maze[playerPosX][playerPosY];
    int flag = 0;
    if (direction.equals("N")) {
      if (curr.getUpCell() != null) {
        goUp();
        curr = maze[playerPosX][playerPosY];
        movehelper(curr, flag);
      } else {
        alert = "Player is running out of bound! Please re-input direction.";
        System.out.println(alert);
      }
    } else if (direction.equals("S")) {
      if (curr.getDownCell() != null) {
        goDown();
        curr = maze[playerPosX][playerPosY];
        flag = 1;
        movehelper(curr, flag);
      } else {
        alert = "Player is running out of bound! Please re-input direction.";
        System.out.println(alert);
      }
    } else if (direction.equals("W")) {
      if (curr.getLeftCell() != null) {
        goLeft();
        curr = maze[playerPosX][playerPosY];
        flag = 2;
        movehelper(curr, flag);
      } else {
        alert = "Player is running out of bound! Please re-input direction.";
        System.out.println(alert);
      }
    } else if (direction.equals("E")) {
      if (curr.getRightCell() != null) {
        goRight();
        curr = maze[playerPosX][playerPosY];
        flag = 3;
        movehelper(curr, flag);
      } else {
        alert = "Player is running out of bound! Please re-input direction.";
        System.out.println(alert);
      }
    } else {
      throw new IllegalArgumentException("The direction string is invalid!");
    }
  }

  @Override
  public boolean getGameOver() {
    return isGameOver;
  }

  @Override
  public boolean checkUnwinnable() {
    Queue<Cell> queue = new LinkedList<>();
    Set<Cell> visited = new HashSet<>();
    queue.offer(wumpus);
    while (!queue.isEmpty()) {
      Cell curr = queue.poll();
      curr.couldReachToWumpus = true;
      visited.add(curr);
      if (curr.getUpCell() != null && !visited.contains(curr.getUpCell()) &&
              (!curr.getUpCell().isPit || curr.getUpCell().isPit && curr.getUpCell().hasBat)) {
        queue.offer(curr.getUpCell());
      }
      if (curr.getDownCell() != null && !visited.contains(curr.getDownCell()) &&
              (!curr.getDownCell().isPit || curr.getDownCell().isPit &&
                      curr.getDownCell().hasBat)) {
        queue.offer(curr.getDownCell());
      }
      if (curr.getLeftCell() != null && !visited.contains(curr.getLeftCell()) &&
              (!curr.getLeftCell().isPit || curr.getLeftCell().isPit &&
                      curr.getLeftCell().hasBat)) {
        queue.offer(curr.getLeftCell());
      }
      if (curr.getRightCell() != null && !visited.contains(curr.getRightCell()) &&
              (!curr.getRightCell().isPit || curr.getRightCell().isPit
                      && curr.getRightCell().hasBat)) {
        queue.offer(curr.getRightCell());
      }
    }
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (maze[i][j].isRoom && !maze[i][j].couldReachToWumpus &&
                i == player1.getPlayerStartLocation()[0] &&
                j == player1.getPlayerStartLocation()[1] || maze[i][j].isRoom &&
                !maze[i][j].couldReachToWumpus && i == player2.getPlayerStartLocation()[0] &&
                j == player2.getPlayerStartLocation()[1]) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean checkShootSuccess() {
    return isShootSuccess;
  }

  @Override
  public Cell getCurrentCell() {
    return maze[currPlayer.getPlayerLocation()[0]][currPlayer.getPlayerLocation()[1]];
  }

  @Override
  public String getShootRes() {
    if (isShootSuccess) {
      return "You shooted to the wumpus successfully!";
    }
    return "You didn't shooted to the wumpus, and you have " + arrows + " arrows remains.";
  }

  /**
   * The helper function for move method in order to print the current cave info.
   *
   * @param curr The current cell.
   * @param flag The direction flag.
   */
  private void movehelper(Cell curr, int flag) {
    if (curr.closeToWumpus) {
      alert = "You smell a Wumpus!";
      System.out.println(alert);
    } else if (curr.closeToPit) {
      alert = "You smell something terrible nearby.";
      System.out.println(alert);
    } else if (curr.isWumpus) {
      getInWumpus();
    } else if (curr.hasBat) {
      getInBats();
      getPlayerLocation();
    } else if (curr.isPit) {
      getInPits();
    } else if (curr.isTunnel) {
      moveInTunnel(flag);
    } else {
      alert = "You feel a draft.";
      System.out.println(alert);
      getPlayerLocation();
    }
  }

  @Override
  public void shoot(String direction, int distance) throws IllegalArgumentException {
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    if (!direction.equals("N") && !direction.equals("S") && !direction.equals("W") &&
            !direction.equals("E")) {
      throw new IllegalArgumentException("The direction input is invalid!");
    }
    if (distance == 0) {
      throw new IllegalArgumentException("shooting distance cannot be zero!");
    }
    Cell curr = maze[playerPosX][playerPosY];
    if (direction.equals("N")) {
      curr = goShootByDistance(distance, curr, 0);
    }
    if (direction.equals("S")) {
      curr = goShootByDistance(distance, curr, 1);
    }
    if (direction.equals("W")) {
      curr = goShootByDistance(distance, curr, 2);
    }

    if (direction.equals("E")) {
      curr = goShootByDistance(distance, curr, 3);
    }
    if (curr == null) {
      alert = "You didn't shoot to the wumpus!";
      System.out.println(alert);
    } else if (curr.isWumpus) {
      isGameOver = true;
      isShootSuccess = true;
      arrows--;
      alert = "Hee hee hee, you got the wumpus! Next time you won't be so lucky!";
      System.out.println(alert);
    } else {
      alert = "You didn't shoot to the wumpus!";
      System.out.println(alert);
      arrows--;
      if (arrows <= 0) {
        isGameOver = true;
        alert = "You do not have any arrow to shoot! Game Over.";
        System.out.println(alert);
      }
    }
  }

  /**
   * A helper method for shoot that get the target cell to shoot after assigning a distance and
   * direction.
   * @param distance Number of caves.
   * @param curr The current cell.
   * @param flag The direction flag.
   * @return The target cell to shoot.
   */
  private Cell goShootByDistance(int distance, Cell curr, int flag) {
    for (int i = 0; i < distance; i++) {
      Cell prev = curr;
      if (flag == 1) {
        if (curr.getDownCell() == null) {
          arrows--;
          return null;
        }
        curr = curr.getDownCell();
        flag = getFlag(prev, curr);
      } else if (flag == 0) {
        if (curr.getUpCell() == null) {
          arrows--;
          return null;
        }
        curr = curr.getUpCell();
        flag = getFlag(prev, curr);
      } else if (flag == 2) {
        if (curr.getLeftCell() == null) {
          arrows--;
          return null;
        }
        curr = curr.getLeftCell();
        flag = getFlag(prev, curr);
      } else if (flag == 3) {
        if (curr.getRightCell() == null) {
          arrows--;
          return null;
        }
        curr = curr.getRightCell();
        flag = getFlag(prev, curr);
      }
    }
    return curr;
  }

  /**
   * Get the direction flag by moving from the last cell to the current cell.
   * @param prev The cell in the last step.
   * @param curr The current cell.
   * @return The flag indicates direction.
   */
  private int getFlag(Cell prev, Cell curr) {
    int flag = 0;
    if (curr.getUpCell() == prev) {
      flag = 1;
    }
    if (curr.getDownCell() == prev) {
      flag = 0;
    }
    if (curr.getLeftCell() == prev) {
      flag = 3;
    }
    if (curr.getRightCell() == prev) {
      flag = 2;
    }
    return flag;
  }

  /**
   * The player move in tunnel and update the player's location when the player get out of the
   * tunnel.
   */
  private void moveInTunnel(int flag) {
    int playerPosX = currPlayer.getPlayerLocation()[0];
    int playerPosY = currPlayer.getPlayerLocation()[1];
    while (maze[playerPosX][playerPosY].isTunnel) {
      if (maze[playerPosX][playerPosY].getDownCell() != null && flag != 0) {
        goDown();
        flag = 1;
      } else if (maze[playerPosX][playerPosY].getUpCell() != null && flag != 1) {
        goUp();
        flag = 0;
      } else if (maze[playerPosX][playerPosY].getLeftCell() != null && flag != 3) {
        goLeft();
        flag = 2;
      } else if (maze[playerPosX][playerPosY].getRightCell() != null && flag != 2) {
        goRight();
        flag = 3;
      } else {
        break;
      }
    }
    movehelper(maze[playerPosX][playerPosY], flag);
  }

  public String getAlert() {
    return alert;
  }

  public int[] getPlayerPosition(int flag) {
    Player player = null;
    if (flag == 1) {
      player = player1;
    } else {
      player = player2;
    }
    int[] position = new int[2];
    position[0] = player.getPlayerLocation()[0];
    position[1] = player.getPlayerLocation()[1];
    return position;
  }

  @Override
  public Cell getCurrCell() {
    int[] pos = currPlayer.getPlayerLocation();
    return maze[pos[0]][pos[1]];
  }

}
