package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
	
	public LevelManager(Game game)
	{
		this.game = game;
//		this.levelSprite = 
		this.importExternalSprites();
		this.levelOne = new Level(LoadSave.getLevelData());
	}
	
	private void importExternalSprites()
	{
		this.levelSprite = new BufferedImage[48];
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				int index = i*12 + j;
				levelSprite[index] = img.getSubimage(j*32, i*32, 32, 32);
				
			}
		}
	}
	
	public void draw(Graphics g)
	{
		for (int i = 0; i < Game.TILES_IN_HEIGHT; i++)
		{
			for (int j = 0; j < Game.TILES_IN_WIDTH; j++)
			{
				int index = this.levelOne.getSpriteIndex(j, i);
				g.drawImage(this.levelSprite[index], Game.TILES_SIZE * j, Game.TILES_SIZE * i, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
		}
		
	}
	
	public void update()
	{
		
	}
	
	public Level getCurrentLevel()
	{
		return levelOne;
	}
	
}
