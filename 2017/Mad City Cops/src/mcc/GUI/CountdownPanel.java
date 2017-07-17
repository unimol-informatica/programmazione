package MCC.GUI;

import java.awt.*;
import javax.swing.*;
import MCC.utils.Resources;
import MCC.utils.SwapPanel;

public class CountdownPanel extends JPanel {

    private final Image countdownBackground;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private static final int TIMER_START = 5;
    private int seconds;
    private static final int TIMER_POSITION_X = 550;
    private static final int TIMER_POSITION_Y = 460;
    private static final int TIMER_SIZE = 400;

    public CountdownPanel() {

        this.setSize(WIDTH, HEIGHT);
        this.countdownBackground = Resources.getImage("/MCC/GUI/images/countdown.png");

    }

    @Override
    protected void paintComponent(Graphics g) {

        g.drawImage(countdownBackground, 0, 0, null);
        g.setColor(Color.RED);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, TIMER_SIZE));
        g.drawString(this.seconds + "", TIMER_POSITION_X, TIMER_POSITION_Y);

    }

    public void startTimer() {
        this.seconds = TIMER_START;
        Thread timer = new Thread(new TimerThread());
        timer.start();
    }

    private class TimerThread implements Runnable {

        @Override
        public void run() {
            while (seconds > 0) {
                seconds = seconds - 1;
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            SwapPanel.SwapPanel(MainFrame.countdownPanel, MainFrame.gamePanel);
            MainFrame.gamePanel.requestFocusInWindow();
            MainFrame.gamePanel.StartGame();

        }

    }

}
