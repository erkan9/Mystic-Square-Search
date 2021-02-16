package gameboardadditions;

import java.awt.*;
import java.util.Date;

public class Timer {

    private final int TIME_X = 470;
    private final int TIME_Y = 3;

    Font timerFont = new Font("Tacoma", Font.BOLD, 50);
    public Date date = new Date();

    public Timer() { }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Method created to draw the timer
     *
     * @param g Object of Graphics
     */
    public void drawTimer(Graphics g) {

        timerAppearance(g);

        timerSecondsVisualiser(g);

        //stopGameWhenWin(g);
    }

    /**
     * Method that sets appearance of the counter box
     *
     * @param g Object of Graphics
     */
    private void timerAppearance(Graphics g) {

        g.setColor(Color.ORANGE);
        g.fillRect(this.TIME_X, this.TIME_Y, 124, 60);

        g.setColor(Color.BLACK);
        g.setFont(this.timerFont);
    }

    /**
     * Method that visualises the seconds in the "Timer Box"
     *
     * @param g Object of Graphics
     */
    private void timerSecondsVisualiser(Graphics g) {

        int secondsSpentInGame = (int) ((new Date().getTime() - date.getTime()) / 1000);

        if (secondsSpentInGame < 10) {

            g.drawString("00" + secondsSpentInGame, this.TIME_X + 20, this.TIME_Y + 50);

        } else if (secondsSpentInGame < 100) {

            g.drawString("0" + secondsSpentInGame, this.TIME_X + 20, this.TIME_Y + 50);

        } else {

            g.drawString(Integer.toString(secondsSpentInGame), this.TIME_X, this.TIME_Y + 50);
        }
    }
}
