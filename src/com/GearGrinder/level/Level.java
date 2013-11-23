package com.GearGrinder.level;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.GearGrinder.Game;
import com.GearGrinder.Networking.InitialStat;
import com.GearGrinder.entity.Entity;
import com.GearGrinder.entity.Projectile.Projectile;
import com.GearGrinder.entity.mob.Player;
import com.GearGrinder.entity.particle.Particle;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();
	
	public static int mobxl;//boundaries for mob
	public static int mobyb;
	public static int mobxr;
	public static int mobyt;
	public static int proxl;//boundaries for projectile
	public static int proyb;
	public static int proxr;
	public static int proyt;
	public static int prodamage;

	public static Level world = new SpawnLevel("/levels/world.png");
	public static Level dungeon1 = new SpawnLevel("/levels/dungeon1.png");
	public static Level noobisland = new SpawnLevel("/levels/noobisland.png");

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}

	public Level(String path) { // loads level from file
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() { // creates random level
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
			}
		}
		tile_size = 16;
	}

	protected void loadLevel(String path) {

	}

	public void update() { // updates the levels
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
		mobhit();
		remove();
	}

	public void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved())
				entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved())
				projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved())
				particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved())
				players.remove(i);
		}
	}
	
	public void mobhit(){
		boolean hit = false;
		for (int i = 0; i < entities.size(); i++) {
			if(projectiles.size() >= 0){
				String mobname = entities.get(i).getName();
				if(mobname == "ninjabot" || mobname == "ninjabotboss"){//sets boundaries for ninjabot/ninjabot boss mobs
					mobxl = entities.get(i).getX() - 13;
					mobyb = entities.get(i).getY() - 16;
					mobxr = entities.get(i).getX() + 10;
					mobyt = entities.get(i).getY() + 16;
				}
				for (int j = 0; j < projectiles.size(); j++) {
					int prox = projectiles.get(j).getX();
					int proy = projectiles.get(j).getY();
					prodamage = projectiles.get(j).getDamage();
					String name = projectiles.get(j).getName();
					if(((prox >= mobxl) && (proy >= mobyb )) && ((prox <= mobxr) && (proy <= mobyt))){
						entities.get(i).takeDmg(prodamage);
						if(entities.get(i).getHP() <=0){
							entities.get(i).remove();
							if(InitialStat.XP + 1 > InitialStat.leveltotalxp){
								
								InitialStat.PlayerLevel += 1;
								InitialStat.XP = 0;
								InitialStat.XP += 1;
								InitialStat.XPpercent = 725 / InitialStat.leveltotalxp * InitialStat.XP;
							}else{
								InitialStat.XP += 1;
								InitialStat.XPpercent = 725 / InitialStat.leveltotalxp * InitialStat.XP;
							}
						}
						projectiles.get(j).remove();
						hit = true;
						continue;
					}
				}
				if(hit == true){
					continue;
				}
			}				
		}
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	private void time() { // manages the time in game ( day / night)

	}

	public boolean projectileCollision(int x, int y, int size, int xOffset,
			int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) / 16;
			int yt = (y - c / 2 * size + (yOffset * 3)) / 16;
			if (getTile(xt, yt).solid())
				solid = true;
		} return solid;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4; // >> 4 == / 16
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);				
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else {
			entities.add(e);
		}
	}
	public void remove(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.remove((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.remove((Projectile) e);
		} else if (e instanceof Player) {
			players.remove((Player) e);
		} else {
			entities.remove(e);
		}
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
			if (distance <= radius)
				result.add(entity);
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
			if (distance <= radius)
				result.add(player);
		}
		return result;
	}

	public Tile getTile(int x, int y) {
		if(Game.nightTime == false){
			//System.out.println("RENDER DAY TILES!");
			if (x < 0 || y < 0 || x >= width || y >= height)return Tile.outdoors_grasscliff_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_001_tile)return Tile.outdoors_grass_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_002_tile)return Tile.outdoors_grass_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_003_tile)return Tile.outdoors_grass_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_004_tile)return Tile.outdoors_grass_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_005_tile)return Tile.outdoors_grass_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_006_tile)return Tile.outdoors_grass_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_007_tile)return Tile.outdoors_grass_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_008_tile)return Tile.outdoors_grass_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_009_tile)return Tile.outdoors_grass_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_010_tile)return Tile.outdoors_grass_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_011_tile)return Tile.outdoors_grass_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_012_tile)return Tile.outdoors_grass_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_013_tile)return Tile.outdoors_grass_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_014_tile)return Tile.outdoors_grass_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_015_tile)return Tile.outdoors_grass_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_016_tile)return Tile.outdoors_grass_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_017_tile)return Tile.outdoors_grass_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_018_tile)return Tile.outdoors_grass_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_019_tile)return Tile.outdoors_grass_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_020_tile)return Tile.outdoors_grass_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_021_tile)return Tile.outdoors_grass_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_022_tile)return Tile.outdoors_grass_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_023_tile)return Tile.outdoors_grass_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_024_tile)return Tile.outdoors_grass_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_001_tile)return Tile.outdoors_dirt_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_002_tile)return Tile.outdoors_dirt_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_003_tile)return Tile.outdoors_dirt_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_004_tile)return Tile.outdoors_dirt_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_005_tile)return Tile.outdoors_dirt_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_006_tile)return Tile.outdoors_dirt_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_007_tile)return Tile.outdoors_dirt_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_008_tile)return Tile.outdoors_dirt_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_009_tile)return Tile.outdoors_dirt_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_010_tile)return Tile.outdoors_dirt_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_011_tile)return Tile.outdoors_dirt_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_012_tile)return Tile.outdoors_dirt_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_013_tile)return Tile.outdoors_dirt_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_014_tile)return Tile.outdoors_dirt_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_015_tile)return Tile.outdoors_dirt_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_016_tile)return Tile.outdoors_dirt_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_017_tile)return Tile.outdoors_dirt_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_018_tile)return Tile.outdoors_dirt_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_019_tile)return Tile.outdoors_dirt_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_020_tile)return Tile.outdoors_dirt_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_021_tile)return Tile.outdoors_dirt_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_022_tile)return Tile.outdoors_dirt_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_023_tile)return Tile.outdoors_dirt_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_024_tile)return Tile.outdoors_dirt_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_001_tile)return Tile.outdoors_sand_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_002_tile)return Tile.outdoors_sand_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_003_tile)return Tile.outdoors_sand_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_004_tile)return Tile.outdoors_sand_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_005_tile)return Tile.outdoors_sand_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_006_tile)return Tile.outdoors_sand_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_007_tile)return Tile.outdoors_sand_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_008_tile)return Tile.outdoors_sand_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_009_tile)return Tile.outdoors_sand_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_010_tile)return Tile.outdoors_sand_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_011_tile)return Tile.outdoors_sand_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_012_tile)return Tile.outdoors_sand_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_013_tile)return Tile.outdoors_sand_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_014_tile)return Tile.outdoors_sand_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_015_tile)return Tile.outdoors_sand_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_016_tile)return Tile.outdoors_sand_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_017_tile)return Tile.outdoors_sand_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_018_tile)return Tile.outdoors_sand_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_019_tile)return Tile.outdoors_sand_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_020_tile)return Tile.outdoors_sand_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_021_tile)return Tile.outdoors_sand_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_022_tile)return Tile.outdoors_sand_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_023_tile)return Tile.outdoors_sand_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_024_tile)return Tile.outdoors_sand_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_001_tile)return Tile.outdoors_grassstone_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_002_tile)return Tile.outdoors_grassstone_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_003_tile)return Tile.outdoors_grassstone_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_004_tile)return Tile.outdoors_grassstone_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_005_tile)return Tile.outdoors_grassstone_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_006_tile)return Tile.outdoors_grassstone_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_007_tile)return Tile.outdoors_grassstone_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_008_tile)return Tile.outdoors_grassstone_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_009_tile)return Tile.outdoors_grassstone_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_010_tile)return Tile.outdoors_grassstone_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_011_tile)return Tile.outdoors_grassstone_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_012_tile)return Tile.outdoors_grassstone_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_013_tile)return Tile.outdoors_grassstone_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_014_tile)return Tile.outdoors_grassstone_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_015_tile)return Tile.outdoors_grassstone_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_016_tile)return Tile.outdoors_grassstone_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_017_tile)return Tile.outdoors_grassstone_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_018_tile)return Tile.outdoors_grassstone_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_019_tile)return Tile.outdoors_grassstone_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_020_tile)return Tile.outdoors_grassstone_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_021_tile)return Tile.outdoors_grassstone_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_022_tile)return Tile.outdoors_grassstone_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_023_tile)return Tile.outdoors_grassstone_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_024_tile)return Tile.outdoors_grassstone_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_001_tile)return Tile.outdoors_dirtstone_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_002_tile)return Tile.outdoors_dirtstone_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_003_tile)return Tile.outdoors_dirtstone_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_004_tile)return Tile.outdoors_dirtstone_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_005_tile)return Tile.outdoors_dirtstone_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_006_tile)return Tile.outdoors_dirtstone_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_007_tile)return Tile.outdoors_dirtstone_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_008_tile)return Tile.outdoors_dirtstone_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_009_tile)return Tile.outdoors_dirtstone_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_010_tile)return Tile.outdoors_dirtstone_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_011_tile)return Tile.outdoors_dirtstone_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_012_tile)return Tile.outdoors_dirtstone_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_013_tile)return Tile.outdoors_dirtstone_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_014_tile)return Tile.outdoors_dirtstone_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_015_tile)return Tile.outdoors_dirtstone_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_016_tile)return Tile.outdoors_dirtstone_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_017_tile)return Tile.outdoors_dirtstone_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_018_tile)return Tile.outdoors_dirtstone_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_019_tile)return Tile.outdoors_dirtstone_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_020_tile)return Tile.outdoors_dirtstone_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_021_tile)return Tile.outdoors_dirtstone_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_022_tile)return Tile.outdoors_dirtstone_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_023_tile)return Tile.outdoors_dirtstone_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_024_tile)return Tile.outdoors_dirtstone_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_001_tile)return Tile.outdoors_sandstone_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_002_tile)return Tile.outdoors_sandstone_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_003_tile)return Tile.outdoors_sandstone_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_004_tile)return Tile.outdoors_sandstone_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_005_tile)return Tile.outdoors_sandstone_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_006_tile)return Tile.outdoors_sandstone_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_007_tile)return Tile.outdoors_sandstone_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_008_tile)return Tile.outdoors_sandstone_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_009_tile)return Tile.outdoors_sandstone_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_010_tile)return Tile.outdoors_sandstone_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_011_tile)return Tile.outdoors_sandstone_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_012_tile)return Tile.outdoors_sandstone_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_013_tile)return Tile.outdoors_sandstone_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_014_tile)return Tile.outdoors_sandstone_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_015_tile)return Tile.outdoors_sandstone_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_016_tile)return Tile.outdoors_sandstone_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_017_tile)return Tile.outdoors_sandstone_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_018_tile)return Tile.outdoors_sandstone_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_019_tile)return Tile.outdoors_sandstone_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_020_tile)return Tile.outdoors_sandstone_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_021_tile)return Tile.outdoors_sandstone_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_022_tile)return Tile.outdoors_sandstone_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_023_tile)return Tile.outdoors_sandstone_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_024_tile)return Tile.outdoors_sandstone_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_001_tile)return Tile.outdoors_grasswater_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_002_tile)return Tile.outdoors_grasswater_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_003_tile)return Tile.outdoors_grasswater_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_004_tile)return Tile.outdoors_grasswater_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_005_tile)return Tile.outdoors_grasswater_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_006_tile)return Tile.outdoors_grasswater_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_007_tile)return Tile.outdoors_grasswater_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_008_tile)return Tile.outdoors_grasswater_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_009_tile)return Tile.outdoors_grasswater_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_010_tile)return Tile.outdoors_grasswater_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_011_tile)return Tile.outdoors_grasswater_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_012_tile)return Tile.outdoors_grasswater_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_013_tile)return Tile.outdoors_grasswater_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_014_tile)return Tile.outdoors_grasswater_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_015_tile)return Tile.outdoors_grasswater_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_016_tile)return Tile.outdoors_grasswater_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_017_tile)return Tile.outdoors_grasswater_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_018_tile)return Tile.outdoors_grasswater_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_019_tile)return Tile.outdoors_grasswater_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_020_tile)return Tile.outdoors_grasswater_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_021_tile)return Tile.outdoors_grasswater_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_022_tile)return Tile.outdoors_grasswater_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_023_tile)return Tile.outdoors_grasswater_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_024_tile)return Tile.outdoors_grasswater_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_001_tile)return Tile.outdoors_dirtwater_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_002_tile)return Tile.outdoors_dirtwater_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_003_tile)return Tile.outdoors_dirtwater_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_004_tile)return Tile.outdoors_dirtwater_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_005_tile)return Tile.outdoors_dirtwater_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_006_tile)return Tile.outdoors_dirtwater_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_007_tile)return Tile.outdoors_dirtwater_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_008_tile)return Tile.outdoors_dirtwater_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_009_tile)return Tile.outdoors_dirtwater_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_010_tile)return Tile.outdoors_dirtwater_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_011_tile)return Tile.outdoors_dirtwater_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_012_tile)return Tile.outdoors_dirtwater_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_013_tile)return Tile.outdoors_dirtwater_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_014_tile)return Tile.outdoors_dirtwater_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_015_tile)return Tile.outdoors_dirtwater_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_016_tile)return Tile.outdoors_dirtwater_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_017_tile)return Tile.outdoors_dirtwater_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_018_tile)return Tile.outdoors_dirtwater_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_019_tile)return Tile.outdoors_dirtwater_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_020_tile)return Tile.outdoors_dirtwater_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_021_tile)return Tile.outdoors_dirtwater_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_022_tile)return Tile.outdoors_dirtwater_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_023_tile)return Tile.outdoors_dirtwater_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_024_tile)return Tile.outdoors_dirtwater_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_001_tile)return Tile.outdoors_sandwater_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_002_tile)return Tile.outdoors_sandwater_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_003_tile)return Tile.outdoors_sandwater_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_004_tile)return Tile.outdoors_sandwater_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_005_tile)return Tile.outdoors_sandwater_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile)return Tile.outdoors_sandwater_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_007_tile)return Tile.outdoors_sandwater_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_008_tile)return Tile.outdoors_sandwater_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_009_tile)return Tile.outdoors_sandwater_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_010_tile)return Tile.outdoors_sandwater_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_011_tile)return Tile.outdoors_sandwater_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_012_tile)return Tile.outdoors_sandwater_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_013_tile)return Tile.outdoors_sandwater_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_014_tile)return Tile.outdoors_sandwater_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_015_tile)return Tile.outdoors_sandwater_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_016_tile)return Tile.outdoors_sandwater_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_017_tile)return Tile.outdoors_sandwater_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_018_tile)return Tile.outdoors_sandwater_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_019_tile)return Tile.outdoors_sandwater_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_020_tile)return Tile.outdoors_sandwater_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_021_tile)return Tile.outdoors_sandwater_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_022_tile)return Tile.outdoors_sandwater_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_023_tile)return Tile.outdoors_sandwater_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_024_tile)return Tile.outdoors_sandwater_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_001_tile)return Tile.outdoors_grasscliff_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_002_tile)return Tile.outdoors_grasscliff_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_003_tile)return Tile.outdoors_grasscliff_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_004_tile)return Tile.outdoors_grasscliff_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_005_tile)return Tile.outdoors_grasscliff_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_006_tile)return Tile.outdoors_grasscliff_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_007_tile)return Tile.outdoors_grasscliff_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_008_tile)return Tile.outdoors_grasscliff_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_009_tile)return Tile.outdoors_grasscliff_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_010_tile)return Tile.outdoors_grasscliff_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_011_tile)return Tile.outdoors_grasscliff_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_012_tile)return Tile.outdoors_grasscliff_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_013_tile)return Tile.outdoors_grasscliff_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_014_tile)return Tile.outdoors_grasscliff_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_015_tile)return Tile.outdoors_grasscliff_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_016_tile)return Tile.outdoors_grasscliff_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_017_tile)return Tile.outdoors_grasscliff_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_018_tile)return Tile.outdoors_grasscliff_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_019_tile)return Tile.outdoors_grasscliff_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_020_tile)return Tile.outdoors_grasscliff_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_021_tile)return Tile.outdoors_grasscliff_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_022_tile)return Tile.outdoors_grasscliff_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_023_tile)return Tile.outdoors_grasscliff_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_024_tile)return Tile.outdoors_grasscliff_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_025_tile)return Tile.outdoors_grasscliff_025_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_026_tile)return Tile.outdoors_grasscliff_026_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_027_tile)return Tile.outdoors_grasscliff_027_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_028_tile)return Tile.outdoors_grasscliff_028_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_029_tile)return Tile.outdoors_grasscliff_029_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_030_tile)return Tile.outdoors_grasscliff_030_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_031_tile)return Tile.outdoors_grasscliff_031_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_032_tile)return Tile.outdoors_grasscliff_032_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_033_tile)return Tile.outdoors_grasscliff_033_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_034_tile)return Tile.outdoors_grasscliff_034_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_035_tile)return Tile.outdoors_grasscliff_035_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_036_tile)return Tile.outdoors_grasscliff_036_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_037_tile)return Tile.outdoors_grasscliff_037_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_038_tile)return Tile.outdoors_grasscliff_038_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_039_tile)return Tile.outdoors_grasscliff_039_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_040_tile)return Tile.outdoors_grasscliff_040_tile;			
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_001_tile)return Tile.outdoors_dirtcliff_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_002_tile)return Tile.outdoors_dirtcliff_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_003_tile)return Tile.outdoors_dirtcliff_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_004_tile)return Tile.outdoors_dirtcliff_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_005_tile)return Tile.outdoors_dirtcliff_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_006_tile)return Tile.outdoors_dirtcliff_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_007_tile)return Tile.outdoors_dirtcliff_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_008_tile)return Tile.outdoors_dirtcliff_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_009_tile)return Tile.outdoors_dirtcliff_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_010_tile)return Tile.outdoors_dirtcliff_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_011_tile)return Tile.outdoors_dirtcliff_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_012_tile)return Tile.outdoors_dirtcliff_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_013_tile)return Tile.outdoors_dirtcliff_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_014_tile)return Tile.outdoors_dirtcliff_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_015_tile)return Tile.outdoors_dirtcliff_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_016_tile)return Tile.outdoors_dirtcliff_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_017_tile)return Tile.outdoors_dirtcliff_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_018_tile)return Tile.outdoors_dirtcliff_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_019_tile)return Tile.outdoors_dirtcliff_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_020_tile)return Tile.outdoors_dirtcliff_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_021_tile)return Tile.outdoors_dirtcliff_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_022_tile)return Tile.outdoors_dirtcliff_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_023_tile)return Tile.outdoors_dirtcliff_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_024_tile)return Tile.outdoors_dirtcliff_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_025_tile)return Tile.outdoors_dirtcliff_025_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_026_tile)return Tile.outdoors_dirtcliff_026_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_027_tile)return Tile.outdoors_dirtcliff_027_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_028_tile)return Tile.outdoors_dirtcliff_028_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_029_tile)return Tile.outdoors_dirtcliff_029_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_030_tile)return Tile.outdoors_dirtcliff_030_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_031_tile)return Tile.outdoors_dirtcliff_031_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_032_tile)return Tile.outdoors_dirtcliff_032_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_033_tile)return Tile.outdoors_dirtcliff_033_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_034_tile)return Tile.outdoors_dirtcliff_034_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_035_tile)return Tile.outdoors_dirtcliff_035_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_036_tile)return Tile.outdoors_dirtcliff_036_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_037_tile)return Tile.outdoors_dirtcliff_037_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_038_tile)return Tile.outdoors_dirtcliff_038_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_039_tile)return Tile.outdoors_dirtcliff_039_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_040_tile)return Tile.outdoors_dirtcliff_040_tile;			
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_001_tile)return Tile.outdoors_sandcliff_001_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_002_tile)return Tile.outdoors_sandcliff_002_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_003_tile)return Tile.outdoors_sandcliff_003_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_004_tile)return Tile.outdoors_sandcliff_004_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_005_tile)return Tile.outdoors_sandcliff_005_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_006_tile)return Tile.outdoors_sandcliff_006_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_007_tile)return Tile.outdoors_sandcliff_007_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_008_tile)return Tile.outdoors_sandcliff_008_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_009_tile)return Tile.outdoors_sandcliff_009_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_010_tile)return Tile.outdoors_sandcliff_010_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_011_tile)return Tile.outdoors_sandcliff_011_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_012_tile)return Tile.outdoors_sandcliff_012_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_013_tile)return Tile.outdoors_sandcliff_013_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_014_tile)return Tile.outdoors_sandcliff_014_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_015_tile)return Tile.outdoors_sandcliff_015_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_016_tile)return Tile.outdoors_sandcliff_016_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_017_tile)return Tile.outdoors_sandcliff_017_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_018_tile)return Tile.outdoors_sandcliff_018_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_019_tile)return Tile.outdoors_sandcliff_019_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_020_tile)return Tile.outdoors_sandcliff_020_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_021_tile)return Tile.outdoors_sandcliff_021_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_022_tile)return Tile.outdoors_sandcliff_022_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_023_tile)return Tile.outdoors_sandcliff_023_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_024_tile)return Tile.outdoors_sandcliff_024_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_025_tile)return Tile.outdoors_sandcliff_025_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_026_tile)return Tile.outdoors_sandcliff_026_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_027_tile)return Tile.outdoors_sandcliff_027_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_028_tile)return Tile.outdoors_sandcliff_028_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_029_tile)return Tile.outdoors_sandcliff_029_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_030_tile)return Tile.outdoors_sandcliff_030_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_031_tile)return Tile.outdoors_sandcliff_031_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_032_tile)return Tile.outdoors_sandcliff_032_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_033_tile)return Tile.outdoors_sandcliff_033_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_034_tile)return Tile.outdoors_sandcliff_034_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_035_tile)return Tile.outdoors_sandcliff_035_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_036_tile)return Tile.outdoors_sandcliff_036_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_037_tile)return Tile.outdoors_sandcliff_037_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_038_tile)return Tile.outdoors_sandcliff_038_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_039_tile)return Tile.outdoors_sandcliff_039_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_040_tile)return Tile.outdoors_sandcliff_040_tile;
		
		}else if(Game.nightTime){
			//System.out.println("RENDERING NIGHT TILES!");
			if (x < 0 || y < 0 || x >= width || y >= height)return Tile.outdoors_grasscliff_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_001_tile)return Tile.outdoors_grass_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_002_tile)return Tile.outdoors_grass_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_003_tile)return Tile.outdoors_grass_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_004_tile)return Tile.outdoors_grass_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_005_tile)return Tile.outdoors_grass_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_006_tile)return Tile.outdoors_grass_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_007_tile)return Tile.outdoors_grass_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_008_tile)return Tile.outdoors_grass_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_009_tile)return Tile.outdoors_grass_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_010_tile)return Tile.outdoors_grass_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_011_tile)return Tile.outdoors_grass_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_012_tile)return Tile.outdoors_grass_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_013_tile)return Tile.outdoors_grass_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_014_tile)return Tile.outdoors_grass_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_015_tile)return Tile.outdoors_grass_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_016_tile)return Tile.outdoors_grass_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_017_tile)return Tile.outdoors_grass_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_018_tile)return Tile.outdoors_grass_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_019_tile)return Tile.outdoors_grass_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_020_tile)return Tile.outdoors_grass_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_021_tile)return Tile.outdoors_grass_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_022_tile)return Tile.outdoors_grass_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_023_tile)return Tile.outdoors_grass_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grass_024_tile)return Tile.outdoors_grass_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_001_tile)return Tile.outdoors_dirt_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_002_tile)return Tile.outdoors_dirt_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_003_tile)return Tile.outdoors_dirt_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_004_tile)return Tile.outdoors_dirt_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_005_tile)return Tile.outdoors_dirt_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_006_tile)return Tile.outdoors_dirt_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_007_tile)return Tile.outdoors_dirt_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_008_tile)return Tile.outdoors_dirt_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_009_tile)return Tile.outdoors_dirt_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_010_tile)return Tile.outdoors_dirt_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_011_tile)return Tile.outdoors_dirt_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_012_tile)return Tile.outdoors_dirt_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_013_tile)return Tile.outdoors_dirt_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_014_tile)return Tile.outdoors_dirt_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_015_tile)return Tile.outdoors_dirt_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_016_tile)return Tile.outdoors_dirt_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_017_tile)return Tile.outdoors_dirt_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_018_tile)return Tile.outdoors_dirt_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_019_tile)return Tile.outdoors_dirt_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_020_tile)return Tile.outdoors_dirt_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_021_tile)return Tile.outdoors_dirt_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_022_tile)return Tile.outdoors_dirt_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_023_tile)return Tile.outdoors_dirt_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirt_024_tile)return Tile.outdoors_dirt_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_001_tile)return Tile.outdoors_sand_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_002_tile)return Tile.outdoors_sand_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_003_tile)return Tile.outdoors_sand_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_004_tile)return Tile.outdoors_sand_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_005_tile)return Tile.outdoors_sand_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_006_tile)return Tile.outdoors_sand_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_007_tile)return Tile.outdoors_sand_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_008_tile)return Tile.outdoors_sand_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_009_tile)return Tile.outdoors_sand_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_010_tile)return Tile.outdoors_sand_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_011_tile)return Tile.outdoors_sand_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_012_tile)return Tile.outdoors_sand_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_013_tile)return Tile.outdoors_sand_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_014_tile)return Tile.outdoors_sand_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_015_tile)return Tile.outdoors_sand_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_016_tile)return Tile.outdoors_sand_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_017_tile)return Tile.outdoors_sand_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_018_tile)return Tile.outdoors_sand_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_019_tile)return Tile.outdoors_sand_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_020_tile)return Tile.outdoors_sand_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_021_tile)return Tile.outdoors_sand_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_022_tile)return Tile.outdoors_sand_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_023_tile)return Tile.outdoors_sand_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sand_024_tile)return Tile.outdoors_sand_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_001_tile)return Tile.outdoors_grassstone_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_002_tile)return Tile.outdoors_grassstone_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_003_tile)return Tile.outdoors_grassstone_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_004_tile)return Tile.outdoors_grassstone_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_005_tile)return Tile.outdoors_grassstone_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_006_tile)return Tile.outdoors_grassstone_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_007_tile)return Tile.outdoors_grassstone_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_008_tile)return Tile.outdoors_grassstone_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_009_tile)return Tile.outdoors_grassstone_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_010_tile)return Tile.outdoors_grassstone_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_011_tile)return Tile.outdoors_grassstone_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_012_tile)return Tile.outdoors_grassstone_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_013_tile)return Tile.outdoors_grassstone_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_014_tile)return Tile.outdoors_grassstone_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_015_tile)return Tile.outdoors_grassstone_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_016_tile)return Tile.outdoors_grassstone_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_017_tile)return Tile.outdoors_grassstone_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_018_tile)return Tile.outdoors_grassstone_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_019_tile)return Tile.outdoors_grassstone_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_020_tile)return Tile.outdoors_grassstone_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_021_tile)return Tile.outdoors_grassstone_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_022_tile)return Tile.outdoors_grassstone_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_023_tile)return Tile.outdoors_grassstone_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grassstone_024_tile)return Tile.outdoors_grassstone_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_001_tile)return Tile.outdoors_dirtstone_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_002_tile)return Tile.outdoors_dirtstone_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_003_tile)return Tile.outdoors_dirtstone_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_004_tile)return Tile.outdoors_dirtstone_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_005_tile)return Tile.outdoors_dirtstone_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_006_tile)return Tile.outdoors_dirtstone_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_007_tile)return Tile.outdoors_dirtstone_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_008_tile)return Tile.outdoors_dirtstone_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_009_tile)return Tile.outdoors_dirtstone_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_010_tile)return Tile.outdoors_dirtstone_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_011_tile)return Tile.outdoors_dirtstone_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_012_tile)return Tile.outdoors_dirtstone_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_013_tile)return Tile.outdoors_dirtstone_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_014_tile)return Tile.outdoors_dirtstone_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_015_tile)return Tile.outdoors_dirtstone_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_016_tile)return Tile.outdoors_dirtstone_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_017_tile)return Tile.outdoors_dirtstone_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_018_tile)return Tile.outdoors_dirtstone_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_019_tile)return Tile.outdoors_dirtstone_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_020_tile)return Tile.outdoors_dirtstone_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_021_tile)return Tile.outdoors_dirtstone_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_022_tile)return Tile.outdoors_dirtstone_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_023_tile)return Tile.outdoors_dirtstone_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtstone_024_tile)return Tile.outdoors_dirtstone_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_001_tile)return Tile.outdoors_sandstone_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_002_tile)return Tile.outdoors_sandstone_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_003_tile)return Tile.outdoors_sandstone_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_004_tile)return Tile.outdoors_sandstone_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_005_tile)return Tile.outdoors_sandstone_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_006_tile)return Tile.outdoors_sandstone_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_007_tile)return Tile.outdoors_sandstone_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_008_tile)return Tile.outdoors_sandstone_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_009_tile)return Tile.outdoors_sandstone_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_010_tile)return Tile.outdoors_sandstone_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_011_tile)return Tile.outdoors_sandstone_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_012_tile)return Tile.outdoors_sandstone_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_013_tile)return Tile.outdoors_sandstone_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_014_tile)return Tile.outdoors_sandstone_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_015_tile)return Tile.outdoors_sandstone_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_016_tile)return Tile.outdoors_sandstone_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_017_tile)return Tile.outdoors_sandstone_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_018_tile)return Tile.outdoors_sandstone_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_019_tile)return Tile.outdoors_sandstone_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_020_tile)return Tile.outdoors_sandstone_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_021_tile)return Tile.outdoors_sandstone_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_022_tile)return Tile.outdoors_sandstone_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_023_tile)return Tile.outdoors_sandstone_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandstone_024_tile)return Tile.outdoors_sandstone_024_night_tile;	
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_001_tile)return Tile.outdoors_grasswater_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_002_tile)return Tile.outdoors_grasswater_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_003_tile)return Tile.outdoors_grasswater_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_004_tile)return Tile.outdoors_grasswater_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_005_tile)return Tile.outdoors_grasswater_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_006_tile)return Tile.outdoors_grasswater_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_007_tile)return Tile.outdoors_grasswater_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_008_tile)return Tile.outdoors_grasswater_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_009_tile)return Tile.outdoors_grasswater_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_010_tile)return Tile.outdoors_grasswater_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_011_tile)return Tile.outdoors_grasswater_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_012_tile)return Tile.outdoors_grasswater_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_013_tile)return Tile.outdoors_grasswater_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_014_tile)return Tile.outdoors_grasswater_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_015_tile)return Tile.outdoors_grasswater_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_016_tile)return Tile.outdoors_grasswater_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_017_tile)return Tile.outdoors_grasswater_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_018_tile)return Tile.outdoors_grasswater_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_019_tile)return Tile.outdoors_grasswater_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_020_tile)return Tile.outdoors_grasswater_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_021_tile)return Tile.outdoors_grasswater_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_022_tile)return Tile.outdoors_grasswater_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_023_tile)return Tile.outdoors_grasswater_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasswater_024_tile)return Tile.outdoors_grasswater_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_001_tile)return Tile.outdoors_dirtwater_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_002_tile)return Tile.outdoors_dirtwater_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_003_tile)return Tile.outdoors_dirtwater_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_004_tile)return Tile.outdoors_dirtwater_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_005_tile)return Tile.outdoors_dirtwater_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_006_tile)return Tile.outdoors_dirtwater_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_007_tile)return Tile.outdoors_dirtwater_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_008_tile)return Tile.outdoors_dirtwater_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_009_tile)return Tile.outdoors_dirtwater_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_010_tile)return Tile.outdoors_dirtwater_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_011_tile)return Tile.outdoors_dirtwater_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_012_tile)return Tile.outdoors_dirtwater_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_013_tile)return Tile.outdoors_dirtwater_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_014_tile)return Tile.outdoors_dirtwater_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_015_tile)return Tile.outdoors_dirtwater_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_016_tile)return Tile.outdoors_dirtwater_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_017_tile)return Tile.outdoors_dirtwater_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_018_tile)return Tile.outdoors_dirtwater_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_019_tile)return Tile.outdoors_dirtwater_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_020_tile)return Tile.outdoors_dirtwater_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_021_tile)return Tile.outdoors_dirtwater_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_022_tile)return Tile.outdoors_dirtwater_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_023_tile)return Tile.outdoors_dirtwater_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtwater_024_tile)return Tile.outdoors_dirtwater_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_001_tile)return Tile.outdoors_sandwater_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_002_tile)return Tile.outdoors_sandwater_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_003_tile)return Tile.outdoors_sandwater_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_004_tile)return Tile.outdoors_sandwater_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_005_tile)return Tile.outdoors_sandwater_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_006_tile)return Tile.outdoors_sandwater_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_007_tile)return Tile.outdoors_sandwater_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_008_tile)return Tile.outdoors_sandwater_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_009_tile)return Tile.outdoors_sandwater_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_010_tile)return Tile.outdoors_sandwater_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_011_tile)return Tile.outdoors_sandwater_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_012_tile)return Tile.outdoors_sandwater_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_013_tile)return Tile.outdoors_sandwater_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_014_tile)return Tile.outdoors_sandwater_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_015_tile)return Tile.outdoors_sandwater_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_016_tile)return Tile.outdoors_sandwater_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_017_tile)return Tile.outdoors_sandwater_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_018_tile)return Tile.outdoors_sandwater_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_019_tile)return Tile.outdoors_sandwater_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_020_tile)return Tile.outdoors_sandwater_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_021_tile)return Tile.outdoors_sandwater_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_022_tile)return Tile.outdoors_sandwater_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_023_tile)return Tile.outdoors_sandwater_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandwater_024_tile)return Tile.outdoors_sandwater_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_001_tile)return Tile.outdoors_grasscliff_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_002_tile)return Tile.outdoors_grasscliff_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_003_tile)return Tile.outdoors_grasscliff_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_004_tile)return Tile.outdoors_grasscliff_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_005_tile)return Tile.outdoors_grasscliff_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_006_tile)return Tile.outdoors_grasscliff_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_007_tile)return Tile.outdoors_grasscliff_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_008_tile)return Tile.outdoors_grasscliff_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_009_tile)return Tile.outdoors_grasscliff_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_010_tile)return Tile.outdoors_grasscliff_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_011_tile)return Tile.outdoors_grasscliff_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_012_tile)return Tile.outdoors_grasscliff_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_013_tile)return Tile.outdoors_grasscliff_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_014_tile)return Tile.outdoors_grasscliff_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_015_tile)return Tile.outdoors_grasscliff_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_016_tile)return Tile.outdoors_grasscliff_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_017_tile)return Tile.outdoors_grasscliff_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_018_tile)return Tile.outdoors_grasscliff_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_019_tile)return Tile.outdoors_grasscliff_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_020_tile)return Tile.outdoors_grasscliff_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_021_tile)return Tile.outdoors_grasscliff_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_022_tile)return Tile.outdoors_grasscliff_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_023_tile)return Tile.outdoors_grasscliff_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_024_tile)return Tile.outdoors_grasscliff_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_025_tile)return Tile.outdoors_grasscliff_025_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_026_tile)return Tile.outdoors_grasscliff_026_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_027_tile)return Tile.outdoors_grasscliff_027_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_028_tile)return Tile.outdoors_grasscliff_028_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_029_tile)return Tile.outdoors_grasscliff_029_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_030_tile)return Tile.outdoors_grasscliff_030_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_031_tile)return Tile.outdoors_grasscliff_031_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_032_tile)return Tile.outdoors_grasscliff_032_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_033_tile)return Tile.outdoors_grasscliff_033_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_034_tile)return Tile.outdoors_grasscliff_034_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_035_tile)return Tile.outdoors_grasscliff_035_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_036_tile)return Tile.outdoors_grasscliff_036_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_037_tile)return Tile.outdoors_grasscliff_037_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_038_tile)return Tile.outdoors_grasscliff_038_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_039_tile)return Tile.outdoors_grasscliff_039_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_grasscliff_040_tile)return Tile.outdoors_grasscliff_040_night_tile;			
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_001_tile)return Tile.outdoors_dirtcliff_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_002_tile)return Tile.outdoors_dirtcliff_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_003_tile)return Tile.outdoors_dirtcliff_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_004_tile)return Tile.outdoors_dirtcliff_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_005_tile)return Tile.outdoors_dirtcliff_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_006_tile)return Tile.outdoors_dirtcliff_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_007_tile)return Tile.outdoors_dirtcliff_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_008_tile)return Tile.outdoors_dirtcliff_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_009_tile)return Tile.outdoors_dirtcliff_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_010_tile)return Tile.outdoors_dirtcliff_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_011_tile)return Tile.outdoors_dirtcliff_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_012_tile)return Tile.outdoors_dirtcliff_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_013_tile)return Tile.outdoors_dirtcliff_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_014_tile)return Tile.outdoors_dirtcliff_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_015_tile)return Tile.outdoors_dirtcliff_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_016_tile)return Tile.outdoors_dirtcliff_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_017_tile)return Tile.outdoors_dirtcliff_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_018_tile)return Tile.outdoors_dirtcliff_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_019_tile)return Tile.outdoors_dirtcliff_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_020_tile)return Tile.outdoors_dirtcliff_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_021_tile)return Tile.outdoors_dirtcliff_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_022_tile)return Tile.outdoors_dirtcliff_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_023_tile)return Tile.outdoors_dirtcliff_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_024_tile)return Tile.outdoors_dirtcliff_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_025_tile)return Tile.outdoors_dirtcliff_025_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_026_tile)return Tile.outdoors_dirtcliff_026_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_027_tile)return Tile.outdoors_dirtcliff_027_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_028_tile)return Tile.outdoors_dirtcliff_028_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_029_tile)return Tile.outdoors_dirtcliff_029_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_030_tile)return Tile.outdoors_dirtcliff_030_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_031_tile)return Tile.outdoors_dirtcliff_031_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_032_tile)return Tile.outdoors_dirtcliff_032_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_033_tile)return Tile.outdoors_dirtcliff_033_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_034_tile)return Tile.outdoors_dirtcliff_034_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_035_tile)return Tile.outdoors_dirtcliff_035_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_036_tile)return Tile.outdoors_dirtcliff_036_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_037_tile)return Tile.outdoors_dirtcliff_037_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_038_tile)return Tile.outdoors_dirtcliff_038_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_039_tile)return Tile.outdoors_dirtcliff_039_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_dirtcliff_040_tile)return Tile.outdoors_dirtcliff_040_night_tile;			
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_001_tile)return Tile.outdoors_sandcliff_001_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_002_tile)return Tile.outdoors_sandcliff_002_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_003_tile)return Tile.outdoors_sandcliff_003_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_004_tile)return Tile.outdoors_sandcliff_004_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_005_tile)return Tile.outdoors_sandcliff_005_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_006_tile)return Tile.outdoors_sandcliff_006_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_007_tile)return Tile.outdoors_sandcliff_007_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_008_tile)return Tile.outdoors_sandcliff_008_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_009_tile)return Tile.outdoors_sandcliff_009_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_010_tile)return Tile.outdoors_sandcliff_010_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_011_tile)return Tile.outdoors_sandcliff_011_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_012_tile)return Tile.outdoors_sandcliff_012_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_013_tile)return Tile.outdoors_sandcliff_013_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_014_tile)return Tile.outdoors_sandcliff_014_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_015_tile)return Tile.outdoors_sandcliff_015_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_016_tile)return Tile.outdoors_sandcliff_016_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_017_tile)return Tile.outdoors_sandcliff_017_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_018_tile)return Tile.outdoors_sandcliff_018_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_019_tile)return Tile.outdoors_sandcliff_019_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_020_tile)return Tile.outdoors_sandcliff_020_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_021_tile)return Tile.outdoors_sandcliff_021_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_022_tile)return Tile.outdoors_sandcliff_022_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_023_tile)return Tile.outdoors_sandcliff_023_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_024_tile)return Tile.outdoors_sandcliff_024_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_025_tile)return Tile.outdoors_sandcliff_025_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_026_tile)return Tile.outdoors_sandcliff_026_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_027_tile)return Tile.outdoors_sandcliff_027_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_028_tile)return Tile.outdoors_sandcliff_028_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_029_tile)return Tile.outdoors_sandcliff_029_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_030_tile)return Tile.outdoors_sandcliff_030_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_031_tile)return Tile.outdoors_sandcliff_031_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_032_tile)return Tile.outdoors_sandcliff_032_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_033_tile)return Tile.outdoors_sandcliff_033_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_034_tile)return Tile.outdoors_sandcliff_034_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_035_tile)return Tile.outdoors_sandcliff_035_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_036_tile)return Tile.outdoors_sandcliff_036_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_037_tile)return Tile.outdoors_sandcliff_037_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_038_tile)return Tile.outdoors_sandcliff_038_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_039_tile)return Tile.outdoors_sandcliff_039_night_tile;
			if (tiles[x + y * width] == Tile.col_outdoors_sandcliff_040_tile)return Tile.outdoors_sandcliff_040_night_tile;		
		}
		
		return Tile.outdoors_sandwater_016_tile;
		//return Tile.outdoors_grasscliff_018_tile;
	}
}
