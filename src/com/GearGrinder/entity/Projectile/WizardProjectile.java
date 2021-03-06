 package com.GearGrinder.entity.Projectile;

import com.GearGrinder.entity.spawner.ParticleSpawner;
import com.GearGrinder.entity.spawner.Spawner;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;

public class WizardProjectile extends Projectile{

	public static final int FIRE_RATE = 1;// higher the value the slower the rate of fire
	
	public WizardProjectile(int x, int y, double dir) {
		super(x, y, dir);
		range = 150 + random.nextInt(20);
		speed = 2;
		damage = 20;
		sprite = Sprite.projectile_wizard;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update(){
		if(level.projectileCollision((int)(x + nx), (int)(y + ny), 8, 4, 4)){
			level.add(new ParticleSpawner((int)x, (int)y + 6, 15, 50, level));
			remove();
		}
		move();
	}
		
	protected void move(){
		x += nx;
		y += ny;
		if(distance() > range) remove();		
	}
	
	private double distance() {
		double dist = 0;		
		dist = Math.sqrt(Math.abs((xOrigin - x)*(xOrigin - x) + (yOrigin - y) * (yOrigin - y)));		
		return dist;
	}

	public void render(Screen screen){
		screen.renderProjectile((int)x - 8, (int)y, this);
	}
}
