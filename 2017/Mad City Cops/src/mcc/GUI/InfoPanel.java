package MCC.GUI;

import java.awt.*;
import javax.swing.*;
import MCC.utils.Resources;
import MCC.utils.SwapPanel;
import java.awt.event.*;

public class InfoPanel extends JPanel {

    private final Image infoBackground;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private final Rectangle backRectangle;

    public InfoPanel() {
        this.setSize(WIDTH, HEIGHT);
       
        this.infoBackground = Resources.getImage("/MCC/GUI/images/info.png");
        this.backRectangle = new Rectangle(500, 568, 187, 106);
        
        this.addMouseListener(new MouseListener());
        this.addMouseMotionListener(new MouseListener());
       
    }

    @Override

    public void paintComponent(Graphics g) {
        g.drawImage(infoBackground, 0, -30, null);
    }

    public class MouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            Point p = e.getPoint();
            if (backRectangle.contains(p)) {
                SwapPanel.SwapPanel(MainFrame.infoPanel, MainFrame.menuPanel);
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
