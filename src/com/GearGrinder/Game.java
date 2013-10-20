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

import com.GearGrinder.entity.mob.Player;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;
import com.GearGrinder.level.Level;
import com.GearGrinder.level.TileCoordinate;


public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	Dimension maxRes = Toolkit.getDefaultToolkit().getScreenSize();
	//int maxW = maxRes.width;
	//int maxY = maxRes.height;
	
	//public int width = maxW;
	//public int height = maxY;
	private static int width = 300;
	private static int height = 168;
	private static int scale = 3;

	public boolean auth = false;
	private Thread gamethread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);		

		screen = new Screen(width, height);
		
		frame = new JFrame();
		//frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		//frame.setUndecorated(true);
		frame.setVisible(true);
		//frame.setAlwaysOnTop(true);
		
		key = new Keyboard();		
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(68, 48);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		player.init(level);
		addKeyListener(key);
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int getWindowWidth(){
		return width * scale;
	}
	
	public static int getWindowHeight(){
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
			}			
		/*	if(key.menu == true){
				key.menu = false;
				JOptionPane.showMessageDialog(null, "menu initialized!");
				
			}*/			
		}
	}

	public void update() {
		key.update();
		player.update();
		level.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) { // if the buffer isn't created yet, this creates it
			createBufferStrategy(3); // 3 = triple buffer
			return;
		}
		screen.clear();
		int xScroll = player.x - screen.width /2; // puts player in middle of x-axis
		int yScroll = player.y - screen.height /2; // puts player in middle of y-axis
		level.render(xScroll, yScroll, screen);
		player.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i]; // assigns the value of pixels defined
											// here to the pixels array in
											// Screen
		}

	    Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE); //x,y display
		g.setFont(new Font("Veranda", 0, 16)); //x,y display
		g.drawString("Button: " + Mouse.getB(),  width * 3 - 70, height * 3 - 5);
		g.drawString("Tile X: " + (player.x / 16) + ", Y: " + ((player.y / 16) + 1), 85, 16); //x,y display
		g.drawString("X: " + player.x + ", Y: " + player.y, 225, 16); //x,y display		
		//g.drawString(""+level, 25, (height * 3 -4));
		// for mouse angle display
		/*int sx = width / 2;
		int sy = height / 2;
		double dx = Mouse.getX() - sx;
		double dy = Mouse.getY() - sy;
		double dir = Math.atan2(dy, dx);
		double dir2 = dir;
		dir2 *= 180 / Math.PI;
		g.drawString("mouse angle = Rad: " + dir + "   Deg: " + dir2, 5, height * 3 - 4);*/
		
		g.dispose();
		bs.show(); // shows the next available buffer
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
