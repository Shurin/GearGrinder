package com.GearGrinder.entity.mob;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.GearGrinder.Game;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.graphics.Sprite;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;

public class Player extends Mob{
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	
	
	public Player(Keyboard input){
		this.input = input;
		sprite = Sprite.player_down;
	}
	
	public Player(int x, int y, Keyboard input){
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_down;
	}
	
	public void update(){
		int xa = 0;
		int ya = 0;
		if (anim < 7500)anim++; else anim = 0;
		if (input.up)ya--;
		if (input.down)ya++;
		if (input.left)xa--;
		if (input.right)xa++;		
		if(xa != 0 || ya != 0){
		move(xa, ya);
		walking = true;
		} else {
			walking = false;
		}
		
		updateShooting();	
	}
	
	private void updateShooting() {		
		if(Mouse.getB() == 1){
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);// atan2 takes y first
			shoot(x, y, dir);
		}
	}

	public void render(Screen screen){
		// assigns sprite frame based on direction of movement
		// and how long it is displayed until next frame
		// and continues to loop until direction change or movement stops
		if(dir == 0){
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
		}
		if(dir == 5){
			sprite = Sprite.player_upright;
			if(walking){
				if(anim % 25 > 10){
					sprite = Sprite.player_upright_1;
				} else{
					sprite = Sprite.player_upright_2;
				}
			}
		}
		if(dir == 4){
			sprite = Sprite.player_downright;
			if(walking){
				if(anim % 25 > 10){
					sprite = Sprite.player_downright_1;
				} else{
					sprite = Sprite.player_downright_2;
				}
			}
		}
		if(dir == 7){
			sprite = Sprite.player_upleft;
			if(walking){
				if(anim % 25 > 10){
					sprite = Sprite.player_upleft_1;
				} else{
					sprite = Sprite.player_upleft_2;
				}
			}
		}
		if(dir == 6){
			sprite = Sprite.player_downleft;
			if(walking){
				if(anim % 25 > 10){
					sprite = Sprite.player_downleft_1;
				} else{
					sprite = Sprite.player_downleft_2;
				}
			}
		}

		// the -16 is to make the center of the player 0,0		
		screen.renderPlayer(x - 16, y - 16, sprite);
	}
}
