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

		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;//gravity
	}
	
	public void update(){
		time++;
		if(time >= 7400 -1) time = 0;// fail safe to clear "everlasting particles" 
		if(time > life)	remove();
		za -= 0.1;//gravity
		
		if(zz < 0){
			zz = 0;//makes sure particles don't go below "floor"
			za *= -0.55;//bounce degrade
			xa *= 0.4;// travel degrade
			ya *= 0.4;
		}
		
		move(xx + xa, (yy + ya) + (zz + za));
	}
	
	private void move(double x, double y) {
		if(collision(x, y)){
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;//gravity
		
	}
	
	public boolean collision(double x, double y) {
		boolean solid = false;
		for (int c = 0; c < 4; c++){
			double xt = (x - c % 2 * 16) /16;
			double yt = (y - c / 2 * 16) /16;
			int ix = (int) Math.ceil(xt);// ceil rounds up
			int iy = (int) Math.ceil(yt);
			if(c % 2 == 0) ix = (int) Math.floor(xt);// floor rounds down
			if(c / 2 == 0) iy = (int) Math.floor(yt);
			
			if (level.getTile(ix, iy).solid()) solid = true;
		}		
		return solid;
	}	

	public void render(Screen screen){
		sprite = Sprite.particle_normal;
		screen.renderSprite((int)xx - 1, (int)yy - (int)zz, sprite, true);//gravity ( -(int) zz  ) only
	}
}
