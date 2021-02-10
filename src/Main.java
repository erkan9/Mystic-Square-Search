import javax.swing.*;

import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class Main implements Runnable {

    GUI gui = new GUI();

    public static void main(String[] args) {

        new Thread(new Main()).start();

    }

    @Override
    public void run() {

        while (true) {

            this.gui.repaint();

            this.gui.checkIfEndOfGame();

            showMessageWhenGameOver();
        }
    }

    /**
     * Show window with two options and Reset or Exit the game
     */
    public void showMessageWhenGameOver() {

        if (this.gui.isGameOver) {

            String[] options = {"Reset Game", "Exit Game"};

            int x = JOptionPane.showOptionDialog(null, "You won the Game!!!\n" +
                            "Smiley is sad because the Game is over\n",
                    "Reset or Exit the game",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (x == JOptionPane.YES_OPTION) {

                this.gui.resetBoardWhenClickSmileyFace();
            } else if (x == JOptionPane.NO_OPTION) {

                System.exit(0);
            }
        }
    }
}
