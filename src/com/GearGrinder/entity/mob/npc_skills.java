package com.GearGrinder.entity.mob;

import java.util.ArrayList;

import com.GearGrinder.graphics.AnimatedSprite;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.SpriteSheet;

public class npc_skills extends Mob {
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.skills_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.skills_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.skills_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.skills_right, 32, 32, 3);

	private AnimatedSprite animSprite = right;

	public npc_skills(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		sprite = animSprite.getSprite();
		this.Name = "Ivin";
		this.mobHP = 2147483647;
		this.npc = true;
		this.npcType = "skills";
		this.npcText = new ArrayList<String>();
		npcText.add("Hello.");
		npcText.add(" ");
		npcText.add("I am a  general skill trainer");
		npcText.add("We will all be the same throughout the world.");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add("You can learn new skills from us for a small");
		npcText.add("fee, so long as your level is high enough.");
		npcText.add(" ");
		npcText.add("These will be non class specific skills.");
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
