package com.GearGrinder.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.GearGrinder.entity.mob.NinjaBot;
import com.GearGrinder.entity.mob.NinjaBotBoss;

public class SpawnLevel extends Level{

	
	public SpawnLevel(String path) {		
		super(path);
	}
	
	
	protected void loadLevel(String path){
		try{ 
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e){			
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
		add(new NinjaBot(20, 8));
		add(new NinjaBot(20, 10));
		add(new NinjaBotBoss(4, 17));
	}
	
	// Grass = 0x00FF00
	// Flower = 0xFFFF00
	// Rock = 0x7F7F00
	protected void generateLevel(){
		
	}

}
