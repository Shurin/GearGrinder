package com.GearGrinder.level.tile;


import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.graphics.SpriteSheet;
import com.GearGrinder.level.tile.spawn_level.SpawnFloorTile;
import com.GearGrinder.level.tile.spawn_level.SpawnGrassTile;
import com.GearGrinder.level.tile.spawn_level.SpawnHedgeTile;
import com.GearGrinder.level.tile.spawn_level.SpawnWallTile;
import com.GearGrinder.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
	
	public int x, y;
	public Sprite sprite;
	
	// tile definitions
	public static Tile spawn_grass_tile = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge_tile = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water_tile = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1_tile = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2_tile = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_wall3_tile = new SpawnWallTile(Sprite.spawn_wall3);
	public static Tile spawn_wall4_tile = new SpawnWallTile(Sprite.spawn_wall4);
	public static Tile spawn_wall5_tile = new SpawnWallTile(Sprite.spawn_wall5);
	public static Tile spawn_wall6_tile = new SpawnWallTile(Sprite.spawn_wall6);
	public static Tile spawn_wall7_tile = new SpawnWallTile(Sprite.spawn_wall7);
	public static Tile spawn_wall8_tile = new SpawnWallTile(Sprite.spawn_wall8);
	public static Tile spawn_wall9_tile = new SpawnWallTile(Sprite.spawn_wall9);
	public static Tile spawn_wall10_tile = new SpawnWallTile(Sprite.spawn_wall10);
	public static Tile spawn_wall11_tile = new SpawnWallTile(Sprite.spawn_wall11);
	public static Tile spawn_wall12_tile = new SpawnWallTile(Sprite.spawn_wall12);
	public static Tile spawn_stairmiddle_tile = new SpawnFloorTile(Sprite.spawn_stairmiddle);
	public static Tile spawn_stairleft_tile = new SpawnFloorTile(Sprite.spawn_stairleft);
	public static Tile spawn_stairright_tile = new SpawnFloorTile(Sprite.spawn_stairright);
	public static Tile spawn_floor_tile = new SpawnFloorTile(Sprite.spawn_floor);
	
	
	// the colors that dictate what tiles are to be rendered
	public final static  int col_spawn_grass = 0xFF00FE00;
	public final static  int col_spawn_hedge = 0xFF2B6A00;
	public final static  int col_spawn_water = 0xFF0026FF;
	public final static  int col_spawn_wall1 = 0xFF808080;
	public final static  int col_spawn_wall2 = 0xFF404040;
	public final static  int col_spawn_wall3 = 0xFF2D2D2D;
	public final static  int col_spawn_wall4 = 0xFF3D3D3D;
	public final static  int col_spawn_wall5 = 0xFF4D4D4D;
	public final static  int col_spawn_wall6 = 0xFF5D5D5D;
	public final static  int col_spawn_wall7 = 0xFF6D6D6D;
	public final static  int col_spawn_wall8 = 0xFF7D7D7D;
	public final static  int col_spawn_wall9 = 0xFF8D8D8D;
	public final static  int col_spawn_wall10 = 0xFF9d9d9d;
	public final static  int col_spawn_wall11 = 0xFFadadad;
	public final static  int col_spawn_wall12 = 0xFFbdbdbd;
	public final static  int col_spawn_stairmiddle = 0xff96F1FF;
	public final static  int col_spawn_stairleft = 0xff85D6E2;
	public final static  int col_spawn_stairright = 0xff63ABB5;
	public final static  int col_spawn_floor = 0xFFFF6A00;
	



	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen){
		
	}
	
	public boolean solid(){ // unless overridden by a subclass, tiles will NOT be solid by default
		return false;
	}

}
