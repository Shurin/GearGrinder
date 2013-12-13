package com.geargrinder.entity.mob;

import java.util.ArrayList;

import com.geargrinder.graphics.AnimatedSprite;
import com.geargrinder.graphics.Screen;
import com.geargrinder.graphics.SpriteSheet;

public class testnpc extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.ninjabot_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.ninjabot_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.ninjabot_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.ninjabot_right, 32, 32, 3);

	private AnimatedSprite animSprite = left;

	public testnpc(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		sprite = animSprite.getSprite();
		this.Name = "a test npc";
		this.mobHP = 2147483647;
		this.npc = true;
		this.npcType = "a simple test npc";
		this.npcText = new ArrayList<String>();
		npcText.add("Hello.");
		npcText.add(" ");
		npcText.add("I am the first test NPC.");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add("Please press ENTER to continue ...");
	}

	public void update() {
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
