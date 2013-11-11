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
	protected double speed, range, distance;
	protected int damage;
	
	protected final Random random = new Random();//var for random range
	
	public Projectile(int x, int y, double dir, String Name){
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x=x;
		this.y=y;
		this.Name = Name;
		this.damage = damage;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getSpriteSize(){
		return sprite.SIZE;
	}
	
	public String getName(){
		return Name;
	}
	
	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}
	
	public int getDamage(){
		return damage;
	}
	
	protected void move(){
		
	}
}
