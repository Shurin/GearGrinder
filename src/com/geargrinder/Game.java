package com.geargrinder;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.JFrame;

import com.geargrinder.Networking.GetLocThread;
import com.geargrinder.Networking.InitialStat;
import com.geargrinder.Networking.SaveLocThread;
import com.geargrinder.Networking.SaveStat;
import com.geargrinder.entity.mob.Player;
import com.geargrinder.graphics.Gui;
import com.geargrinder.graphics.Screen;
import com.geargrinder.input.Keyboard;
import com.geargrinder.input.Mouse;
import com.geargrinder.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static double widthmax = screenSize.getWidth();
	static double heightmax = screenSize.getHeight();

	static Calendar calendar;
	static String time;
	static DateFormat formatter = new SimpleDateFormat("HHmmss");
	static int timeint;
	public static int hours;
	public static int minutes;
	public static int seconds;

	public static boolean nightTime = false;

	private static int width = 1360;
	private static int height = 768;
	private static int scale = 1;
	public static BufferStrategy bs;
	public static Graphics g;

	public int healthPosX = 50, healthPosY = 678;
	public int staminaPosX = 95, staminaPosY = 725;
	public static int mobsonscreen = 3;
	public static String Playername = null;
	public static int PlayerSpawnX = 21;
	public static int PlayerSpawnY = 18;
	public static int currentx = 0;
	public static int currenty = 0;
	public static String currentzone = null;

	public static int fpsDisplay;

	public int upsDisplay = 0;
	public static int accounttableheight = 0;
	public static Thread gamethread;
	public JFrame frame;
	private static Keyboard key;
	public static Level level;
	public static Player player;
	private static Gui gui;
	public static boolean running = false;
	public static boolean logcheck = false;
	public static boolean dungeonallowed = true;

	private int savetick = 0;

	private Mouse mouse = new Mouse();

	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		InitialStat.Onlineint = 1;

		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		gui = new Gui(screen);
		gui.load();

		if (InitialStat.DB_Zone.equals("world")) {
			level = Level.world;
			currentzone = "world";
		} else if (InitialStat.DB_Zone.equals("dungeon1")) {
			level = Level.dungeon1;
			currentzone = "dungeon1";
		} else if (InitialStat.DB_Zone.equals("noobisland")) {
			level = Level.noobisland;
			currentzone = "noobisland";
		}

		if (InitialStat.PSprite.equals("psprite002")) Player.spritefix();

		player = new Player(PlayerSpawnX, PlayerSpawnY, key);
		level.add(player);

		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public static int getWindowWidth(boolean useScale) {
		if (useScale) return width * scale;
		else return width;
	}

	public static int getWindowHeight(boolean useScale) {
		if (useScale) return height * scale;
		else return height;
	}

	public static void launch() {
		Game game = new Game();
		game.frame.setUndecorated(false);
		game.frame.setResizable(false);
		game.frame.setTitle("GearGrinder_ALPHA");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		SaveLocThread.SaveLocThread();
		GetLocThread.GetLocThread();

		game.start();
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
		while (running == true) {
			long now = System.nanoTime();
			delta = delta + (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("GearGrinder    |    FPS: " + frames);
				fpsDisplay = frames;
				upsDisplay = updates;
				tick();
				updates = 0;
				frames = 0;

				savetick = savetick + 1;
				if (savetick == 60) {
					SaveStat.SaveStat();
					savetick = 0;
				}
			}
		}
	}

	public static void tick() {
		calendar = new GregorianCalendar(TimeZone.getTimeZone("PST"));
		time = formatter.format(calendar.getTime());
		formatter.setTimeZone(TimeZone.getTimeZone("GMT-8"));
		timeint = Integer.valueOf(time);
		hours = timeint / 10000;
		minutes = (timeint - (hours * 10000)) / 100;
		seconds = (timeint - (hours * 10000) - (minutes * 100)) / 1;

		if (hours > 18) nightTime = true;
		else if (hours < 8) nightTime = true;
		else nightTime = false;
	}

	public static void update() {
		key.update();
		level.update();

		PlayerSpawnX = player.getX();
		PlayerSpawnY = player.getY();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		screen.clear();

		int xScroll = player.getX() - screen.width / 2;
		int yScroll = player.getY() - screen.height / 2;
		level.render(xScroll, yScroll, screen);

		for (int i = 0; i < pixels.length; i++)
			pixels[i] = screen.pixels[i];

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		gui.renderGameGui(g);

		g.dispose();
		bs.show();
	}

	public static void portalcheck() {
		// the x and y in portal check goes by pixels, not tiles
		if (Game.currentzone.equals("noobisland")) {
			if ((currentx >= 17032 && currentx <= 17049) && (currenty >= 55841 && currenty <= 55854)) {
				/*level.remove(player);
				level.remove();
				level = Game.level.dungeon1;
				currentzone = "dungeon1";
				player = new Player(52, 84, key);
				level.add(player);*/
				dungeonallowed = false;
			}
		}
	}
}
