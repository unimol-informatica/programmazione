package MCC.GUI;

import MCC.Enemies.Enemies;
import MCC.Player.Player;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MCC.utils.Resources;
import MCC.utils.SwapPanel;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.Random;

public class GamePanel extends JPanel {

    private final Image gameBackground;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    public int score;
    private static final int START_SCORE = 0;
    private static final int SCORE_POSITION_X = 50;
    private static final int SCORE_POSITION_Y = 50;
    private static final int SCORE_SIZE = 50;

    private static final int STARTDIFFICULTY = 5;
    private static final int DIFFICULTY_TIME_UPDATE = 300;
    protected int gameSpeed = 5;  //Più e bassa più il gioco scorre veloce

    private static final int enemiesGenerationWait = 1000; //Piu è bassa più le auto possono comparire vicine
    private static final int buildsGenerationWait = 500; //Piu è bassa più i palazzi possono comparire vicini
    private static int pixelOffset = 5;

    private static final int INAIRTIME = 0; //Tempo in volo
    private static final int JUMPMAX = 80;  //Altezza del salto
    private static final int JUMPSTART = 0; //Variabile contatore per la partenza del salto
    private static int jumpHeight = 0; //Variabile che tiene traccia del salto
    private boolean isJumping = false; //Variabile di controllo per evitare doppi salti
    private int jumpSpeed = 5; //Più è bassa, più è veloce

    private Player player;
    private LinkedList<Enemies> enemies;
    private LinkedList<Buildings> builds;

    private Thread threadGenEnemies;
    private Thread buildsGenThread;
    private Thread objectsMovementThread;
    private Thread collisionControlThread;
    private Thread scoreThread;

    public GamePanel(Player pPlayer) {
        this.setSize(WIDTH, HEIGHT);
        this.gameBackground = Resources.getImage("/MCC/GUI/images/game.png");

        this.player = pPlayer;

        this.enemies = new LinkedList<Enemies>();
        this.enemies.add(new Enemies());

        this.builds = new LinkedList<Buildings>();
        this.builds.add(new Buildings());

        this.addKeyListener(new KeyListener());

    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(gameBackground, 0, 0, null);

        g.setColor(Color.RED);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, SCORE_SIZE));
        g.drawString("Punteggio: " + this.score, SCORE_POSITION_X, SCORE_POSITION_Y);

        try {
            for (Buildings build : builds) {
                build.drawBuildings(g);
            }
        } catch (ConcurrentModificationException ex) {
            //System.out.println("Eccezione soffocata nella lista concatenata, non pericolosa");
        }

        try {
            for (Enemies enemy : enemies) {
                enemy.drawEnemies(g);
                //Per visualizzare i collider dei nemici
                //g.fillRect(enemy.enemyCollider.x, enemy.enemyCollider.y, enemy.enemyCollider.width, enemy.enemyCollider.height); //Controllo dei rettangoli nemici
            }
        } catch (ConcurrentModificationException ex) {
            //System.out.println("Eccezione soffocata nella lista concatenata, non pericolosa");
        }

        this.player.setPlayer();
        this.player.drawPlayer(g);

        //Per visualizzare il collider del player
        //g.fillRect(player.playerCollider.x, player.playerCollider.y, player.playerCollider.width, player.playerCollider.height); //Controllo del rettangolo player
    }

