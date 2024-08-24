package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;

public class PlayingState extends State implements Statemethods {
	

	private Player player;
	private LevelManager levelManager;
	
	public PlayingState(Game game) {
		super(game);
		this.initClasses();
	}
	
	private void initClasses()
	{
		this.levelManager = new LevelManager(this.game);
		this.player = new Player(200, 200, (int) (96 * Game.SCALE), (int) (96 * Game.SCALE));
		this.player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
	}
	
	public void windowFocusLost()
	{
		this.player.resetDirBooleans();
	}
	
	public Player getPlayer()
	{
		return this.player;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		this.levelManager.update();
		this.player.update();
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		this.levelManager.draw(g);
		this.player.render(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_A:
				this.player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				this.player.setRight(true);
				break;
			case KeyEvent.VK_X:
				this.player.setAttacking(true);
				break;
			case KeyEvent.VK_Q:
				this.player.setStrongAttacking(true);
				break;
			case KeyEvent.VK_SPACE:
				this.player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE:
				Gamestate.state = Gamestate.MENU;
				break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode())
		{
			
			case KeyEvent.VK_A:
				this.player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				this.player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				this.player.setJump(false);
				break;
//			case KeyEvent.VK_SPACE:
//				gamePanel.getGame().getPlayer().setAttacking(false);
//				break;
		}
		
	}
}
