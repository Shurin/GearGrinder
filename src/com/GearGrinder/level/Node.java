package com.GearGrinder.level;

import com.GearGrinder.Util.Vector2i;

public class Node {

	public Vector2i tile;
	public Node parent; // the parent is NodeX-1
	public double fCost, gCost, hCost;

	// gCost = the total value of the nodes A* chose to travel from start to finish
	// hCost = the direct line between the current node and the finish
	// fCost = the sum of hCost and gCost

	public Node(Vector2i tile, Node parent, double gCost, double jCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}

}
