package com.GearGrinder.entity.mob;

import com.GearGrinder.graphics.AnimatedSprite;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.SpriteSheet;

public class campfire extends Mob{
	private AnimatedSprite flames = new AnimatedSprite(SpriteSheet.campfire_flames, 32, 32, 4);

	private AnimatedSprite animSprite = flames;
	
	public campfire(int x, int y){
		this.x = x * 16;
		this.y = y * 16;
		sprite = animSprite.getSprite();
		this.Name = "campfire";
		this.mobHP = 214748364;
		this.npc = false;		
	}
	
	public void update() {
			animSprite.update();			
						
		this.mobXL = getX() - 10;
		this.mobXR = getX() + 10;
		this.mobYB = getY() - 22; //this is actually the top edge
		this.mobYT = getY() + 10; //this is actually the bottom edge
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite);
	}
}
