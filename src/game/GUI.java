package game;

import gameboardadditions.ResetButtonSmiley;
import gameboardadditions.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.Random;

public class GUI extends JFrame{

    private final byte NUMBER_OF_COWS_AND_ROWS = 8;

    private final byte boxWidth  = 72;
    private   final byte boxHeight = 65;

    public boolean isGameOver     = false;

    private int grannyYagaHouseColumn;
    private int grannyYagaHouseRow;

    private final Random random               = new Random();
    protected final Timer timer              = new Timer();
    protected final ResetButtonSmiley smiley = new ResetButtonSmiley();

    private final int SMILEY_X_CENTER = smiley.getRESET_BUTTON_X() + 40;
    private final int SMILEY_Y_CENTER = smiley.getRESET_BUTTON_Y() + 35;

    private final int[][] mines        = new int[NUMBER_OF_COWS_AND_ROWS][NUMBER_OF_COWS_AND_ROWS];
    private final boolean[][] revealed = new boolean[NUMBER_OF_COWS_AND_ROWS][NUMBER_OF_COWS_AND_ROWS];

    private int lastClickedColumn;
    private int lastClickedRow;
    private int currentClickedColumn;
    private int currentClickedRow;

    private int mouseLocationX = -100;
    private int mouseLocationY = -100;

    public GUI() {

        this.setTitle("Find Yaga's House");
        this.setSize(592, 625);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocation(400, 80);

        blueGreenRedBoxGenerator();
        makeSureYellowBoxIsPlaced();
        placeGrannyYagaHouse();

        Board board = new Board(this);
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

    }

    public byte getBoxWidth() {
        return boxWidth;
    }

    public byte getBoxHeight() {
        return boxHeight;
    }

    public byte getNUMBER_OF_COWS_AND_ROWS() {
        return NUMBER_OF_COWS_AND_ROWS;
    }

    public byte getSpacing() {
        return (byte) 2;
    }

    /**
     * Method that finds the Green boxes and chosen one of them for granny's house
     */
    private void placeGrannyYagaHouse() {

        int randomNumber;

        for (int column = 0; column < NUMBER_OF_COWS_AND_ROWS; column++) {

            for (int row = 0; row < NUMBER_OF_COWS_AND_ROWS; row++) {

                if (mines[column][row] == 2) {

                    randomNumber = numberGeneratorForYellowBoxLocation(random);

                    if (randomNumber <= 2) {

                        grannyYagaHouseColumn = column;
                        grannyYagaHouseRow = row;

                        break;
                    }
                }
            }
        }
    }

    /**
     * Method that generates a random number [0 to 3]
     *
     * @param random Object of Random
     * @return The randomly generated number
     */
    private int numberGeneratorForYellowBoxLocation(Random random) {

        return random.nextInt(4);
    }

    /**
     * Method that calls another methods if the chosen corner is occupied
     */
    private void makeSureYellowBoxIsPlaced() {

        boolean isCornerEmpty = yellowBoxChecker(random);

        while (!isCornerEmpty) {

            numberGeneratorForYellowBoxLocation(random);

            isCornerEmpty = yellowBoxChecker(random);
        }
    }

    /**
     * Method that is created if the corner where yellow box will be placed, is occupied
     *
     * @param random Object of Random
     * @return boolean that tells if the chosen corner for yellow box is empty
     */
    private boolean yellowBoxChecker(Random random) {

        int generatedNumber = numberGeneratorForYellowBoxLocation(random);

        boolean isCornerEmpty = false;

        if (generatedNumber == 0 && mines[0][0] == 3) {

            mines[0][0] = 4;
            isCornerEmpty = true;

        } else if (generatedNumber == 1 && mines[0][7] == 3) {

            mines[0][7] = 4;
            isCornerEmpty = true;

        } else if (generatedNumber == 2 && mines[7][0] == 3) {

            mines[7][0] = 4;
            isCornerEmpty = true;

        } else if (generatedNumber == 3 && mines[7][7] == 3) {

            mines[7][7] = 4;
            isCornerEmpty = true;
        }

        return isCornerEmpty;
    }

