package com.geargrinder.entity.mob;

import java.util.ArrayList;

import com.geargrinder.graphics.AnimatedSprite;
import com.geargrinder.graphics.Screen;
import com.geargrinder.graphics.SpriteSheet;

public class npc_smith extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.smith_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.smith_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.smith_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.smith_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	public npc_smith(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		sprite = animSprite.getSprite();
		this.Name = "Verdyn";
		this.mobHP = 2147483647;
		this.npc = true;
		this.npcType = "quest";
		this.npcText = new ArrayList<String>();
		npcText.add("Hello.");
		npcText.add(" ");
		npcText.add("I am the NPC who tells you some story about how you got");
		npcText.add("here. And I will start a quest chain, teaching you the");
		npcText.add("basics of the game.");
		npcText.add(" ");
		npcText.add("In the process, I will have you kill some MOBs around");
		npcText.add("here.");
		npcText.add(" ");
		npcText.add("You will receive some gold, some items, and some XP.");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add(" ");
		npcText.add("For starters, head east and kill 10 Ninja Bots.");
		npcText.add("Talk to me when you're done for a reward.");
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
