package com.GearGrinder.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.GearGrinder.entity.mob.Player;

public class Keyboard implements KeyListener{

	private boolean[] keys = new boolean[230];
	public static boolean up, down, left, right, sprint, damage, spawnmob, escape;
	public static boolean Cinv;
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
		Cinv = keys[KeyEvent.VK_I];
		
		
		for(int i=0; i< keys.length; i++){
			if(keys[i] == true){
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
			keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
	}
	
	public void keyTyped(KeyEvent e) {
	
				if(keys[e.getKeyCode()] != keys[KeyEvent.VK_I]){
					if(Player.invshow == false){
						Player.invshow = true;
					}else if(Player.invshow == true){
						Player.invshow = false;
					}
				}
	}
}
