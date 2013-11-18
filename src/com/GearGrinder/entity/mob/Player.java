package com.GearGrinder.entity.mob;

import java.awt.Color;

import com.GearGrinder.Game;
import com.GearGrinder.Networking.GetLoc;
import com.GearGrinder.Networking.InitialStat;
import com.GearGrinder.Networking.SaveStat;
import com.GearGrinder.Networking.UserVerify;
import com.GearGrinder.entity.Projectile.Projectile;
import com.GearGrinder.entity.Projectile.QuakeProjectile;
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
	
	public static boolean helpshow = false;
	public static boolean invshow = false;
	public static boolean charshow = false;	
	
	
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
		if((input.damage == true) && (InitialStat.currenthealth - 500 > 0)){
			InitialStat.currenthealth -= 500;
			InitialStat.healthpercent = InitialStat.currenthealth / InitialStat.maxhealth * 100;			
		}
		if (input.up){
			animSprite = up;
			InitialStat.PSpriteDir = "up";
			ya--;
		} else if (input.down){
			animSprite = down;
			InitialStat.PSpriteDir = "down";
			ya++;
		}
		if (input.left){
			animSprite = left;
			InitialStat.PSpriteDir = "left";
			xa--;
		} else if (input.right){
			animSprite = right;
			InitialStat.PSpriteDir = "right";
			xa++;		
		}
		//sprint stuff
		if (input.up && input.sprint && InitialStat.currentstamina > 0){
			animSprite = up;
			InitialStat.PSpriteDir = "up";
			ya-=1.5;
			InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
			InitialStat.currentstamina-- ;
		} else if (input.down && input.sprint && InitialStat.currentstamina > 0){
			animSprite = down;
			InitialStat.PSpriteDir = "down";
			ya+=1.5;
			InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
			InitialStat.currentstamina--;
		}
		if (input.left && input.sprint && InitialStat.currentstamina > 0){
			animSprite = left;
			InitialStat.PSpriteDir = "left";
			xa-=1.5;
			InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
			InitialStat.currentstamina--;
		} else if (input.right && input.sprint && InitialStat.currentstamina > 0){
			animSprite = right;
			InitialStat.PSpriteDir = "right";
			xa+=1.5;	
			InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
			InitialStat.currentstamina--;
		}		
		
		if(input.spawnmob){
			for(int i = 0; i < 1; i++){
			level.add(new NinjaBot(x / 16,y / 16));
			Game.mobsonscreen = Game.mobsonscreen + 1;
			}
		}
		
		if(xa != 0 || ya != 0){
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
		if(input.escape){	
			InitialStat.Onlineint = 0;
			SaveStat.SaveStat();
			System.exit(0);			
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
			int manacost = 5;
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);// atan2 takes y first
			String Name = "wizpro";
			Projectile p = new WizardProjectile(x, y, dir, Name);
			shoot(x, y, dir, p, manacost);
			fireRate = WizardProjectile.FIRE_RATE;
		}
		else if(Mouse.getB() == 3 && fireRate <= 0){
			int manacost = 85;
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);// atan2 takes y first
			String Name = "quakepro";
			Projectile p = new QuakeProjectile(x, y, dir, Name);
			shoot(x, y, dir, p, manacost);
			fireRate = QuakeProjectile.FIRE_RATE;
		}
		
	}

	public void render(Screen screen){
		sprite = animSprite.getSprite();
		// the -16 is to make the center of the player 0,0
		screen.renderMob(x - 16, y - 16, sprite);
		if(GetLoc.RENDER){
			if(GetLoc.psdir.equals("down")){
				sprite = animSprite.player_down;
				screen.renderMob(GetLoc.xp1 -16, GetLoc.yp1 -16, sprite);
			}else if(GetLoc.psdir.equals("up")){
				sprite = animSprite.player_up;
				screen.renderMob(GetLoc.xp1 -16, GetLoc.yp1 -16, sprite);
			}else if(GetLoc.psdir.equals("left")){
				sprite = animSprite.player_left;
				screen.renderMob(GetLoc.xp1 -16, GetLoc.yp1 -16, sprite);
			}else if(GetLoc.psdir.equals("right")){
				sprite = animSprite.player_right;
				screen.renderMob(GetLoc.xp1 -16, GetLoc.yp1 -16, sprite);
			}			
		}
	}
	
	public void CharPanel(){
	}
}
