package com.GearGrinder.level;

import java.util.ArrayList;

public class Quests {
	
	/* for reference use 
	  		// reads CSV data, 5 rows, 5 columns 
			List<List<String>> csvData = readCSVData(); 
			csvData.get(1).add("extraDataAfterColumn"); 
			// now row 1 has a value in (nonexistant) column 6
			csvData.get(2).remove(3); 
			// values in columns 4 and 5 moved to columns 3 and 4, 
			// attempting to access column 5 now throws an IndexOutOfBoundsException. 
	 */
	
	
	
	public static ArrayList<ArrayList<String>> listofquests = new ArrayList<ArrayList<String>>();
	public static ArrayList<String> quest1 = new ArrayList<String>();
	public static ArrayList<String> quest2 = new ArrayList<String>();
	
	public static ArrayList<ArrayList<String>> playerquests;
	
	public static boolean quest1comp = false;
	
	public static void Quests(){
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
		
		
		quest2.add("Ninja Boss"); //quest title
		quest2.add("5"); //min level for quest
		quest2.add("100"); //max level for quest
		quest2.add("1"); // solo or not (0 is solo quest, 1 can be done in a group)
		quest2.add(" "); // timer for the quest (if it's time based)
		quest2.add(" "); // time left;
		quest2.add("ninjabot boss"); //mob to kill
		quest2.add("1"); // amount to kill
		quest2.add(" "); // amount left to kill
		quest2.add(" "); //item to collect
		quest2.add(" "); //number to collect
		quest2.add(" "); //amount left
		quest2.add(" "); //item to collect
		quest2.add(" "); //number to collect
		quest2.add(" "); //amount left
		quest2.add(" "); //item to collect
		quest2.add(" "); //number to collect
		quest2.add(" "); //amount left
		quest2.add(" "); //item to collect
		quest2.add(" "); //number to collect
		quest2.add(" "); //amount left
		quest2.add(" "); //item to collect
		quest2.add(" "); //number to collect
		quest2.add(" "); //amount left
		quest2.add(" "); //item to collect
		quest2.add(" "); //number to collect
		quest2.add(" "); //amount left
		quest2.add("3000"); //gold reward
		quest2.add("55"); //xp reward
		quest2.add(" "); //item reward
		quest2.add(" "); //item reward
		quest2.add(" "); //item reward
		quest2.add(" "); //item reward
		quest2.add(" "); //item reward
		quest2.add(" "); //item reward
		
		// add the quests to the quest list
		listofquests.add(quest1); //slot 1 in list
		listofquests.add(quest2); //slot 1 in list
		
		// .get().get() is how you traverse the list of lists
		//int i = 0;
		//int j = 1;
		//System.out.println(listofquests.get(i).get(j));
		
		System.out.println(listofquests);
		
		System.out.println("Finished Loading Quests ...");
		
		ArrayList playerquests = (ArrayList) listofquests.clone();
		
	}

}
