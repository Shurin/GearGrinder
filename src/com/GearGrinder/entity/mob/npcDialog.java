package com.GearGrinder.entity.mob;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.GearGrinder.Game;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.level.Level;

public class npcDialog extends Game{
	
	public static int npcindex;
	
	public static boolean showDialog = false;

	private static final long serialVersionUID = 1L;

	public static void initDialog(Integer mobindex){
		npcindex = mobindex;
		showDialog = true;
	}
}
