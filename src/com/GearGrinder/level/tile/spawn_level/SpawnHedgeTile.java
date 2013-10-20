package com.GearGrinder.level.tile.spawn_level;

import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.level.tile.Tile;

public class SpawnHedgeTile extends Tile{

	public SpawnHedgeTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 4, y << 4, sprite);
	}
	// REMEMBER TO ADD THIS TO SPRITEXTILE CLASS TO DFEINE IF SOLID
	// TILES ARE NOT BY DEFAULT
	public boolean solid() {
		return true;
	}
}
