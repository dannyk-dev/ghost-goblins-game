package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	
	public Entity(float x, float y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;		
	}
	
	protected void drawHitbox(Graphics g)
	{
		// for debugging the hitbox
		g.setColor(Color.pink);
		g.drawRect(
				(int)this.hitbox.x, 
				(int)this.hitbox.y, 
				(int)this.hitbox.width, 
				(int)this.hitbox.height
			);
	}
	 
	protected void initHitbox(float x, float y, float width, float height)
	{
		
		this.hitbox = new Rectangle2D.Float(x, y, width/3, (float) (height/3.5f));
		
	}
	
	protected void updateHitbox()
	{
		this.hitbox.x = this.x;
		this.hitbox.y = this.y;
	}
	
	public Rectangle2D.Float getHitbox()
	{
		return this.hitbox;
	}
	
}
