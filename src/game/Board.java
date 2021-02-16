package game;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private final GUI gui;
    private final byte BOX_WIDTH;
    private final byte BOX_HEIGHT;
    private final byte NUMBER_OF_COWS_AND_ROWS;
    private final byte spacing;

    public Board(GUI gui) {

        this.gui = gui;
        this.BOX_WIDTH = gui.getBoxWidth();
        this.BOX_HEIGHT = gui. getBoxHeight();
        this.NUMBER_OF_COWS_AND_ROWS = gui.getNUMBER_OF_COWS_AND_ROWS();
        this.spacing = gui.getSpacing();
    }

    /**
     * Method that paints the components of the board like figures, background, boxes
     *
     * @param g Object of Graphics
     */
    public void paintComponent(Graphics g) {

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 586, 629);

        paintBoxes(g);

        gui.smiley.drawResetButton(g);

        gui.timer.drawTimer(g);

    }

    /**
     * Method that paints the boxes
     *
     * @param g Object of Graphics
     */
    private void paintBoxes(Graphics g) {

        for (int column = 0; column < this.NUMBER_OF_COWS_AND_ROWS; column++) {

            for (int row = 0; row < this.NUMBER_OF_COWS_AND_ROWS; row++) {

                gui.boxColorPicker(column, row, g);

                gui.paintTheBoxThatIsHovered(column, row, g);

                g.fillRect(this.spacing + column * this.BOX_WIDTH, this.spacing + row * this.BOX_HEIGHT + this.BOX_HEIGHT,
                        this.BOX_WIDTH - 2 * this.spacing, this.BOX_HEIGHT - 2 * this.spacing);

                gui.drawQuestionMarkInChosenBox(column, row, g);
            }
        }
    }
}
