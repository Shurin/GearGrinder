package com.GearGrinder.graphics;
// sprites can use blank pixel transparency or they 
// can use the color 0xff00ff
// just remember that the alpha for that color 
// must be 100% (ff) so 0xffff00ff
public class Sprite {

	public final int SIZE;
	private int x, y, width, height;
	public int[] pixels;
	protected SpriteSheet sheet;

	// Sprite can take in size, x, y, SpriteSheet.name
	// OR it can take size and fixed color like below
	public static Sprite voidSprite = new Sprite(16, 0x1B87E0);
	//SPAWN LEVEL SPRITES
	//DAY SPRITES
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_floor = new Sprite(16, 1, 1, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall3 = new Sprite(16, 3, 0, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall4 = new Sprite(16, 3, 1, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall5 = new Sprite(16, 3, 2, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall6 = new Sprite(16, 4, 0, SpriteSheet.spawn_level_tiles);	
	public static Sprite spawn_wall7 = new Sprite(16, 4, 1, SpriteSheet.spawn_level_tiles);	
	public static Sprite spawn_wall8 = new Sprite(16, 5, 0, SpriteSheet.spawn_level_tiles);	
	public static Sprite spawn_wall9 = new Sprite(16, 5, 1, SpriteSheet.spawn_level_tiles);	
	public static Sprite spawn_wall10 = new Sprite(16, 6, 0, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall11 = new Sprite(16, 6, 1, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_wall12 = new Sprite(16, 6, 2, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_stairmiddle = new Sprite(16, 2, 1, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_stairleft = new Sprite(16, 2, 1, SpriteSheet.spawn_level_tiles);
	public static Sprite spawn_stairright = new Sprite(16, 2, 1, SpriteSheet.spawn_level_tiles);
	public static Sprite portal_up = new Sprite(16, 1, 2, SpriteSheet.spawn_level_tiles);
	public static Sprite portal_down = new Sprite(16, 2, 2, SpriteSheet.spawn_level_tiles);
	
	//NIGHT SPRITES
	public static Sprite spawn_grass_night = new Sprite(16, 0, 0, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_hedge_night = new Sprite(16, 1, 0, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_water_night = new Sprite(16, 2, 0, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_wall1_night = new Sprite(16, 0, 1, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_wall2_night = new Sprite(16, 0, 2, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_floor_night = new Sprite(16, 1, 1, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_wall3_night = new Sprite(16, 3, 0, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_wall4_night = new Sprite(16, 3, 1, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_wall5_night = new Sprite(16, 3, 2, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_wall6_night = new Sprite(16, 4, 0, SpriteSheet.spawn_level_tiles_night);	
	public static Sprite spawn_wall7_night = new Sprite(16, 4, 1, SpriteSheet.spawn_level_tiles_night);	
	public static Sprite spawn_wall8_night = new Sprite(16, 5, 0, SpriteSheet.spawn_level_tiles_night);	
	public static Sprite spawn_wall9_night = new Sprite(16, 5, 1, SpriteSheet.spawn_level_tiles_night);	
	public static Sprite spawn_wall10_night = new Sprite(16, 6, 0, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_wall11_night = new Sprite(16, 6, 1, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_wall12_night = new Sprite(16, 6, 2, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_stairmiddle_night = new Sprite(16, 2, 1, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_stairleft_night = new Sprite(16, 2, 1, SpriteSheet.spawn_level_tiles_night);
	public static Sprite spawn_stairright_night = new Sprite(16, 2, 1, SpriteSheet.spawn_level_tiles_night);
	public static Sprite portal_up_night = new Sprite(16, 1, 2, SpriteSheet.spawn_level_tiles_night);
	public static Sprite portal_down_night = new Sprite(16, 2, 2, SpriteSheet.spawn_level_tiles_night);
		
	

	//PARTICLES
	public static Sprite particle_normal = new Sprite(3, 0xff68B96F);//size in px and color
	// PROJECTILE SPRITES
	public static Sprite projectile_wizard = new Sprite (16, 0, 0, SpriteSheet.projectile_wizard);
	public static Sprite projectile_quake = new Sprite (16, 1, 0, SpriteSheet.projectile_wizard);
	//PLAYER SPRITES
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
	//PLAYER2 SPRITES
	public static Sprite player2_up = new Sprite (32, 0, 0, SpriteSheet.player2sprites);
	public static Sprite player2_up_1 = new Sprite (32, 0, 1, SpriteSheet.player2sprites);
	public static Sprite player2_up_2 = new Sprite (32, 0, 2, SpriteSheet.player2sprites);
	public static Sprite player2_down = new Sprite (32, 2, 0, SpriteSheet.player2sprites);
	public static Sprite player2_down_1 = new Sprite (32, 2, 1, SpriteSheet.player2sprites);
	public static Sprite player2_down_2 = new Sprite (32, 2, 2, SpriteSheet.player2sprites);	
	public static Sprite player2_left = new Sprite (32, 3, 0, SpriteSheet.player2sprites);
	public static Sprite player2_left_1 = new Sprite (32, 3, 1, SpriteSheet.player2sprites);	
	public static Sprite player2_left_2 = new Sprite (32, 3, 2, SpriteSheet.player2sprites);
	public static Sprite player2_right = new Sprite (32, 1, 0, SpriteSheet.player2sprites);	
	public static Sprite player2_right_1 = new Sprite (32, 1, 1, SpriteSheet.player2sprites);
	public static Sprite player2_right_2 = new Sprite (32, 1, 2, SpriteSheet.player2sprites);	
	//
	//MOB SPRITES
	//
	//NINJA BOT
	public static Sprite ninjabot = new Sprite(32, 0, 0, SpriteSheet.ninjabot_down);
	//NINJA BOT BOSS
	public static Sprite ninjabotboss = new Sprite(32, 0, 0, SpriteSheet.ninjabotboss_down);
	
	
	protected Sprite(SpriteSheet sheet, int width, int height){
		if(width == height) SIZE = width;
		else SIZE = -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite (int width, int height, int color){
		SIZE = -1;//not used, just makes this function error free
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}

	public Sprite(int[] pixels, int width, int height){
		if(width == height) SIZE = width;
		else SIZE = -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	private void setColor(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}
}
