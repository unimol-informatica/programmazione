package MCC.GUI;

import static MCC.GUI.MainFrame.gamePanel;

import java.awt.*;
import javax.swing.*;
import MCC.utils.Resources;
import MCC.utils.SwapPanel;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameOverPanel extends JPanel {

    private Image gameOverBackground;

    private Rectangle menuButton;
    private Rectangle restartButton;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private int score;
    private static final int SCORE_POSITION_X = 850;
    private static final int SCORE_POSITION_Y = 660;
    private static final int HIGH_SCORE_POSITION_X = 850;
    private static final int HIGH_SCORE_POSITION_Y = 610;
    private static final int SCORE_SIZE = 50;

    private static final String SCORE_SAVE_URL = "C:\\Mad City Cops\\High Score.txt";
    private File saveScoreFile;

    public GameOverPanel() {
        this.setSize(WIDTH, HEIGHT);

        this.menuButton = new Rectangle(72, 574, 247, 87);
        this.restartButton = new Rectangle(351, 576, 249, 84);

        this.addMouseListener(new MouseListener());
        this.addMouseMotionListener(new MouseListener());

        this.saveScoreFile = new File(SCORE_SAVE_URL);
        this.saveScoreFile.getParentFile().mkdirs();

    }

    public void SetBackground() {

        if (ChoosePlayerPanel.activePlayer == ChoosePlayerPanel.JONATHAN_FLAG) {
            this.gameOverBackground = Resources.getImage("/MCC/GUI/images/jayover.png");
        }
        if (ChoosePlayerPanel.activePlayer == ChoosePlayerPanel.MICHELE_FLAG) {
            this.gameOverBackground = Resources.getImage("/MCC/GUI/images/mikeover.png");
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        this.SetBackground();
        g.drawImage(gameOverBackground, 0, 0, null);

        g.setColor(Color.RED);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, SCORE_SIZE));

        g.drawString("Record: " + loadHighScore(), HIGH_SCORE_POSITION_X, HIGH_SCORE_POSITION_Y);
        g.drawString("Punteggio: " + gamePanel.getScore(), SCORE_POSITION_X, SCORE_POSITION_Y);
    }

    public void saveHighScore() {
        try {

            if (loadHighScore() < gamePanel.score) {

                PrintWriter writer = new PrintWriter(SCORE_SAVE_URL);

                writer.print(gamePanel.getScore());
                writer.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(GameOverPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int loadHighScore() {

        Scanner reader;
        try {
            reader = new Scanner(saveScoreFile);

            if (reader.hasNextInt() == false) {
                this.score = 0;
            } else {
                this.score = reader.nextInt();
            }
            reader.close();
        } catch (Exception e) {
        }

        return score;

    }

    public class MouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            Point p = e.getPoint();
            if (menuButton.contains(p)) {
                SwapPanel.SwapPanel(MainFrame.gameOverPanel, MainFrame.menuPanel);
                MainFrame.gameOverAudio.stop();
                MainFrame.menuAudio.playLoop();
            } else if (restartButton.contains(p)) {
                SwapPanel.SwapPanel(MainFrame.gameOverPanel, MainFrame.choosePlayerPanel);
                MainFrame.gameOverAudio.stop();
                MainFrame.menuAudio.playLoop();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point p = e.getPoint();
            if ((menuButton.contains(p)) || (restartButton.contains(p))) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

}
