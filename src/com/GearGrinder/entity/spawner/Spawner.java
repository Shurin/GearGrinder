package com.GearGrinder.entity.spawner;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.entity.particle.Particle;
import com.GearGrinder.level.Level;

public class Spawner extends Entity{
	
	public enum Type{
		MOB, PARTICLE
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level){
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
}
