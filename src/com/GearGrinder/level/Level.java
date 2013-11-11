package com.GearGrinder.level;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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

	public static Level world = new SpawnLevel("/levels/world.png");
	public static Level dungeon1 = new SpawnLevel("/levels/dungeon1.png");

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
					String name = projectiles.get(j).getName();
					System.out.println("pro damage: " + projectiles.get(j).getDamage());
					if(((prox >= mobxl) && (proy >= mobyb )) && ((prox <= mobxr) && (proy <= mobyt))){
						entities.get(i).remove();
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
		if (x < 0 || y < 0 || x >= width || y >= height)
			if(Player.invshow){
				return Tile.spawn_grass_tile_night;
			}else{
				return Tile.spawn_grass_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_grass)
			if(Player.invshow){
				return Tile.spawn_grass_tile_night;
			}else{
				return Tile.spawn_grass_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_hedge)
			if(Player.invshow){
				return Tile.spawn_hedge_tile_night;
			}else{
				return Tile.spawn_hedge_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_water)
			if(Player.invshow){
				return Tile.spawn_water_tile_night;
			}else{
				return Tile.spawn_water_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall1)
			if(Player.invshow){
				return Tile.spawn_wall1_tile_night;
			}else{
				return Tile.spawn_wall1_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall2)
			if(Player.invshow){
				return Tile.spawn_wall2_tile_night;
			}else{
				return Tile.spawn_wall2_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall3)
			if(Player.invshow){
				return Tile.spawn_wall3_tile_night;
			}else{
				return Tile.spawn_wall3_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall4)
			if(Player.invshow){
				return Tile.spawn_wall4_tile_night;
			}else{
				return Tile.spawn_wall4_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall5)
			if(Player.invshow){
				return Tile.spawn_wall5_tile_night;
			}else{
				return Tile.spawn_wall5_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall6)
			if(Player.invshow){
				return Tile.spawn_wall6_tile_night;
			}else{
				return Tile.spawn_wall6_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall7)
			if(Player.invshow){
				return Tile.spawn_wall7_tile_night;
			}else{
				return Tile.spawn_wall7_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall8)
			if(Player.invshow){
				return Tile.spawn_wall8_tile_night;
			}else{
				return Tile.spawn_wall8_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall9)
			if(Player.invshow){
				return Tile.spawn_wall9_tile_night;
			}else{
				return Tile.spawn_wall9_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall10)
			if(Player.invshow){
				return Tile.spawn_wall10_tile_night;
			}else{
				return Tile.spawn_wall10_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall11)
			if(Player.invshow){
				return Tile.spawn_wall11_tile_night;
			}else{
				return Tile.spawn_wall11_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_wall12)
			if(Player.invshow){
				return Tile.spawn_wall12_tile_night;
			}else{
				return Tile.spawn_wall12_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_stairmiddle)
			if(Player.invshow){
				return Tile.spawn_stairmiddle_tile_night;
			}else{
				return Tile.spawn_stairmiddle_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_stairleft)
			if(Player.invshow){
				return Tile.spawn_stairleft_tile_night;
			}else{
				return Tile.spawn_stairleft_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_stairright)
			if(Player.invshow){
				return Tile.spawn_stairright_tile_night;
			}else{
				return Tile.spawn_stairright_tile;
			}
		if (tiles[x + y * width] == Tile.col_spawn_floor)
			if(Player.invshow){
				return Tile.spawn_floor_tile_night;
			}else{
				return Tile.spawn_floor_tile;
			}
		if (tiles[x + y * width] == Tile.col_portal_up)
			if(Player.invshow){
				return Tile.global_portal_up_night;
			}else{
				return Tile.global_portal_up;
			}
		if (tiles[x + y * width] == Tile.col_portal_down)
			if(Player.invshow){
				return Tile.global_portal_down_night;
			}else{
				return Tile.global_portal_down;
			}
		

		return Tile.spawn_grass_tile;
	}
}
