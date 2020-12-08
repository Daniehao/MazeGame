package game.controller;

import java.io.IOException;

import game.model.MazeGame;

/**
 * The controller class for the text version of maze game.
 */
public interface Controller {
  /**
   * Motivate the start of the maze game's game.controller.
   *
   * @throws IOException if an I/O error occurs.
   */
  public void start(MazeGame game) throws IOException;
}
