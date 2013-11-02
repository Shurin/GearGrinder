package com.GearGrinder.entity;

import java.util.Random;

import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.level.Level;

public class Entity {

	protected int x;
	protected int y;
	public static int mX;
	
	
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	

	public Entity(){
		
	}
	
	public Entity(int x, int y, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;		
	}
	
	public void update(){
		
	}
	
	public void render(Screen screen){
		mX = x-16;
		if (sprite != null) screen.renderSprite(x, y, sprite, true);
	}
	
	public void remove(){
		removed = true;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public void init(Level level){
		this.level = level;
	}
}
