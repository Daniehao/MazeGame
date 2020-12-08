package game.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class implements the MenuView interface, which shows the menu panel and fetch the game
 * setting options that are chosen by the user.
 */
public class MazeMenu extends JFrame implements MenuView {
  static final String gapPlayerList[] = {"1", "2"};
  static final String gapDiffList[] = {"easy mode", "medium mode", "hard mode"};
  static final String wrappingList[] = {"Yes", "No"};
  private JButton quitButton;
  private MazePanel menuPanel;
  JTextField rowsTextBox;
  JTextField colsTextBox;
  JTextField wallsTextBox;
  JComboBox playersComboBox;
  JComboBox wrappingComboBox;
  JComboBox difficultyComboBox;
  JButton startButton;

  /**
   * Default constructor.
   */
  public MazeMenu() {
    super();
    this.setTitle("Maze Game");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    initGaps();
    this.setLayout(new BorderLayout());

    menuPanel = new MazePanel();
    this.add(menuPanel, BorderLayout.NORTH);
    menuPanel.setPreferredSize(new Dimension(300, 50));
    menuPanel.add(new Label("MAZE GAME MENU"));
    JPanel controls = new JPanel();
    controls.setPreferredSize(new Dimension(500, 300));

    controls.setLayout(new GridLayout(6, 2));
    controls.add(new Label("Input maze rows: "));
    controls.add(rowsTextBox);
    controls.add(new Label("Input maze columns: "));
    controls.add(colsTextBox);
    controls.add(new Label("Input the remaining walls: "));
    controls.add(wallsTextBox);
    controls.add(new Label("Input the number of players (1 or 2): "));
    controls.add(playersComboBox);
    controls.add(new Label("Whether the maze is Wrapping: "));
    controls.add(wrappingComboBox);
    controls.add(new Label("Input game difficulty: "));
    controls.add(difficultyComboBox);
    this.add(controls, BorderLayout.CENTER);

    JPanel commendPanel = new JPanel();
    startButton = new JButton("Start New");
    commendPanel.add(startButton);
    startButton.setActionCommand("Start New");
    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    commendPanel.add(quitButton);
    commendPanel.setPreferredSize(new Dimension(500, 50));
    this.add(commendPanel, BorderLayout.SOUTH);
  }

  @Override
  public void msgbox() {
    JOptionPane.showMessageDialog(this, "The input of remaining walls " +
            "is incorrect. Please choose a valid number");
  }

  /**
   * Initialize the components from users' input.
   */
  private void initGaps() {
    rowsTextBox = new JTextField(5);
    colsTextBox = new JTextField(5);
    wallsTextBox = new JTextField(5);
    playersComboBox = new JComboBox(gapPlayerList);
    difficultyComboBox = new JComboBox(gapDiffList);
    wrappingComboBox = new JComboBox(wrappingList);
  }

  /**
   * Get the array of user's input on the maze game.
   *
   * @return The array of user's input.
   */
  public int[] getMazeInput() {
    int[] info = new int[4];
    info[0] = Integer.valueOf(rowsTextBox.getText());
    info[1] = Integer.valueOf(colsTextBox.getText());
    info[2] = Integer.valueOf(wallsTextBox.getText());
    info[3] = Integer.valueOf((String) playersComboBox.getSelectedItem());
    return info;
  }

  @Override
  public boolean getWrapping() {
    boolean rst = wrappingComboBox.getSelectedItem() == "Yes" ? true : false;
    return rst;
  }

  @Override
  public String getDifficulty() {
    return (String) difficultyComboBox.getSelectedItem();
  }

  private static void createAndShowGui() {
    //Create and set up the window.
    MazeMenu frame = new MazeMenu();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void setListener(ActionListener listener) {
    startButton.addActionListener(listener);
  }

  @Override
  public void display() {
    setVisible(true);
  }
}
