import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    // ADD YOUR INSTANCE VARIABLES HERE
    private int widthofBoard, heightOfBoard, numberOfMines;
    GameView view;
    GameModel model;

    /**
     * Constructor used for initializing the controller. It creates the game's view
     * and the game's model instances
     *
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {

        // ADD YOU CODE HERE
        this.widthofBoard = width;
        this.heightOfBoard = height;
        this.numberOfMines = numberOfMines;
        model = new GameModel(width, height, numberOfMines);
        view = new GameView(model, this);
        model.reset();
        System.out.println(model);



    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {

        // ADD YOU CODE HERE
        if (e.getSource() instanceof DotButton){
            for(int i = 0 ; i<heightOfBoard ; ++i){
                for (int j =0 ; j<widthofBoard ; ++j){
                    if (((DotButton) e.getSource()).getRow()==i && ((DotButton) e.getSource()).getColumn()==j){
                        //-System.out.print("button pressed: "+ i +" " +  j);
                        play(j, i);

                        //((DotButton) e.getSource()).setEnabled(false);
                    }
                }
            }
        }
        else{
            JButton temp = (JButton) e.getSource();
            System.out.println(temp.getText());
            if (temp.getText()=="Reset"){
                reset();
                System.out.println("reset the game!");
            }
            else{
                System.exit(0);

            }
        }
    }

    /**
     * resets the game
     */
    private void reset(){

        // ADD YOU CODE HERE
        model.reset();
        view.update();

        System.out.println(model);

    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares.
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){

        // ADD YOU CODE HERE
        if (! model.hasBeenClicked(heigth, width)){
            model.click(heigth, width);
            model.uncover(heigth, width);
            model.step();
            view.update();
            if (model.isMined(heigth, width)==true){
                System.out.println("GAME OVER");
                for (int i = 0 ; i<heightOfBoard ; i++){
                    for (int j = 0 ; j<widthofBoard ; ++j){

                        model.uncover(i, j);

                    }
                }
                view.update();

                int result = JOptionPane.showConfirmDialog(null, "Ouch, you lost in " + model.getNumberOfSteps() + " steps! \n Would you like to play again?", "BOOM!", 0);
                if(result==JOptionPane.YES_OPTION){
                    model.reset();
                    view.update();
                }
                else{
                    System.exit(0);
                }

            }
            else{
                /*if(model.getNeighbooringMines(width,heigth)==0){
                    GenericArrayStack stack = new GenericArrayStack(9);
                    DotInfo square = model.get(heigth,width);
                    stack.push(square);
                    while (stack.isEmpty()==false){
                        stack.pop();

                        if ( square.getY()> 0 && square.getX() > 0) {
                            model.uncover(heigth-1, width-1);
                            if(model.getNeighbooringMines(heigth-1, width-1)==0){
                                DotInfo case1 = model.get(heigth-1, width-1);
                                stack.push(case1);
                            }
                        }
                        if (square.getX() > 0 ) {
                            model.uncover(heigth-1, width);
                            if(model.getNeighbooringMines(heigth-1, width)==0){
                                DotInfo case2 = model.get(heigth-1, width);
                                stack.push(case2);
                            }
                        }

                        if (square.getX() > 0 && square.getY()<19 ) {
                            model.uncover(heigth-1, width+1);
                            if(model.getNeighbooringMines(heigth-1, width+1)==0){
                                DotInfo case3 = model.get(heigth-1, width+1);
                                stack.push(case3);
                            }
                        }
                        if (square.getY() > 0 ) {
                            model.uncover(heigth, width-1);
                            if(model.getNeighbooringMines(heigth, width-1)==0){
                                DotInfo case4 = model.get(heigth, width-1);
                                stack.push(case4);
                            }
                        }
                        if (square.getY()<19 ) {
                            model.uncover(heigth, width+1);
                            if(model.getNeighbooringMines(heigth, width+1)==0){
                                DotInfo case5 = model.get(heigth, width+1);
                                stack.push(case5);
                            }
                        }
                        if (square.getX()<11 && square.getY() > 0 ) {
                            model.uncover(heigth+1, width-1);
                            if(model.getNeighbooringMines(heigth+1, width-1)==0){
                                DotInfo case6 = model.get(heigth+1, width-1);
                                stack.push(case6);
                            }
                        }
                        if (square.getX()<11 ) {
                            model.uncover(heigth+1, width);
                            if(model.getNeighbooringMines(heigth+1, width)==0){
                                DotInfo case7 = model.get(heigth+1, width);
                                stack.push(case7);
                            }
                        }
                        if (square.getX()<11 && square.getY()<19 ) {
                            model.uncover(heigth+1, width+1);
                            if(model.getNeighbooringMines(heigth+1, width+1)==0){
                                DotInfo case1 = model.get(heigth+1, width+1);
                                stack.push(case1);
                            }
                        }

                    }
                    view.update();
                }*/
                if (model.isFinished()==true){
                    for (int i = 0 ; i<heightOfBoard ; i++){
                        for (int j = 0 ; j<widthofBoard ; ++j){

                            model.uncover(i, j);

                        }
                    }
                    view.update();
                    int result = JOptionPane.showConfirmDialog(null, "Congratulationsyou won in " + model.getNumberOfSteps() + " steps! \n Would you like to play again?", "WON!", 0);
                    if(result==JOptionPane.YES_OPTION){
                        model.reset();
                        view.update();
                    }
                    else{
                        System.exit(0);
                    }

                    System.out.println("YOU WON!");
                    System.out.println("Number of moves: " + model.getNumberOfSteps());
                }


            }
        }

    }

    /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered''
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {


        // ADD YOU CODE HERE


    }



}
