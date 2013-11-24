package com.GearGrinder.level.tile;

import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.level.tile.Tile;

public class WalkableTile extends Tile{

	public WalkableTile(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 4, y << 4, sprite);
	}
}
