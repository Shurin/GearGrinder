package com.GearGrinder.entity.particle;

import java.util.ArrayList;
import java.util.List;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;

public class Particle extends Entity{

	private List<Particle> particles = new ArrayList<Particle>();
	private Sprite sprite;
	
	private int life;
	
	protected double xa, ya, xx, yy;
	
	public Particle(int x, int y, int life){
		this.x = x;
		this.y = y;
		this.xx= x;
		this.yy = y;
		this.life = life;
		sprite = Sprite.particle_normal;

		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
	}
	
	public Particle(int x, int y, int life, int amount){
		this(x, y, life);		
		for(int i = 0; i < amount - 1; i++){
			particles.add(new Particle(x, y, life));
		}
		particles.add(this);
	}
	
	public void update(){
		this.xx += xa;
		this.yy += ya;
	}
	
	public void render(Screen screen){
		screen.renderSprite((int)xx, (int)yy, sprite, true);
	}
}
