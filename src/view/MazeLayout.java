package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.*;

import model.Cell;

/**
 * The class for the view of the MazeGame.
 */
public class MazeLayout extends JFrame implements GameView {

  private JButton commandButton, quitButton;
  private MazePanel mazePanel;
  private JScrollPane scrollPane;
  private JPanel buttonPanel;
  private transient Consumer<String> commandCallback;

  /**
   * Default constructor.
   */
  public MazeLayout() {
    super();
    this.setTitle("Maze Game!");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // use a borderlayout with drawing panel in center and button panel in south
    this.setLayout(new BorderLayout());
    mazePanel = new MazePanel();
    mazePanel.setPreferredSize(new Dimension(500, 500));
    scrollPane = new JScrollPane(mazePanel);
    this.add(scrollPane, BorderLayout.CENTER);

    // button panel
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);

    // quit button
    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    buttonPanel.add(quitButton);

    commandCallback = null;

    this.pack();
  }

  public void addComponentsToPane(final Container mazePanel) {
    JPanel mazeGraph = new JPanel();
    mazeGraph.setLayout(new GridLayout(2, 3));
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setCommandCallback(Consumer<String> callback) {
    commandCallback = callback;
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);

  }

  @Override
  public void buildMaze(Cell[] cells) {
    ImageIcon mazeIcon = null;
    for (Cell element : cells) {
      if (element.getIsRoom()) {
        int roomNum = element.geRoomDoors();
        if (roomNum == 1) {
          if (element.getUpCell() != null) {
            mazeIcon = new ImageIcon("../../res/images/roombase-1-up.png");
          }
          if (element.getDownCell() != null) {
            mazeIcon = new ImageIcon("../../res/images/roombase-1-down.png");
          }
          if (element.getLeftCell() != null) {
            mazeIcon = new ImageIcon("../../res/images/roombase-1-left.png");
          }
          if (element.getRightCell() != null) {
            mazeIcon = new ImageIcon("../../res/images/roombase-1-right.png");
          }
        } else if (roomNum == 3) {
          if (element.getUpCell() != null && element.getLeftCell() != null && element.getDownCell() != null) {
            mazeIcon = new ImageIcon("../../res/images/roombase-3-up-down-left.png");
          }
          if (element.getUpCell() != null && element.getLeftCell() != null && element.getRightCell() != null) {
            mazeIcon = new ImageIcon("../../res/images/roombase-3-up-left-right.png");
          }
          if (element.getUpCell() != null && element.getRightCell() != null && element.getDownCell() != null) {
            mazeIcon = new ImageIcon("../../res/images/roombase-3-up-down-right.png");
          }
          if (element.getDownCell() != null && element.getLeftCell() != null && element.getRightCell() != null) {
            mazeIcon = new ImageIcon("../../res/images/roombase-1-down-left-right.png");
          }
        } else {
          mazeIcon = new ImageIcon("../../res/images/roombase-4.png");
        }
      } else if (element.getIsTunnel()) {
        if (element.getLeftCell() != null && element.getRightCell() != null) {
          mazeIcon = new ImageIcon("../../res/images/hallway-h.png");
        }
        if (element.getLeftCell() != null && element.getUpCell() != null) {
          mazeIcon = new ImageIcon("../../res/images/hallway-up-left.png");
        }
        if (element.getLeftCell() != null && element.getDownCell() != null) {
          mazeIcon = new ImageIcon("../../res/images/hallway-down-left.png");
        }
        if (element.getUpCell() != null && element.getDownCell() != null) {
          mazeIcon = new ImageIcon("../../res/images/hallway-v.png");
        }
        if (element.getDownCell() != null && element.getRightCell() != null) {
          mazeIcon = new ImageIcon("../../res/images/hallway-down-right.png");
        }
        if (element.getUpCell() != null && element.getRightCell() != null) {
          mazeIcon = new ImageIcon("../../res/images/hallway-up-right.png");
        }
      }

//      ImageIcon grassIcon = new ImageIcon("images/grass_tile.jpg");
      JPanel panel = new JPanel(new GridLayout(3, 5, 0, 0));

      JLabel labels[] = new JLabel[(3 * 5)];

      for (
              int i = 0;
              i < 3 * 5; i++) {
        labels[i] = new JLabel(mazeIcon);
        panel.add(labels[i]);
      }
    }
  }

  private static void createAndShowGUI() {
    //Create and set up the window.
    MazeLayout frame = new MazeLayout();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //Set up the content pane.
    //Display the window.
    frame.pack();
    frame.setVisible(true);
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
