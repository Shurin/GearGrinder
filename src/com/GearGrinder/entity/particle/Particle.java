package com.GearGrinder.entity.particle;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;

public class Particle extends Entity{

	
	private Sprite sprite;
	
	private int life;
	private int time = 0;
	
	protected double xx, yy, zz;
	protected double xa, ya, za;
	// zz, za adds gravity to the particles
	// to disable, comment out any // gravity
	public Particle(int x, int y, int life){
		this.x = x;
		this.y = y;
		this.xx= x;
		this.yy = y;
		this.life = life + (random.nextInt(35) + random.nextInt(25) - random.nextInt(59));
		sprite = Sprite.particle_normal;

		this.xa = random.nextGaussian() + 2.0;
		this.ya = random.nextGaussian();
		this.zz = 10.0;//gravity
	}
	
	public void update(){
		time++;
		if(time >= 7400 -1) time = 0;// fail safe to clear "everlasting particles" 
		if(time > life)	remove();
		za -= 0.1;//gravity
		
		if(zz < 0){
			zz = 0;//makes sure particles don't go below "floor"
			za *= -0.8;//bounce degrade
			xa *= 0.5;// travel degrade
			ya *= 0.5;
		}
		
		this.xx += xa;
		this.yy += ya;
		this.zz += za;//gravity
	}
	
	public void render(Screen screen){
		screen.renderSprite((int)xx, (int)yy - (int)zz, sprite, true);//gravity ( -(int) zz  ) only
	}
}
