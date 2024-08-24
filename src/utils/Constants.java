package utils;

public class Constants {
	
	public static class Directions
	{
		public static final int LEFT =0;
		public static final int RIGHT = 1;
		public static final int UP = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants
	{
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK_1 = 2;
		public static final int ATTACK_2 = 3;
		public static final int BOW_ATTACK = 4;
		public static final int HIT = 5;
		public static final int DIE = 6;
		
		
		public static int getSpriteAmount(int playerAction)
		{
			switch(playerAction)
			{
				case IDLE:
				case ATTACK_1:
				case ATTACK_2:
					return 6;
				case RUNNING:
					return 8;
				case BOW_ATTACK:
					return 9;
				case HIT:
				case DIE:
					return 4;
				default:
					return 1;
			}
		}
	}

}
