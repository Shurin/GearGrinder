package com.GearGrinder.entity.mob;

import com.GearGrinder.Game;
import com.GearGrinder.entity.Projectile.Projectile;
import com.GearGrinder.entity.Projectile.WizardProjectile;
import com.GearGrinder.graphics.AnimatedSprite;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.graphics.SpriteSheet;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private int fireRate = 0;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;
	
	public Player(Keyboard input){
		this.input = input;
		sprite = Sprite.player_down;
	}
	
	public Player(int x, int y, Keyboard input){
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_down;
		fireRate = WizardProjectile.FIRE_RATE;
	}
	
	public void update(){
		if(walking == true) animSprite.update();
		else animSprite.setFrame(0);
		if(fireRate > 0) fireRate--;
		int xa = 0;
		int ya = 0;
		if (anim < 7500)anim++; 
		else anim = 0;
		if (input.up){
			animSprite = up;
			ya--;
		} else if (input.down){
			animSprite = down;
			ya++;
		}
		if (input.left){
			animSprite = left;
			xa--;
		} else if (input.right){
			animSprite = right;
			xa++;		
		}
		//sprint stuff
		if (input.up && input.sprint){
			animSprite = up;
			ya-=1.5;
		} else if (input.down && input.sprint){
			animSprite = down;
			ya+=1.5;
		}
		if (input.left && input.sprint){
			animSprite = left;
			xa-=1.5;
		} else if (input.right && input.sprint){
			animSprite = right;
			xa+=1.5;		
		}
		
		if(xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		clear();
		updateShooting();	
	}
	
	private void clear(){
		for(int i = 0; i< level.getProjectiles().size(); i++){
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()) level.getProjectiles().remove(i);
		}
	}
	
	private void updateShooting() {		
		if(Mouse.getB() == 1 && fireRate <= 0){
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);// atan2 takes y first
			shoot(x, y, dir);
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen){
		sprite = animSprite.getSprite();
		// the -16 is to make the center of the player 0,0		
		screen.renderMob(x - 16, y - 16, sprite);
	}
}
