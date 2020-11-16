package controller;

import java.io.IOException;

import game.MazeGame;

/**
 * The controller for the MazeGame interface, which enables users to do the move operation to move
 * in the maze as well as the shoot operation to shoot towards to a cave that may have.
 */
public interface Controller {
  /**
   * Motivate the start of the maze game's controller.
   *
   * @throws IOException if an I/O error occurs.
   */
  public void start(MazeGame game) throws IOException;
}
