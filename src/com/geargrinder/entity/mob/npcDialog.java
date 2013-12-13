package com.geargrinder.entity.mob;

import com.geargrinder.Game;

public class npcDialog extends Game {

	public static int npcindex;

	public static boolean showDialog = false;

	private static final long serialVersionUID = 1L;

	public static void initDialog(Integer mobindex) {
		npcindex = mobindex;
		showDialog = true;
	}
}
