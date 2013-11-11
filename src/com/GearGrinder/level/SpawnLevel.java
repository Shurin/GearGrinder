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
		//add(new NinjaBot(10,17));
		add(new NinjaBot(10,35));
		
		//add(new NinjaBot(39,12));
		//add(new NinjaBot(17,5));
		//add(new NinjaBotBoss(22, 45));
	}
	
	protected void generateLevel(){
		
	}

}
