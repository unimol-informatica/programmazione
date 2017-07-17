package MCC.GUI;

import MCC.Player.Player;
import javax.swing.JFrame;
import Utilities.*;
import javax.swing.ImageIcon;


public class MainFrame extends JFrame {

    public static MenuPanel menuPanel;
    public static CreditsPanel creditsPanel;
    public static InfoPanel infoPanel;
    public static GamePanel gamePanel;
    public static CountdownPanel countdownPanel;
    public static ChoosePlayerPanel choosePlayerPanel;
    public static GameOverPanel gameOverPanel;
    public static Player player;
    

    public static AudioPlayer menuAudio;
    public static AudioPlayer countDownAudio;
    public static AudioPlayer gameAudio;
    public static AudioPlayer gameOverAudio;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    public MainFrame() {

        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setIconImage(createImage("/MCC/GUI/images/icon.png").getImage());
        this.setTitle("MAD CITY COPS");

        menuAudio = new AudioPlayer("/MCC/Audio/menu.mp3");
        countDownAudio = new AudioPlayer("/MCC/Audio/countdown.mp3");
        gameAudio = new AudioPlayer("/MCC/Audio/game.mp3");
        gameOverAudio = new AudioPlayer("/MCC/Audio/gameover.mp3");

        menuPanel = new MenuPanel();
        creditsPanel = new CreditsPanel();
        infoPanel = new InfoPanel();
        player = new Player();
        gamePanel = new GamePanel(player);
        countdownPanel = new CountdownPanel();
        gameOverPanel = new GameOverPanel();
        choosePlayerPanel = new ChoosePlayerPanel();

        this.getContentPane().add(menuPanel);
        this.getContentPane().add(creditsPanel);
        this.getContentPane().add(infoPanel);
        this.getContentPane().add(gamePanel);
        this.getContentPane().add(countdownPanel);
        this.getContentPane().add(gameOverPanel);
        this.getContentPane().add(choosePlayerPanel);
        
        menuPanel.setVisible(true);
        creditsPanel.setVisible(false);
        infoPanel.setVisible(false);
        gamePanel.setVisible(false);
        countdownPanel.setVisible(false);
        gameOverPanel.setVisible(false);
        choosePlayerPanel.setVisible(false);
    }

    private ImageIcon createImage(String path) {
       return new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getClass().getResource(path));
    }
    
 
}
