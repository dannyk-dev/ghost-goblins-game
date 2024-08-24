package entities;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

import main.Game;
import utils.LoadSave;
import static utils.HelperMethods.*;

public class Player extends Entity {
	
	private BufferedImage[][] animations;
	private int animTick, animIndex, animSpeed = 5;
	private int playerAction = IDLE;
	private boolean moving = false, attacking=false, strong_attacking=false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 2f;
	
	// gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
	
	private int[][] levelData;
	private float xDrawOffset = 42f * Game.SCALE;
	private float yDrawOffset = 38f * Game.SCALE;
	
	public Player(float x, float y, int width, int height)
	{
		super(x, y, width, height);
		this.loadAnimations();
		initHitbox(x, y, this.width/2.5f, this.height/1.5f);
		
	}
	
	public void update()
	{
		this.updatePos();
		this.updateHitbox();
		this.updateAnimationTick();		
		this.setAnimation();
	}
	
	public void render(Graphics g)
	{
		g.drawImage(this.animations[playerAction][this.animIndex], (int)(this.hitbox.x - this.xDrawOffset), (int)(this.hitbox.y - this.yDrawOffset), this.width, this.height, null);
//		this.drawHitbox(g);
	}
	
	private void loadAnimations()
	{
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
		this.animations = new BufferedImage[7][9];
		
		for (int i = 0; i < this.animations.length; i++)
		{
			for (int j = 0; j < this.animations[i].length;j++)
			{				
				this.animations[i][j] = img.getSubimage(j*100, i*100, 96, 96);
			}
		}
	}
	
	
	private void updateAnimationTick()
	{
		this.animTick++;
		if (this.animTick >= this.animSpeed)
		{
			this.animTick = 0;
			this.animIndex++;
			
			if (this.animIndex >= getSpriteAmount(playerAction))
			{
				this.animIndex = 0;
				this.attacking = false;
				this.strong_attacking = false;
			}
				
		}
	}
	
	private void setAnimation()
	{
		int startAnim = playerAction;
		
		if (this.moving)
		{
			this.playerAction = RUNNING;
		}
		else 
		{
			this.playerAction = IDLE;
		}
		
		if (inAir)
		{
			this.playerAction = IDLE;
		}
		
		
		if (this.attacking)
		{
			this.playerAction = ATTACK_1;
		}
		
		if (this.strong_attacking)
			this.playerAction = ATTACK_2;
		
		if (startAnim != playerAction)
		{
			this.resetAnimTick();
		}
	}
	
	private void resetAnimTick()
	{
		this.animTick = 0;
		this.animIndex = 0;
	}
	
	private void updatePos()
	{
		this.moving = false;
		
		if (jump)
		{
			jump();
		}
		
		if (!left && !right && !inAir)
			return;
		
		float xSpeed = 0;
		
		if (left)
		{
			xSpeed -= this.playerSpeed;
//			this.moving = true;
		}
			
		
		if (right)
		{
			xSpeed += this.playerSpeed;
//			this.moving = true;
		}
		
		if (!inAir)
			if (!isEntityOnFloor(this.hitbox, this.levelData))
				this.inAir = true;
			
		if (inAir)
		{
			this.updateYPos(xSpeed);
		}
		else
		{
			this.updateXPos(xSpeed);
		}
		
		this.moving = true;
	}
	
	private void updateYPos(float xSpeed)
	{
		if (CanMoveHere(this.hitbox.x, this.hitbox.y + this.airSpeed, this.hitbox.width, this.hitbox.height, this.levelData))
		{
			this.y += this.airSpeed;
			this.airSpeed += gravity;
			this.updateXPos(xSpeed);
		}
		else
		{
			this.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, this.airSpeed);
			if (this.airSpeed > 0)
				this.resetInAir();
			else 
				this.airSpeed = fallSpeedAfterCollision;
			
			updateXPos(xSpeed);
		}
	}
	
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(this.hitbox.x + xSpeed, this.hitbox.y + this.airSpeed, this.hitbox.width, this.hitbox.height, this.levelData))
		{
			this.x += xSpeed;
//			this.moving = true;
		} else {
			this.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}
		
	}
	
	private void jump()
	{
		if (this.inAir)
		{
			return;
		}
		
		this.inAir = true;
		this.airSpeed = this.jumpSpeed;
	}
	
	private void resetInAir()
	{
		this.inAir = false;
		this.airSpeed = 0;
	}

	public void loadLevelData(int[][] levelData)
	{
		this.levelData = levelData;
		if (!isEntityOnFloor(hitbox, levelData))
		{
			this.inAir = true;
		}
		
	}
	
	public void resetDirBooleans()
	{
		this.left = false;
		this.right = false;
		this.up = false;
		this.down = false;
	}
	
	public void setAttacking(boolean attacking)
	{
		this.attacking = attacking;
	}
	
	public void setStrongAttacking(boolean strongAttacking)
	{
//		this.attacking = attacking;
		this.strong_attacking = strongAttacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJump(boolean jump)
	{
		this.jump = jump;
	}
	
	
	
}
