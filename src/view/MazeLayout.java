package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import model.Cell;

/**
 * The class for the view of the MazeGame.
 */
public class MazeLayout extends JFrame implements GameView {
  private MazePanel alertPanel;
  private MazePanel mazePanel;
  private MazePanel controlPanel;
  private JScrollPane scrollPane;
  private JButton quitButton;
  private JButton startSameButton;
  private JButton moveUp;
  private JButton moveDown;
  private JButton moveLeft;
  private JButton moveRight;
  private JButton shootUp;
  private JButton shootDown;
  private JButton shootLeft;
  private JButton shootRight;
  private JTextField shootDistance;
  private JButton applyMoveButton;
  private JButton applyShootButton;
  private String moveDirection;
  private String shootDirection;
  private Label currRoundLabel;
  private Label alertLabel;
  private JLabel[][] labelArray;
  private int rows;
  private int cols;
  private Map<String, String> alertPicMap;

  /**
   * Default constructor.
   */
  public MazeLayout() {
    super();
    initGaps();
    this.setTitle("Maze Game!");
    this.setSize(800, 800);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
  }

  public void addComponents(int rows, int cols, int playerNum) {
    this.rows = rows;
    this.cols = cols;
    // maze panel
    mazePanel = new MazePanel();
    mazePanel.setPreferredSize(new Dimension(500, 500));
    mazePanel.setLayout(new GridLayout(rows, cols));
    initImageArray();
    scrollPane = new JScrollPane(mazePanel);
    this.add(scrollPane, BorderLayout.CENTER);

    // move
    controlPanel = new MazePanel();
    controlPanel.setLayout(new GridLayout(4, 6));
    controlPanel.add(new Label("Move Direction: "));
    controlPanel.add(moveUp);
    moveUp.setActionCommand("Move up");
    controlPanel.add(moveDown);
    moveDown.setActionCommand("Move down");
    controlPanel.add(moveLeft);
    moveLeft.setActionCommand("Move left");
    controlPanel.add(moveRight);
    moveRight.setActionCommand("Move right");

    // shoot
    controlPanel.add(new Label("Shoot Direction: "));
    controlPanel.add(shootUp);
    shootUp.setActionCommand("Shoot up");
    controlPanel.add(shootDown);
    shootDown.setActionCommand("Shoot down");
    controlPanel.add(shootLeft);
    shootLeft.setActionCommand("Shoot left");
    controlPanel.add(shootRight);
    shootRight.setActionCommand("Shoot right");
    controlPanel.add(new Label("Shoot Distance: "));
    controlPanel.add(shootDistance);
    controlPanel.add(new Label(" "));
    controlPanel.add(new Label(" "));
    controlPanel.add(new Label(" "));

    //quit
    controlPanel.add(applyMoveButton);
    applyMoveButton.setActionCommand("Move");

    controlPanel.add(applyShootButton);
    applyShootButton.setActionCommand("Shoot");
    controlPanel.add(new Label(" "));

    controlPanel.add(startSameButton);
    startSameButton.setActionCommand("Start Same Game");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    controlPanel.add(quitButton);
    this.add(controlPanel, BorderLayout.SOUTH);
  }

