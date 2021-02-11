import game.GUI;
import musicplayer.AudioPlay;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class Main implements Runnable {

    GUI gui = new GUI();
    private boolean endGame = false;

    public static void main(String[] args) {

        new Thread(new Main()).start();
        String audioFilePath = "D:/GPS/music/GPS.wav";
        AudioPlay player = new AudioPlay();
        player.play(audioFilePath);

    }

    @Override
    public void run() {

        while (!endGame) {

            this.gui.repaint();

            this.gui.checkIfEndOfGame();

            showMessageWhenGameOver();
        }
        System.exit(0);
    }

    /**
     * Show window with two options and Reset or Exit the game
     */
    public void showMessageWhenGameOver() {

        if (this.gui.isGameOver) {

            String[] options = {"Reset Game", "Exit Game"};

            int message = JOptionPane.showOptionDialog(null, "You won the Game!!!\n" +
                            "Smiley is sad because the Game is over\n",
                    "Reset or Exit the game",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (message == JOptionPane.YES_OPTION) {

                this.gui.resetBoardWhenClickSmileyFace();

            } else if (message == JOptionPane.NO_OPTION) {

                this.endGame = true;

            }
        }
    }
}
