import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out an instance of  <b>BoardView</b> (the actual game) and
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

    // ADD YOUR INSTANCE VARIABLES HERE
    private GameModel gameModel;
    private DotButton[][] board;
    private javax.swing.JLabel nbreOfStepsLabel;

    /**
     * Constructor used for initializing the Frame
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {

        // ADD YOU CODE HERE
        super("Minesweeper");
        this.gameModel = gameModel;
        board = new DotButton[gameModel.getHeigth()][gameModel.getWidth()];
        for (int i = 0 ; i<gameModel.getHeigth() ; ++i){
            for (int j = 0 ; j<gameModel.getWidth() ; ++j){
                board[i][j] = new DotButton(j, i, 11);
                board[i][j].addActionListener(gameController);

            }
        }

        JPanel middle = new JPanel();
        middle.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(12, 20));
        for(int i = 0 ; i<gameModel.getHeigth() ; ++i){
            for (int j = 0 ; j<gameModel.getWidth() ; ++j){
               buttons.add(board[i][j]);
            }
        }
        middle.add(buttons, BorderLayout.CENTER);
        middle.setBorder(new EmptyBorder(20,20,20,20));

        JPanel stuff = new JPanel();
        stuff.setLayout(new FlowLayout());
        nbreOfStepsLabel = new JLabel("Number of steps: " + gameModel.getNumberOfSteps());
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(gameController);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(gameController);


        stuff.add(nbreOfStepsLabel);
        stuff.add(Box.createHorizontalStrut(20));
        stuff.add(resetButton);
        stuff.add(Box.createHorizontalStrut(20));
        stuff.add(quitButton);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(middle, BorderLayout.CENTER);
        content.add(stuff, BorderLayout.SOUTH);
        add(content);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.gray);
        setSize(700,500);
        setVisible(true);
        setResizable(false);
        validate();





    }

    /**
     * update the status of the board's DotButton instances based
     * on the current game model, then redraws the view
     */

    public void update(){

        // ADD YOU CODE HERE
        nbreOfStepsLabel.setText("Number of steps: " + gameModel.getNumberOfSteps());
        for(int i = 0 ; i< board.length ; ++i){
            for (int j  = 0 ; j<board[0].length ; ++j){
                if ( gameModel.isCovered(i, j)==false){
                    int iconNumber = getIcon(i,j);
                    System.out.println();
                    System.out.println(iconNumber);
                    board[i][j] = new DotButton(j,i,iconNumber);
                    board[i][j].setIcon(new ImageIcon("Minesweeper_0.png"));

                }
            }
        }
        this.repaint();



    }

    /**
     * returns the icon value that must be used for a given dot
     * in the game
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */
    private int getIcon(int i, int j){

        // ADD YOU CODE HERE
        if (gameModel.isCovered(i, j)==true){
            return 11;
        }
        else {
            return gameModel.getNeighbooringMines(i,j);
        }

    }


}
