package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "Soldier/Soldier.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	
	public static BufferedImage getSpriteAtlas(String fileName)
	{
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		
		try 
		{
			img = ImageIO.read(is);
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally 
		{
			try 
			{
				is.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		return img;
	}
	
	public static int[][] getLevelData()
	{
		int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);
		
		for (int i = 0; i < img.getHeight(); i++)
		{
			for (int j = 0; j < img.getWidth(); j++)
			{
				Color color = new Color(img.getRGB(j, i));
				int value = color.getRed();
				
				if (value >= 48)
					value = 0;
				
				levelData[i][j] = value;
				
			}
		}
		
//		System.out.println(levelData.toString());
		return levelData;
		
	}

}









