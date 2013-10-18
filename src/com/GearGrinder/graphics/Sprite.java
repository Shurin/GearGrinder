package com.GearGrinder.graphics;
// sprites can use blank pixel transparency or they 
// can use the color 0xff00ff
// just remember that the alpha for that color 
// must be 100% (ff) so 0xffff00ff
public class Sprite {

	public final int SIZE;
	private int x, y;
	public int[] pixels;
	public SpriteSheet sheet;

	// Sprite can take in size, x, y, SpriteSheet.name
	// OR it can take size and fixed color like below
	public static Sprite voidSprite = new Sprite(16, 0x1B87E0);
	
	//SPAWN LEVEL SPRITES
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level_tiles);
	
	
	
	//PLAYER SPRITES
	//
	// 8 directional player sprites + animation frames
	// _1 is right foot forward
	// _2 is left foot forward
	public static Sprite player_up = new Sprite (32, 0, 0, SpriteSheet.playersprites);
	public static Sprite player_up_1 = new Sprite (32, 0, 1, SpriteSheet.playersprites);
	public static Sprite player_up_2 = new Sprite (32, 0, 2, SpriteSheet.playersprites);	
	public static Sprite player_down = new Sprite (32, 2, 0, SpriteSheet.playersprites);
	public static Sprite player_down_1 = new Sprite (32, 2, 1, SpriteSheet.playersprites);
	public static Sprite player_down_2 = new Sprite (32, 2, 2, SpriteSheet.playersprites);	
	public static Sprite player_left = new Sprite (32, 3, 0, SpriteSheet.playersprites);
	public static Sprite player_left_1 = new Sprite (32, 3, 1, SpriteSheet.playersprites);
	public static Sprite player_left_2 = new Sprite (32, 3, 2, SpriteSheet.playersprites);	
	public static Sprite player_right = new Sprite (32, 1, 0, SpriteSheet.playersprites);
	public static Sprite player_right_1 = new Sprite (32, 1, 1, SpriteSheet.playersprites);
	public static Sprite player_right_2 = new Sprite (32, 1, 2, SpriteSheet.playersprites);	
	public static Sprite player_upright = new Sprite (32, 5, 0, SpriteSheet.playersprites);
	public static Sprite player_upright_1 = new Sprite (32, 5, 1, SpriteSheet.playersprites);
	public static Sprite player_upright_2 = new Sprite (32, 5, 2, SpriteSheet.playersprites);	
	public static Sprite player_downright = new Sprite (32, 4, 0, SpriteSheet.playersprites);
	public static Sprite player_downright_1 = new Sprite (32, 4, 1, SpriteSheet.playersprites);
	public static Sprite player_downright_2 = new Sprite (32, 4, 2, SpriteSheet.playersprites);	
	public static Sprite player_upleft = new Sprite (32, 7, 0, SpriteSheet.playersprites);
	public static Sprite player_upleft_1 = new Sprite (32, 7, 1, SpriteSheet.playersprites);
	public static Sprite player_upleft_2 = new Sprite (32, 7, 2, SpriteSheet.playersprites);	
	public static Sprite player_downleft = new Sprite (32, 6, 0, SpriteSheet.playersprites);
	public static Sprite player_downleft_1 = new Sprite (32, 6, 1, SpriteSheet.playersprites);
	public static Sprite player_downleft_2 = new Sprite (32, 6, 2, SpriteSheet.playersprites);	
	
	

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite(int size, int color) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	private void setColor(int color) {
		for (int i = 0; i < SIZE * SIZE; i++) {
			pixels[i] = color;
		}
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y)	* sheet.SIZE];
			}
		}
	}
}