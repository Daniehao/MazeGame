package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.*;


public class MazeMenu extends JFrame implements MenuView {
  static final String gapPlayerList[] = {"1", "2"};
  static final String gapDiffList[] = {"easy mode", "medium mode", "hard mode"};
  private JButton quitButton;
  private MazePanel menuPanel;
  JTextField rowsTextBox;
  JTextField colsTextBox;
  JTextField wallsTextBox;
  JComboBox playersComboBox;
  JComboBox difficultyComboBox;
  Button startButton;
  Button startSameButton;
  private transient Consumer<String> commandCallback;

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

    controls.setLayout(new GridLayout(5,2));
    controls.add(new Label("Input maze rows: "));
    controls.add(rowsTextBox);
    controls.add(new Label("Input maze columns: "));
    controls.add(colsTextBox);
    controls.add(new Label("Input the remaining walls: "));
    controls.add(wallsTextBox);
    controls.add(new Label("Input the number of players (1 or 2): "));
    controls.add(playersComboBox);
    controls.add(new Label("Input game difficulty: "));
    controls.add(difficultyComboBox);
    this.add(controls, BorderLayout.CENTER);

    JPanel commendPanel = new JPanel();
    startButton = new Button("Start New");
    commendPanel.add(startButton);
    startButton.setActionCommand("Start New Game Button");
    startSameButton = new Button("Start Same Game");
    commendPanel.add(startSameButton);
    startSameButton.setActionCommand("Start Same Game Button");
    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    commendPanel.add(quitButton);
    commendPanel.setPreferredSize(new Dimension(500, 50));
    this.add(commendPanel, BorderLayout.SOUTH);
  }

  public void initGaps() {
    rowsTextBox = new JTextField(5);
    colsTextBox = new JTextField(5);
    wallsTextBox = new JTextField(5);
    playersComboBox = new JComboBox(gapPlayerList);
    difficultyComboBox = new JComboBox(gapDiffList);
  }

  public int getRows() {
    return Integer.valueOf(rowsTextBox.getText());
  }

  public int getCols() {
    return Integer.valueOf(colsTextBox.getText());
  }

//  public void addComponentsToPane(final Container pane) {
//    initGaps();
//    final JPanel menuPane = new JPanel();
//    JPanel controls = new JPanel();
//    controls.setLayout(new GridLayout(4,2));
//    menuPane.add(new Label("MAZE GAME MENU"));
//    controls.add(new Label("Input maze rows: "));
//    controls.add(rowsComboBox);
//    controls.add(new Label("Input maze columns: "));
//    controls.add(colsComboBox);
//    controls.add(new Label("Input the number of players (1 or 2): "));
//    controls.add(playersComboBox);
//    controls.add(new Label("Input game difficulty: "));
//    controls.add(difficultyComboBox);
//  }

  private static void createAndShowGUI() {
    //Create and set up the window.
    MazeMenu frame = new MazeMenu();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Set up the content pane.
//    frame.addComponentsToPane(frame.getContentPane());
    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void setListener(ActionListener listener) {
    startButton.addActionListener(listener);
    startSameButton.addActionListener(listener);
  }

  @Override
  public void showErrorMessage(java.lang.String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setCommandCallback(Consumer<java.lang.String> callback) {

  }

  @Override
  public void display() {
    setVisible(true);
  }

  public static void main(String[] args) {
    /* Use an appropriate Look and Feel */
    try {
      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    } catch (UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
    } catch (InstantiationException ex) {
      ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    /* Turn off metal's use of bold fonts */
    UIManager.put("swing.boldMetal", Boolean.FALSE);

    //Schedule a job for the event dispatch thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
}
