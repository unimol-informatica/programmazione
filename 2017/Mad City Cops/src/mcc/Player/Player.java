package MCC.Player;

import MCC.utils.Resources;
import java.awt.*;
import MCC.GUI.ChoosePlayerPanel;
import java.awt.Rectangle;

public class Player {

    public Rectangle playerCollider;
    private Image playerImage;
    private Point coordinates = new Point();
    
    public static int jumpPixelOffset = 5;

    private static final int SPAWN_COORDINATE_X = 70;
    private static final int SPAWN_COORDINATE_Y = 470;
    private static final int COLLIDER_WIDTH = 120;
    private static final int COLLIDER_HEIGHT = 221;

    public Player() {

        this.coordinates.x = SPAWN_COORDINATE_X;
        this.coordinates.y = SPAWN_COORDINATE_Y;

        this.playerCollider = new Rectangle(SPAWN_COORDINATE_X, SPAWN_COORDINATE_Y, COLLIDER_WIDTH, COLLIDER_HEIGHT);

    }

    public void setPlayer() {

        if (ChoosePlayerPanel.activePlayer == ChoosePlayerPanel.JONATHAN_FLAG) {
            this.playerImage = Resources.getImage("/MCC/Player/images/j.png");
        }
        if (ChoosePlayerPanel.activePlayer == ChoosePlayerPanel.MICHELE_FLAG) {
            this.playerImage = Resources.getImage("/MCC/Player/images/m.png");
        }
    }

    public void jumpOn() {
        this.coordinates.y -= jumpPixelOffset;
        this.playerCollider.y -= jumpPixelOffset;
    }

    public void jumpDown() {
        this.coordinates.y += jumpPixelOffset;
        this.playerCollider.y += jumpPixelOffset;
    }

    public void drawPlayer(Graphics g) {
        g.drawImage(playerImage, this.coordinates.x, this.coordinates.y, null);
    }

}
