import gamewindowheader.ResetButtonSmiley;
import gamewindowheader.Timer;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.*;

//TODO make the logic about movement and end of game

public class GUI extends JFrame {

    private final byte NUMBER_OF_COWS_AND_ROWS = 8;

    boolean isBoxPlaced = false;
    public int lastClickedColumn = 16;
    int checker = 0;
    public int lastClickedRow = 16;

    public int currentClickedColumn;
    public int currentClickedRow;

    private final byte spacing = 2;
    private final byte boxWidth = 72;
    private final byte boxHeight = 65;

    public int mX = -100;
    public int mY = -100;

    boolean isSmileyHappy = true;
    public boolean isGameOver = false;
    public boolean defeat = false;

    Random random = new Random();
    Timer timer = new Timer();
    ResetButtonSmiley smiley = new ResetButtonSmiley(isSmileyHappy);

    int[][] mines = new int[8][8];
    boolean[][] revealed = new boolean[8][8];
    boolean[][] flagged = new boolean[8][8];

    public GUI() {

        this.setTitle("My GPS is broken");
        this.setSize(592, 625);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        blueGreenRedBoxGenerator(random);
        makeSureYellowBoxIsPlaced(random, mines);

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();

        this.addMouseListener(click);
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
     * Method that is created if the corner where yellow box will be placed, is occupied
     *
     * @param random Object of Random
     * @param mine   The 2D array that holds information about the board
     * @return boolean that tells if the chosen corner for yellow box is empty
     */
    private boolean yellowBoxChecker(Random random, int[][] mine) {

        int generatedNumber = numberGeneratorForYellowBoxLocation(random);

        boolean isCornerEmpty = false;

        if (generatedNumber == 0 && mine[0][0] == 3) {

            mine[0][0] = 4;
            isCornerEmpty = true;
        } else if (generatedNumber == 1 && mine[0][7] == 3) {

            mine[0][7] = 4;
            isCornerEmpty = true;
        } else if (generatedNumber == 2 && mine[7][0] == 3) {

            mine[7][0] = 4;
            isCornerEmpty = true;
        } else if (generatedNumber == 3 && mine[7][7] == 3) {

            mine[7][7] = 4;
            isCornerEmpty = true;
        }

        return isCornerEmpty;
    }

    /**
     * Method that calls another methods if the chosen corner is occupied
     *
     * @param random Object of Random
     * @param mine   The 2D array that holds the information about the boxes
     */
    private void makeSureYellowBoxIsPlaced(Random random, int[][] mine) {

        boolean isCornerEmpty = yellowBoxChecker(random, mine);

        while (!isCornerEmpty) {

            numberGeneratorForYellowBoxLocation(random);

            isCornerEmpty = yellowBoxChecker(random, mine);
        }
    }

    /**
     * Method that gets a random number and places a blue box on the board if statement is true
     *
     * @param random Object of Random
     */
    private void blueGreenRedBoxGenerator(Random random) {

        int randomNumber;
        byte blueBoxCounter = 1;
        byte greenBoxCounter = 1;

        //Makes sure there are 5 blue boxes
        while (blueBoxCounter <= 5 && greenBoxCounter <= 8) {

            for (int column = 0; column < NUMBER_OF_COWS_AND_ROWS; column++) {

                for (int row = 0; row < NUMBER_OF_COWS_AND_ROWS; row++) {

                    randomNumber = random.nextInt(100);

                    if (randomNumber <= 15 && blueBoxCounter <= 5) {

                        //The smaller the number is, the better distance between blue boxes
                        mines[column][row] = 1;

                        blueBoxCounter++;

                    } else if (randomNumber <= 25 && greenBoxCounter <= 8) {

                        mines[column][row] = 2;

                        greenBoxCounter++;

                    } else {

                        mines[column][row] = 3;
                    }

                    revealed[column][row] = false;
                }
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
    private boolean isCursorInBox(int column, int row) {

        return mX >= spacing + column * boxWidth &&
                mX < spacing + column * boxWidth + boxWidth - 2 * spacing &&
                mY >= spacing + row * boxHeight + boxHeight + 33 &&
                mY < spacing + row * boxHeight + 33 + boxHeight + boxHeight - 2 * spacing;
    }

    /**
     * Method that fills the box if the cursor is inside a box
     *
     * @param column The column of the box that the cursor is in
     * @param row    The row of the box that the cursor is in
     * @param g      Object of Graphics
     */
    private void paintTheBoxThatIsHovered(int column, int row, Graphics g) {

        if (isCursorInBox(column, row)) {

            g.setColor(Color.lightGray);
        }
    }

    /**
     * Method that draws a question mark in clicked bo z
     */
    private void drawQuestionMarkInChosenBox(int column, int row, Graphics g) {

        if (revealed[column][row]) {

            g.setColor(Color.BLACK);
            g.setFont(new Font("Tacoma", Font.BOLD, 30));
            g.drawString("?", column * boxWidth + 25, row * boxHeight + boxHeight + 42);
        }
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
     * Method that chooses the color for every box
     *
     * @param column The current column that the look is checking
     * @param row    The current row that the look is checking
     * @param g      Object of Graphics
     */
    private void boxColorPicker(int column, int row, Graphics g) {

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

    //TODO make double click logic methods smaller
    private void doubleClickLogicForBox(Graphics g) {

        if(currentClickedColumn == lastClickedColumn && currentClickedRow == lastClickedRow) {

            if (revealed[currentClickedColumn][currentClickedRow]) {

                revealed[currentClickedColumn][currentClickedRow] = false;

                int num = random.nextInt(10);

                if (num <= 1) {

                    mines[currentClickedColumn][currentClickedRow] = 1;

                    System.out.printf("The rolled number is [%d], so the box is BLUE\n\n", num);

                } else {

                    mines[currentClickedColumn][currentClickedRow] = 4;

                    System.out.printf("The rolled number is [%d], so the box is YELLOW\n\n", num);
                }
            }
        }
        else {

            revealed[lastClickedColumn][lastClickedRow] = false;

            g.setColor(Color.ORANGE);
        }
    }

    public class Board extends JPanel {

        /**
         * Method that paints the components of the board like figures, background, boxes
         *
         * @param g Object of Graphics
         */
        public void paintComponent(Graphics g) {

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 586, 629);

            paintBoxes(g);

            //Draws a smiley face as a Reset button
            smiley.drawResetButton(g);

            //Shows a timer Timer
            timer.drawTimer(g);

        }

        /**
         * Method that paints the boxes
         *
         * @param g Object of Graphics
         */
        private void paintBoxes(Graphics g) {

            for (int column = 0; column < 8; column++) {

                for (int row = 0; row < 8; row++) {

                    boxColorPicker(column, row, g);

                    paintTheBoxThatIsHovered(column, row, g);

                    g.fillRect(spacing + column * boxWidth, spacing + row * boxHeight + boxHeight, boxWidth - 2 * spacing, boxHeight - 2 * spacing);

                    drawQuestionMarkInChosenBox(column, row, g);
                }
            }
        }
    }

    // Methods created to check if our mouse clicked inside a box
    // If click is in side a box, they will return X and Y value of the clicked box

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

            mX = e.getX();
            mY = e.getY();
        }
    }

    public class Click implements MouseListener {

        public void mouseClicked(MouseEvent e) {

            if (clickedBoxColumn() != -1 && clickedBoxRow() != -1) {

                if (mines[clickedBoxColumn()][clickedBoxRow()] == 3 || mines[clickedBoxColumn()][clickedBoxRow()] == 2) {

                    lastClickedColumn = currentClickedColumn;
                    currentClickedColumn = clickedBoxColumn();

                    lastClickedRow = currentClickedRow;
                    currentClickedRow = clickedBoxRow();

                    revealed[clickedBoxColumn()][clickedBoxRow()] = true;
                    System.out.printf("Clicked Box coordinates -> Column:[%d] Row:[%d] Type:[%d]\n", clickedBoxColumn(), clickedBoxRow(), mines[currentClickedColumn][currentClickedRow]);
                    System.out.printf("Last Clicked Column [%d] Row[%d]\n", lastClickedColumn, lastClickedRow);

                }
            else if (mines[clickedBoxColumn()][clickedBoxRow()] == 1) {

                    System.out.println("You clicked on a BLUE box");
                }
            }
            else {

                System.out.println("You did not click in any Box!");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        public void resetBoardWhenClickSmileyFace() {

            isSmileyHappy = true;
            isGameOver = false;
            defeat = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}