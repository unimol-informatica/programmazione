package MCC.GUI;

import MCC.utils.Resources;
import MCC.utils.SwapPanel;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class ChoosePlayerPanel extends JPanel {

    private final Image choosePlayerBackground;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    public static final int JONATHAN_FLAG = 1;
    public static final int MICHELE_FLAG = -1;

    private final Rectangle playerMichele;
    private final Rectangle playerJonathan;

    public static int activePlayer;

    public ChoosePlayerPanel() {
        this.setSize(WIDTH, HEIGHT);
        this.choosePlayerBackground = Resources.getImage("/MCC/GUI/images/chooseplayer.png");

        this.playerMichele = new Rectangle(151, 201, 307, 370);
        this.playerJonathan = new Rectangle(850, 194, 271, 381);

        this.addMouseListener(new ChoosePlayerPanel.MouseListener());
        this.addMouseMotionListener(new ChoosePlayerPanel.MouseListener());
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(choosePlayerBackground, 0, 0, null);
    }

    public class MouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            Point p = e.getPoint();
            if (playerMichele.contains(p)) {
                activePlayer = MICHELE_FLAG;
                SwapPanel.SwapPanel(MainFrame.choosePlayerPanel, MainFrame.countdownPanel);
                MainFrame.menuAudio.stop();
                MainFrame.countDownAudio.play();
                MainFrame.countdownPanel.startTimer();
            }
            if (playerJonathan.contains(p)) {
                activePlayer = JONATHAN_FLAG;
                SwapPanel.SwapPanel(MainFrame.choosePlayerPanel, MainFrame.countdownPanel);
                MainFrame.menuAudio.stop();
                MainFrame.countDownAudio.play();
                MainFrame.countdownPanel.startTimer();

            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point p = e.getPoint();
            if ((playerJonathan.contains(p)) || (playerMichele.contains(p))) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

}
