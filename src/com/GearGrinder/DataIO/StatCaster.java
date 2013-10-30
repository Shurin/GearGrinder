package com.GearGrinder.DataIO;

import com.GearGrinder.Game;

public class StatCaster {

	public static void StatCaster(){
		Game.PlayerSpawnX = Integer.parseInt(Load.PSX);
		Game.PlayerSpawnY = Integer.parseInt(Load.PSY);
	}
}
