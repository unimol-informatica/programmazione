package MCC.Enemies;

import MCC.utils.Resources;
import java.awt.*;

public class Enemies {

    private Image enemyImage;
    private Point generationPoint = new Point();
    public Rectangle enemyCollider;
    
    public static final int MOVEMENT_PIXEL_OFFSET = 5;

    public Enemies() {
        
        this.enemyImage = Resources.getImage("/MCC/Enemies/images/policecar.png");
        
        this.generationPoint.x = 1280;
        this.generationPoint.y = 530;
        
        this.enemyCollider = new Rectangle (1300, 550, 150, 200);
    }

 
    public void drawEnemies(Graphics g) {
        g.drawImage(enemyImage, this.generationPoint.x, this.generationPoint.y, null);
    }
    
    public void moveEnemies() {
        this.generationPoint.x -= MOVEMENT_PIXEL_OFFSET;
        this.enemyCollider.x -= MOVEMENT_PIXEL_OFFSET;
    }

}