    /**
     * Method that gets a random number and places a blue box on the board if statement is true
     */
    private void blueGreenRedBoxGenerator() {

        int  randomNumber;
        byte blueBoxCounter = 1;
        byte greenBoxCounter = 1;

        for (int column = 0; column < NUMBER_OF_COWS_AND_ROWS; column++) {

            for (int row = 0; row < NUMBER_OF_COWS_AND_ROWS; row++) {

                randomNumber = random.nextInt(100);

                if (randomNumber <= 12 && blueBoxCounter <= 5) {

                    mines[column][row] = 1;

                    blueBoxCounter++;

                } else if (randomNumber <= 30 && greenBoxCounter <= 8) {

                    mines[column][row] = 2;

                    greenBoxCounter++;

                } else {

                    mines[column][row] = 3;
                }

                revealed[column][row] = false;
            }
        }
    }

    /**
     * Method that checks if the cursor is inside a box
     *
     * @param column The column of the box that the cursor is in
     * @param row    The row of the box that the cursor is in
     * @return boolean to check if the cursor is inside a box
     */
    public boolean isCursorInBox(int column, int row) {

        final byte spacing = 2;
        return  mouseLocationX >= spacing + column * boxWidth &&
                mouseLocationX < spacing  + column * boxWidth  + boxWidth - 2 * spacing &&
                mouseLocationY >= spacing + row    * boxHeight + boxHeight + 33 &&
                mouseLocationY < spacing  + row    * boxHeight + 33 + boxHeight + boxHeight - 2 * spacing;
    }

    /**
     * Method that returns the column of the box that we clicked in
     *
     * @return The column of the box that we clicked
     */
    public int clickedBoxColumn() {

        for (int column = 0; column < NUMBER_OF_COWS_AND_ROWS; column++) {

            for (int row = 0; row < NUMBER_OF_COWS_AND_ROWS; row++) {

                if (isCursorInBox(column, row)) {

                    return column;
                }
            }
        }
        return -1;
    }

    /**
     * Method that returns the row of the box that we clicked in
     *
     * @return The row of the box that we clicked
     */
    public int clickedBoxRow() {

        for (int column = 0; column < NUMBER_OF_COWS_AND_ROWS; column++) {

            for (int row = 0; row < NUMBER_OF_COWS_AND_ROWS; row++) {

                if (isCursorInBox(column, row)) {

                    return row;
                }
            }
        }
        return -1;
    }


    /**
     * Method that checks if the Yaga's house is found
     */
    public void checkIfEndOfGame() {

        if (mines[grannyYagaHouseColumn][grannyYagaHouseRow] == 4 || mines[grannyYagaHouseColumn][grannyYagaHouseRow] == 1) {

            isGameOver = true;
            smiley.setSmileyHappy(false);
        }
    }

    /**
     * Method that resets the board if player clicked Smiley's nose
     */
    private void isClickedInsideSmileyTrue() {

        if (calculateIfClickedInsideSmiley()) {

            resetBoardWhenClickSmileyFace();
        }
    }

    /**
     * Method that checks if player has clicked inside Smiley
     *
     * @return The difference between clicked and Smiley's location
     */
    private boolean calculateIfClickedInsideSmiley() {

        int clickedCoordinatesDifference;

        clickedCoordinatesDifference = (int) Math.sqrt(Math.abs(mouseLocationX - SMILEY_X_CENTER) * Math.sqrt(Math.abs(mouseLocationX - SMILEY_X_CENTER) +
                Math.sqrt(Math.abs(mouseLocationY - SMILEY_Y_CENTER) * Math.sqrt(Math.abs(mouseLocationY - SMILEY_Y_CENTER)))));

        return clickedCoordinatesDifference < 6;
    }

    /**
     * Method that resets the board and time when player clicks around Smiley's mouse
     */
    public void resetBoardWhenClickSmileyFace() {

        smiley.setSmileyHappy(true);
        isGameOver = false;

        timer.setDate(new Date());

        blueGreenRedBoxGenerator();
        placeGrannyYagaHouse();
        makeSureYellowBoxIsPlaced();
    }

