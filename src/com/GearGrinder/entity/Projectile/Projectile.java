package com.GearGrinder.entity.Projectile;

import java.util.Random;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.graphics.Sprite;

public class Projectile extends Entity{

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny; //vector variables
	protected double speed, range, damage, distance;
	
	protected final Random random = new Random();//var for random range
	
	public Projectile(int x, int y, double dir){
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x=x;
		this.y=y;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getSpriteSize(){
		return sprite.SIZE;
	}
	
	protected void move(){
		
	}
}
