package controller;

import java.io.IOException;

/**
 * The controller for the MazeGame interface, which enables users to do the move operation to move
 * in the maze as well as the shoot operation to shoot towards to a cave that may have.
 */
public interface Controller {
  /**
   * Generate the maze by input the dimension of the maze, maze type, and player's start position.
   */
  public void createMaze();

  /**
   * @throws IOException if an I/O error occurs.
   */
  public void start() throws IOException;
}