    /**
     * Method that checks if the player clicked inside a box
     */
    private void checkIfClickedInBox() {

        if (clickedBoxColumn() != -1 && clickedBoxRow() != -1) {

            setCurrentAndLastCoordinates();

            int checkColumn = currentClickedColumn;
            int checkRow = currentClickedRow;

            checkIfClickedBoxGreenOrRed(checkColumn, checkRow);
        }
    }

    /**
     * Method that checks if the clicked box is Green or Red
     *
     * @param checkColumn Int to check a yellow box's column
     * @param checkRow    Int to check a yellow box's row
     */
    private void checkIfClickedBoxGreenOrRed(int checkColumn, int checkRow) {

        if (mines[currentClickedColumn][currentClickedRow] == 3 || mines[currentClickedColumn][currentClickedRow] == 2) {

            analyzerAndYellowBoxFinder(checkColumn, checkRow);
        }
    }

    /**
     * Method that sets the last and current clicked coordinates
     */
    private void setCurrentAndLastCoordinates() {

        lastClickedColumn = currentClickedColumn;
        currentClickedColumn = clickedBoxColumn();

        lastClickedRow = currentClickedRow;
        currentClickedRow = clickedBoxRow();
    }

    /**
     * Method that analyses where is the clicked box and analyses how to find the Yellow box around
     *
     * @param checkColumn Int to check a yellow box's column
     * @param checkRow    Int to check a yellow box's row
     */
    private void analyzerAndYellowBoxFinder(int checkColumn, int checkRow) {

        if (currentClickedColumn >= 1 && currentClickedColumn <= 6) {

            findYellowBoxWithColumn(checkColumn, checkRow);
        }
        if (currentClickedRow >= 1 && currentClickedRow <= 6) {

            findYellowBoxWithRow(checkColumn, checkRow);
        }
        if (currentClickedColumn == 0) {

            checkIfClickedColumnIsFirstColumn(checkColumn, checkRow);
        }
        if (currentClickedColumn == 7) {

            checkIfClickedColumnIsLastColumn(checkColumn, checkRow);
        }
        if (currentClickedRow == 0) {

            checkIfClickedRowIsFirstRow(checkColumn, checkRow);
        }
        if (currentClickedRow == 7) {

            checkIfClickedRowIsLastRow(checkColumn, checkRow);
        }
    }

    /**
     * Method that checks if the there is a yellow box by Row
     *
     * @param checkColumn Int to check a yellow box's column
     * @param checkRow    Int to check a yellow box's row
     */
    private void checkIfClickedRowIsLastRow(int checkColumn, int checkRow) {

        if (mines[checkColumn][--checkRow] == 4) {

            revealed[clickedBoxColumn()][clickedBoxRow()] = true;
        }
    }


    /**
     * Method that checks if the there is a Yellow box by Row
     *
     * @param checkColumn Int to check a yellow box's column
     * @param checkRow    Int to check a yellow box's row
     */
    private void checkIfClickedRowIsFirstRow(int checkColumn, int checkRow) {

        if (mines[checkColumn][++checkRow] == 4) {

            revealed[clickedBoxColumn()][clickedBoxRow()] = true;
        }
    }

    /**
     * Method that checks if the there is a Yellow box by column
     *
     * @param checkColumn Int to check a yellow box's column
     * @param checkRow    Int to check a yellow box's row
     */
    private void checkIfClickedColumnIsLastColumn(int checkColumn, int checkRow) {

        if (mines[--checkColumn][checkRow] == 4) {

            revealed[clickedBoxColumn()][clickedBoxRow()] = true;
        }
    }

    /**
     * Method that checks if the there is a Yellow box by column
     *
     * @param checkColumn Int to check a yellow box's column
     * @param checkRow    Int to check a yellow box's row
     */
    private void checkIfClickedColumnIsFirstColumn(int checkColumn, int checkRow) {

        if (mines[++checkColumn][checkRow] == 4) {

            revealed[clickedBoxColumn()][clickedBoxRow()] = true;
        }
    }

