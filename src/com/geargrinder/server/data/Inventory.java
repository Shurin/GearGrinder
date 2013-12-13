package com.geargrinder.server.data;

public class Inventory {

	public final int maxItems = 36;
	public int[] Items = new int[maxItems];

	public Inventory() {

	}

	public int[] getItems() {
		return Items;
	}

	public int getItem(int id) {
		return Items[id];
	}

	public int getMaxItems() {
		return maxItems;
	}

}