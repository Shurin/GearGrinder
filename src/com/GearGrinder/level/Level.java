package com.GearGrinder.level;

import java.util.ArrayList;
import java.util.List;

import com.GearGrinder.entity.Entity;
import com.GearGrinder.entity.Projectile.Projectile;
import com.GearGrinder.entity.mob.Player;
import com.GearGrinder.entity.particle.Particle;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	protected int tile_size;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile>  projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();
	
	public static Level spawn = new SpawnLevel("/levels/spawn.png");

	
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
		for(int y = 0; y < 64; y++){
			for(int x = 0; x < 64; x++){
				getTile(x, y);
			}
		}
		tile_size = 16;
	}

	protected void loadLevel(String path) {

	}

	public void update() { // updates the levels
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).update();
		}
		for(int i = 0; i < projectiles.size(); i++){
			projectiles.get(i).update();
		}
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).update();
		}
		for(int i = 0; i < players.size(); i++){
			players.get(i).update();
		}
		remove();
	}
	
	private void remove(){
		for(int i = 0; i < entities.size(); i++){
			if(entities.get(i).isRemoved()) entities.remove(i);
		}
		for(int i = 0; i < projectiles.size(); i++){
			if(projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for(int i = 0; i < particles.size(); i++){
			if(particles.get(i).isRemoved()) particles.remove(i);
		}	
		for(int i = 0; i < players.size(); i++){
			if(players.get(i).isRemoved()) players.remove(i);
		}
	}
	
	public List<Projectile> getProjectiles(){
		return projectiles;
	}

	private void time() { // manages the time in game ( day / night)

	}
	public boolean projectileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++){
			int xt = (x - c % 2 * size + xOffset) /16;
			int yt = (y - c / 2 * size + (yOffset * 3)) /16;
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
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).render(screen);
		}
		for(int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).render(screen);
		}
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).render(screen);
		}
		for(int i = 0; i < players.size(); i++){
			players.get(i).render(screen);
		}
	}
	
	public void add(Entity e){
		e.init(this);
		if(e instanceof Particle){
			particles.add((Particle) e);
		}else if( e instanceof Projectile){
			projectiles.add((Projectile) e);
		}else if( e instanceof Player){
			players.add((Player) e);
		}else{
			entities.add(e);
		}
	}
	public void addProjectile(Projectile p){
		p.init(this);
		projectiles.add(p);
	}

	public List<Player> getPlayers(){
		return players;
	}
	
	public Player getPlayerAt(int index){
		return players.get(index);
	}
	
	public Player getClientPlayer(){
		return players.get(0);
	}
	
	public List<Entity> getEntities(Entity e, int radius){
		List<Entity> result = new ArrayList<Entity>();
		int ex = e.getX();
		int ey = e.getY();
		for(int i = 0; i < entities.size(); i++){
			Entity entity = entities.get(i);
			int x = entity.getX();
			int y = entity.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));			
			if(distance <= radius) result.add(entity);
		}
		return result;
	}
	
	public List<Player> getPlayers(Entity e, int radius){
		List<Player> result = new ArrayList<Player>();
		int ex = e.getX();
		int ey = e.getY();
		for(int i = 0; i < players.size(); i++){
			Player player = players.get(i);
			int x = player.getX();
			int y = player.getY();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius) result.add(player);
			}
		return result;
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.spawn_grass_tile;
		if (tiles[x + y * width] == Tile.col_spawn_grass) return Tile.spawn_grass_tile;
		if (tiles[x + y * width] == Tile.col_spawn_hedge) return Tile.spawn_hedge_tile;
		if (tiles[x + y * width] == Tile.col_spawn_water) return Tile.spawn_water_tile;
		if (tiles[x + y * width] == Tile.col_spawn_wall1) return Tile.spawn_wall1_tile;
		if (tiles[x + y * width] == Tile.col_spawn_wall2) return Tile.spawn_wall2_tile;
		if (tiles[x + y * width] == Tile.col_spawn_floor) return Tile.spawn_floor_tile;
		
		return Tile.spawn_grass_tile;
	}
}
