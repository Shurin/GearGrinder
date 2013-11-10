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
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Date;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import com.GearGrinder.Networking.InitialStat;
import com.GearGrinder.Networking.SaveStat;
import com.GearGrinder.Networking.TimeLookup;
import com.GearGrinder.entity.mob.Player;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;
import com.GearGrinder.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double widthmax = screenSize.getWidth();
	static double heightmax = screenSize.getHeight();

	private static int width = (int) widthmax;
	private static int height = (int) heightmax;
	private static int scale = 1;
	public static BufferStrategy bs;
	public static Graphics g;
	private Font boldfont = new Font("veranda", Font.BOLD, 18);

	public int healthPosX = 50, healthPosY = 678;
	public int staminaPosX = 95, staminaPosY = 725;
	public static int mobsonscreen = 3;
	public static String Playername = null;
	public static int PlayerSpawnX = 21;
	public static int PlayerSpawnY = 18;
	public static int currentx = 0;
	public static int currenty = 0;
	public static String currentzone = null;
	
	private boolean uicached = false;
	
	public int fpsDisplay = 0;
	public static int accounttableheight = 0;
	public static Thread gamethread;
	public static JFrame frame;
	private static Keyboard key;
	public static Level level;
	private static Player player;
	public static boolean running = false;
	public static boolean logcheck = false;
	
	//ui elements
	private BufferedImage inventory = null;
	private BufferedImage hud = null;
	private BufferedImage logo = null;
	private BufferedImage bag = null;
	private BufferedImage bag2 = null;
	private BufferedImage help = null;
	private BufferedImage help2 = null;
	private BufferedImage helppage = null;
	private BufferedImage hpmpframe = null;
	private BufferedImage staminaframe = null;
	private BufferedImage charpanel = null;
	private int savetick = 0;
	
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
		
		if(InitialStat.DB_Zone.equals("world")){
			level = level.world;
			currentzone = "world";
		}else if(InitialStat.DB_Zone.equals("dungeon1")){
			level = level.dungeon1;
			currentzone = "dungeon1";
		}

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
				try {
					//g.drawString("TIME INIT...", width / 3, height / 2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//g.drawString("TIME INIT FAILED!", width / 4, height / 2);
					e.printStackTrace();
				}
				frame.setTitle("GearGrinder    |    FPS: " + frames);
				savetick = savetick +1;
				if(savetick == 60){
					System.out.println("Saving Game ...");
					SaveStat.SaveStat();
					savetick = 0;
				}
				fpsDisplay = frames;
				updates = 0;
				frames = 0;				
				
				//MAGIC HEALING LOOP
				double magichealrate = InitialStat.maxmagic * 0.1;
				if(InitialStat.currentmagic < InitialStat.maxmagic && (InitialStat.currentmagic + magichealrate) <= InitialStat.maxmagic){
					InitialStat.currentmagic = (int)magichealrate + InitialStat.currentmagic;
					InitialStat.magicpercent = InitialStat.currentmagic / InitialStat.maxmagic * 100;
				}else {
					InitialStat.currentmagic = InitialStat.maxmagic;
					InitialStat.magicpercent = InitialStat.currentmagic / InitialStat.maxmagic * 100;
				}
				
				//MAGIC HEALING LOOP
				double healthhealrate = InitialStat.maxhealth * 0.02;
				if(InitialStat.currenthealth < InitialStat.maxhealth && (InitialStat.currenthealth + healthhealrate) <= InitialStat.maxhealth){
					InitialStat.currenthealth = (int)healthhealrate + InitialStat.currenthealth;
					InitialStat.healthpercent = InitialStat.currenthealth / InitialStat.maxhealth * 100;
				}else {
					InitialStat.currenthealth = InitialStat.maxhealth;
					InitialStat.healthpercent = InitialStat.currenthealth / InitialStat.maxhealth * 100;
				}
				
				//STAMINA REGEN LOOP
				double staminaregenrate = InitialStat.maxstamina * 0.02;
				if(InitialStat.currentstamina < InitialStat.maxstamina && (InitialStat.currentstamina + staminaregenrate) <= InitialStat.maxstamina){
					InitialStat.currentstamina = (int)staminaregenrate + InitialStat.currentstamina;
					InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
				}else {
					InitialStat.currentstamina = InitialStat.maxstamina;
					InitialStat.staminapercent = InitialStat.currentstamina / InitialStat.maxstamina * 100;
				}
			}
			PlayerSpawnX = player.getX();
			PlayerSpawnY = player.getY();
			
			 //JOptionPane.showMessageDialog(null, "menu initialized!");
		}
	}

	public void uicache(){
		try {
			String testpic = "inventory.png";
			inventory = ImageIO.read(new File(testpic));
			hud = ImageIO.read(new File("hud.png"));
			logo = ImageIO.read(new File("logo.png"));
			bag = ImageIO.read(new File("bag.png"));
			bag2 = ImageIO.read(new File("bag2.png"));
			help = ImageIO.read(new File("help.png"));
			help2 = ImageIO.read(new File("help2.png"));
			helppage = ImageIO.read(new File("helppage.png"));
			hpmpframe = ImageIO.read(new File("hpmpframe.png"));
			staminaframe = ImageIO.read(new File("staminaframe.png"));
			charpanel = ImageIO.read(new File("charpanel.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		uicached = true;
	}
	
	public static void update() {
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
		g.drawString("Button: " + Mouse.getB(), width - 70, height - 5);
		g.drawString("Tile X: " + (player.getX() / 16) + ", Y: " + ((player.getY() / 16) + 1), 85, 16);
		g.drawString("X: " + player.getX() + ", Y: " + player.getY(), 225, 16);
		currentx = player.getX();
		currenty = player.getY();
		
		g.setColor(Color.BLACK);
		g.fillRect(width / 2 - 370, height - 88, 220, 70);
		
		// health bar
		g.setColor(Color.RED);
		g.fillRect(width / 2 - 361, height - 53, ((int)InitialStat.healthpercent * 2), 30);
		g.setColor(Color.lightGray);
		
		// Magic bar
		g.setColor(Color.BLUE);
		g.fillRect(width / 2 - 361, height - 88, ((int)InitialStat.magicpercent * 2), 30);
		g.setColor(Color.lightGray);

		// hud elements
		
		//INVENTORY
		if(Player.invshow == true){
			g.drawImage(bag2, width / 2 + 322, height - 78, null);
			g.drawImage(inventory, width - 499, height / 2 - 20, null);
		}else {
			g.drawImage(bag, width / 2 + 322, height - 78, null);
		}
		
		// HUD
		g.drawImage(hud, width / 2 - 388, height - 112, null);
		//HUD top elements
		g.drawImage(hpmpframe, width / 2 - 373, height - 97, null);
		
		
		//HELP button
		if(Player.helpshow == true){
			g.drawImage(help2, width / 2 - 9, height - 99, null);
		}else {
			g.drawImage(help, width / 2 - 9, height - 99, null);
		}
		
		//HELP PANEL
		if(Player.helpshow == true){
			g.drawImage(helppage, width / 2 - 160, 35, null);
			g.setColor(Color.GREEN);
			g.drawString("- Shoot1 = left mouse", width / 2 - 60, 75);
			g.drawString("- Shoot2 = right mouse", width / 2 - 60, 100);
			g.drawString("- Inventory = I", width / 2 - 60, 125);
			g.drawString("- This Page = H", width / 2 - 60, 150);
			g.drawString("- Close game = Esc", width / 2 - 60, 175);
			g.setColor(Color.RED);
			g.drawString("- Finish adding UI elements", width / 2 - 60, 200);
			g.drawString("- Finish adding UI mouse interaction", width / 2 - 60, 225);
			g.drawString("- Finish adding basic player stats", width / 2 - 60, 250);
			g.drawString("- Finish adding Save/Load game", width / 2 - 60, 275);
			g.drawString("- Start adding items", width / 2 - 60, 300);
			g.drawString("- Start adding a chatbox", width / 2 - 60, 325);
			g.drawString("- Start adding Mob collision", width / 2 - 60, 350);
			g.drawString("- Start adding Database I/O", width / 2 - 60, 375);
			g.drawString("- Start adding Networking", width / 2 - 60, 400);
			g.setColor(Color.WHITE);
			g.drawString("I'll add more to this as needed ...", width / 2 - 60, 700);
		}

		//CHARACTER PANEL
		if(Player.charshow == true){
			g.drawImage(charpanel, 30, 265, null);		
			g.setColor(Color.GREEN);
			g.drawString("" + InitialStat.health, 135,  600);
			g.drawString("" + InitialStat.magic, 420,  600);
			g.drawString("" + InitialStat.strength, 135, 650);
			g.drawString("" + InitialStat.defense, 135, 675);
			g.drawString("" + InitialStat.vitality, 135, 700);
			g.drawString("" + InitialStat.stamina, 135, 725);
			//g.setFont(boldfont);
			g.drawString("" + InitialStat.agility, 420, 650);
			g.drawString("" + InitialStat.intelligence, 420, 675);
			g.drawString("" + InitialStat.dexterity, 420, 700);
			g.drawString("" + InitialStat.luck, 420, 725);
			
		}
		
		// Stamina bar
		g.setColor(Color.DARK_GRAY);
		g.fillRect(width / 2 - 37, height - 47, 100, 20);
		g.setColor(Color.BLACK);
		g.fillRect(width / 2 - 37, height - 47, (int)InitialStat.staminapercent, 20);
		g.setColor(Color.lightGray);
		g.drawString((int)InitialStat.staminapercent + "%", width / 2 - 8, height - 30);
		g.drawImage(staminaframe, width / 2 - 40, height - 50, null);
		
		//DEV DISPLAY
		g.setColor(Color.WHITE);
		g.drawString("Mobs on screen: " + mobsonscreen, 370, 16);
		g.drawString("FPS: " + fpsDisplay, width - 100, 16);		
		
		//logo
		g.drawImage(logo, width - 335, height - 105, null);
		
		if(SaveStat.saving == true){
			g.setColor(Color.red);
			g.drawString("SAVING GAME ...", width / 2 - 80, 16);
		}
		
		portalcheck();
		
		g.dispose();
		bs.show();
	}	

	public static void portalcheck(){
		if(Game.currentzone.equals("world")){
			if((currentx >= 328 && currentx <= 345) && (currenty >= 193 && currenty <= 206)){
				level.remove(player);
				level.remove();
				level = Game.level.dungeon1;
				currentzone = "dungeon1";
				player = new Player(52, 84, key);
				level.add(player);
			}
		}else if(Game.currentzone.equals("dungeon1")){
			if((currentx >= 21 && currentx <= 34) && (currenty >= 65 && currenty <= 95)){
				level.remove(player);
				level.remove();
				level = Game.level.world;
				currentzone = "world";
				player = new Player(337, 228, key);
				level.add(player);
			}else if((currentx >= 1640 && currentx <= 1645) && (currenty >= 225 && currenty <= 271)){
				level.remove(player);
				level.remove();
				level = Game.level.world;
				currentzone = "world";
				player = new Player(337, 228, key);
				level.add(player);
			}
		}
	}
	
	public static void launch() {
		Game game = new Game();
		game.frame.setUndecorated(true);
		game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.frame.setResizable(false); // must be first thing applied to frame
		game.frame.setTitle("GearGrinder_BETA");
		//Game.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); // centers the frame
		game.frame.setVisible(true);

		//TimeLookup.TimeLookup();
		
		game.start();
	}
}
