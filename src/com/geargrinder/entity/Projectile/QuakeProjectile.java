package com.geargrinder.entity.Projectile;

import com.geargrinder.entity.spawner.ParticleSpawner;
import com.geargrinder.graphics.Screen;
import com.geargrinder.graphics.Sprite;

public class QuakeProjectile extends Projectile {

	public static final int FIRE_RATE = 25;// higher the value the slower the rate of fire

	public QuakeProjectile(int x, int y, double dir, String Name) {
		super(x, y, dir, Name);
		range = 150 + random.nextInt(20);
		speed = 2;
		damage = 100;
		sprite = Sprite.projectile_quake;

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		if (level.projectileCollision((int) (x + nx), (int) (y + ny), 8, 4, 4)) {
			level.add(new ParticleSpawner((int) x, (int) y + 6, 15, 50, level));
			remove();
		}
		move();
	}

	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) remove();
	}

	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - 8, (int) y, this);
	}
}
