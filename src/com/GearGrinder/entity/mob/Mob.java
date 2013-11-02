package com.GearGrinder.entity.mob;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.entity.Projectile.Projectile;
import com.GearGrinder.graphics.Screen;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false; // ******* if everything is working ok, delete this line

	protected enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	protected Direction dir;
	
	public void move(int xa, int ya) {
		// this allows "sliding" on solid tile edges
		if(xa !=0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			return;
		}
		// movement handling
		if (ya < 0)	dir = Direction.UP;
		if (ya > 0)	dir = Direction.DOWN;
		if (xa < 0)	dir = Direction.LEFT;
		if (xa > 0)	dir = Direction.RIGHT;

		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
		}
	}

	public int abs(double value){
		if(value < 0) return -1;
		else return 1;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
	
	protected void shoot(int x, int y, double dir, Projectile p, int manacost){
		if(Player.currentmagic > 5){
			level.add(p);
			Player.currentmagic-=manacost;
			Player.magicpercent = Player.currentmagic / Player.maxmagic * 100;
		}
	}
	
	private boolean collision(int xa, int ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++){
			int xt = ((x + xa) + c % 2 * 14 - 8) /16; // x is handled with % operand
			int yt = ((y + ya) + c / 2 + 12 + 3) /16; // y is handled with / operand
			if (level.getTile(xt, yt).solid()) solid = true;
		}		
		return solid;
	}
}
