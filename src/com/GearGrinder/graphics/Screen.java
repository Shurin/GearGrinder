package com.GearGrinder.graphics;

import java.util.Random;

import com.GearGrinder.level.tile.Tile;

public class Screen {
	// tile size is going to be 32x32

	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int xOffset, yOffset;
	public int[] tiles = new int[64 * 64]; // if MAP_SIZE = 64, then
														// tiles = 4,096
	private Random random = new Random();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; // atm it's 50,400

		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xcc00cc);
		}
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
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
				if(xa <0) xa = 0;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}
	
	public void renderTil(int xp, int yp, Tile tile){
		renderTile(xp, yp, tile.sprite);
	}
	
	public void renderPlayer(int xp, int yp, Sprite sprite){
		// all player sprites are in 16 px chunks!!
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < 32; y++) {
			int ya = y + yp; // ya = actual y position
			for (int x = 0; x < 32; x++) {
				int xa = x + xp; // ya = actual y position
				if (xa < -32 || xa >= width || ya < 0 || ya >= height)	break;
				if(xa <0) xa = 0;
				int col = sprite.pixels[x + y * 32];
				// oxfff00ff has 2 extra f's because it needs the alpha value too
				// so it's really just 0xff00ff but with full alpha (+ ff)
				// so you get 0xffff00ff
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}