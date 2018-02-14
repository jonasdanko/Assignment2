import java.util.ArrayList;
import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems.
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough
 *  appropriate Getters.
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {


    // ADD YOUR INSTANCE VARIABLES HERE

    private int widthOfGame;
    private int heigthOfGame;
    private int numberOfMines;
    private int numSteps;
    private int numberUncovered;
    private DotInfo[][] model;
    private Random generator;

    /**
     * Constructor to initialize the model to a given size of board.
     *
     * @param width
     *            the width of the board
     *
     * @param height
     *            the heigth of the board
     *
     * @param numberOfMines
     *            the number of mines to hide in the board
     */


    public GameModel(int width, int height, int numberOfMines) {

        // ADD YOU CODE HERE
        widthOfGame = width;
        heigthOfGame = height;
        this.numberOfMines = numberOfMines;
        model = new DotInfo[height][width];

    }



    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up .
     */
    public void reset(){
        // ADD YOU CODE HERE

        // initialize array of DotInfo's
        for (int i=0 ; i<heigthOfGame ; ++i){
            for (int j = 0 ; j<widthOfGame ; ++j){
                model[i][j] = new DotInfo(i,j);
            }
        }

        //initialize mines for DotInfo's
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0 ; i<heigthOfGame ; ++i) {
            for (int j = 0; j < widthOfGame; ++j) {
                list.add((i*100)+j);
            }
        }
        int listSize = list.size();
        for (int i = 0 ; i<numberOfMines ; ++i){
            int choice = (int) (Math.random() * list.size());
            model[list.get(choice) / 100][list.get(choice) % 100].setMined();
            list.remove(choice);
        }

        //initialize neighbour count
        for (int i = 0 ; i<heigthOfGame ; ++i){
            for (int j = 0 ; j<widthOfGame ; ++j) {
                if (model[i][j].isMined() == false) {
                    int count = 0;
                    if (i > 0 && j > 0 && model[i - 1][j - 1].isMined() == true) {
                        ++count;
                    }
                    if (i > 0 && model[i-1][j].isMined() == true) {
                        ++count;
                    }

                    if (i > 0 && j<19 && model[i - 1][j + 1].isMined() == true) {
                        ++count;
                    }
                    if (j > 0 && model[i][j - 1].isMined() == true) {
                        ++count;
                    }
                    if (j<19 && model[i][j + 1].isMined() == true) {
                        ++count;
                    }
                    if (i<11 && j > 0 && model[i + 1][j - 1].isMined() == true) {
                        ++count;
                    }
                    if (i<11 && model[i + 1][j].isMined() == true) {
                        ++count;
                    }
                    if (i<11 && j<19 && model[i + 1][j + 1].isMined() == true) {
                        ++count;
                    }

                    model[i][j].setNeighbooringMines(count);
                }
            }numSteps = 0;
        }



    }


    /**
     * Getter method for the heigth of the game
     *
     * @return the value of the attribute heigthOfGame
     */
    public int getHeigth(){

        // ADD YOU CODE HERE
        return heigthOfGame;

    }

    /**
     * Getter method for the width of the game
     *
     * @return the value of the attribute widthOfGame
     */
    public int getWidth(){

        // ADD YOU CODE HERE
        return widthOfGame;

    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isMined(int i, int j){

        // ADD YOU CODE HERE
        if (model[i][j].isMined()==true){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * returns true if the dot  at location (i,j) has
     * been clicked, false otherwise
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean hasBeenClicked(int i, int j){

        // ADD YOU CODE HERE
        return model[i][j].hasBeenClicked();

    }

    /**
     * returns true if the dot  at location (i,j) has zero mined
     * neighboor, false otherwise
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isBlank(int i, int j){

        // ADD YOU CODE HERE
        if (model[i][j].getNeighbooringMines()==0){
            return true;
        }
        else{
            return false;
        }

    }
    /**
     * returns true if the dot is covered, false otherwise
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isCovered(int i, int j){

        // ADD YOU CODE HERE
        if(model[i][j].isCovered()==true){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * returns the number of neighbooring mines os the dot
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */
    public int getNeighbooringMines(int i, int j){

        // ADD YOU CODE HERE
        return model[i][j].getNeighbooringMines();

    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void uncover(int i, int j){

        // ADD YOU CODE HERE
        model[i][j].uncover();

    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void click(int i, int j){

        // ADD YOU CODE HERE
        model[i][j].click();

    }
    /**
     * Uncover all remaining covered dot
     */
    public void uncoverAll(){

        // ADD YOU CODE HERE
        for (int i = 0 ; i<heigthOfGame ; ++i){
            for (int j = 0 ; j<widthOfGame ; ++j){
                model[i][j].uncover();
            }
        }

    }



    /**
     * Getter method for the current number of steps
     *
     * @return the current number of steps
     */
    public int getNumberOfSteps(){

        // ADD YOU CODE HERE
        return numSteps;

    }



    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */
    public DotInfo get(int i, int j) {

        // ADD YOU CODE HERE
        return model[i][j];

    }


    /**
     * The metod <b>step</b> updates the number of steps. It must be called
     * once the model has been updated after the payer selected a new square.
     */
    public void step(){

        // ADD YOU CODE HERE
        ++numSteps;

    }

    /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){

        // ADD YOU CODE HERE
        //NEED TO IMPLEMENT
        return false;

    }


    /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){

        // ADD YOU CODE HERE
        String s = "";
        int count = 0;
        for (int i=0 ; i<heigthOfGame ; ++i){
            for (int j = 0 ; j <widthOfGame ; ++j){
                if(model[i][j].isMined()){
                    s = s + " [*] ";
                    ++count;
                }
                if(model[i][j].isMined()==false){
                    s = s + " [" + model[i][j].getNeighbooringMines() + "] ";
                    ++count;
                }
                if (count == 20){
                    s = s + "\n";
                    count = 0;
                }
            }
        }
        return s;

    }
}
