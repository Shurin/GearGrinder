package com.GearGrinder.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private String path;
	public final int SIZE;
	public int[] pixels;
	
	public static SpriteSheet playersprites = new SpriteSheet("/textures/sheets/playersprites.png", 256); // 256 is the length of the spritesheet in pixels
	public static SpriteSheet spawn_level_tiles = new SpriteSheet("/textures/sheets/spawnleveltiles.png", 48); // 48 is the length of the spritesheet in pixels
	public static SpriteSheet projectile_wizard = new SpriteSheet("/textures/sheets/projectiles/wizard.png", 48); // 48 is the length of the spritesheet in pixels

	
	public SpriteSheet(String path, int size){
		this.path = path; 
		SIZE = size;
		
		pixels = new int[SIZE * SIZE];
		load();
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
