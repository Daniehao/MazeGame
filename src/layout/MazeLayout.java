package layout;

import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

import javax.swing.*;

/**
 * The class for the view of the MazeGame.
 */
public class MazeLayout extends JFrame implements GameView {
  private JButton commandButton, quitButton;
  private MazePanel mazePanel;
  private JScrollPane scrollPane;
  private JPanel buttonPanel;
  private transient Consumer<String> commandCallback;

  /** Default constructor. */
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
    mazeGraph.setLayout(new GridLayout(2,3));
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

  ImageIcon grassIcon = new ImageIcon("images/grass_tile.jpg");
  JPanel panel = new JPanel(new GridLayout(haps,snaps,0,0));

  JLabel labels[] = new JLabel[(haps*snaps)];

for (int i =  0; i < haps*snaps; i++)
  {
    labels[i] = new JLabel(grassIcon );
    panel.add(labels[i]);
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
