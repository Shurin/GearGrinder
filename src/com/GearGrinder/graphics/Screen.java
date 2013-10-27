package com.GearGrinder.graphics;

import java.util.Random;

import com.GearGrinder.entity.Projectile.Projectile;

public class Screen {
	// tile size is going to be 32x32

	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int xOffset, yOffset;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE]; // if MAP_SIZE = 64, then
														// tiles = 4,096
	private Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; // atm it's 50,400

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	//testing purposes only
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sheet.HEIGHT; y++){
			int ya = y + yp;
			for(int x = 0; x < sheet.WIDTH; x++){
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.WIDTH];
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
		if(fixed){
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++){
			int ya = y + yp;
			for(int x = 0; x < sprite.getWidth(); x++){
				int xa = x + xp;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}

	public void renderTile(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp; // ya = actual y position
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp; // ya = actual y position
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)	break;
				if(xa <0 ) xa = 0;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}
	
	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp; // ya = actual y position
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp; // ya = actual y position
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height)	break;
				if(xa <0) xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSprite().SIZE];
				if(col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}
	
	
	
	public void renderMob(int xp, int yp, Sprite sprite){
		// all player sprites are in 16 px chunks!!
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)	break;
				if(xa <0) xa = 0;
				int col = sprite.pixels[x + y * sprite.SIZE];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}