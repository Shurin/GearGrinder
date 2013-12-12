package com.GearGrinder.entity.mob;

import com.GearGrinder.Game;
import com.GearGrinder.graphics.AnimatedSprite;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.SpriteSheet;

public class campfire extends Mob {
	private AnimatedSprite flames = new AnimatedSprite(SpriteSheet.campfire_flames, 32, 32, 3);
	private AnimatedSprite noflames = new AnimatedSprite(SpriteSheet.campfire_noflames, 32, 32, 1);

	private AnimatedSprite animSprite = noflames;

	public campfire(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		sprite = animSprite.getSprite();
		this.Name = "campfire";
		this.mobHP = 214748364;
		this.npc = false;
	}

	public void update() {
		if (Game.nightTime) animSprite = flames;
		else animSprite = noflames;

		animSprite.update();

		this.mobXL = getX() - 10;
		this.mobXR = getX() + 10;
		this.mobYB = getY() - 22; // this is actually the top edge
		this.mobYT = getY() + 10; // this is actually the bottom edge
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite);
	}
}
