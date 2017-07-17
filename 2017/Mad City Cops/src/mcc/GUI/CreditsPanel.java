package MCC.GUI;

import java.awt.*;
import javax.swing.*;
import MCC.utils.Resources;
import MCC.utils.SwapPanel;
import java.awt.event.*;

public class CreditsPanel extends JPanel {

    private final Image creditsBackground;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private final Rectangle backRectangle;

    public CreditsPanel() {
        this.setSize(WIDTH, HEIGHT);
        this.creditsBackground = Resources.getImage("/MCC/GUI/images/credits.png");
       
        this.backRectangle = new Rectangle(533, 567, 187, 106);
      
        this.addMouseListener(new MouseListener());
        this.addMouseMotionListener(new MouseListener());
       
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(creditsBackground, 0, 0, null);

    }

    public class MouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            Point p = e.getPoint();
            if (backRectangle.contains(p)) {

                SwapPanel.SwapPanel(MainFrame.creditsPanel, MainFrame.menuPanel);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point p = e.getPoint();
            if ((backRectangle.contains(p))) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

}
