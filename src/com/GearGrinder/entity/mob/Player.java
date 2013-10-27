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
		// assigns sprite frame based on direction of movement
		// and how long it is displayed until next frame
		// and continues to loop until direction change or movement stops
		/*if(dir == 0){
			sprite = Sprite.player_up;			
			if(walking){
				if(anim % 25 > 10){
					sprite = Sprite.player_up_1;
				} else{
					sprite = Sprite.player_up_2;
				}
			}
		}
		if(dir == 2){
			sprite = Sprite.player_down;
			if(walking){
				if(anim % 25 > 10){
					sprite = Sprite.player_down_1;
				} else{
					sprite = Sprite.player_down_2;
				}
			}
		}
		if(dir == 3){
			sprite = Sprite.player_left;
			if(walking){
				if(anim % 25 > 10){
					sprite = Sprite.player_left_1;
				} else{
					sprite = Sprite.player_left_2;
				}
			}
		}
		if(dir == 1){
			sprite = Sprite.player_right;
			if(walking){
				if(anim % 25 > 10){
					sprite = Sprite.player_right_1;
				} else{
					sprite = Sprite.player_right_2;
				}
			}
		}*/
		sprite = animSprite.getSprite();
		// the -16 is to make the center of the player 0,0		
		screen.renderPlayer(x - 16, y - 16, sprite);
	}
}
