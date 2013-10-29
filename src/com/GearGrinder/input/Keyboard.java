package com.GearGrinder.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.GearGrinder.entity.mob.Player;

public class Keyboard implements KeyListener{

	private boolean[] keys = new boolean[230];
	public static boolean up, down, left, right, sprint, damage, spawnmob;
	public String keystring;
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		sprint = keys[KeyEvent.VK_SHIFT];
		damage = keys[KeyEvent.VK_1];
		spawnmob = keys[KeyEvent.VK_3];
		
		
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
		
		
	}
}
