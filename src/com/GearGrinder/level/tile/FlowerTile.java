package com.GearGrinder.level.tile;

import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;

public class FlowerTile extends Tile {

	public FlowerTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 4, y << 4, sprite);
	}

}
