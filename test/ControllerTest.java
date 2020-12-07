import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import controller.Controller;
import controller.ControllerImpl;
import model.MazeGameImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test for the Controller interface which checks if the controller could successfully execute the
 * operations when player moves in the maze or shoot at a wumpus.
 */
public class ControllerTest {
//
//  @Test
//  public void testStartMove() throws Exception {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("Move N Move E");
//    Controller controller = new ControllerImpl(in, out);
//    controller.start(new MazeGameImpl(3, 4, 6, true, false,
//            0.2, 0.3, 3, 1));
//    assertEquals("You are in cave (1, 1). Tunnels lead to the E, W, N, S You are in " +
//            "cave (0, 3). Tunnels lead to the S ", out.toString());
//  }
//
//  @Test(expected = Exception.class)
//  public void testStartMoveToWall() throws Exception {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("Move E Move E");
//    Controller controller = new ControllerImpl(in, out);
//    controller.start(new MazeGameImpl(3, 4, 6, true, false,
//            0.2, 0.3, 3, 1));
//    assertEquals("You are in cave (1, 1). Tunnels lead to the E, W, N, S You are in " +
//            "cave (0, 3). Tunnels lead to the S ", out.toString());
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testStartMoveInvalid() throws Exception {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("Move abcdefs Move E");
//    Controller controller = new ControllerImpl(in, out);
//    controller.start(new MazeGameImpl(3, 4, 6, true, false,
//            0.2, 0.3, 3, 1));
//  }
//
//  @Test
//  public void testStartShootSuccess() throws Exception {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("Move N Shoot E 1");
//    Controller controller = new ControllerImpl(in, out);
//    controller.start(new MazeGameImpl(3, 4, 6, true, false,
//            0.2, 0.3, 3, 1));
//    assertEquals("You are in cave (1, 1). Tunnels lead to the E, W, N, S " +
//            "You shooted to the wumpus successfully!", out.toString());
//  }
//
//  @Test(expected = IllegalStateException.class)
//  public void testStartShootIllegal() throws Exception {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("Move N Shoot E 10 E 1");
//    Controller controller = new ControllerImpl(in, out);
//    controller.start(new MazeGameImpl(3, 4, 6, true, false,
//            0.2, 0.3, 3));
//  }
//
//  @Test(expected = IllegalArgumentException.class)
//  public void testStartShootIllegalDirection() throws Exception {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("Move N Shoot AAA 10 E 1");
//    Controller controller = new ControllerImpl(in, out);
//    controller.start(new MazeGameImpl(3, 4, 6, true, false,
//            0.2, 0.3, 3));
//  }
//
//  @Test
//  public void testStartShootTwice() throws Exception {
//    StringBuffer out = new StringBuffer();
//    Reader in = new StringReader("Move N Shoot E 10 Shoot E 1");
//    Controller controller = new ControllerImpl(in, out);
//    controller.start(new MazeGameImpl(3, 4, 6, true, false,
//            0.2, 0.3, 3));
//    assertEquals("You are in cave (1, 1). Tunnels lead to the E, W, N, S You didn't " +
//            "shooted to the wumpus, and you have 2 arrows remains.You shooted to the wumpus " +
//            "successfully!", out.toString());
//  }
}
