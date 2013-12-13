package com.geargrinder.server.data;

public class Quests {

	public int QuestID;
	public String Type;
	public int Started;
	public int Complete;
	public String title;
	public int MinLevel;
	public int MaxLevel;
	public int Solo;
	public int Timer;
	public int TimeLeft;
	public String MobName;
	public int MobAmount;
	public int MobKilled;

	public String[] Collect = new String[6];
	public int[] Amount = new int[6];
	public int[] Left = new int[6];

	public int Gold;
	public int XP;
	public String[] Item = new String[6];
}