package com.GearGrinder.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.GearGrinder.entity.mob.Player;

public class Mouse implements MouseListener, MouseMotionListener{

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	
	public static int getX(){
		return mouseX;
	}
	public static int getY(){
		return mouseY;
	}
	public static int getB(){
		return mouseB;
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		System.out.println("" + e.getX() + ", " + e.getY());
	}

	public void mouseClicked(MouseEvent e) {
			
	}

	public void mousePressed(MouseEvent e) {
		
		//checks if an object was clicked
		if((e.getX() >= 631 && e.getX() <= 663) && (e.getY() >= 673 && e.getY() <= 702)){
			if(Player.helpshow == false){
				Player.helpshow = true;
			}else if(Player.helpshow == true){
				Player.helpshow = false;
			}
		}else if((e.getX() >= 960 && e.getX() <= 1002) && (e.getY() >= 693 && e.getY() <= 738)){
			if(Player.invshow == false){
				Player.invshow = true;
			}else if(Player.invshow == true){
				Player.invshow = false;
			}
		} else{
			mouseB = e.getButton();
		}
	}

	public void mouseReleased(MouseEvent e) {
		mouseB = -1;		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
