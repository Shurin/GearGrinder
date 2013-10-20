package com.GearGrinder.level;

import java.util.ArrayList;
import java.util.List;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size;
	
	private List<Entity> entities = new ArrayList<Entity>();
	
	public static Level spawn = new SpawnLevel("/levels/spawn.png");

	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path) { // loads level from file
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() { // creates random level
		for(int y = 0; y < 64; y++){
			for(int x = 0; x < 64; x++){
				getTile(x, y);
			}
		}
		tile_size = 16;
	}

	protected void loadLevel(String path) {

	}

	public void update() { // updates the levels
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).update();
		}

	}

	private void time() { // manages the time in game ( day / night)

	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		// Scroll keeps track of player location
		// x0 & y0 are the starting cornerpins of the screen
		// x1 & y1 are the far cornerpins of the screen
		int x0 = xScroll >> 4; // >> 4 == / 16
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).render(screen);
		}
	}
	
	public void add(Entity e){
		entities.add(e);
	}

	// Grass = 0xFF00FF00
	// Flower = 0xFFFFFF00
	// Rock = 0xFF7F7F00
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.spawn_grass_tile;
		if (tiles[x + y * width] == Tile.col_spawn_grass) return Tile.spawn_grass_tile;
		if (tiles[x + y * width] == Tile.col_spawn_hedge) return Tile.spawn_hedge_tile;
		if (tiles[x + y * width] == Tile.col_spawn_water) return Tile.spawn_water_tile;
		if (tiles[x + y * width] == Tile.col_spawn_wall1) return Tile.spawn_wall1_tile;
		if (tiles[x + y * width] == Tile.col_spawn_wall2) return Tile.spawn_wall2_tile;
		if (tiles[x + y * width] == Tile.col_spawn_floor) return Tile.spawn_floor_tile;
		
		return Tile.spawn_grass_tile;
	}
}
