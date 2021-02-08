package gamewindowheader;

import java.awt.*;

public class ResetButtonSmiley {

    private final int RESET_BUTTON_X = 253;
    private final byte RESET_BUTTON_Y = 1;
    private boolean isSmileyHappy = true;

    public byte getRESET_BUTTON_Y() {
        return RESET_BUTTON_Y;
    }

    public int getRESET_BUTTON_X() {
        return RESET_BUTTON_X;
    }

    public ResetButtonSmiley(boolean isSmileyHappy) {

        this.isSmileyHappy = isSmileyHappy;
    }

    /**
     * Method that draws a reset button as a Smiley face
     *
     * @param g Object of Graphics
     */
    public void drawResetButton(Graphics g) {

        resetButtonAppearance(g);

        isResetButtonHappy(g);
    }

    /**
     * Method created to set the Reset Button as a Smiley face
     *
     * @param g Object of Graphic
     */
    private void resetButtonAppearance(Graphics g) {

        g.setColor(Color.ORANGE);
        g.fillOval(this.RESET_BUTTON_X, this.RESET_BUTTON_Y, 68, 63);

        g.setColor(Color.BLACK);
        g.fillOval(this.RESET_BUTTON_X + 15, this.RESET_BUTTON_Y + 15, 10, 10);
        g.fillOval(this.RESET_BUTTON_X + 45, this.RESET_BUTTON_Y + 15, 10, 10);
        g.fillOval(this.RESET_BUTTON_X + 32, this.RESET_BUTTON_Y + 30, 5, 5);
    }

    /**
     * Method created to change reset button "Smiley's" face
     *
     * @param g Object of Graphics
     */
    private void isResetButtonHappy(Graphics g) {

        if (this.isSmileyHappy) {

            g.fillRect(this.RESET_BUTTON_X + 20, this.RESET_BUTTON_Y + 45, 30, 5);
            g.fillOval(this.RESET_BUTTON_X + 15, this.RESET_BUTTON_Y + 40, 6, 5);
            g.fillOval(this.RESET_BUTTON_X + 48, this.RESET_BUTTON_Y + 40, 6, 5);

        } else {

            g.fillRect(this.RESET_BUTTON_X + 20, this.RESET_BUTTON_Y + 40, 30, 5);
            g.fillOval(this.RESET_BUTTON_X + 15, this.RESET_BUTTON_Y + 42, 6, 5);
            g.fillOval(this.RESET_BUTTON_X + 48, this.RESET_BUTTON_Y + 42, 6, 5);
        }
    }
}
