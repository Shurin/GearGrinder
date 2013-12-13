package com.geargrinder.level.tile;

import com.geargrinder.graphics.Screen;
import com.geargrinder.graphics.Sprite;

public class WalkableTile extends Tile {

	public WalkableTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, sprite);
	}
}