//Inizializzazione della partita
    public void StartGame() {

        //Inizializzazione del punteggio
        this.score = START_SCORE;

        //Pulizia liste concatenate
        this.enemies.clear();
        this.builds.clear();

        //Istanziamento dei thread e avvio
        this.buildsGenThread = new Thread(new BuildingsGenThread());
        this.buildsGenThread.start();
        this.objectsMovementThread = new Thread(new ObjectsMovementThread());
        this.objectsMovementThread.start();
        this.threadGenEnemies = new Thread(new EnemiesGenThread());
        this.threadGenEnemies.start();
        this.scoreThread = new Thread(new ScoreThread());
        this.scoreThread.start();
        this.collisionControlThread = new Thread(new CollisionControlThread());
        this.collisionControlThread.start();

        MainFrame.gameAudio.playLoop();

    }

    public void GameOver() {
        //Fine partita
        this.buildsGenThread.stop();
        this.objectsMovementThread.stop();
        this.threadGenEnemies.stop();
        this.scoreThread.stop();

        MainFrame.gameAudio.stop();
        MainFrame.gameOverAudio.play();

    }

    public void resetDifficulty() {
        this.gameSpeed = STARTDIFFICULTY;
        this.jumpSpeed = STARTDIFFICULTY;
    }

    public int getScore() {
        return this.score;
    }

    private static class Buildings {

        private Image build;
        private Point generationPoint = new Point();

        private static final int SPAWN_COORDINATE_X = 145;
        private static final int SPAWN_COORDINATE_Y = 1500;

        public Buildings() {

            this.build = Resources.getImage("/MCC/World/images/build.png");

            this.generationPoint.x = SPAWN_COORDINATE_X;
            this.generationPoint.y = SPAWN_COORDINATE_Y;

        }

        public void drawBuildings(Graphics g) {
            g.drawImage(build, this.generationPoint.y, this.generationPoint.x, null);
        }

        public void moveBuildings() {
            this.generationPoint.y -= pixelOffset;
        }

    }

    //Thread generatore degli edifici
    private class BuildingsGenThread implements Runnable {

        @Override
        public void run() {

            //Uso una variabile ausiliaria
            int buildsWait;

            while (true) {
                Random randomSpaceBuildings = new Random();
                buildsWait = randomSpaceBuildings.nextInt(2);

                if (buildsWait == 1) {
                    builds.add(new Buildings());
                }

                //Mando il Thread in sleep per evitare sovrapposizioni
                try {
                    Thread.sleep(buildsGenerationWait);
                } catch (InterruptedException ex) {
                }
            }

        }
    }

    private class EnemiesGenThread implements Runnable {

        @Override
        public void run() {

            //Anche qui variabile ausiliaria
            int enemiesWait;

            while (true) {
                Random randomSpaceEnemies = new Random();
                enemiesWait = randomSpaceEnemies.nextInt(2);

                if (enemiesWait == 1) {
                    enemies.add(new Enemies());
                }

                //Evito anche qui sovrapposizioni
                try {
                    Thread.sleep(enemiesGenerationWait);
                } catch (InterruptedException ex) {
                }
            }

        }

    }

    //Thread per i movimenti degli oggetti
    private class ObjectsMovementThread implements Runnable {

        @Override
        public void run() {

            while (true) {

                for (Enemies enemy : enemies) {
                    enemy.moveEnemies();
                }

                for (Buildings build : builds) {
                    build.moveBuildings();
                }

                try {
                    Thread.sleep(gameSpeed);
                } catch (InterruptedException ex) {
                }

                repaint();

            }

        }

    }

    //Thread per il salto del giocatore
    private class JumpThread implements Runnable {

        @Override
        public void run() {

            if (isJumping == true) {
                return;
            }
            isJumping = true;

            while (jumpHeight <= JUMPMAX) {
                player.jumpOn();
                jumpHeight++;
                try {
                    Thread.sleep(jumpSpeed);
                } catch (InterruptedException ex) {
                }
            }

            try {
                Thread.sleep(INAIRTIME);
            } catch (InterruptedException ex) {
            }

            while (jumpHeight >= JUMPSTART) {
                player.jumpDown();
                jumpHeight--;
                try {
                    Thread.sleep(jumpSpeed);
                } catch (InterruptedException ex) {
                }

            }
            isJumping = false;

        }

    }

    //Thread per il controllo delle collisioni
    public class CollisionControlThread implements Runnable {

        @Override
        public void run() {

            boolean inGame = true;

            while (inGame) {
                try {
                    for (Enemies enemy : enemies) {
                        if (player.playerCollider.intersects(enemy.enemyCollider)) {
                            inGame = false;
                            SwapPanel.SwapPanel(MainFrame.gamePanel, MainFrame.gameOverPanel);

                            //Salvo il punteggio ottenuto su file e visualizzo
                            MainFrame.gameOverPanel.saveHighScore();
                            MainFrame.gameOverPanel.loadHighScore();

                            GameOver();

                            //Reset della difficoltà (velocità gioco)
                            resetDifficulty();
                        }

                    }
                } catch (ConcurrentModificationException ex) {

                } catch (NullPointerException ex) {

                }
            }

        }

    }

    //Thread per il conteggio del punteggio
    private class ScoreThread implements Runnable {

        @Override
        public void run() {

            while (true) {

                if (score % DIFFICULTY_TIME_UPDATE == 0 && score != 0) {

                    jumpSpeed = jumpSpeed - 1;
                    gameSpeed = gameSpeed - 1;

                }

                score = score + 1;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
        }

    }

    public class KeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent ke) {

            if (ke.getKeyCode() == KeyEvent.VK_SPACE) {

                Thread threadJump = new Thread(new JumpThread());
                threadJump.start();
            }
        }
    }

}