    /**
     * Method calls color method for double clicked box and changes its "revealed" status
     */
    private void isClickedInDifferentBox() {

        if (revealed[currentClickedColumn][currentClickedRow]) {

            revealed[currentClickedColumn][currentClickedRow] = false;

            colorChooserForDoubleClickedBox();
        }
    }

    /**
     * Method that randomises a number to pick a color for the double clicked box
     */
    private void colorChooserForDoubleClickedBox() {

        int num = random.nextInt(10);

        if (num <= 1) {

            mines[currentClickedColumn][currentClickedRow] = 1;
        } else {

            mines[currentClickedColumn][currentClickedRow] = 4;
        }
    }


    /**
     * Method that checks if player clicked on same box twice or clicked in difference box
     *
     * @param g Object of Graphics
     */
    public void doubleClickLogicForBox(Graphics g) {

        if (currentClickedColumn == lastClickedColumn && currentClickedRow == lastClickedRow) {

            isClickedInDifferentBox();
        } else {

            revealed[lastClickedColumn][lastClickedRow] = false;
            g.setColor(Color.ORANGE);
        }
    }

    /**
     * Method that draws a question mark in clicked bo z
     */
    protected void drawQuestionMarkInChosenBox(int column, int row, Graphics g) {

        if (revealed[column][row]) {

            g.setColor(Color.BLACK);
            g.setFont(new Font("Tacoma", Font.BOLD, 30));
            g.drawString("?", column * boxWidth + 25, row * boxHeight + boxHeight + 42);
        }
    }

    /**
     * Method that fills the box if the cursor is inside a box
     *
     * @param column The column of the box that the cursor is in
     * @param row    The row of the box that the cursor is in
     * @param g      Object of Graphics
     */
    protected void paintTheBoxThatIsHovered(int column, int row, Graphics g) {

        if (isCursorInBox(column, row)) {

            g.setColor(Color.lightGray);
        }
    }

    /**
     * Method that chooses the color for every box
     *
     * @param column The current column that the look is checking
     * @param row    The current row that the look is checking
     * @param g      Object of Graphics
     */
    protected void boxColorPicker(int column, int row, Graphics g) {

        if (mines[column][row] == 1) {

            g.setColor(Color.BLUE);

        } else if (revealed[column][row]) {

            doubleClickLogicForBox(g);

        } else if (mines[column][row] == 2) {

            g.setColor(Color.GREEN);

        } else if (mines[column][row] == 4) {

            g.setColor(Color.ORANGE);

        } else {

            g.setColor(Color.PINK);
        }
    }
    /**
     * Method that searches the Yellow box around the clicked location
     *
     * @param checkColumn Int created for checking
     * @param checkRow    Int created for checking
     */
    private void findYellowBoxWithColumn(int checkColumn, int checkRow) {

        int previousColumn = checkColumn;
        int nextColumn = checkColumn;

        if (mines[--previousColumn][checkRow] == 4 || mines[++nextColumn][checkRow] == 4) {

            revealed[clickedBoxColumn()][clickedBoxRow()] = true;
        }
    }

    /**
     * Method that searches the Yellow box around the clicked location
     *
     * @param checkColumn Created for checking Column
     * @param checkRow    Created for checking Row
     */
    private void findYellowBoxWithRow(int checkColumn, int checkRow) {

        int previousRow = checkRow;
        int nextRow = checkRow;

        if (mines[checkColumn][--previousRow] == 4 || mines[checkColumn][++nextRow] == 4) {

            revealed[clickedBoxColumn()][clickedBoxRow()] = true;
        }
    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {}

        @Override
        public void mouseMoved(MouseEvent e) {

            mouseLocationX = e.getX();
            mouseLocationY = e.getY();
        }
    }

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            mouseLocationX = e.getX();
            mouseLocationY = e.getY();

            checkIfClickedInBox();
            isClickedInsideSmileyTrue();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}