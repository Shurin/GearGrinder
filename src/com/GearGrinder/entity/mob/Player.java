package com.GearGrinder.entity.mob;

import java.awt.Color;

import com.GearGrinder.Game;
import com.GearGrinder.Networking.GetLoc;
import com.GearGrinder.Networking.InitialStat;
import com.GearGrinder.Networking.SaveStat;
import com.GearGrinder.entity.Entity;
import com.GearGrinder.entity.Projectile.Projectile;
import com.GearGrinder.entity.Projectile.QuakeProjectile;
import com.GearGrinder.entity.Projectile.WizardProjectile;
import com.GearGrinder.graphics.AnimatedSprite;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.graphics.SpriteSheet;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;
import com.GearGrinder.level.Level;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private int fireRate = 0;
	private static AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	//player2
	private static AnimatedSprite down2 = new AnimatedSprite(SpriteSheet.player2_down, 32, 32, 3);
	private AnimatedSprite up2 = new AnimatedSprite(SpriteSheet.player2_up, 32, 32, 3);
	private AnimatedSprite left2 = new AnimatedSprite(SpriteSheet.player2_left, 32, 32, 3);
	private AnimatedSprite right2 = new AnimatedSprite(SpriteSheet.player2_right, 32, 32, 3);
	private static AnimatedSprite animSprite = down;
	
	public static boolean helpshow = false;
	public static boolean invshow = false;
	public static boolean charshow = false;	
	public static boolean dialog = false;
	protected int j;
	
	
	public static void spritefix(){
		if(InitialStat.PSprite.equals("psprite001")){
			animSprite = down;
		}else if(InitialStat.PSprite.equals("psprite002")){
			animSprite = down2;
		}
	}
	
	public Player(Keyboard input){
		this.input = input;
		if(InitialStat.PSprite.equals("psprite001")){
			sprite = Sprite.player_down;
		}else if(InitialStat.PSprite.equals("psprite002")){
			sprite = Sprite.player2_down;
		}
	}
	
	public Player(int x, int y, Keyboard input){
		this.x = x;
		this.y = y;
		this.input = input;
		if(InitialStat.PSprite.equals("psprite001")){
			sprite = Sprite.player_down;
		}else if(InitialStat.PSprite.equals("psprite002")){
			sprite = Sprite.player2_down;
		}
		fireRate = WizardProjectile.FIRE_RATE;
	}
	
	public void update(){
		boolean walkup = true;
		boolean walkdown = true;
		boolean walkleft = true;
		boolean walkright = true;

		
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
				if(InitialStat.PSprite.equals("psprite001")){
					animSprite = up;
				}else if(InitialStat.PSprite.equals("psprite002")){
					animSprite = up2;
				}
				InitialStat.PSpriteDir = "up";
				
				for(int i = 0; i < Level.entities.size(); i++){
					if(((Game.currentx >= Level.entities.get(i).getXL()) && (Game.currenty - 5 <= Level.entities.get(i).getYT())) && ((Game.currentx <= Level.entities.get(i).getXR()) && (Game.currenty >= Level.entities.get(i).getYB()))){
						walkup = false;
						break;
					}else{
						walkup = true;
					}
				}
				if(walkup){
					ya-=1.5;
				}				
			} else if (input.down){
				if(InitialStat.PSprite.equals("psprite001")){
					animSprite = down;
				}else if(InitialStat.PSprite.equals("psprite002")){
					animSprite = down2;
				}
				InitialStat.PSpriteDir = "down";
				
				for(int i = 0; i < Level.entities.size(); i++){
					if(((Game.currentx >= Level.entities.get(i).getXL()) && (Game.currenty <= Level.entities.get(i).getYT())) && ((Game.currentx <= Level.entities.get(i).getXR()) && (Game.currenty + 10 >= Level.entities.get(i).getYB()))){
						walkdown = false;
						break;
					}else{
						walkdown = true;
					}
				}
				if(walkdown){
					ya+=1.5;
				}				
			}
			if (input.right){
				if(InitialStat.PSprite.equals("psprite001")){
					animSprite = right;
				}else if(InitialStat.PSprite.equals("psprite002")){
					animSprite = right2;
				}
				InitialStat.PSpriteDir = "right";
				
				for(int i = 0; i < Level.entities.size(); i++){
					if(((Game.currentx + 10 >= Level.entities.get(i).getXL()) && (Game.currenty <= Level.entities.get(i).getYT())) && ((Game.currentx <= Level.entities.get(i).getXR()) && (Game.currenty >= Level.entities.get(i).getYB()))){
						walkright = false;
						break;
					}else{
						walkright = true;
					}
				}
				if(walkright){
					xa+=1.5;
				}				
			}else if (input.left){
				if(InitialStat.PSprite.equals("psprite001")){
					animSprite = left;
				}else if(InitialStat.PSprite.equals("psprite002")){
					animSprite = left2;
				}
				InitialStat.PSpriteDir = "left";
				
				for(int i = 0; i < Level.entities.size(); i++){
					if(((Game.currentx >= Level.entities.get(i).getXL()) && (Game.currenty <= Level.entities.get(i).getYT())) && ((Game.currentx - 10 <= Level.entities.get(i).getXR()) && (Game.currenty >= Level.entities.get(i).getYB()))){
						walkleft = false;
						break;
					}else{
						walkleft = true;
					}
				}
				if(walkleft){
					xa-=1.5;
				}	
			}
			
			//sprint stuff
			if (input.up && input.sprint && InitialStat.currentstamina > 0){
				if(InitialStat.PSprite.equals("psprite001")){
					animSprite = up;
				}else if(InitialStat.PSprite.equals("psprite002")){
					animSprite = up2;
				}
				InitialStat.PSpriteDir = "up";
				ya-=20; //default is 1.5
				InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
				InitialStat.currentstamina-- ;
			} else if (input.down && input.sprint && InitialStat.currentstamina > 0){
				if(InitialStat.PSprite.equals("psprite001")){
					animSprite = down;
				}else if(InitialStat.PSprite.equals("psprite002")){
					animSprite = down2;
				}
				InitialStat.PSpriteDir = "down";
				ya+=20; //default is 1.5
				InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
				InitialStat.currentstamina--;
			}
			if (input.left && input.sprint && InitialStat.currentstamina > 0){
				if(InitialStat.PSprite.equals("psprite001")){
					animSprite = left;
				}else if(InitialStat.PSprite.equals("psprite002")){
					animSprite = left2;
				}
				InitialStat.PSpriteDir = "left";
				xa-=20; //default is 1.5
				InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
				InitialStat.currentstamina--;
			} else if (input.right && input.sprint && InitialStat.currentstamina > 0){
				if(InitialStat.PSprite.equals("psprite001")){
					animSprite = right;
				}else if(InitialStat.PSprite.equals("psprite002")){
					animSprite = right2;
				}
				InitialStat.PSpriteDir = "right";
				xa+=20;	//default is 1.5
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
			System.out.println("Preparing Shutdown ...");
			InitialStat.Onlineint = 0;
			System.out.println("Saving Player Info ...");
			SaveStat.SaveStat();
			System.out.println("Closing Game ...");
			System.exit(0);			
		}			
		
		/////
		//check to see if player is interacting with an npc
		/////
		
		for(int i = 0; i < Level.entities.size(); i++){
			if(((Game.currentx >= Level.entities.get(i).getXL()) && (Game.currenty - 5 <= Level.entities.get(i).getYT())) && ((Game.currentx <= Level.entities.get(i).getXR()) && (Game.currenty >= Level.entities.get(i).getYB()))){
				if(input.interacting == true && dialog == false && Level.entities.get(i).npc() == true){
					dialog = true;
					j=i;
					//System.out.println("interacting from below");
					npcDialog.initDialog(j);
				}
			}else if(((Game.currentx >= Level.entities.get(i).getXL()) && (Game.currenty <= Level.entities.get(i).getYT())) && ((Game.currentx <= Level.entities.get(i).getXR()) && (Game.currenty + 10 >= Level.entities.get(i).getYB()))){
				if(input.interacting == true && dialog == false && Level.entities.get(i).npc() == true){
					dialog = true;
					j=i;
					//System.out.println("interacting from above");
					npcDialog.initDialog(j);
				}
			}else if(((Game.currentx >= Level.entities.get(i).getXL()) && (Game.currenty <= Level.entities.get(i).getYT())) && ((Game.currentx - 10 <= Level.entities.get(i).getXR()) && (Game.currenty >= Level.entities.get(i).getYB()))){
				if(input.interacting == true && dialog == false && Level.entities.get(i).npc() == true){
					dialog = true;
					j=i;
					//System.out.println("interacting from right");
					npcDialog.initDialog(j);
				}
			}else if(((Game.currentx + 10 >= Level.entities.get(i).getXL()) && (Game.currenty <= Level.entities.get(i).getYT())) && ((Game.currentx <= Level.entities.get(i).getXR()) && (Game.currenty >= Level.entities.get(i).getYB()))){
				if(input.interacting == true && dialog == false && Level.entities.get(i).npc() == true){
					dialog = true;
					j=i;
					//System.out.println("interacting from left");
					npcDialog.initDialog(j);
				}
			}
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
			if(GetLoc.pspr.equals("psprite002")){
				if(GetLoc.psdir.equals("down")){
					sprite = animSprite.player2_down;
					screen.renderMob(GetLoc.xp1 -16, GetLoc.yp1 -16, sprite);
				}else if(GetLoc.psdir.equals("up")){
					sprite = animSprite.player2_up;				
					screen.renderMob(GetLoc.xp1 -16, GetLoc.yp1 -16, sprite);
				}else if(GetLoc.psdir.equals("left")){
					sprite = animSprite.player2_left;
					screen.renderMob(GetLoc.xp1 -16, GetLoc.yp1 -16, sprite);
				}else if(GetLoc.psdir.equals("right")){
					sprite = animSprite.player2_right;
					screen.renderMob(GetLoc.xp1 -16, GetLoc.yp1 -16, sprite);
				}
			}else if(GetLoc.pspr.equals("psprite001")){
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
	}
}
