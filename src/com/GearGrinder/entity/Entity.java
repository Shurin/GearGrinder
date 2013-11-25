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
	protected String Name;
	protected int mobHP;
	protected int mobXL;
	protected int mobXR;
	protected int mobYT;
	protected int mobYB;
	
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	

	public Entity(){
		
	}
	
	public Entity(int x, int y, Sprite sprite, String Name){
		this.x = x;
		this.y = y;
		this.sprite = sprite;		
		this.Name = Name;
		this.mobHP = mobHP;
		this.mobXL = mobXL;
		this.mobXR = mobXR;
		this.mobYB = mobYB;
		this.mobYT = mobYT;
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
	
	public int getXL(){
		return mobXL;
	}
	
	public int getXR(){
		return mobXR;
	}
	
	public int getYT(){
		return mobYT;
	}
	
	public int getYB(){
		return mobYB;
	}
	
	public int getHP(){
		return mobHP;
	}
	public int takeDmg(int damage){
		if(mobHP - damage > 0){
			mobHP = mobHP - damage;
		}else{
			mobHP = 0;
		}
		return mobHP;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public String getName(){
		return Name;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public void init(Level level){
		this.level = level;
	}
}
