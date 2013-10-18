package com.GearGrinder.level.tile;

import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;

public class GrassTile extends Tile {

	public GrassTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) { // needs to be in EVERY tile class
		screen.renderTile(x << 4, y << 4, this); // << 4 == * 16									
	}

}
