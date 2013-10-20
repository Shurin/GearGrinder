package com.GearGrinder.level.tile;

import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x << 4, y << 4, sprite);
	}
	
	public boolean solid(){
		return true;
	}

}
