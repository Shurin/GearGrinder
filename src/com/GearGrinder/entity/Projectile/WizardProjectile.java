package com.GearGrinder.entity.Projectile;

import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;

public class WizardProjectile extends Projectile{

	public WizardProjectile(int x, int y, int dir) {
		super(x, y, dir);
		range = 200;
		speed = 20;
		damage = 20;
		rateOfFire = 15;
		sprite = Sprite.spawn_wall1;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update(){
		move();
	}
	
	protected void move(){
		x += nx;
		y += ny;
	}
	
	public void render(Screen screen){
		screen.renderTile(x, y, sprite);
	}
}
