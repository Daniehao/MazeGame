package controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import game.MazeGame;

/**
 * A controller for the maze game. This controller works with a Readable and Appendable object. It
 * has been designed to accept a sequence of multiple inputs from the Readable object.
 */
public class ControllerImpl implements Controller {
  private final Readable in;
  private final Appendable out;

  /**
   * Constructor for the ControllerImpl class.
   *
   * @param in  The input stream.
   * @param out The output strem.
   */
  public ControllerImpl(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public void start(MazeGame game) throws IOException, IllegalArgumentException {
    Objects.requireNonNull(game);
    String direction;
    int distance;
    try (Scanner scan = new Scanner(this.in)) {
      while (!game.getGameOver()) {
        System.out.println("Shoot or Move? ");
        switch (scan.next()) {
          case "Shoot":
            System.out.println("Towards direction(E/W/N/S)? ");
            direction = scan.next();
            System.out.println("No. of caves? ");
            distance = scan.nextInt();
            game.shoot(direction, distance);
            this.out.append(game.getShootRes());
            break;
          case "Move":
            System.out.println("Where to? ");
            direction = scan.next();
            game.move(direction);
            this.out.append(game.getPlayerLocation());
            this.out.append(" ");
            break;
          case "Quit":
            return;
          default:
            throw new IllegalStateException("ERROR: Input string is not Shoot or Move");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
