package com.GearGrinder.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.GearGrinder.Networking.SaveStat;
import com.GearGrinder.entity.mob.Player;

public class Keyboard implements KeyListener{

	public static boolean[] keys = new boolean[230];
	public static boolean up, down, left, right, sprint, damage, spawnmob, escape, space, dialog;
	public static boolean Cinv;
	public static boolean interacting = false;
	public String keystring;
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		sprint = keys[KeyEvent.VK_SHIFT];
		damage = keys[KeyEvent.VK_1];
		spawnmob = keys[KeyEvent.VK_3];
		escape = keys[KeyEvent.VK_ESCAPE];
		space = keys[KeyEvent.VK_SPACE];
		dialog = keys[KeyEvent.VK_ENTER];
		
		
		for(int i=0; i< keys.length; i++){
			if(keys[i] == true){
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
			keys[e.getKeyCode()] = true;
			if((keys[e.getKeyCode()] == keys[KeyEvent.VK_SPACE]) && interacting == false){
				interacting = true;
			}
	}

	public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
			if((keys[e.getKeyCode()] == keys[KeyEvent.VK_SPACE]) && interacting == true){
				interacting = false;
				Player.dialog = false;
			}
	}
	
	public void keyTyped(KeyEvent e) {		
				if(keys[e.getKeyCode()] != keys[KeyEvent.VK_I]){
					if(Player.invshow == false){
						Player.invshow = true;
					}else if(Player.invshow == true){
						Player.invshow = false;
					}
				}else if(keys[e.getKeyCode()] != keys[KeyEvent.VK_H]){
					if(Player.helpshow == false){
						Player.helpshow = true;
					}else if(Player.helpshow == true){
						Player.helpshow = false;
					}
				}else if(keys[e.getKeyCode()] != keys[KeyEvent.VK_C]){
					if(Player.charshow == false){
						Player.charshow = true;
					}else if(Player.charshow == true){
						Player.charshow = false;
					}				
				}else if(keys[e.getKeyCode()] != keys[KeyEvent.VK_NUMPAD9] && SaveStat.saving == false){
					SaveStat.SaveStat();
				}
	}
}
