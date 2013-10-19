package com.GearGrinder.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{

	private boolean[] keys = new boolean[230];
	public boolean up, down, left, right, menu;
	public String keystring;
	public void update(){
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		menu = keys[KeyEvent.VK_NUMPAD9];
		
		
		for(int i=0; i< keys.length; i++){
			if(keys[i] == true){
				
				//System.out.println("KEY: " + i);
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
