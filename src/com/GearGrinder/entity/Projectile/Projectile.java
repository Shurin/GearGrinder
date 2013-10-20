package com.GearGrinder.entity.Projectile;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.graphics.Sprite;

public class Projectile extends Entity{

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double nx, ny; //vector variables
	protected double speed, rateOfFire,  range, damage;
	
	public Projectile(int x, int y, int dir){
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x=x;
		this.y=y;
	}
	
	protected void move(){
		
	}
}