  private void initImageArray() {
    labelArray = new JLabel[rows][cols];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        ImageIcon icon = new ImageIcon("res/images/empty.png");
        labelArray[i][j] = new JLabel(icon);
        mazePanel.add(labelArray[i][j]);
      }
    }
  }

  public void setAlertPanel(String alert, int flag) {
    alertPanel = new MazePanel();
    alertPanel.setPreferredSize(new Dimension(200, 50));
    currRoundLabel = new Label("Player " + flag + "'s Round");
    alertPanel.add(currRoundLabel);
    alertLabel = new Label(alert);
    alertPanel.add(alertLabel);
    this.add(alertPanel, BorderLayout.NORTH);
  }

  public void changeAlertPanel(String alert, int flag) {
    currRoundLabel.setText("Player " + flag + "'s Round");
    alertLabel.setText(alert);
  }

  @Override
  public void setListener(ActionListener listener) {
    startSameButton.addActionListener(listener);
    moveUp.addActionListener(listener);
    moveDown.addActionListener(listener);
    moveLeft.addActionListener(listener);
    moveRight.addActionListener(listener);
    shootUp.addActionListener(listener);
    shootDown.addActionListener(listener);
    shootLeft.addActionListener(listener);
    shootRight.addActionListener(listener);
    shootDistance.addActionListener(listener);
    applyMoveButton.addActionListener(listener);
    applyShootButton.addActionListener(listener);
  }

  @Override
  public void showPlayer(int flag, int x, int y) {
    String source = "";
    if (flag == 1) {
      source = "res/images/player.png";
    } else {
      source = "res/images/player2.png";
    }
    ImageIcon icon = new ImageIcon(source);
    labelArray[x][y] = new JLabel(icon);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        mazePanel.add(labelArray[i][j]);
      }
    }
  }

  @Override
  public void changeViewByMove(int[] prevPos, String prevCellStatus, int[] newPos) {

  }

  private void initGaps() {
    moveUp = new JButton("Up");
    moveDown = new JButton("Down");
    moveLeft = new JButton("Left");
    moveRight = new JButton("Right");
    shootUp = new JButton("Up");
    shootDown = new JButton("Down");
    shootLeft = new JButton("Left");
    shootRight = new JButton("Right");
    shootDistance = new JTextField(5);
    applyMoveButton = new JButton("Move");
    applyShootButton = new JButton("Shoot");
    startSameButton = new JButton("Start Same Game");
    quitButton = new JButton("Quit");
    alertPicMap = new HashMap<>();
  }

  public String getShootDistance() {
    return shootDistance.getText();
  }

  @Override
  public void setMoveDirection(String direction) {
    this.moveDirection = direction;
  }

  @Override
  public String getMoveDirection() {
    return moveDirection;
  }

  @Override
  public void setShootDirection(String direction) {
    this.shootDirection = direction;
  }

  @Override
  public String getShootDirection() {
    return null;
  }


  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);

  }

  private void generateAlertPicMap() {
    alertPicMap.put("close to wumpus", "res/images/wumpus-nearby.png");
    alertPicMap.put("close to pit", "res/images/slime-pit-nearby.png");
    alertPicMap.put("is pit", "res/images/slime-pit.png");
    alertPicMap.put("has bat", "res/images/superbat.png");
    alertPicMap.put("is tunnel vertical", "res/images/hallway-v.png");
    alertPicMap.put("is tunnel horizontal", "res/images/hallway-h.png");
    alertPicMap.put("is tunnel 1", "res/images/hallway-up-left.png");
    alertPicMap.put("is tunnel 2", "res/images/hallway-down-left.png");
    alertPicMap.put("is tunnel 3", "res/images/hallway-down-right.png");
    alertPicMap.put("is tunnel 4", "res/images/hallway-up-right.png");
    alertPicMap.put("is room 1 up", "res/images/roombase-1-up.png");
    alertPicMap.put("is room 1 down", "res/images/roombase-1-down.png");
    alertPicMap.put("is room 1 left", "res/images/roombase-1-left.png");
    alertPicMap.put("is room 1 right", "res/images/roombase-1-right.png");
    alertPicMap.put("is room 3 1", "res/images/roombase-3-up-down-left.png");
    alertPicMap.put("is room 3 2", "res/images/roombase-3-up-left-right.png");
    alertPicMap.put("is room 3 3", "res/images/roombase-3-up-down-right.png");
    alertPicMap.put("is room 3 4", "res/images/roombase-3-down-left-right.png");
    alertPicMap.put("is room 4", "res/images/roombase-4.png");
  }

  public void createAndShowGUI() {
    //Create and set up the window.
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Set up the content pane.
    //Display the window.
    this.pack();
    this.setVisible(true);
  }


//  public static void main(String[] args) {
//    /* Use an appropriate Look and Feel */
//    try {
//      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//    } catch (UnsupportedLookAndFeelException ex) {
//      ex.printStackTrace();
//    } catch (IllegalAccessException ex) {
//      ex.printStackTrace();
//    } catch (InstantiationException ex) {
//      ex.printStackTrace();
//    } catch (ClassNotFoundException ex) {
//      ex.printStackTrace();
//    }
//    /* Turn off metal's use of bold fonts */
//    UIManager.put("swing.boldMetal", Boolean.FALSE);
//
//    //Schedule a job for the event dispatch thread:
//    //creating and showing this application's GUI.
//    javax.swing.SwingUtilities.invokeLater(new Runnable() {
//      public void run() {
//        createAndShowGUI();
//      }
//    });
//  }
}
