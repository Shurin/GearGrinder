package com.geargrinder.entity.mob;

import java.util.List;

import com.geargrinder.graphics.AnimatedSprite;
import com.geargrinder.graphics.Screen;
import com.geargrinder.graphics.SpriteSheet;

public class NinjaBotBoss extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.ninjabotboss_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.ninjabotboss_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.ninjabotboss_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.ninjabotboss_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private int xa = 0;
	private int ya = 0;

	public NinjaBotBoss(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		sprite = animSprite.getSprite();
		this.Name = "ninjabotboss";
		this.mobHP = 2000;
		this.npc = false;
	}

	private void move() {
		xa = 0;
		ya = 0;
		List<Player> players = level.getPlayers(this, 50);
		if (players.size() > 0) {
			Player player = players.get(0);
			if (x < player.getX()) xa++;
			if (x > player.getX()) xa--;
			if (y < player.getY()) ya++;
			if (y > player.getY()) ya--;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else walking = false;
	}

	public void update() {
		move();
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}
		this.mobXL = getX() - 10;
		this.mobXR = getX() + 10;
		this.mobYB = getY() - 22; // this is actually the top edge
		this.mobYT = getY() + 10; // this is actually the bottom edge
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		// -16 MUST be there to make sure mob collides properly
		screen.renderMob(x - 16, y - 16, this);
	}
}