package com.GearGrinder.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.entity.Projectile.Projectile;
import com.GearGrinder.entity.Projectile.WizardProjectile;
import com.GearGrinder.graphics.Sprite;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 2;
	protected boolean moving = false;
	
	protected List<Projectile> projectiles = new ArrayList<Projectile>();

	public void move(int xa, int ya) {
		// this allows "sliding" on solid tile edges
		if(xa !=0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			// diagonal movement has to be here
			if (xa > 0 && ya > 0) dir = 4;
			if (xa > 0 && ya < 0) dir = 5;
			if (xa < 0 && ya > 0) dir = 6;
			if (xa < 0 && ya < 0) dir = 7;
			return;
		}
		
		// this assigns what direction we're going
		// based off of x/y travel
		// 0 up, 1 east, 2 down, 3 west
		// up,down,left,right must be here
		if (xa > 0)	dir = 1; // east
		if (xa < 0)	dir = 3; // west
		if (ya > 0)	dir = 2; // down
		if (ya < 0)	dir = 0; // up

		if (!collision(xa, ya)) {
			// x or y = -1, 0, 1
			// 0 == not moving
			x = x + xa;
			y = y + ya;
		}
	}

	public void update() {

	}
	
	protected void shoot(int x, int y, double dir){
		Projectile p = new WizardProjectile(x-9, y, (int) dir);
		projectiles.add(p);
		level.add(p);
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

	public void render() {

	}
}
