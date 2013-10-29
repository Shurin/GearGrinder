package com.GearGrinder;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.GearGrinder.entity.mob.Mob;
import com.GearGrinder.entity.mob.Player;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;
import com.GearGrinder.level.Level;
import com.GearGrinder.level.TileCoordinate;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	Dimension maxRes = Toolkit.getDefaultToolkit().getScreenSize();
	private static int width = 300;
	private static int height = 168;
	private static int scale = 3;

	// experimental
	public int healthPosX = 50, healthPosY = 678;
	public int staminaPosX = 95, staminaPosY = 725;
	private Mob mob;

	public boolean auth = false;
	private Thread gamethread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(21, 18); // SPAWN
																	// LOCATION!!
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);

		addKeyListener(key);

		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public synchronized void start() {
		running = true;
		gamethread = new Thread(this, "Display");
		gamethread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			gamethread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
	    long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		// game loop
		while (running == true) {
			long now = System.nanoTime();
			delta = delta + (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();// our gfx handler
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer = timer + 1000; // adds a second to above criteria
				frame.setTitle("GearGrinder    |    FPS: " + frames);
				updates = 0;
				frames = 0;
				
				//MAGIC HEALING LOOP
				double magichealrate = Player.maxmagic * 0.1;
				if(Player.currentmagic < Player.maxmagic && (Player.currentmagic + magichealrate) <= Player.maxmagic){
					Player.currentmagic = (int)magichealrate + Player.currentmagic;
					Player.magicpercent = Player.currentmagic / Player.maxmagic * 100;
				}else {
					Player.currentmagic = Player.maxmagic;
					Player.magicpercent = Player.currentmagic / Player.maxmagic * 100;
				}
				
				//MAGIC HEALING LOOP
				double healthhealrate = Player.maxhealth * 0.02;
				if(Player.currenthealth < Player.maxhealth && (Player.currenthealth + healthhealrate) <= Player.maxhealth){
					Player.currenthealth = (int)healthhealrate + Player.currenthealth;
					Player.healthpercent = Player.currenthealth / Player.maxhealth * 100;
				}else {
					Player.currenthealth = Player.maxhealth;
					Player.healthpercent = Player.currenthealth / Player.maxhealth * 100;
				}
			}
			 //JOptionPane.showMessageDialog(null, "menu initialized!");
		}
	}

	public void update() {
		key.update();
		level.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // 3 = triple buffer
			return;
		}
		screen.clear();
		int xScroll = player.getX() - screen.width / 2; // puts player in middle
														// of x-axis
		int yScroll = player.getY() - screen.height / 2; // puts player in
															// middle of y-axis
		level.render(xScroll, yScroll, screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Veranda", 0, 16));
		g.drawString("Button: " + Mouse.getB(), width * 3 - 70, height * 3 - 5);
		g.drawString("Tile X: " + (player.getX() / 16) + ", Y: " + ((player.getY() / 16) + 1), 85, 16);
		g.drawString("X: " + player.getX() + ", Y: " + player.getY(), 225, 16);
		// experimental
		// health bar
		g.setColor(Color.DARK_GRAY);
		g.fillRect(11, height * scale - 30, 209, 30);
		g.setColor(Color.RED);
		g.fillRect(16, height * scale - 25, ((int)Player.healthpercent * 2), 20);
		g.setColor(Color.lightGray);
		g.drawString("Health : " + player.currenthealth + " / " + player.maxhealth, 35, height * scale - 10);
		// Magic bar
		g.setColor(Color.DARK_GRAY);
		g.fillRect(11, height * scale - 62, 209, 30);
		g.setColor(Color.BLUE);
		g.fillRect(16, height * scale - 57, ((int)Player.magicpercent * 2), 20);
		g.setColor(Color.lightGray);
		g.drawString("Magic : " + (int)player.currentmagic + " / " + (int)player.maxmagic, 35, height * scale - 42);
		// Stamina bar
		g.setColor(Color.DARK_GRAY);
		g.fillRect(345, height * scale - 30, 110, 30);
		g.setColor(Color.BLACK);
		g.fillRect(350, height * scale - 25, (int)Player.staminapercent, 20);
		g.setColor(Color.lightGray);
		g.drawString("Stamina : " + (int)Player.staminapercent, 351, height * scale - 10);
		
		g.setColor(Color.WHITE);
		g.drawString("current " + Player.currentmagic + " max " + Player.maxmagic + "percent " + Player.magicpercent, 150, 150);
		
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false); // must be first thing applied to frame
		game.frame.setTitle("rain");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); // centers the frame
		game.frame.setVisible(true);

		game.start();
	}

}
