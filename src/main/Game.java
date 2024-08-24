package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;

public class Game implements Runnable
{
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 60;
	private final int UPS_SET = 60;
	private Player player;
	private LevelManager levelManager;
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f; 
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	public Game()
	{
		this.initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		
		gamePanel.requestFocus();
		
		this.startGameLoop();
	}
	
	private void startGameLoop()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void initClasses()
	{
		this.levelManager = new LevelManager(this);
		this.player = new Player(200, 200, (int) (96 * Game.SCALE), (int) (96 * Game.SCALE));
		this.player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
	}
	
	public void update()
	{
//		gamePanel.updateGame();
		this.player.update();
		this.levelManager.update();
	}
	
	public void render(Graphics g)
	{
		this.levelManager.draw(g);
		player.render(g);
	}

	@Override
	public void run()
	{
		
		double timePerFrame = 1000000000.0 / this.FPS_SET;
		double timePerUpdate = 1000000000.0 / this.UPS_SET;
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		
		while (true)
		{
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if (deltaU >= 1)
			{
				this.update();
				updates++;
				deltaU--;
			}
			
			if (deltaF >= 1)
			{
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			if (System.currentTimeMillis() - lastCheck >= 1000)
			{
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
		
	}
	
	public void windowFocusLost()
	{
		this.player.resetDirBooleans();
	}
	
	public Player getPlayer()
	{
		return this.player;
	}

}
