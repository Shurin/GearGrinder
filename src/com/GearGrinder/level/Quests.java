package com.GearGrinder.level;

import java.util.ArrayList;

public class Quests {
	
	ArrayList<ArrayList<String>> listquests = new ArrayList<ArrayList<String>>();
	ArrayList<String> quest1 = new ArrayList<String>();
	
	public static boolean quest1comp = false;
	
	public void Quests(){
		System.out.println("Loading Quest Data ...");
		//define the quests
		quest1.add("Ninja Slayer"); //quest title
		quest1.add("1"); //min level for quest
		quest1.add("100"); //max level for quest
		quest1.add("1"); // solo or not (0 is solo quest, 1 can be done in a group)
		quest1.add(" "); // timer for the quest (if it's time based)
		quest1.add(" "); // time left;
		quest1.add("ninjabot"); //mob to kill
		quest1.add("10"); // amount to kill
		quest1.add(" "); // amount left to kill
		quest1.add(" "); //item to collect
		quest1.add(" "); //number to collect
		quest1.add(" "); //amount left
		quest1.add(" "); //item to collect
		quest1.add(" "); //number to collect
		quest1.add(" "); //amount left
		quest1.add(" "); //item to collect
		quest1.add(" "); //number to collect
		quest1.add(" "); //amount left
		quest1.add(" "); //item to collect
		quest1.add(" "); //number to collect
		quest1.add(" "); //amount left
		quest1.add(" "); //item to collect
		quest1.add(" "); //number to collect
		quest1.add(" "); //amount left
		quest1.add(" "); //item to collect
		quest1.add(" "); //number to collect
		quest1.add(" "); //amount left
		quest1.add("1000"); //gold reward
		quest1.add("33"); //xp reward
		quest1.add(" "); //item reward
		quest1.add(" "); //item reward
		quest1.add(" "); //item reward
		quest1.add(" "); //item reward
		quest1.add(" "); //item reward
		quest1.add(" "); //item reward
		
		// add the quests to the quest list
		listquests.add(quest1); //slot 1 in list
		
		System.out.println("Finished Loading Quests ...");
	}

}
