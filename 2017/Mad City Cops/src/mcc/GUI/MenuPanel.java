package MCC.GUI;

import java.awt.*;
import javax.swing.*;
import MCC.utils.Resources;
import MCC.utils.SwapPanel;
import java.awt.event.*;

public class MenuPanel extends JPanel {

    private final Image menuBackground;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private final Rectangle playRectangle;
    private final Rectangle infoRectangle;
    private final Rectangle creditsRectangle;

    public MenuPanel() {

        this.setSize(WIDTH, HEIGHT);
        this.menuBackground = Resources.getImage("/MCC/GUI/images/menu.png");
        
        this.playRectangle = new Rectangle(922, 429, 229, 76);
        this.infoRectangle = new Rectangle(921, 538, 229, 76);
        this.creditsRectangle = new Rectangle(481, 20, 272, 73);
       
        this.addMouseListener(new MouseListener());
        this.addMouseMotionListener(new MouseListener());
        
        MainFrame.menuAudio.playLoop();

    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(menuBackground, 0, 0, null);

    }

    public class MouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            Point p = e.getPoint();
            if (playRectangle.contains(p)) {
                SwapPanel.SwapPanel(MainFrame.menuPanel, MainFrame.choosePlayerPanel);
                } else if (infoRectangle.contains(p)) {
                SwapPanel.SwapPanel(MainFrame.menuPanel, MainFrame.infoPanel);
            } else if (creditsRectangle.contains(p)) {
                SwapPanel.SwapPanel(MainFrame.menuPanel, MainFrame.creditsPanel);
            }
        }


        @Override
        public void mouseMoved(MouseEvent e) {
            Point p = e.getPoint();
            if ((playRectangle.contains(p)) || (infoRectangle.contains(p)) || (creditsRectangle.contains(p))) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

}
