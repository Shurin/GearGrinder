package com.GearGrinder.entity.mob;

import com.GearGrinder.graphics.AnimatedSprite;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.graphics.SpriteSheet;

public class NinjaBot extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.ninjabot_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.ninjabot_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.ninjabot_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.ninjabot_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;
	
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	private int xplimit = 5; //x positive travel constraint
	private int yplimit = 5; //y positive travel constraint
	private int xnlimit = -5; //x negative travel constraint
	private int ynlimit = -5; //y negative travel constraint
	private int xstart = 0;
	private int ystart = 0;
	
	public NinjaBot(int x, int y){
		this.x = x * 16;
		this.y = y * 16;
		sprite = animSprite.getSprite();
		this.Name = "ninjabot";
		this.mobHP = 200;
		this.npc = false;		
	}
	
	public void update() {
		time++;
		if(time % 30 == 0){
			xa = 0;
			ya = 0;
			if(random.nextInt(2) == 1){
				//if(random.nextInt(12) == 6){
				xa = random.nextInt(3) - 1;
				ya = random.nextInt(3) - 1;
				
				if(xa + xstart >= xplimit){
					xa = 0;
					xstart += 0;
				}else if(xa + xstart <= xnlimit){
					xa = 0;
					xstart += 0;
				}else{
					xstart += xa;
				}
				
				if(ya + ystart >= yplimit){
					ya = 0;
					ystart += 0;
				}else if(ya + ystart <= ynlimit){
					ya = 0;
					ystart += 0;
				}else{
					ystart += ya;
				}
				
				
			}
		}
		
		if(walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya < 0){
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0){
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa < 0){
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0){
			animSprite = right;
			dir = Direction.RIGHT;
		}
		if(xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
		this.mobXL = getX() - 10;
		this.mobXR = getX() + 10;
		this.mobYB = getY() - 22; //this is actually the top edge
		this.mobYT = getY() + 10; //this is actually the bottom edge
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite);
	}
}