package controller;

import java.io.IOException;

import game.MazeGame;

/**
 * The controller for the MazeGame interface, which enables users to do the move operation to move
 * in the maze as well as the shoot operation to shoot towards to a cave that may have.
 */
public interface Controller {
  /**
   * @param game the maze game that shoud be run.
   *
   * @throws IOException if an I/O error occurs.
   */
  public void start(MazeGame game) throws IOException;
}
