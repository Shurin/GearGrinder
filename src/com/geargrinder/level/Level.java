package com.geargrinder.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.geargrinder.Game;
import com.geargrinder.Networking.InitialStat;
import com.geargrinder.Networking.SaveLoc;
import com.geargrinder.Util.Vector2i;
import com.geargrinder.entity.Entity;
import com.geargrinder.entity.Projectile.Projectile;
import com.geargrinder.entity.mob.NinjaBot;
import com.geargrinder.entity.mob.NinjaBotBoss;
import com.geargrinder.entity.mob.Player;
import com.geargrinder.entity.mob.campfire;
import com.geargrinder.entity.mob.npc_skills;
import com.geargrinder.entity.mob.npc_smith;
import com.geargrinder.entity.mob.testnpc;
import com.geargrinder.entity.mob.testnpc2;
import com.geargrinder.entity.particle.Particle;
import com.geargrinder.graphics.Screen;
import com.geargrinder.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size;

	public static List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();

	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return 1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};

	public static int proxl;// boundaries for projectile
	public static int proyb;
	public static int proxr;
	public static int proyt;
	public static int prodamage;
	public static int levelticker = 0;
	protected int timer = 0;
	protected boolean wateranimate = false;

	public static Level world = new SpawnLevel("/levels/world_base.png");
	public static Level dungeon1 = new SpawnLevel("/levels/dungeon1_base.png");
	public static Level noobisland = new SpawnLevel("/levels/noobisland_base.png");

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
	}

	public Level(String path) { // loads level from file
		/*String mid = F_mid.png";
		String top = "_top.png";
		String[] parts = path.split("_");
		String layer2 = parts[0] + mid;
		String layer3 = parts[0] + top;		
		System.out.println(path + "||" + layer2 + "||" + layer3);*/

		loadLevel(path);
		// loadLevel(layer2);
		// loadLevel(layer3);

		levelticker++; // makes sure mobs are only spawned on the last run of Level();
		if (levelticker == 6) {
			addMob();
			levelticker = 0;
		}
	}

	protected void generateLevel() { // creates random level
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++)
				getTile(x, y);
		}
		tile_size = 16;
	}

	protected void loadLevel(String path) {

	}

	public void update() { // updates the levels
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).update();
		for (int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).update();
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).update();
		for (int i = 0; i < players.size(); i++)
			players.get(i).update();
		mobhit();
		remove();
	}

	public void remove() {
		for (int i = 0; i < entities.size(); i++)
			if (entities.get(i).isRemoved()) entities.remove(i);
		for (int i = 0; i < projectiles.size(); i++)
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		for (int i = 0; i < particles.size(); i++)
			if (particles.get(i).isRemoved()) particles.remove(i);
		for (int i = 0; i < players.size(); i++)
			if (players.get(i).isRemoved()) players.remove(i);
	}

	public void addMob() {
		if (Game.level == noobisland) {
			add(new npc_smith(1062, 3687));
			add(new testnpc(1777, 763));
			add(new testnpc2(1773, 763));
			add(new npc_skills(1063, 3470));
			add(new campfire(1061, 3476));
			add(new campfire(1114, 3476));
			add(new campfire(1061, 3470));
			add(new campfire(1114, 3470));
			add(new campfire(1062, 3685));
			add(new NinjaBot(1115, 3670));
			add(new NinjaBot(1132, 3650));
			add(new NinjaBot(1151, 3671));
			add(new NinjaBot(1170, 3644));
			add(new NinjaBot(1200, 3668));
			add(new NinjaBot(1223, 3647));
			add(new NinjaBot(1255, 3651));
			add(new NinjaBot(1136, 3681));
			add(new NinjaBot(1090, 3650));
			add(new NinjaBot(1169, 3628));
			add(new NinjaBot(1156, 3611));
			add(new NinjaBot(1183, 3601));
			add(new NinjaBot(1177, 3574));
			add(new NinjaBot(1154, 3537));
			add(new NinjaBot(1200, 3534));
			add(new NinjaBot(1258, 3528));
			add(new NinjaBot(1241, 3551));
			add(new NinjaBot(1212, 3564));
			add(new NinjaBot(1229, 3591));
			add(new NinjaBot(1253, 3615));
			add(new NinjaBot(1209, 3626));
			add(new NinjaBot(1178, 3684));
			add(new NinjaBot(1157, 3591));
			add(new NinjaBot(1177, 3551));
			add(new NinjaBot(1225, 3537));
			add(new NinjaBot(1206, 3582));
			add(new NinjaBot(1255, 3569));
			add(new NinjaBot(1205, 3607));
			add(new NinjaBot(1227, 3571));
			add(new NinjaBot(1251, 3597));
			add(new NinjaBot(1190, 3527));
			add(new NinjaBot(1185, 3648));
			add(new NinjaBot(1162, 3564));
			add(new NinjaBot(1156, 3526));
			add(new NinjaBot(1240, 3530));
			add(new NinjaBot(1232, 3604));
			add(new NinjaBot(1164, 3652));
			add(new NinjaBot(1207, 3548));
			add(new NinjaBot(1262, 3544));
			add(new NinjaBot(1262, 3602));
			add(new NinjaBotBoss(1231, 3623));
		}
	}

	public void mobhit() {
		boolean hit = false;
		for (int i = 0; i < entities.size(); i++) {
			if (projectiles.size() >= 0) {
				for (int j = 0; j < projectiles.size(); j++) {
					int prox = projectiles.get(j).getX();
					int proy = projectiles.get(j).getY();
					prodamage = projectiles.get(j).getDamage();
					if (((prox >= entities.get(i).getXL()) && (proy <= entities.get(i).getYT())) && ((prox <= entities.get(i).getXR()) && (proy >= entities.get(i).getYB()))) {
						entities.get(i).takeDmg(prodamage);
						if (entities.get(i).getHP() <= 0) {
							entities.get(i).remove();
							for (int k = 0; k < InitialStat.questcount; k++) {
								if (InitialStat.listofquests.get(k).get(10).equals(entities.get(i).getName())) {
									String tempstring = null;
									int tempint = 0;
									String tempstring2 = null;
									int tempint2 = 0;
									tempstring = InitialStat.listofquests.get(k).get(12);
									tempint = Integer.valueOf(tempstring);
									tempstring2 = InitialStat.listofquests.get(k).get(11);
									tempint2 = Integer.valueOf(tempstring2);
									if (tempint + 1 <= tempint2) {
										tempint = tempint + 1;
										tempstring = Integer.toString(tempint);
										InitialStat.listofquests.get(k).set(12, tempstring);
									} else if (tempint + 1 > tempint2) {
										InitialStat.listofquests.get(k).set(3, "1");
										SaveLoc.SaveQuests();
									}
								}
							}
							if (InitialStat.XP + 1 > InitialStat.leveltotalxp) {
								InitialStat.PlayerLevel += 1;
								InitialStat.XP = 0;
								InitialStat.XP += 1;
								InitialStat.XPpercent = 725 / InitialStat.leveltotalxp * InitialStat.XP;
							} else {
								InitialStat.XP += 1;
								InitialStat.XPpercent = 725 / InitialStat.leveltotalxp * InitialStat.XP;
							}
						}
						projectiles.get(j).remove();
						hit = true;
						continue;
					}
				}
				if (hit == true) continue;
			}
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public boolean projectileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) / 16;
			int yt = (y - c / 2 * size + (yOffset * 3)) / 16;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4; // >> 4 == / 16
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++)
				getTile(x, y).render(x, y, screen);
		}
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).render(screen);
		for (int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).render(screen);
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).render(screen);
		for (int i = 0; i < players.size(); i++)
			players.get(i).render(screen);
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) particles.add((Particle) e);
		else if (e instanceof Projectile) projectiles.add((Projectile) e);
		else if (e instanceof Player) players.add((Player) e);
		else entities.add(e);
	}

	public void remove(Entity e) {
		e.init(this);
		if (e instanceof Particle) particles.remove((Particle) e);
		else if (e instanceof Projectile) projectiles.remove((Projectile) e);
		else if (e instanceof Player) players.remove((Player) e);
		else entities.remove(e);
	}

	public void addProjectile(Projectile p) {
		p.init(this);
		projectiles.add(p);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getPlayerAt(int index) {
		return players.get(index);
	}

	public Player getClientPlayer() {
		return players.get(0);
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + yi, y + yi);
				if (at == null) continue;
				if (at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + getDistance(current.tile, a);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= current.gCost) continue;
				if (!vecInList(openList, a) || gCost < current.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list)
			if (n.tile.equals(vector)) return true;
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = entity.getX();
			int y = entity.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(entity);
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int ex = e.getX();
		int ey = e.getY();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = player.getX();
			int y = player.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(player);
		}
		return result;
	}

	public Tile getTile(int x, int y) {
		/*timer++;
		if(timer / 1500 == 1){
			timer = 0;
			if(wateranimate == true){
				wateranimate = false;
			}else if(wateranimate == false){
				wateranimate = true;
			}
		}*/

		if (Game.nightTime == false) {
			// System.out.println("RENDER DAY TILES!");
			if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_001_tile) return Tile.outdoors_grass_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_002_tile) return Tile.outdoors_grass_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_003_tile) return Tile.outdoors_grass_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_004_tile) return Tile.outdoors_grass_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_005_tile) return Tile.outdoors_grass_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_006_tile) return Tile.outdoors_grass_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_007_tile) return Tile.outdoors_grass_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_008_tile) return Tile.outdoors_grass_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_009_tile) return Tile.outdoors_grass_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_010_tile) return Tile.outdoors_grass_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_011_tile) return Tile.outdoors_grass_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_012_tile) return Tile.outdoors_grass_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_013_tile) return Tile.outdoors_grass_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_014_tile) return Tile.outdoors_grass_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_015_tile) return Tile.outdoors_grass_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_016_tile) return Tile.outdoors_grass_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_017_tile) return Tile.outdoors_grass_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_018_tile) return Tile.outdoors_grass_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_019_tile) return Tile.outdoors_grass_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_020_tile) return Tile.outdoors_grass_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_021_tile) return Tile.outdoors_grass_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_022_tile) return Tile.outdoors_grass_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_023_tile) return Tile.outdoors_grass_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_024_tile) return Tile.outdoors_grass_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_001_tile) return Tile.outdoors_dirt_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_002_tile) return Tile.outdoors_dirt_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_003_tile) return Tile.outdoors_dirt_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_004_tile) return Tile.outdoors_dirt_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_005_tile) return Tile.outdoors_dirt_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_006_tile) return Tile.outdoors_dirt_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_007_tile) return Tile.outdoors_dirt_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_008_tile) return Tile.outdoors_dirt_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_009_tile) return Tile.outdoors_dirt_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_010_tile) return Tile.outdoors_dirt_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_011_tile) return Tile.outdoors_dirt_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_012_tile) return Tile.outdoors_dirt_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_013_tile) return Tile.outdoors_dirt_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_014_tile) return Tile.outdoors_dirt_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_015_tile) return Tile.outdoors_dirt_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_016_tile) return Tile.outdoors_dirt_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_017_tile) return Tile.outdoors_dirt_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_018_tile) return Tile.outdoors_dirt_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_019_tile) return Tile.outdoors_dirt_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_020_tile) return Tile.outdoors_dirt_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_021_tile) return Tile.outdoors_dirt_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_022_tile) return Tile.outdoors_dirt_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_023_tile) return Tile.outdoors_dirt_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_024_tile) return Tile.outdoors_dirt_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_001_tile) return Tile.outdoors_sand_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_002_tile) return Tile.outdoors_sand_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_003_tile) return Tile.outdoors_sand_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_004_tile) return Tile.outdoors_sand_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_005_tile) return Tile.outdoors_sand_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_006_tile) return Tile.outdoors_sand_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_007_tile) return Tile.outdoors_sand_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_008_tile) return Tile.outdoors_sand_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_009_tile) return Tile.outdoors_sand_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_010_tile) return Tile.outdoors_sand_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_011_tile) return Tile.outdoors_sand_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_012_tile) return Tile.outdoors_sand_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_013_tile) return Tile.outdoors_sand_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_014_tile) return Tile.outdoors_sand_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_015_tile) return Tile.outdoors_sand_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_016_tile) return Tile.outdoors_sand_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_017_tile) return Tile.outdoors_sand_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_018_tile) return Tile.outdoors_sand_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_019_tile) return Tile.outdoors_sand_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_020_tile) return Tile.outdoors_sand_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_021_tile) return Tile.outdoors_sand_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_022_tile) return Tile.outdoors_sand_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_023_tile) return Tile.outdoors_sand_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_024_tile) return Tile.outdoors_sand_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_001_tile) return Tile.outdoors_grassstone_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_002_tile) return Tile.outdoors_grassstone_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_003_tile) return Tile.outdoors_grassstone_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_004_tile) return Tile.outdoors_grassstone_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_005_tile) return Tile.outdoors_grassstone_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_006_tile) return Tile.outdoors_grassstone_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_007_tile) return Tile.outdoors_grassstone_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_008_tile) return Tile.outdoors_grassstone_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_009_tile) return Tile.outdoors_grassstone_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_010_tile) return Tile.outdoors_grassstone_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_011_tile) return Tile.outdoors_grassstone_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_012_tile) return Tile.outdoors_grassstone_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_013_tile) return Tile.outdoors_grassstone_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_014_tile) return Tile.outdoors_grassstone_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_015_tile) return Tile.outdoors_grassstone_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_016_tile) return Tile.outdoors_grassstone_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_017_tile) return Tile.outdoors_grassstone_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_018_tile) return Tile.outdoors_grassstone_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_019_tile) return Tile.outdoors_grassstone_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_020_tile) return Tile.outdoors_grassstone_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_021_tile) return Tile.outdoors_grassstone_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_022_tile) return Tile.outdoors_grassstone_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_023_tile) return Tile.outdoors_grassstone_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_024_tile) return Tile.outdoors_grassstone_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_001_tile) return Tile.outdoors_dirtstone_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_002_tile) return Tile.outdoors_dirtstone_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_003_tile) return Tile.outdoors_dirtstone_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_004_tile) return Tile.outdoors_dirtstone_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_005_tile) return Tile.outdoors_dirtstone_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_006_tile) return Tile.outdoors_dirtstone_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_007_tile) return Tile.outdoors_dirtstone_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_008_tile) return Tile.outdoors_dirtstone_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_009_tile) return Tile.outdoors_dirtstone_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_010_tile) return Tile.outdoors_dirtstone_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_011_tile) return Tile.outdoors_dirtstone_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_012_tile) return Tile.outdoors_dirtstone_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_013_tile) return Tile.outdoors_dirtstone_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_014_tile) return Tile.outdoors_dirtstone_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_015_tile) return Tile.outdoors_dirtstone_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_016_tile) return Tile.outdoors_dirtstone_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_017_tile) return Tile.outdoors_dirtstone_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_018_tile) return Tile.outdoors_dirtstone_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_019_tile) return Tile.outdoors_dirtstone_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_020_tile) return Tile.outdoors_dirtstone_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_021_tile) return Tile.outdoors_dirtstone_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_022_tile) return Tile.outdoors_dirtstone_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_023_tile) return Tile.outdoors_dirtstone_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_024_tile) return Tile.outdoors_dirtstone_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_001_tile) return Tile.outdoors_sandstone_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_002_tile) return Tile.outdoors_sandstone_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_003_tile) return Tile.outdoors_sandstone_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_004_tile) return Tile.outdoors_sandstone_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_005_tile) return Tile.outdoors_sandstone_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_006_tile) return Tile.outdoors_sandstone_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_007_tile) return Tile.outdoors_sandstone_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_008_tile) return Tile.outdoors_sandstone_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_009_tile) return Tile.outdoors_sandstone_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_010_tile) return Tile.outdoors_sandstone_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_011_tile) return Tile.outdoors_sandstone_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_012_tile) return Tile.outdoors_sandstone_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_013_tile) return Tile.outdoors_sandstone_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_014_tile) return Tile.outdoors_sandstone_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_015_tile) return Tile.outdoors_sandstone_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_016_tile) return Tile.outdoors_sandstone_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_017_tile) return Tile.outdoors_sandstone_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_018_tile) return Tile.outdoors_sandstone_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_019_tile) return Tile.outdoors_sandstone_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_020_tile) return Tile.outdoors_sandstone_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_021_tile) return Tile.outdoors_sandstone_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_022_tile) return Tile.outdoors_sandstone_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_023_tile) return Tile.outdoors_sandstone_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_024_tile) return Tile.outdoors_sandstone_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_001_tile) return Tile.outdoors_grasswater_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_002_tile) return Tile.outdoors_grasswater_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_003_tile) return Tile.outdoors_grasswater_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_004_tile) return Tile.outdoors_grasswater_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_005_tile) return Tile.outdoors_grasswater_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_006_tile) return Tile.outdoors_grasswater_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_007_tile) return Tile.outdoors_grasswater_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_008_tile) return Tile.outdoors_grasswater_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_009_tile) return Tile.outdoors_grasswater_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_010_tile) return Tile.outdoors_grasswater_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_011_tile) return Tile.outdoors_grasswater_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_012_tile) return Tile.outdoors_grasswater_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_013_tile) return Tile.outdoors_grasswater_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_014_tile) return Tile.outdoors_grasswater_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_015_tile) return Tile.outdoors_grasswater_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_016_tile) return Tile.outdoors_grasswater_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_017_tile) return Tile.outdoors_grasswater_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_018_tile) return Tile.outdoors_grasswater_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_019_tile) return Tile.outdoors_grasswater_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_020_tile) return Tile.outdoors_grasswater_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_021_tile) return Tile.outdoors_grasswater_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_022_tile) return Tile.outdoors_grasswater_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_023_tile) return Tile.outdoors_grasswater_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_024_tile) return Tile.outdoors_grasswater_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_001_tile) return Tile.outdoors_dirtwater_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_002_tile) return Tile.outdoors_dirtwater_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_003_tile) return Tile.outdoors_dirtwater_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_004_tile) return Tile.outdoors_dirtwater_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_005_tile) return Tile.outdoors_dirtwater_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_006_tile) return Tile.outdoors_dirtwater_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_007_tile) return Tile.outdoors_dirtwater_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_008_tile) return Tile.outdoors_dirtwater_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_009_tile) return Tile.outdoors_dirtwater_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_010_tile) return Tile.outdoors_dirtwater_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_011_tile) return Tile.outdoors_dirtwater_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_012_tile) return Tile.outdoors_dirtwater_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_013_tile) return Tile.outdoors_dirtwater_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_014_tile) return Tile.outdoors_dirtwater_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_015_tile) return Tile.outdoors_dirtwater_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_016_tile) return Tile.outdoors_dirtwater_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_017_tile) return Tile.outdoors_dirtwater_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_018_tile) return Tile.outdoors_dirtwater_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_019_tile) return Tile.outdoors_dirtwater_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_020_tile) return Tile.outdoors_dirtwater_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_021_tile) return Tile.outdoors_dirtwater_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_022_tile) return Tile.outdoors_dirtwater_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_023_tile) return Tile.outdoors_dirtwater_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_024_tile) return Tile.outdoors_dirtwater_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_001_tile) return Tile.outdoors_sandwater_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_002_tile) return Tile.outdoors_sandwater_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_003_tile) return Tile.outdoors_sandwater_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_004_tile) return Tile.outdoors_sandwater_004_tile;
			/*if(wateranimate == true){
				System.out.println("animateion stage 1");
				if (tiles[x + y * width] == Tile.col_outdoors_sandwater_005_tile)return Tile.outdoors_sandwater_005_tile;
				if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile)return Tile.outdoors_sandwater_006_tile;
			}else if(wateranimate == false){
				System.out.println("animateion stage 1");
				if (tiles[x + y * width] == Tile.col_outdoors_sandwater_005_tile)return Tile.outdoors_sandwater_006_tile;
				if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile)return Tile.outdoors_sandwater_005_tile;
			}*/
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_005_tile) return Tile.outdoors_sandwater_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile) return Tile.outdoors_sandwater_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_007_tile) return Tile.outdoors_sandwater_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_008_tile) return Tile.outdoors_sandwater_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_009_tile) return Tile.outdoors_sandwater_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_010_tile) return Tile.outdoors_sandwater_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_011_tile) return Tile.outdoors_sandwater_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_012_tile) return Tile.outdoors_sandwater_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_013_tile) return Tile.outdoors_sandwater_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_014_tile) return Tile.outdoors_sandwater_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_015_tile) return Tile.outdoors_sandwater_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_016_tile) return Tile.outdoors_sandwater_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_017_tile) return Tile.outdoors_sandwater_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_018_tile) return Tile.outdoors_sandwater_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_019_tile) return Tile.outdoors_sandwater_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_020_tile) return Tile.outdoors_sandwater_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_021_tile) return Tile.outdoors_sandwater_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_022_tile) return Tile.outdoors_sandwater_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_023_tile) return Tile.outdoors_sandwater_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_024_tile) return Tile.outdoors_sandwater_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_001_tile) return Tile.outdoors_grassdirt_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_002_tile) return Tile.outdoors_grassdirt_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_003_tile) return Tile.outdoors_grassdirt_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_004_tile) return Tile.outdoors_grassdirt_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_005_tile) return Tile.outdoors_grassdirt_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_006_tile) return Tile.outdoors_grassdirt_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_007_tile) return Tile.outdoors_grassdirt_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_008_tile) return Tile.outdoors_grassdirt_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_009_tile) return Tile.outdoors_grassdirt_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_010_tile) return Tile.outdoors_grassdirt_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_011_tile) return Tile.outdoors_grassdirt_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_012_tile) return Tile.outdoors_grassdirt_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_013_tile) return Tile.outdoors_grassdirt_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_014_tile) return Tile.outdoors_grassdirt_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_015_tile) return Tile.outdoors_grassdirt_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_016_tile) return Tile.outdoors_grassdirt_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_017_tile) return Tile.outdoors_grassdirt_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_018_tile) return Tile.outdoors_grassdirt_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_019_tile) return Tile.outdoors_grassdirt_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_020_tile) return Tile.outdoors_grassdirt_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_021_tile) return Tile.outdoors_grassdirt_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_022_tile) return Tile.outdoors_grassdirt_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_023_tile) return Tile.outdoors_grassdirt_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_024_tile) return Tile.outdoors_grassdirt_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_001_tile) return Tile.outdoors_dirtgrass_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_002_tile) return Tile.outdoors_dirtgrass_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_003_tile) return Tile.outdoors_dirtgrass_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_004_tile) return Tile.outdoors_dirtgrass_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_005_tile) return Tile.outdoors_dirtgrass_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_006_tile) return Tile.outdoors_dirtgrass_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_007_tile) return Tile.outdoors_dirtgrass_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_008_tile) return Tile.outdoors_dirtgrass_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_009_tile) return Tile.outdoors_dirtgrass_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_010_tile) return Tile.outdoors_dirtgrass_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_011_tile) return Tile.outdoors_dirtgrass_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_012_tile) return Tile.outdoors_dirtgrass_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_013_tile) return Tile.outdoors_dirtgrass_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_014_tile) return Tile.outdoors_dirtgrass_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_015_tile) return Tile.outdoors_dirtgrass_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_016_tile) return Tile.outdoors_dirtgrass_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_017_tile) return Tile.outdoors_dirtgrass_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_018_tile) return Tile.outdoors_dirtgrass_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_019_tile) return Tile.outdoors_dirtgrass_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_020_tile) return Tile.outdoors_dirtgrass_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_021_tile) return Tile.outdoors_dirtgrass_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_022_tile) return Tile.outdoors_dirtgrass_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_023_tile) return Tile.outdoors_dirtgrass_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_024_tile) return Tile.outdoors_dirtgrass_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_001_tile) return Tile.outdoors_sandgrass_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_002_tile) return Tile.outdoors_sandgrass_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_003_tile) return Tile.outdoors_sandgrass_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_004_tile) return Tile.outdoors_sandgrass_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_005_tile) return Tile.outdoors_sandgrass_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_006_tile) return Tile.outdoors_sandgrass_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_007_tile) return Tile.outdoors_sandgrass_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_008_tile) return Tile.outdoors_sandgrass_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_009_tile) return Tile.outdoors_sandgrass_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_010_tile) return Tile.outdoors_sandgrass_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_011_tile) return Tile.outdoors_sandgrass_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_012_tile) return Tile.outdoors_sandgrass_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_013_tile) return Tile.outdoors_sandgrass_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_014_tile) return Tile.outdoors_sandgrass_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_015_tile) return Tile.outdoors_sandgrass_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_016_tile) return Tile.outdoors_sandgrass_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_017_tile) return Tile.outdoors_sandgrass_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_018_tile) return Tile.outdoors_sandgrass_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_019_tile) return Tile.outdoors_sandgrass_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_020_tile) return Tile.outdoors_sandgrass_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_021_tile) return Tile.outdoors_sandgrass_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_022_tile) return Tile.outdoors_sandgrass_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_023_tile) return Tile.outdoors_sandgrass_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_024_tile) return Tile.outdoors_sandgrass_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_001_tile) return Tile.outdoors_grasscliff_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_002_tile) return Tile.outdoors_grasscliff_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_003_tile) return Tile.outdoors_grasscliff_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_004_tile) return Tile.outdoors_grasscliff_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_005_tile) return Tile.outdoors_grasscliff_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_006_tile) return Tile.outdoors_grasscliff_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_007_tile) return Tile.outdoors_grasscliff_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_008_tile) return Tile.outdoors_grasscliff_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_009_tile) return Tile.outdoors_grasscliff_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_010_tile) return Tile.outdoors_grasscliff_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_011_tile) return Tile.outdoors_grasscliff_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_012_tile) return Tile.outdoors_grasscliff_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_013_tile) return Tile.outdoors_grasscliff_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_014_tile) return Tile.outdoors_grasscliff_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_015_tile) return Tile.outdoors_grasscliff_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_016_tile) return Tile.outdoors_grasscliff_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_017_tile) return Tile.outdoors_grasscliff_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_018_tile) return Tile.outdoors_grasscliff_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_019_tile) return Tile.outdoors_grasscliff_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_020_tile) return Tile.outdoors_grasscliff_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_021_tile) return Tile.outdoors_grasscliff_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_022_tile) return Tile.outdoors_grasscliff_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_023_tile) return Tile.outdoors_grasscliff_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_024_tile) return Tile.outdoors_grasscliff_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_025_tile) return Tile.outdoors_grasscliff_025_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_026_tile) return Tile.outdoors_grasscliff_026_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_027_tile) return Tile.outdoors_grasscliff_027_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_028_tile) return Tile.outdoors_grasscliff_028_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_029_tile) return Tile.outdoors_grasscliff_029_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_030_tile) return Tile.outdoors_grasscliff_030_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_031_tile) return Tile.outdoors_grasscliff_031_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_032_tile) return Tile.outdoors_grasscliff_032_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_033_tile) return Tile.outdoors_grasscliff_033_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_034_tile) return Tile.outdoors_grasscliff_034_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_035_tile) return Tile.outdoors_grasscliff_035_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_036_tile) return Tile.outdoors_grasscliff_036_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_037_tile) return Tile.outdoors_grasscliff_037_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_038_tile) return Tile.outdoors_grasscliff_038_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_039_tile) return Tile.outdoors_grasscliff_039_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_040_tile) return Tile.outdoors_grasscliff_040_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_001_tile) return Tile.outdoors_dirtcliff_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_002_tile) return Tile.outdoors_dirtcliff_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_003_tile) return Tile.outdoors_dirtcliff_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_004_tile) return Tile.outdoors_dirtcliff_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_005_tile) return Tile.outdoors_dirtcliff_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_006_tile) return Tile.outdoors_dirtcliff_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_007_tile) return Tile.outdoors_dirtcliff_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_008_tile) return Tile.outdoors_dirtcliff_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_009_tile) return Tile.outdoors_dirtcliff_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_010_tile) return Tile.outdoors_dirtcliff_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_011_tile) return Tile.outdoors_dirtcliff_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_012_tile) return Tile.outdoors_dirtcliff_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_013_tile) return Tile.outdoors_dirtcliff_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_014_tile) return Tile.outdoors_dirtcliff_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_015_tile) return Tile.outdoors_dirtcliff_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_016_tile) return Tile.outdoors_dirtcliff_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_017_tile) return Tile.outdoors_dirtcliff_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_018_tile) return Tile.outdoors_dirtcliff_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_019_tile) return Tile.outdoors_dirtcliff_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_020_tile) return Tile.outdoors_dirtcliff_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_021_tile) return Tile.outdoors_dirtcliff_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_022_tile) return Tile.outdoors_dirtcliff_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_023_tile) return Tile.outdoors_dirtcliff_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_024_tile) return Tile.outdoors_dirtcliff_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_025_tile) return Tile.outdoors_dirtcliff_025_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_026_tile) return Tile.outdoors_dirtcliff_026_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_027_tile) return Tile.outdoors_dirtcliff_027_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_028_tile) return Tile.outdoors_dirtcliff_028_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_029_tile) return Tile.outdoors_dirtcliff_029_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_030_tile) return Tile.outdoors_dirtcliff_030_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_031_tile) return Tile.outdoors_dirtcliff_031_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_032_tile) return Tile.outdoors_dirtcliff_032_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_033_tile) return Tile.outdoors_dirtcliff_033_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_034_tile) return Tile.outdoors_dirtcliff_034_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_035_tile) return Tile.outdoors_dirtcliff_035_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_036_tile) return Tile.outdoors_dirtcliff_036_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_037_tile) return Tile.outdoors_dirtcliff_037_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_038_tile) return Tile.outdoors_dirtcliff_038_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_039_tile) return Tile.outdoors_dirtcliff_039_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_040_tile) return Tile.outdoors_dirtcliff_040_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_001_tile) return Tile.outdoors_sandcliff_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_002_tile) return Tile.outdoors_sandcliff_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_003_tile) return Tile.outdoors_sandcliff_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_004_tile) return Tile.outdoors_sandcliff_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_005_tile) return Tile.outdoors_sandcliff_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_006_tile) return Tile.outdoors_sandcliff_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_007_tile) return Tile.outdoors_sandcliff_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_008_tile) return Tile.outdoors_sandcliff_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_009_tile) return Tile.outdoors_sandcliff_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_010_tile) return Tile.outdoors_sandcliff_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_011_tile) return Tile.outdoors_sandcliff_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_012_tile) return Tile.outdoors_sandcliff_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_013_tile) return Tile.outdoors_sandcliff_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_014_tile) return Tile.outdoors_sandcliff_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_015_tile) return Tile.outdoors_sandcliff_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_016_tile) return Tile.outdoors_sandcliff_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_017_tile) return Tile.outdoors_sandcliff_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_018_tile) return Tile.outdoors_sandcliff_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_019_tile) return Tile.outdoors_sandcliff_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_020_tile) return Tile.outdoors_sandcliff_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_021_tile) return Tile.outdoors_sandcliff_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_022_tile) return Tile.outdoors_sandcliff_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_023_tile) return Tile.outdoors_sandcliff_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_024_tile) return Tile.outdoors_sandcliff_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_025_tile) return Tile.outdoors_sandcliff_025_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_026_tile) return Tile.outdoors_sandcliff_026_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_027_tile) return Tile.outdoors_sandcliff_027_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_028_tile) return Tile.outdoors_sandcliff_028_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_029_tile) return Tile.outdoors_sandcliff_029_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_030_tile) return Tile.outdoors_sandcliff_030_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_031_tile) return Tile.outdoors_sandcliff_031_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_032_tile) return Tile.outdoors_sandcliff_032_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_033_tile) return Tile.outdoors_sandcliff_033_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_034_tile) return Tile.outdoors_sandcliff_034_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_035_tile) return Tile.outdoors_sandcliff_035_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_036_tile) return Tile.outdoors_sandcliff_036_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_037_tile) return Tile.outdoors_sandcliff_037_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_038_tile) return Tile.outdoors_sandcliff_038_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_039_tile) return Tile.outdoors_sandcliff_039_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_040_tile) return Tile.outdoors_sandcliff_040_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffgrass_001_tile) return Tile.outdoors_sandcliffgrass_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffgrass_002_tile) return Tile.outdoors_sandcliffgrass_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffgrass_003_tile) return Tile.outdoors_sandcliffgrass_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffgrass_004_tile) return Tile.outdoors_sandcliffgrass_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffcave_001_tile) return Tile.outdoors_sandcliffcave_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffcave_002_tile) return Tile.outdoors_sandcliffcave_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffcave_003_tile) return Tile.outdoors_sandcliffcave_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffcave_004_tile) return Tile.outdoors_sandcliffcave_004_tile;
			if (tiles[x + y * width] == Tile.col_trees_001_tile) return Tile.trees_001_tile;
			if (tiles[x + y * width] == Tile.col_trees_002_tile) return Tile.trees_002_tile;
			if (tiles[x + y * width] == Tile.col_trees_003_tile) return Tile.trees_003_tile;
			if (tiles[x + y * width] == Tile.col_trees_004_tile) return Tile.trees_004_tile;
			if (tiles[x + y * width] == Tile.col_trees_005_tile) return Tile.trees_005_tile;
			if (tiles[x + y * width] == Tile.col_trees_006_tile) return Tile.trees_006_tile;
			if (tiles[x + y * width] == Tile.col_trees_007_tile) return Tile.trees_007_tile;
			if (tiles[x + y * width] == Tile.col_trees_008_tile) return Tile.trees_008_tile;
			if (tiles[x + y * width] == Tile.col_trees_009_tile) return Tile.trees_009_tile;
			if (tiles[x + y * width] == Tile.col_trees_010_tile) return Tile.trees_010_tile;
			if (tiles[x + y * width] == Tile.col_trees_011_tile) return Tile.trees_011_tile;
			if (tiles[x + y * width] == Tile.col_trees_012_tile) return Tile.trees_012_tile;
			if (tiles[x + y * width] == Tile.col_trees_013_tile) return Tile.trees_013_tile;
			if (tiles[x + y * width] == Tile.col_trees_014_tile) return Tile.trees_014_tile;
			if (tiles[x + y * width] == Tile.col_trees_015_tile) return Tile.trees_015_tile;
			if (tiles[x + y * width] == Tile.col_trees_016_tile) return Tile.trees_016_tile;
			if (tiles[x + y * width] == Tile.col_trees_017_tile) return Tile.trees_017_tile;
			if (tiles[x + y * width] == Tile.col_trees_018_tile) return Tile.trees_018_tile;
			if (tiles[x + y * width] == Tile.col_trees_019_tile) return Tile.trees_019_tile;
			if (tiles[x + y * width] == Tile.col_trees_020_tile) return Tile.trees_020_tile;
			if (tiles[x + y * width] == Tile.col_trees_021_tile) return Tile.trees_021_tile;
			if (tiles[x + y * width] == Tile.col_trees_022_tile) return Tile.trees_022_tile;
			if (tiles[x + y * width] == Tile.col_trees_023_tile) return Tile.trees_023_tile;
			if (tiles[x + y * width] == Tile.col_trees_024_tile) return Tile.trees_024_tile;
			if (tiles[x + y * width] == Tile.col_trees_025_tile) return Tile.trees_025_tile;
			if (tiles[x + y * width] == Tile.col_trees_026_tile) return Tile.trees_026_tile;
			if (tiles[x + y * width] == Tile.col_trees_027_tile) return Tile.trees_027_tile;
			if (tiles[x + y * width] == Tile.col_trees_028_tile) return Tile.trees_028_tile;
			if (tiles[x + y * width] == Tile.col_trees_029_tile) return Tile.trees_029_tile;
			if (tiles[x + y * width] == Tile.col_trees_030_tile) return Tile.trees_030_tile;
			if (tiles[x + y * width] == Tile.col_trees_031_tile) return Tile.trees_031_tile;
			if (tiles[x + y * width] == Tile.col_trees_032_tile) return Tile.trees_032_tile;
			if (tiles[x + y * width] == Tile.col_trees_033_tile) return Tile.trees_033_tile;
			if (tiles[x + y * width] == Tile.col_trees_034_tile) return Tile.trees_034_tile;
			if (tiles[x + y * width] == Tile.col_trees_035_tile) return Tile.trees_035_tile;
			if (tiles[x + y * width] == Tile.col_trees_036_tile) return Tile.trees_036_tile;
			if (tiles[x + y * width] == Tile.col_trees_037_tile) return Tile.trees_037_tile;
			if (tiles[x + y * width] == Tile.col_trees_038_tile) return Tile.trees_038_tile;
			if (tiles[x + y * width] == Tile.col_trees_039_tile) return Tile.trees_039_tile;
			if (tiles[x + y * width] == Tile.col_trees_040_tile) return Tile.trees_040_tile;
			if (tiles[x + y * width] == Tile.col_trees_041_tile) return Tile.trees_041_tile;
			if (tiles[x + y * width] == Tile.col_trees_042_tile) return Tile.trees_042_tile;
			if (tiles[x + y * width] == Tile.col_trees_043_tile) return Tile.trees_043_tile;
			if (tiles[x + y * width] == Tile.col_trees_044_tile) return Tile.trees_044_tile;
			if (tiles[x + y * width] == Tile.col_trees_045_tile) return Tile.trees_045_tile;
			if (tiles[x + y * width] == Tile.col_trees_046_tile) return Tile.trees_046_tile;
			if (tiles[x + y * width] == Tile.col_trees_047_tile) return Tile.trees_047_tile;
			if (tiles[x + y * width] == Tile.col_trees_048_tile) return Tile.trees_048_tile;
			if (tiles[x + y * width] == Tile.col_voidTile) return Tile.voidTile;

		} else if (Game.nightTime) {
			// System.out.println("RENDERING NIGHT TILES!");
			if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_001_tile) return Tile.outdoors_grass_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_002_tile) return Tile.outdoors_grass_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_003_tile) return Tile.outdoors_grass_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_004_tile) return Tile.outdoors_grass_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_005_tile) return Tile.outdoors_grass_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_006_tile) return Tile.outdoors_grass_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_007_tile) return Tile.outdoors_grass_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_008_tile) return Tile.outdoors_grass_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_009_tile) return Tile.outdoors_grass_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_010_tile) return Tile.outdoors_grass_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_011_tile) return Tile.outdoors_grass_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_012_tile) return Tile.outdoors_grass_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_013_tile) return Tile.outdoors_grass_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_014_tile) return Tile.outdoors_grass_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_015_tile) return Tile.outdoors_grass_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_016_tile) return Tile.outdoors_grass_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_017_tile) return Tile.outdoors_grass_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_018_tile) return Tile.outdoors_grass_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_019_tile) return Tile.outdoors_grass_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_020_tile) return Tile.outdoors_grass_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_021_tile) return Tile.outdoors_grass_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_022_tile) return Tile.outdoors_grass_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_023_tile) return Tile.outdoors_grass_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_024_tile) return Tile.outdoors_grass_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_001_tile) return Tile.outdoors_dirt_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_002_tile) return Tile.outdoors_dirt_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_003_tile) return Tile.outdoors_dirt_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_004_tile) return Tile.outdoors_dirt_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_005_tile) return Tile.outdoors_dirt_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_006_tile) return Tile.outdoors_dirt_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_007_tile) return Tile.outdoors_dirt_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_008_tile) return Tile.outdoors_dirt_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_009_tile) return Tile.outdoors_dirt_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_010_tile) return Tile.outdoors_dirt_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_011_tile) return Tile.outdoors_dirt_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_012_tile) return Tile.outdoors_dirt_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_013_tile) return Tile.outdoors_dirt_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_014_tile) return Tile.outdoors_dirt_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_015_tile) return Tile.outdoors_dirt_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_016_tile) return Tile.outdoors_dirt_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_017_tile) return Tile.outdoors_dirt_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_018_tile) return Tile.outdoors_dirt_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_019_tile) return Tile.outdoors_dirt_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_020_tile) return Tile.outdoors_dirt_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_021_tile) return Tile.outdoors_dirt_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_022_tile) return Tile.outdoors_dirt_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_023_tile) return Tile.outdoors_dirt_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_024_tile) return Tile.outdoors_dirt_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_001_tile) return Tile.outdoors_sand_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_002_tile) return Tile.outdoors_sand_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_003_tile) return Tile.outdoors_sand_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_004_tile) return Tile.outdoors_sand_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_005_tile) return Tile.outdoors_sand_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_006_tile) return Tile.outdoors_sand_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_007_tile) return Tile.outdoors_sand_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_008_tile) return Tile.outdoors_sand_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_009_tile) return Tile.outdoors_sand_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_010_tile) return Tile.outdoors_sand_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_011_tile) return Tile.outdoors_sand_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_012_tile) return Tile.outdoors_sand_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_013_tile) return Tile.outdoors_sand_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_014_tile) return Tile.outdoors_sand_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_015_tile) return Tile.outdoors_sand_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_016_tile) return Tile.outdoors_sand_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_017_tile) return Tile.outdoors_sand_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_018_tile) return Tile.outdoors_sand_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_019_tile) return Tile.outdoors_sand_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_020_tile) return Tile.outdoors_sand_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_021_tile) return Tile.outdoors_sand_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_022_tile) return Tile.outdoors_sand_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_023_tile) return Tile.outdoors_sand_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_024_tile) return Tile.outdoors_sand_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_001_tile) return Tile.outdoors_grassstone_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_002_tile) return Tile.outdoors_grassstone_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_003_tile) return Tile.outdoors_grassstone_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_004_tile) return Tile.outdoors_grassstone_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_005_tile) return Tile.outdoors_grassstone_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_006_tile) return Tile.outdoors_grassstone_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_007_tile) return Tile.outdoors_grassstone_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_008_tile) return Tile.outdoors_grassstone_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_009_tile) return Tile.outdoors_grassstone_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_010_tile) return Tile.outdoors_grassstone_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_011_tile) return Tile.outdoors_grassstone_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_012_tile) return Tile.outdoors_grassstone_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_013_tile) return Tile.outdoors_grassstone_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_014_tile) return Tile.outdoors_grassstone_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_015_tile) return Tile.outdoors_grassstone_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_016_tile) return Tile.outdoors_grassstone_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_017_tile) return Tile.outdoors_grassstone_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_018_tile) return Tile.outdoors_grassstone_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_019_tile) return Tile.outdoors_grassstone_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_020_tile) return Tile.outdoors_grassstone_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_021_tile) return Tile.outdoors_grassstone_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_022_tile) return Tile.outdoors_grassstone_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_023_tile) return Tile.outdoors_grassstone_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_024_tile) return Tile.outdoors_grassstone_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_001_tile) return Tile.outdoors_dirtstone_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_002_tile) return Tile.outdoors_dirtstone_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_003_tile) return Tile.outdoors_dirtstone_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_004_tile) return Tile.outdoors_dirtstone_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_005_tile) return Tile.outdoors_dirtstone_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_006_tile) return Tile.outdoors_dirtstone_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_007_tile) return Tile.outdoors_dirtstone_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_008_tile) return Tile.outdoors_dirtstone_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_009_tile) return Tile.outdoors_dirtstone_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_010_tile) return Tile.outdoors_dirtstone_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_011_tile) return Tile.outdoors_dirtstone_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_012_tile) return Tile.outdoors_dirtstone_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_013_tile) return Tile.outdoors_dirtstone_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_014_tile) return Tile.outdoors_dirtstone_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_015_tile) return Tile.outdoors_dirtstone_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_016_tile) return Tile.outdoors_dirtstone_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_017_tile) return Tile.outdoors_dirtstone_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_018_tile) return Tile.outdoors_dirtstone_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_019_tile) return Tile.outdoors_dirtstone_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_020_tile) return Tile.outdoors_dirtstone_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_021_tile) return Tile.outdoors_dirtstone_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_022_tile) return Tile.outdoors_dirtstone_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_023_tile) return Tile.outdoors_dirtstone_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_024_tile) return Tile.outdoors_dirtstone_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_001_tile) return Tile.outdoors_sandstone_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_002_tile) return Tile.outdoors_sandstone_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_003_tile) return Tile.outdoors_sandstone_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_004_tile) return Tile.outdoors_sandstone_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_005_tile) return Tile.outdoors_sandstone_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_006_tile) return Tile.outdoors_sandstone_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_007_tile) return Tile.outdoors_sandstone_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_008_tile) return Tile.outdoors_sandstone_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_009_tile) return Tile.outdoors_sandstone_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_010_tile) return Tile.outdoors_sandstone_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_011_tile) return Tile.outdoors_sandstone_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_012_tile) return Tile.outdoors_sandstone_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_013_tile) return Tile.outdoors_sandstone_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_014_tile) return Tile.outdoors_sandstone_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_015_tile) return Tile.outdoors_sandstone_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_016_tile) return Tile.outdoors_sandstone_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_017_tile) return Tile.outdoors_sandstone_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_018_tile) return Tile.outdoors_sandstone_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_019_tile) return Tile.outdoors_sandstone_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_020_tile) return Tile.outdoors_sandstone_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_021_tile) return Tile.outdoors_sandstone_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_022_tile) return Tile.outdoors_sandstone_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_023_tile) return Tile.outdoors_sandstone_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_024_tile) return Tile.outdoors_sandstone_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_001_tile) return Tile.outdoors_grasswater_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_002_tile) return Tile.outdoors_grasswater_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_003_tile) return Tile.outdoors_grasswater_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_004_tile) return Tile.outdoors_grasswater_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_005_tile) return Tile.outdoors_grasswater_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_006_tile) return Tile.outdoors_grasswater_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_007_tile) return Tile.outdoors_grasswater_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_008_tile) return Tile.outdoors_grasswater_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_009_tile) return Tile.outdoors_grasswater_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_010_tile) return Tile.outdoors_grasswater_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_011_tile) return Tile.outdoors_grasswater_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_012_tile) return Tile.outdoors_grasswater_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_013_tile) return Tile.outdoors_grasswater_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_014_tile) return Tile.outdoors_grasswater_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_015_tile) return Tile.outdoors_grasswater_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_016_tile) return Tile.outdoors_grasswater_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_017_tile) return Tile.outdoors_grasswater_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_018_tile) return Tile.outdoors_grasswater_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_019_tile) return Tile.outdoors_grasswater_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_020_tile) return Tile.outdoors_grasswater_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_021_tile) return Tile.outdoors_grasswater_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_022_tile) return Tile.outdoors_grasswater_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_023_tile) return Tile.outdoors_grasswater_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_024_tile) return Tile.outdoors_grasswater_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_001_tile) return Tile.outdoors_dirtwater_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_002_tile) return Tile.outdoors_dirtwater_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_003_tile) return Tile.outdoors_dirtwater_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_004_tile) return Tile.outdoors_dirtwater_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_005_tile) return Tile.outdoors_dirtwater_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_006_tile) return Tile.outdoors_dirtwater_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_007_tile) return Tile.outdoors_dirtwater_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_008_tile) return Tile.outdoors_dirtwater_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_009_tile) return Tile.outdoors_dirtwater_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_010_tile) return Tile.outdoors_dirtwater_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_011_tile) return Tile.outdoors_dirtwater_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_012_tile) return Tile.outdoors_dirtwater_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_013_tile) return Tile.outdoors_dirtwater_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_014_tile) return Tile.outdoors_dirtwater_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_015_tile) return Tile.outdoors_dirtwater_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_016_tile) return Tile.outdoors_dirtwater_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_017_tile) return Tile.outdoors_dirtwater_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_018_tile) return Tile.outdoors_dirtwater_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_019_tile) return Tile.outdoors_dirtwater_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_020_tile) return Tile.outdoors_dirtwater_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_021_tile) return Tile.outdoors_dirtwater_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_022_tile) return Tile.outdoors_dirtwater_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_023_tile) return Tile.outdoors_dirtwater_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_024_tile) return Tile.outdoors_dirtwater_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_001_tile) return Tile.outdoors_sandwater_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_002_tile) return Tile.outdoors_sandwater_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_003_tile) return Tile.outdoors_sandwater_003_night_tile;
			/*if(wateranimate == true){
				System.out.println("animateion stage 1   " + wateranimate);
				if (tiles[x + y * width] == Tile.col_outdoors_sandwater_005_tile)return Tile.outdoors_sandwater_005_night_tile;
				if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile)return Tile.outdoors_sandwater_006_night_tile;
			}else if(wateranimate == false){
				System.out.println("animateion stage 2   " + wateranimate);
				if (tiles[x + y * width] == Tile.col_outdoors_sandwater_005_tile)return Tile.outdoors_sandwater_006_night_tile;
				if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile)return Tile.outdoors_sandwater_005_night_tile;
			}*/
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_005_tile) return Tile.outdoors_sandwater_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile) return Tile.outdoors_sandwater_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile) return Tile.outdoors_sandwater_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_007_tile) return Tile.outdoors_sandwater_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_008_tile) return Tile.outdoors_sandwater_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_009_tile) return Tile.outdoors_sandwater_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_010_tile) return Tile.outdoors_sandwater_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_011_tile) return Tile.outdoors_sandwater_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_012_tile) return Tile.outdoors_sandwater_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_013_tile) return Tile.outdoors_sandwater_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_014_tile) return Tile.outdoors_sandwater_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_015_tile) return Tile.outdoors_sandwater_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_016_tile) return Tile.outdoors_sandwater_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_017_tile) return Tile.outdoors_sandwater_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_018_tile) return Tile.outdoors_sandwater_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_019_tile) return Tile.outdoors_sandwater_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_020_tile) return Tile.outdoors_sandwater_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_021_tile) return Tile.outdoors_sandwater_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_022_tile) return Tile.outdoors_sandwater_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_023_tile) return Tile.outdoors_sandwater_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_024_tile) return Tile.outdoors_sandwater_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_001_tile) return Tile.outdoors_grassdirt_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_002_tile) return Tile.outdoors_grassdirt_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_003_tile) return Tile.outdoors_grassdirt_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_004_tile) return Tile.outdoors_grassdirt_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_005_tile) return Tile.outdoors_grassdirt_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_006_tile) return Tile.outdoors_grassdirt_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_007_tile) return Tile.outdoors_grassdirt_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_008_tile) return Tile.outdoors_grassdirt_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_009_tile) return Tile.outdoors_grassdirt_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_010_tile) return Tile.outdoors_grassdirt_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_011_tile) return Tile.outdoors_grassdirt_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_012_tile) return Tile.outdoors_grassdirt_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_013_tile) return Tile.outdoors_grassdirt_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_014_tile) return Tile.outdoors_grassdirt_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_015_tile) return Tile.outdoors_grassdirt_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_016_tile) return Tile.outdoors_grassdirt_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_017_tile) return Tile.outdoors_grassdirt_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_018_tile) return Tile.outdoors_grassdirt_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_019_tile) return Tile.outdoors_grassdirt_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_020_tile) return Tile.outdoors_grassdirt_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_021_tile) return Tile.outdoors_grassdirt_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_022_tile) return Tile.outdoors_grassdirt_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_023_tile) return Tile.outdoors_grassdirt_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassdirt_024_tile) return Tile.outdoors_grassdirt_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_001_tile) return Tile.outdoors_dirtgrass_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_002_tile) return Tile.outdoors_dirtgrass_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_003_tile) return Tile.outdoors_dirtgrass_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_004_tile) return Tile.outdoors_dirtgrass_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_005_tile) return Tile.outdoors_dirtgrass_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_006_tile) return Tile.outdoors_dirtgrass_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_007_tile) return Tile.outdoors_dirtgrass_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_008_tile) return Tile.outdoors_dirtgrass_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_009_tile) return Tile.outdoors_dirtgrass_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_010_tile) return Tile.outdoors_dirtgrass_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_011_tile) return Tile.outdoors_dirtgrass_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_012_tile) return Tile.outdoors_dirtgrass_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_013_tile) return Tile.outdoors_dirtgrass_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_014_tile) return Tile.outdoors_dirtgrass_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_015_tile) return Tile.outdoors_dirtgrass_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_016_tile) return Tile.outdoors_dirtgrass_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_017_tile) return Tile.outdoors_dirtgrass_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_018_tile) return Tile.outdoors_dirtgrass_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_019_tile) return Tile.outdoors_dirtgrass_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_020_tile) return Tile.outdoors_dirtgrass_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_021_tile) return Tile.outdoors_dirtgrass_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_022_tile) return Tile.outdoors_dirtgrass_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_023_tile) return Tile.outdoors_dirtgrass_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtgrass_024_tile) return Tile.outdoors_dirtgrass_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_001_tile) return Tile.outdoors_sandgrass_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_002_tile) return Tile.outdoors_sandgrass_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_003_tile) return Tile.outdoors_sandgrass_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_004_tile) return Tile.outdoors_sandgrass_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_005_tile) return Tile.outdoors_sandgrass_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_006_tile) return Tile.outdoors_sandgrass_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_007_tile) return Tile.outdoors_sandgrass_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_008_tile) return Tile.outdoors_sandgrass_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_009_tile) return Tile.outdoors_sandgrass_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_010_tile) return Tile.outdoors_sandgrass_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_011_tile) return Tile.outdoors_sandgrass_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_012_tile) return Tile.outdoors_sandgrass_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_013_tile) return Tile.outdoors_sandgrass_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_014_tile) return Tile.outdoors_sandgrass_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_015_tile) return Tile.outdoors_sandgrass_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_016_tile) return Tile.outdoors_sandgrass_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_017_tile) return Tile.outdoors_sandgrass_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_018_tile) return Tile.outdoors_sandgrass_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_019_tile) return Tile.outdoors_sandgrass_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_020_tile) return Tile.outdoors_sandgrass_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_021_tile) return Tile.outdoors_sandgrass_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_022_tile) return Tile.outdoors_sandgrass_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_023_tile) return Tile.outdoors_sandgrass_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandgrass_024_tile) return Tile.outdoors_sandgrass_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_001_tile) return Tile.outdoors_grasscliff_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_002_tile) return Tile.outdoors_grasscliff_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_003_tile) return Tile.outdoors_grasscliff_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_004_tile) return Tile.outdoors_grasscliff_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_005_tile) return Tile.outdoors_grasscliff_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_006_tile) return Tile.outdoors_grasscliff_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_007_tile) return Tile.outdoors_grasscliff_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_008_tile) return Tile.outdoors_grasscliff_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_009_tile) return Tile.outdoors_grasscliff_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_010_tile) return Tile.outdoors_grasscliff_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_011_tile) return Tile.outdoors_grasscliff_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_012_tile) return Tile.outdoors_grasscliff_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_013_tile) return Tile.outdoors_grasscliff_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_014_tile) return Tile.outdoors_grasscliff_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_015_tile) return Tile.outdoors_grasscliff_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_016_tile) return Tile.outdoors_grasscliff_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_017_tile) return Tile.outdoors_grasscliff_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_018_tile) return Tile.outdoors_grasscliff_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_019_tile) return Tile.outdoors_grasscliff_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_020_tile) return Tile.outdoors_grasscliff_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_021_tile) return Tile.outdoors_grasscliff_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_022_tile) return Tile.outdoors_grasscliff_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_023_tile) return Tile.outdoors_grasscliff_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_024_tile) return Tile.outdoors_grasscliff_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_025_tile) return Tile.outdoors_grasscliff_025_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_026_tile) return Tile.outdoors_grasscliff_026_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_027_tile) return Tile.outdoors_grasscliff_027_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_028_tile) return Tile.outdoors_grasscliff_028_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_029_tile) return Tile.outdoors_grasscliff_029_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_030_tile) return Tile.outdoors_grasscliff_030_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_031_tile) return Tile.outdoors_grasscliff_031_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_032_tile) return Tile.outdoors_grasscliff_032_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_033_tile) return Tile.outdoors_grasscliff_033_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_034_tile) return Tile.outdoors_grasscliff_034_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_035_tile) return Tile.outdoors_grasscliff_035_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_036_tile) return Tile.outdoors_grasscliff_036_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_037_tile) return Tile.outdoors_grasscliff_037_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_038_tile) return Tile.outdoors_grasscliff_038_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_039_tile) return Tile.outdoors_grasscliff_039_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_040_tile) return Tile.outdoors_grasscliff_040_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_001_tile) return Tile.outdoors_dirtcliff_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_002_tile) return Tile.outdoors_dirtcliff_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_003_tile) return Tile.outdoors_dirtcliff_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_004_tile) return Tile.outdoors_dirtcliff_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_005_tile) return Tile.outdoors_dirtcliff_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_006_tile) return Tile.outdoors_dirtcliff_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_007_tile) return Tile.outdoors_dirtcliff_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_008_tile) return Tile.outdoors_dirtcliff_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_009_tile) return Tile.outdoors_dirtcliff_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_010_tile) return Tile.outdoors_dirtcliff_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_011_tile) return Tile.outdoors_dirtcliff_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_012_tile) return Tile.outdoors_dirtcliff_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_013_tile) return Tile.outdoors_dirtcliff_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_014_tile) return Tile.outdoors_dirtcliff_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_015_tile) return Tile.outdoors_dirtcliff_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_016_tile) return Tile.outdoors_dirtcliff_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_017_tile) return Tile.outdoors_dirtcliff_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_018_tile) return Tile.outdoors_dirtcliff_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_019_tile) return Tile.outdoors_dirtcliff_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_020_tile) return Tile.outdoors_dirtcliff_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_021_tile) return Tile.outdoors_dirtcliff_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_022_tile) return Tile.outdoors_dirtcliff_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_023_tile) return Tile.outdoors_dirtcliff_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_024_tile) return Tile.outdoors_dirtcliff_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_025_tile) return Tile.outdoors_dirtcliff_025_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_026_tile) return Tile.outdoors_dirtcliff_026_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_027_tile) return Tile.outdoors_dirtcliff_027_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_028_tile) return Tile.outdoors_dirtcliff_028_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_029_tile) return Tile.outdoors_dirtcliff_029_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_030_tile) return Tile.outdoors_dirtcliff_030_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_031_tile) return Tile.outdoors_dirtcliff_031_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_032_tile) return Tile.outdoors_dirtcliff_032_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_033_tile) return Tile.outdoors_dirtcliff_033_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_034_tile) return Tile.outdoors_dirtcliff_034_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_035_tile) return Tile.outdoors_dirtcliff_035_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_036_tile) return Tile.outdoors_dirtcliff_036_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_037_tile) return Tile.outdoors_dirtcliff_037_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_038_tile) return Tile.outdoors_dirtcliff_038_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_039_tile) return Tile.outdoors_dirtcliff_039_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_040_tile) return Tile.outdoors_dirtcliff_040_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_001_tile) return Tile.outdoors_sandcliff_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_002_tile) return Tile.outdoors_sandcliff_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_003_tile) return Tile.outdoors_sandcliff_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_004_tile) return Tile.outdoors_sandcliff_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_005_tile) return Tile.outdoors_sandcliff_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_006_tile) return Tile.outdoors_sandcliff_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_007_tile) return Tile.outdoors_sandcliff_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_008_tile) return Tile.outdoors_sandcliff_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_009_tile) return Tile.outdoors_sandcliff_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_010_tile) return Tile.outdoors_sandcliff_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_011_tile) return Tile.outdoors_sandcliff_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_012_tile) return Tile.outdoors_sandcliff_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_013_tile) return Tile.outdoors_sandcliff_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_014_tile) return Tile.outdoors_sandcliff_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_015_tile) return Tile.outdoors_sandcliff_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_016_tile) return Tile.outdoors_sandcliff_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_017_tile) return Tile.outdoors_sandcliff_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_018_tile) return Tile.outdoors_sandcliff_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_019_tile) return Tile.outdoors_sandcliff_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_020_tile) return Tile.outdoors_sandcliff_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_021_tile) return Tile.outdoors_sandcliff_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_022_tile) return Tile.outdoors_sandcliff_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_023_tile) return Tile.outdoors_sandcliff_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_024_tile) return Tile.outdoors_sandcliff_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_025_tile) return Tile.outdoors_sandcliff_025_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_026_tile) return Tile.outdoors_sandcliff_026_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_027_tile) return Tile.outdoors_sandcliff_027_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_028_tile) return Tile.outdoors_sandcliff_028_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_029_tile) return Tile.outdoors_sandcliff_029_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_030_tile) return Tile.outdoors_sandcliff_030_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_031_tile) return Tile.outdoors_sandcliff_031_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_032_tile) return Tile.outdoors_sandcliff_032_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_033_tile) return Tile.outdoors_sandcliff_033_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_034_tile) return Tile.outdoors_sandcliff_034_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_035_tile) return Tile.outdoors_sandcliff_035_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_036_tile) return Tile.outdoors_sandcliff_036_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_037_tile) return Tile.outdoors_sandcliff_037_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_038_tile) return Tile.outdoors_sandcliff_038_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_039_tile) return Tile.outdoors_sandcliff_039_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_040_tile) return Tile.outdoors_sandcliff_040_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffgrass_001_tile) return Tile.outdoors_sandcliffgrass_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffgrass_002_tile) return Tile.outdoors_sandcliffgrass_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffgrass_003_tile) return Tile.outdoors_sandcliffgrass_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffgrass_004_tile) return Tile.outdoors_sandcliffgrass_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffcave_001_tile) return Tile.outdoors_sandcliffcave_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffcave_002_tile) return Tile.outdoors_sandcliffcave_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffcave_003_tile) return Tile.outdoors_sandcliffcave_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliffcave_004_tile) return Tile.outdoors_sandcliffcave_004_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_001_tile) return Tile.trees_001_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_002_tile) return Tile.trees_002_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_003_tile) return Tile.trees_003_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_004_tile) return Tile.trees_004_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_005_tile) return Tile.trees_005_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_006_tile) return Tile.trees_006_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_007_tile) return Tile.trees_007_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_008_tile) return Tile.trees_008_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_009_tile) return Tile.trees_009_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_010_tile) return Tile.trees_010_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_011_tile) return Tile.trees_011_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_012_tile) return Tile.trees_012_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_013_tile) return Tile.trees_013_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_014_tile) return Tile.trees_014_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_015_tile) return Tile.trees_015_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_016_tile) return Tile.trees_016_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_017_tile) return Tile.trees_017_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_018_tile) return Tile.trees_018_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_019_tile) return Tile.trees_019_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_020_tile) return Tile.trees_020_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_021_tile) return Tile.trees_021_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_022_tile) return Tile.trees_022_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_023_tile) return Tile.trees_023_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_024_tile) return Tile.trees_024_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_025_tile) return Tile.trees_025_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_026_tile) return Tile.trees_026_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_027_tile) return Tile.trees_027_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_028_tile) return Tile.trees_028_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_029_tile) return Tile.trees_029_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_030_tile) return Tile.trees_030_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_031_tile) return Tile.trees_031_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_032_tile) return Tile.trees_032_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_033_tile) return Tile.trees_033_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_034_tile) return Tile.trees_034_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_035_tile) return Tile.trees_035_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_036_tile) return Tile.trees_036_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_037_tile) return Tile.trees_037_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_038_tile) return Tile.trees_038_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_039_tile) return Tile.trees_039_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_040_tile) return Tile.trees_040_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_041_tile) return Tile.trees_041_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_042_tile) return Tile.trees_042_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_043_tile) return Tile.trees_043_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_044_tile) return Tile.trees_044_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_045_tile) return Tile.trees_045_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_046_tile) return Tile.trees_046_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_047_tile) return Tile.trees_047_night_tile;
			if (tiles[x + y * width] == Tile.col_trees_048_tile) return Tile.trees_048_night_tile;
			if (tiles[x + y * width] == Tile.col_voidTile) return Tile.voidTile;
		}

		return Tile.voidTile;
		// return Tile.outdoors_grasscliff_018_tile;
	}
}
