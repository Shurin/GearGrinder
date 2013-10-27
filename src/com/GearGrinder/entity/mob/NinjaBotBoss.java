package com.GearGrinder.entity.mob;

import com.GearGrinder.graphics.AnimatedSprite;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.graphics.SpriteSheet;

public class NinjaBotBoss extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.ninjabotboss_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.ninjabotboss_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.ninjabotboss_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.ninjabotboss_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;
	
	private int xa = 0;
	private int ya = 0;
	
	public NinjaBotBoss(int x, int y){
		this.x = x * 16;
		this.y = y * 16;
		sprite = Sprite.ninjabotboss;
	}
	
	private void move(){
		xa = 0;
		ya = 0;
		
		Player player = level.getClientPlayer();
		if(x < player.getX()) xa++;
		if(x > player.getX()) xa--;
		if(y < player.getY()) ya++;
		if(y > player.getY()) ya--;
		
		if(xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void update() {
		move();	
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
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x, y, this);
	}
}