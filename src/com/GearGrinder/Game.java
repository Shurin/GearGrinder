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
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.GearGrinder.Networking.GetLoc;
import com.GearGrinder.Networking.GetLocThread;
import com.GearGrinder.Networking.InitialStat;
import com.GearGrinder.Networking.SaveLocThread;
import com.GearGrinder.Networking.SaveStat;
import com.GearGrinder.Networking.UserVerify;
import com.GearGrinder.entity.mob.Player;
import com.GearGrinder.graphics.Screen;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;
import com.GearGrinder.level.Level;
import com.GearGrinder.net.GameClient;
import com.GearGrinder.net.GameServer;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double widthmax = screenSize.getWidth();
	static double heightmax = screenSize.getHeight();
	
	static Calendar calendar;
	static String time;
	static DateFormat formatter = new SimpleDateFormat("HHmmss");
	static int timeint;
	static int hours;
	static int minutes;
	static int seconds;

	public static boolean nightTime = false;
	
	
	//private static int width = (int) widthmax;
	//private static int height = (int) heightmax;
	private static int width = 1024;
	private static int height = 768;
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
	private BufferedImage inventorybutton1 = null;
	private BufferedImage inventorybutton2 = null;
	private BufferedImage mailbutton1 = null;
	private BufferedImage mailbutton2 = null;
	private BufferedImage characterbutton1 = null;
	private BufferedImage characterbutton2 = null;
	private BufferedImage storebutton1 = null;
	private BufferedImage storebutton2 = null;
	private BufferedImage repbutton1 = null;
	private BufferedImage repbutton2 = null;
	private BufferedImage skillsbutton1 = null;
	private BufferedImage skillsbutton2 = null;
	private BufferedImage questbutton1 = null;
	private BufferedImage questbutton2 = null;
	private BufferedImage mapbutton1 = null;
	private BufferedImage mapbutton2 = null;
	private BufferedImage talentsbutton1 = null;
	private BufferedImage talentsbutton2 = null;
	private BufferedImage help = null;
	private BufferedImage help2 = null;
	private BufferedImage helppage = null;
	private BufferedImage charpanel = null;
	private BufferedImage broadsword = null;
	private BufferedImage spell_greenorb = null;
	private BufferedImage spell_rock = null;
	//URLs to the image locations
	URL inv = this.getClass().getResource("/hud/inventory.png");
	URL invb1 = this.getClass().getResource("/hud/inventorybutton1.png");
	URL invb2 = this.getClass().getResource("/hud/inventorybutton2.png");
	URL mb1 = this.getClass().getResource("/hud/mailbutton1.png");
	URL mb2 = this.getClass().getResource("/hud/mailbutton2.png");
	URL cb1 = this.getClass().getResource("/hud/characterbutton1.png");
	URL cb2 = this.getClass().getResource("/hud/characterbutton2.png");
	URL store1 = this.getClass().getResource("/hud/storebutton1.png");
	URL store2 = this.getClass().getResource("/hud/storebutton2.png");
	URL rep1 = this.getClass().getResource("/hud/repbutton1.png");
	URL rep2 = this.getClass().getResource("/hud/repbutton2.png");
	URL skills1 = this.getClass().getResource("/hud/skillsbutton1.png");
	URL skills2 = this.getClass().getResource("/hud/skillsbutton2.png");
	URL q1 = this.getClass().getResource("/hud/questbutton1.png");
	URL q2 = this.getClass().getResource("/hud/questbutton2.png");
	URL map1 = this.getClass().getResource("/hud/mapbutton1.png");
	URL map2 = this.getClass().getResource("/hud/mapbutton2.png");
	URL tb1 = this.getClass().getResource("/hud/talentsbutton1.png");
	URL tb2 = this.getClass().getResource("/hud/talentsbutton2.png");
	URL h1 = this.getClass().getResource("/hud/help.png");
	URL h2 = this.getClass().getResource("/hud/help2.png");
	URL hp = this.getClass().getResource("/hud/helppage.png");
	URL cpan = this.getClass().getResource("/hud/charpanel.png");
	URL bsword = this.getClass().getResource("/items/broadsword.png");
	URL gorb = this.getClass().getResource("/hud/spells_greenorb.png");
	URL rock = this.getClass().getResource("/hud/spells_rock.png");
	URL lo = this.getClass().getResource("/hud/logo.png");	
	URL str1 = this.getClass().getResource("/hud/storebutton1.png");
	URL str2 = this.getClass().getResource("/hud/storebutton2.png");
	URL h = this.getClass().getResource("/hud/hud.png");
	
	private int savetick = 0;
	
	private Mouse mouse = new Mouse();

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private GameClient socketClient;
	private GameServer socketServer;

	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		InitialStat.Onlineint = 1;
		
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
		
		/*if(JOptionPane.showConfirmDialog(this, "RUN THE SERVER?") ==0){
			socketServer = new GameServer(this);
			socketServer.start();
		}
		socketClient = new GameClient(this, "24.253.228.197");
		socketClient.start();*/
		
       // socketClient.sendData("ping".getBytes());
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
				} catch (Exception e) {
					e.printStackTrace();
				}
				frame.setTitle("GearGrinder    |    FPS: " + frames);
				fpsDisplay = frames;
				updates = 0;
				frames = 0;
				
				savetick = savetick +1;
				if(savetick == 60){
					System.out.println("Saving Game ...");
					SaveStat.SaveStat();
					savetick = 0;
				}
			
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
		}
	}

	public void test(){
		socketClient = new GameClient(this, "24.253.228.197");
		socketClient.start();
		if(UserVerify.clientID != 3){
			String info = (currentx + 16) + "," + (currenty + 16);
			socketClient.sendData(info.getBytes());
		}
		
	}
	
	public void uicache(){
		try {
			inventory = ImageIO.read(inv);
			hud = ImageIO.read(h);
			logo = ImageIO.read(lo);
			inventorybutton1 = ImageIO.read(invb1);
			inventorybutton2 = ImageIO.read(invb2);
			characterbutton1 = ImageIO.read(cb1);
			characterbutton2 = ImageIO.read(cb2);
			mailbutton1 = ImageIO.read(mb1);
			mailbutton2 = ImageIO.read(mb2);
			mapbutton1 = ImageIO.read(map1);
			mapbutton2 = ImageIO.read(map2);
			questbutton1 = ImageIO.read(q1);
			questbutton2 = ImageIO.read(q2);
			repbutton1 = ImageIO.read(rep1);
			repbutton2 = ImageIO.read(rep2);
			skillsbutton1 = ImageIO.read(skills1);
			skillsbutton2 = ImageIO.read(skills2);
			storebutton1 = ImageIO.read(str1);
			storebutton2 = ImageIO.read(str2);
			talentsbutton1 = ImageIO.read(tb1);
			talentsbutton2 = ImageIO.read(tb2);
			spell_greenorb = ImageIO.read(gorb);
			spell_rock = ImageIO.read(rock);
			help = ImageIO.read(h1);
			help2 = ImageIO.read(h2);
			helppage = ImageIO.read(hp);
			charpanel = ImageIO.read(cpan);
			broadsword = ImageIO.read(bsword);
		} catch (IOException e) {
			e.printStackTrace();
		}
		uicached = true;
	}
	
	public static void update() {
		key.update();
		level.update();
	}
	
	public void render() {	
		calendar = new GregorianCalendar(TimeZone.getTimeZone("PST"));
		time = formatter.format(calendar.getTime());
		formatter.setTimeZone(TimeZone.getTimeZone("GMT-8"));
		timeint = Integer.valueOf(time);
		hours = timeint / 10000;
		minutes = (timeint - (hours * 10000)) / 100;
		seconds = (timeint - (hours * 10000) - (minutes * 100)) / 1;
		
		if(hours > 18) {
			nightTime = true;			
		}else if(hours < 8){
			nightTime = true;
		}else{
			nightTime = false;
		}
		
		System.out.println("night status: " + nightTime + "    hours = " + hours);
		
		if(uicached == false) uicache();
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // 3 = triple buffer
			return;
		}
		screen.clear(); //turn back on if gfx bugs arise
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
		
		g.setColor(Color.MAGENTA);
		if(hours < 10){
			g.drawString("TIME: 0" + hours + ":" + minutes + ":" + seconds, width / 2, 100);
		}else if(minutes < 10){
			g.drawString("TIME: " + hours + ":0" + minutes + ":" + seconds, width / 2, 100);
		}else if(seconds < 10){
			g.drawString("TIME: " + hours + ":" + minutes + ":0" + seconds, width / 2, 100);
		}else if(hours < 10 && minutes < 10 && seconds < 10){
			g.drawString("TIME: 0" + hours + ":0" + minutes + ":0" + seconds, width / 2, 100);
		}else if(hours < 10 && minutes < 10){
			g.drawString("TIME: 0" + hours + ":0" + minutes + ":" + seconds, width / 2, 100);
		}else if(hours < 10 && seconds < 10){
			g.drawString("TIME: 0" + hours + ":" + minutes + ":0" + seconds, width / 2, 100);
		}else if(minutes < 10 && seconds < 10){
			g.drawString("TIME: " + hours + ":0" + minutes + ":0" + seconds, width / 2, 100);
		}else{ 
			g.drawString("TIME: " + hours + ":" + minutes + ":" + seconds, width / 2, 100);
		}
		
		
		g.setColor(Color.getHSBColor(170, 0, 255));
		int charsize = 7;
		int middleofname = Playername.length() / 2;
		g.drawString(Game.Playername, currentx - screen.xOffset - (charsize * middleofname), currenty - screen.yOffset - 24);
		
		
		if(GetLoc.RENDER){
			g.setColor(Color.CYAN);
			int midofothername = GetLoc.pname.length() /2;
			g.drawString(GetLoc.pname, GetLoc.xp1 - screen.xOffset - (charsize * midofothername), GetLoc.yp1 - screen.yOffset - 24);
		}
		
		// baseline locations
		//using these makes the UI scale for any resolution
		int hudx = width / 2 - 431; //main HUD
		int hudy = height - 222;
		int ixt = width - 499; // Inventory
		int iyt = height / 2 - 20;
		int cpx = 30; //Char Panel
		int cpy = 265;
		
		g.setColor(Color.BLACK);
		g.fillRect(hudx + 50, hudy + 70, 210, 75);
		g.fillRect(hudx + 50, hudy + 70, 210, 75);
		
		// health bar
		g.setColor(Color.RED);
		g.fillRect(hudx + 57, hudy + 71, ((int)InitialStat.healthpercent * 2), 30);
		
		// Magic bar
		g.setColor(Color.BLUE);
		g.fillRect(hudx + 57, hudy + 103, ((int)InitialStat.magicpercent * 2), 30);
		
		// Stamina bar		
		g.setColor(Color.BLACK);
		g.fillRect(hudx + 381, hudy + 113, 100, 20);
		g.setColor(Color.GREEN);
		g.fillRect(hudx + 380, hudy + 113, (int)InitialStat.staminapercent, 20);
		
		// XP bar
		g.setColor(Color.BLACK);
		g.fillRect(hudx + 56, hudy + 147, 725, 20);
		g.setColor(Color.YELLOW);
		g.fillRect(hudx + 56, hudy + 147, (int)InitialStat.XPpercent, 20);

		// hud elements
		
		g.drawImage(spell_greenorb, hudx + 268, hudy + 74, null);
		g.drawImage(spell_rock, hudx + 323, hudy + 76, null);
		
		//unimplemented hud elements
		//g.drawImage(mailbutton1, hudx + 639, hudy + 88, null);
		g.drawImage(mapbutton1, hudx + 492, hudy + 88, null);
		g.drawImage(questbutton1, hudx + 542, hudy + 88, null);
		//g.drawImage(repbutton1, hudx, hudy + 88, null);
		g.drawImage(skillsbutton1, hudx + 591, hudy + 88, null);
		//g.drawImage(storebutton1, hudx, hudy + 88, null);
		g.drawImage(talentsbutton1, hudx+ 690, hudy + 88, null);
		g.drawImage(characterbutton1, hudx + 641, hudy + 88, null);
		
		// HUD
		g.drawImage(hud, hudx, hudy, null);
		
		
		
		//HELP button
		if(Player.helpshow == true){
			g.drawImage(help2, hudx + 413, hudy + 65, null);
		}else {
			g.drawImage(help, hudx + 413, hudy + 65, null);
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

		// INVENTORY
		if (Player.invshow == true) {
			g.drawImage(inventorybutton2, hudx + 739, hudy + 88, null);
			g.drawImage(inventory, width - 499, height / 2 - 20, null);
			g.drawImage(broadsword, ixt + 10, iyt + 10, null);
		} else {
			g.drawImage(inventorybutton1, hudx + 739, hudy + 88, null);
		}
		
		//CHARACTER PANEL
		if(Player.charshow == true){
			g.drawImage(charpanel, cpx, cpy, null);		
			g.setColor(Color.GREEN);
			g.drawString("Name:  " + Playername, cpx + 30, cpy + 24);
			g.drawString("Level:  " + InitialStat.PlayerLevel, cpx + 225, cpy + 24);
			g.drawString("Zone:  " + currentzone, cpx + 370, cpy + 24);
			g.drawString("" + InitialStat.health, cpx + 105,  cpy + 335);
			g.drawString("" + InitialStat.magic, cpx + 390,  cpy + 335);
			g.drawString("" + InitialStat.strength, cpx + 105, cpy + 385);
			g.drawString("" + InitialStat.defense, cpx + 105, cpy + 410);
			g.drawString("" + InitialStat.vitality, cpx + 105, cpy + 435);
			g.drawString("" + InitialStat.stamina, cpx + 105, cpy + 460);
			g.drawString("" + InitialStat.agility, cpx + 390, cpy + 385);
			g.drawString("" + InitialStat.intelligence, cpx + 390, cpy + 410);
			g.drawString("" + InitialStat.dexterity, cpx + 390, cpy + 435);
			g.drawString("" + InitialStat.luck, cpx + 390, cpy + 460);			
		}		
		
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
		//test();		
		g.dispose();
		bs.show();
	}	
	
	public static void portalcheck(){
		if(Game.currentzone.equals("world")){
			if((currentx >= 3896 && currentx <= 3913) && (currenty >= 3601 && currenty <= 3612)){
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
				player = new Player(3900, 3700, key);
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
		game.frame.setUndecorated(false);
		//game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		game.frame.setResizable(false); // must be first thing applied to frame
		game.frame.setTitle("GearGrinder_BETA");
		//Game.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); // centers the frame
		game.frame.setVisible(true);

		SaveLocThread.SaveLocThread();
		GetLocThread.GetLocThread();
		
		game.start();
	}
}
