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
	public static Tile spawn_floor_tile = new SpawnFloorTile(Sprite.spawn_floor);
	
	
	// the colors that dictate what tiles are to be rendered
	public final static  int col_spawn_grass = 0xFF00FE00;
	public final static  int col_spawn_hedge = 0xFF2B6A00;
	public final static  int col_spawn_water = 0xFF0026FF;
	public final static  int col_spawn_wall1 = 0xFF808080;
	public final static  int col_spawn_wall2 = 0xFF404040;
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
