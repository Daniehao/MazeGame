import controller.ActionController;
import model.MazeGame;
import model.MazeGameImpl;
import view.GameView;
import view.MazeLayout;
import view.MazeMenu;
import view.MenuView;

public class Main {
  public static void main(String[] args) {
    // Create the model
    MazeGame model = new MazeGameImpl();
    // Create the view
    MenuView view = new MazeMenu();
    GameView mazeView = new MazeLayout();
    // Create the controller with the model and the view
    new ActionController(model, view, mazeView);
  }
}
