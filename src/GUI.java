import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.*;

public class GUI extends JFrame {

    Date date = new Date();

    final byte spacing = 2;
    final byte boxWidth = 72;
    final byte boxHeight = 65;

    public int mX = -100;
    public int mY = -100;

    public int smileyForResetX = 253;
    public int smileyForResetY = 1;

    public int timeX = 450;
    public int timeY = 3;

    public int secondsSpentInGame = 0;

    public boolean isSmileyHappy = true;
    public boolean isGameOver = false;
    public boolean defeat = false;

    public final byte NUMBER_OF_COWS_AND_ROWS = 8;

    Random random = new Random();
    Component component = new Board();

    int[][] mines = new int[8][8];
    int[][] neighbours = new int[8][8];
    boolean[][] revealed = new boolean[8][8];
    boolean[][] flagged = new boolean[8][8];

    public GUI() {

        this.setTitle("My GPS is broken");
        this.setSize(592, 629);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        blueGreenRedBoxGenerator(random);
        makeSureYellowBoxIsPlacer(random, mines);

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();

        this.addMouseListener(click);

    }


    private int numberGeneratorForYellowBoxLocation(Random random) {

        return random.nextInt(4);
    }

    private boolean yellowBoxChecker(Random random, int[][] mine) {

        int generatedNumber = numberGeneratorForYellowBoxLocation(random);

        boolean isCornerEmpty = false;

        if(generatedNumber == 0 && mine[0][0] == 3) {

            mine[0][0] = 4;
            isCornerEmpty = true;
        }
        else  if(generatedNumber == 1 && mine[0][7] == 3) {

            mine[0][7] = 4;
            isCornerEmpty = true;
        }
        else  if(generatedNumber == 2 && mine[7][0] == 3) {

            mine[7][0] = 4;
            isCornerEmpty = true;
        }
        else  if(generatedNumber == 3 && mine[7][7] == 3) {

            mine[7][7] = 4;
            isCornerEmpty = true;
        }

        return isCornerEmpty;
    }

    /**
     * Method that calls another methods if the chosen corner is occupied
     * @param random Object of Random
     * @param mine The 2D array that holds the information about the boxes
     */
    private void makeSureYellowBoxIsPlacer(Random random, int[][] mine) {

        boolean isCornerEmpty = yellowBoxChecker(random, mine);

        while(!isCornerEmpty) {

            numberGeneratorForYellowBoxLocation(random);

           isCornerEmpty = yellowBoxChecker(random, mine);
        }
    }

    /**
     * Method that gets a random number and places a blue box on the board if statement is true
     * @param random Object of Random
     */
    private void blueGreenRedBoxGenerator(Random random) {

        int randomNumber;
        byte blueBoxCounter = 1;
        byte greenBoxCounter = 1;

        //Makes sure there are 5 blue boxes
        while (blueBoxCounter <= 5 && greenBoxCounter <= 8) {

            for (int i = 0; i < NUMBER_OF_COWS_AND_ROWS; i++) {

                for (int j = 0; j < NUMBER_OF_COWS_AND_ROWS; j++) {

                    randomNumber = random.nextInt(100);

                    if (randomNumber <= 10) {

                    if (blueBoxCounter <= 5) {

                        //The smaller the number is, the better distance between blue boxes
                            mines[i][j] = 1;

                            blueBoxCounter++;
                        }
                    }
                    else if(randomNumber <= 30) {

                        if (greenBoxCounter <= 8) {

                            mines[i][j] = 2;

                            greenBoxCounter++;
                        }
                    }
                    else { mines[i][j] = 3;}
                }
            }
        }
    }

    public class Board extends JPanel {

        /**
         * Method that paints the components of the board like figures, background, boxes
         * @param g Object of Graphics
         */
        public void paintComponent(Graphics g) {

            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 580, 600);

           paintBoxes(g);

           //Draw smiley face as a Reset button
           drawResetButton(g);

           //Timer
            drawTimer(g);
        }

        private void drawTimer(Graphics g) {

            g.setColor(Color.ORANGE);
            g.fillRect(timeX, timeY, 120,60);

            secondsSpentInGame = (int) ((new Date().getTime() - date.getTime()) / 1000);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Tahoma", Font.PLAIN, 50));

            if(secondsSpentInGame < 10) {

                g.drawString("00" + Integer.toString(secondsSpentInGame), timeX, timeY + 50);
            }
            else if(secondsSpentInGame < 100) {

                g.drawString("0" + Integer.toString(secondsSpentInGame), timeX, timeY + 50);
            }
            else {

                g.drawString(Integer.toString(secondsSpentInGame), timeX, timeY + 50);
            }
        }
        /**
         * Method that draws a reset button as a smiley face
         * @param g
         */
        private void drawResetButton(Graphics g) {

            // Reset button as smiley
            g.setColor(Color.ORANGE);
            g.fillOval(smileyForResetX, smileyForResetY, 68, 63);
            g.setColor(Color.BLACK);
            g.fillOval(smileyForResetX + 15, smileyForResetY + 15, 10,10);
            g.fillOval(smileyForResetX + 45, smileyForResetY + 15, 10,10);

            if (isSmileyHappy) {
                g.fillRect(smileyForResetX + 20, smileyForResetY + 45, 30, 5);
                g.fillOval(smileyForResetX + 15, smileyForResetY + 40, 6,5);
                g.fillOval(smileyForResetX + 48, smileyForResetY + 40, 6,5);
            }
            else {
                g.fillRect(smileyForResetX + 20, smileyForResetY + 40, 30, 5);
                g.fillOval(smileyForResetX + 15, smileyForResetY + 42, 6,5);
                g.fillOval(smileyForResetX + 48, smileyForResetY + 42, 6,5);
            }
        }

        /**
         * Method that paints the boxes
         * @param g Object of Graphics
         */
        private void paintBoxes(Graphics g) {

            for (int i = 0; i < 8; i++) {

                for (int j = 0; j < 8; j++) {

                    g.setColor(Color.GRAY);

                    if (mines[i][j] == 1) {

                        g.setColor(Color.BLUE);
                    }
                    else  if (mines[i][j] == 2) {

                        g.setColor(Color.GREEN);
                    }
                    else if(mines[i][j] == 4) {

                        g.setColor(Color.ORANGE);
                    }
                    else { g.setColor(Color.PINK); }

                     /*if(mX >= spacing + i * boxWidth && mX < i * boxWidth + boxWidth - spacing
                            && mY >= spacing + j + boxHeight + boxHeight + 12 && mY < j * boxHeight + 12 + boxHeight + boxHeight - spacing) {

                        g.setColor(Color.RED);
                    } */

                    g.fillRect(spacing + i * boxWidth, spacing + j * boxHeight + boxHeight, boxWidth - 2 * spacing, boxHeight - 2 * spacing);
                }
            }
        }
    }

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


        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("The mouse was clicked");
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
