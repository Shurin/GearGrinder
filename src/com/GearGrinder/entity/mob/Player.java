package com.GearGrinder.entity.mob;

import com.GearGrinder.Game;
import com.GearGrinder.DataIO.Save;
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
	
	//PLAYER STATS
	public static String charname = "CharName";
	public static int gold = 0;
	public static int health = 100000;
	public static double currenthealth = health;
	public static double maxhealth = health;
	public static double healthpercent = currenthealth / maxhealth * 100;
	public static int magic = 300;
	public static double currentmagic = magic;
	public static double maxmagic = magic;
	public static double magicpercent = currentmagic / maxmagic * 100;
	public static int stamina = 150;
	public static double currentstamina = stamina;
	public static double maxstamina = stamina;
	public static double staminapercent = currentstamina / maxstamina * 100;
	
	
	public static boolean helpshow = false;
	public static boolean invshow = false;
	
	
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
		if(input.damage){
			currenthealth -= 500;
			healthpercent = currenthealth / maxhealth * 100;
		}
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
		if (input.up && input.sprint && currentstamina > 0){
			animSprite = up;
			ya-=1.5;
			staminapercent = currentstamina / maxstamina * 100;
			currentstamina-- ;
		} else if (input.down && input.sprint && currentstamina > 0){
			animSprite = down;
			ya+=1.5;
			staminapercent = currentstamina / maxstamina * 100;
			currentstamina--;
		}
		if (input.left && input.sprint && currentstamina > 0){
			animSprite = left;
			xa-=1.5;
			staminapercent = currentstamina / maxstamina * 100;
			currentstamina--;
		} else if (input.right && input.sprint && currentstamina > 0){
			animSprite = right;
			xa+=1.5;	
			staminapercent = currentstamina / maxstamina * 100;
			currentstamina--;
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

		
		/*if(input.Cpanel && characterpanel == false){
			characterpanel = true;			
		}else if(input.Cpanel && characterpanel == true){
			characterpanel = false;			
		}*/
		
		if(input.escape){	
			Save.Save();
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
			Projectile p = new WizardProjectile(x, y, dir);
			shoot(x, y, dir, p, manacost);
			fireRate = WizardProjectile.FIRE_RATE;
		}
		else if(Mouse.getB() == 3 && fireRate <= 0){
			int manacost = 85;
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);// atan2 takes y first
			Projectile p = new QuakeProjectile(x, y, dir);
			shoot(x, y, dir, p, manacost);
			fireRate = QuakeProjectile.FIRE_RATE;
		}
		
	}

	public void render(Screen screen){
		sprite = animSprite.getSprite();
		// the -16 is to make the center of the player 0,0		
		screen.renderMob(x - 16, y - 16, sprite);
	}
	
	public void CharPanel(){
	}
}
