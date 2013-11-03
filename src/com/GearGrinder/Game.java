package com.GearGrinder;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.GearGrinder.DataIO.Load;
import com.GearGrinder.DataIO.Save;
import com.GearGrinder.entity.mob.Player;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;
import com.GearGrinder.level.Level;
import com.GearGrinder.level.TileCoordinate;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private static int width = (int) 1280/2;
	private static int height = (int) 768/2;
	private static int scale = 2;
	public static BufferStrategy bs;
	public static Graphics g;

	public int healthPosX = 50, healthPosY = 678;
	public int staminaPosX = 95, staminaPosY = 725;
	public static int mobsonscreen = 3;
	public static int PlayerSpawnX = 21;
	public static int PlayerSpawnY = 18;
	
	private boolean uicached = false;
	
	public int fpsDisplay = 0;
	public static Thread gamethread;
	public static JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	public static boolean running = false;
	
	//ui elements
	private BufferedImage slotpic = null;
	private BufferedImage hud = null;
	private BufferedImage logo = null;
	private BufferedImage bag = null;
	private BufferedImage bag2 = null;
	private BufferedImage help = null;
	private BufferedImage help2 = null;
	private BufferedImage helppage = null;
	private BufferedImage hpmpframe = null;
	
	private Mouse mouse = new Mouse();

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		
		Load.Load();
		
		level = Level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(21, 18); // SPAWN
																	// LOCATION!!
		player = new Player(PlayerSpawnX, PlayerSpawnY, key);
		level.add(player);

		addKeyListener(key);

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

	public static synchronized void stop() {
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
				fpsDisplay = frames;
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
				
				//STAMINA REGEN LOOP
				double staminaregenrate = Player.maxstamina * 0.02;
				if(Player.currentstamina < Player.maxstamina && (Player.currentstamina + staminaregenrate) <= Player.maxstamina){
					Player.currentstamina = (int)staminaregenrate + Player.currentstamina;
					Player.staminapercent = Player.currentstamina / Player.maxstamina * 100;
				}else {
					Player.currentstamina = Player.maxstamina;
					Player.staminapercent = Player.currentstamina / Player.maxstamina * 100;
				}
			}
			PlayerSpawnX = player.getX();
			PlayerSpawnY = player.getY();
			Save.Save();
			 //JOptionPane.showMessageDialog(null, "menu initialized!");
		}
	}

	public void uicache(){
		try {
			slotpic = ImageIO.read(new File("slot.png"));
			hud = ImageIO.read(new File("hud.png"));
			logo = ImageIO.read(new File("logo.png"));
			bag = ImageIO.read(new File("bag.png"));
			bag2 = ImageIO.read(new File("bag2.png"));
			help = ImageIO.read(new File("help.png"));
			help2 = ImageIO.read(new File("help2.png"));
			helppage = ImageIO.read(new File("helppage.png"));
			hpmpframe = ImageIO.read(new File("hpmpframe.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		uicached = true;
	}
	
	public void update() {
		key.update();
		level.update();
	}
	
	public void render() {
		
		if(uicached == false) uicache();
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
		
		g.setColor(Color.BLACK);
		g.fillRect(width - 370, height * scale - 88, 220, 70);
		
		// health bar
		g.setColor(Color.RED);
		g.fillRect(width  - 366, height * scale - 53, ((int)Player.healthpercent * 2), 30);
		g.setColor(Color.lightGray);
		g.drawString("Health : " + (int)player.currenthealth + " / " + (int)player.maxhealth, width - 358, height * scale - 35);
		
		// Magic bar
		g.setColor(Color.BLUE);
		g.fillRect(width - 366, height * scale - 88, ((int)Player.magicpercent * 2), 30);
		g.setColor(Color.lightGray);
		g.drawString("Magic : " + (int)player.currentmagic + " / " + (int)player.maxmagic, width - 358, height * scale - 67);

		// hud elements
		
		//INVENTORY
		if(Player.invshow == true){
			g.drawImage(bag2, width + 324, height * 2 - 76, null);			
		}else {
			g.drawImage(bag, width + 324, height * 2 - 76, null);
		}
		
		// HUD
		g.drawImage(hud, width / 2 - 60, height * 2 - 102, null);
		//HUD top elements
		//g.drawImage(hpmpframe, width / 2 - 60, height * 2 - 102, null);
		
		
		//HELP PAGE
		if(Player.helpshow == true){
			g.drawImage(help2, width - 7, height * 2 - 100, null);
		}else {
			g.drawImage(help, width - 7, height * 2 - 100, null);
		}
		
		// Stamina bar
		g.setColor(Color.DARK_GRAY);
		g.fillRect(width - 35, height * scale - 50, 100, 20);
		g.setColor(Color.BLACK);
		g.fillRect(width - 35, height * scale - 50, (int)Player.staminapercent, 20);
		g.setColor(Color.lightGray);
		g.drawString("Stamina : " + (int)Player.staminapercent, width - 35, height * scale - 35);
		
		g.setColor(Color.WHITE);
		g.drawString("Mobs on screen: " + mobsonscreen, 370, 16);
		g.drawString("FPS: " + fpsDisplay, width *2 - 100, 16);
		
		
		// CHARACTER PANEL
		if(Player.invshow == true){
			
			g.drawImage(slotpic, width * 2 - 499, height/18+5, null);
			g.drawImage(slotpic, width * 2 - 499, height/18+75, null);
			g.drawImage(slotpic, width * 2 - 499, height/18+145, null);
			g.drawImage(slotpic, width * 2 - 499, height/18+215, null);
			g.drawImage(slotpic, width * 2 - 499, height/18+285, null);
			g.drawImage(slotpic, width * 2 - 499, height/18+355, null);
			g.drawImage(slotpic, width * 2 - 499, height/18+425, null);
			g.drawImage(slotpic, width * 2 - 499, height/18+495, null);
			g.drawImage(slotpic, width * 2 - 499, height/18+565, null);
		}
		
		//HELP PANEL
		if(Player.helpshow == true){
			g.drawImage(helppage, 16, 35, null);
			g.setColor(Color.GREEN);
			g.drawString("- Shoot1 = left mouse", 40, 75);
			g.drawString("- Shoot2 = right mouse", 40, 100);
			g.drawString("- Inventory = I", 40, 125);
			g.drawString("- This Page = H", 40, 150);
			g.drawString("- Close game = Esc", 40, 175);
			g.setColor(Color.RED);
			g.drawString("- Finish adding UI elements", 40, 200);
			g.drawString("- Finish adding UI mouse interaction", 40, 225);
			g.drawString("- Finish adding basic player stats", 40, 250);
			g.drawString("- Finish adding Save/Load game", 40, 275);
			g.drawString("- Start adding items", 40, 300);
			g.drawString("- Start adding a chatbox", 40, 325);
			g.drawString("- Start adding Mob collision", 40, 350);
			g.drawString("- Start adding Database I/O", 40, 375);
			g.drawString("- Start adding Networking", 40, 400);
			g.setColor(Color.WHITE);
			g.drawString("I'll add more to this as needed ...", 40, 700);
		}
		
		//logo
		g.drawImage(logo, width * 2 - 235, height * 2 - 105, null);
		
		
		g.dispose();
		bs.show();
	}	

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false); // must be first thing applied to frame
		game.frame.setTitle("GearGrinder_BETA");
		//Game.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		Game.frame.setUndecorated(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); // centers the frame
		game.frame.setVisible(true);

		game.start();
	}
}
