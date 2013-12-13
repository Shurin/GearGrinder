package com.GearGrinder.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.GearGrinder.Game;
import com.GearGrinder.Networking.GetLoc;
import com.GearGrinder.Networking.InitialStat;
import com.GearGrinder.Networking.SaveLoc;
import com.GearGrinder.Networking.SaveStat;
import com.GearGrinder.entity.mob.Player;
import com.GearGrinder.entity.mob.npcDialog;
import com.GearGrinder.input.Keyboard;
import com.GearGrinder.input.Mouse;
import com.GearGrinder.level.Level;

public class Gui {
	Screen screen;

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
	private BufferedImage dialogTop = null;
	private BufferedImage dialogMiddle = null;
	private BufferedImage dialogBottom = null;
	private BufferedImage dialogwindow = null;

	public Gui(Screen screen) {
		this.screen = screen;
	}

	public void load() {
		try {
			inventory = ImageIO.read(this.getClass().getResource("/hud/inventory.png"));
			hud = ImageIO.read(this.getClass().getResource("/hud/hud.png"));
			logo = ImageIO.read(this.getClass().getResource("/hud/logo.png"));
			inventorybutton1 = ImageIO.read(this.getClass().getResource("/hud/inventorybutton1.png"));
			inventorybutton2 = ImageIO.read(this.getClass().getResource("/hud/inventorybutton2.png"));
			characterbutton1 = ImageIO.read(this.getClass().getResource("/hud/characterbutton1.png"));
			characterbutton2 = ImageIO.read(this.getClass().getResource("/hud/characterbutton2.png"));
			mailbutton1 = ImageIO.read(this.getClass().getResource("/hud/mailbutton1.png"));
			mailbutton2 = ImageIO.read(this.getClass().getResource("/hud/mailbutton2.png"));
			mapbutton1 = ImageIO.read(this.getClass().getResource("/hud/mapbutton1.png"));
			mapbutton2 = ImageIO.read(this.getClass().getResource("/hud/mapbutton2.png"));
			questbutton1 = ImageIO.read(this.getClass().getResource("/hud/questbutton1.png"));
			questbutton2 = ImageIO.read(this.getClass().getResource("/hud/questbutton2.png"));
			repbutton1 = ImageIO.read(this.getClass().getResource("/hud/repbutton1.png"));
			repbutton2 = ImageIO.read(this.getClass().getResource("/hud/repbutton2.png"));
			skillsbutton1 = ImageIO.read(this.getClass().getResource("/hud/skillsbutton1.png"));
			skillsbutton2 = ImageIO.read(this.getClass().getResource("/hud/skillsbutton2.png"));
			storebutton1 = ImageIO.read(this.getClass().getResource("/hud/storebutton1.png"));
			storebutton2 = ImageIO.read(this.getClass().getResource("/hud/storebutton2.png"));
			talentsbutton1 = ImageIO.read(this.getClass().getResource("/hud/talentsbutton1.png"));
			talentsbutton2 = ImageIO.read(this.getClass().getResource("/hud/talentsbutton2.png"));
			spell_greenorb = ImageIO.read(this.getClass().getResource("/hud/spells_greenorb.png"));
			spell_rock = ImageIO.read(this.getClass().getResource("/hud/spells_rock.png"));
			help = ImageIO.read(this.getClass().getResource("/hud/help.png"));
			help2 = ImageIO.read(this.getClass().getResource("/hud/help2.png"));
			helppage = ImageIO.read(this.getClass().getResource("/hud/helppage.png"));
			charpanel = ImageIO.read(this.getClass().getResource("/hud/charpanel.png"));
			broadsword = ImageIO.read(this.getClass().getResource("/items/broadsword.png"));
			dialogTop = ImageIO.read(this.getClass().getResource("/hud/dialog_top.png"));
			dialogMiddle = ImageIO.read(this.getClass().getResource("/hud/dialog_middle.png"));
			dialogBottom = ImageIO.read(this.getClass().getResource("/hud/dialog_bottom.png"));
			dialogwindow = ImageIO.read(this.getClass().getResource("/hud/dialog.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void renderGameGui(Graphics g) {
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}

		// Not part of the render...
		g.setColor(Color.WHITE);
		g.setFont(new Font("Veranda", 0, 16));
		g.drawString("Button: " + Mouse.getB(), Game.getWindowWidth(false) - 70, Game.getWindowHeight(false) - 5);
		g.drawString("Tile X: " + (Game.player.getX() / 16) + ", Y: " + ((Game.player.getY() / 16) + 1), 85, 16);
		g.drawString("X: " + Game.player.getX() + ", Y: " + Game.player.getY(), 225, 16);
		Game.currentx = Game.player.getX();
		Game.currenty = Game.player.getY();

		g.setColor(Color.MAGENTA);
		if (Game.hours < 10) g.drawString("TIME: 0" + Game.hours + ":" + Game.minutes + ":" + Game.seconds, Game.getWindowWidth(false) / 2, 100);
		else if (Game.minutes < 10) g.drawString("TIME: " + Game.hours + ":0" + Game.minutes + ":" + Game.seconds, Game.getWindowWidth(false) / 2, 100);
		else if (Game.seconds < 10) g.drawString("TIME: " + Game.hours + ":" + Game.minutes + ":0" + Game.seconds, Game.getWindowWidth(false) / 2, 100);
		else if (Game.hours < 10 && Game.minutes < 10 && Game.seconds < 10) g.drawString("TIME: 0" + Game.hours + ":0" + Game.minutes + ":0" + Game.seconds, Game.getWindowWidth(false) / 2, 100);
		else if (Game.hours < 10 && Game.minutes < 10) g.drawString("TIME: 0" + Game.hours + ":0" + Game.minutes + ":" + Game.seconds, Game.getWindowWidth(false) / 2, 100);
		else if (Game.hours < 10 && Game.seconds < 10) g.drawString("TIME: 0" + Game.hours + ":" + Game.minutes + ":0" + Game.seconds, Game.getWindowWidth(false) / 2, 100);
		else if (Game.minutes < 10 && Game.seconds < 10) g.drawString("TIME: " + Game.hours + ":0" + Game.minutes + ":0" + Game.seconds, Game.getWindowWidth(false) / 2, 100);
		else g.drawString("TIME: " + Game.hours + ":" + Game.minutes + ":" + Game.seconds, Game.getWindowWidth(false) / 2, 100);

		g.setColor(Color.getHSBColor(170, 0, 255));
		int charsize = 7;
		int middleofname = Game.Playername.length() / 2;
		g.drawString(Game.Playername, Game.currentx - screen.xOffset - (charsize * middleofname), Game.currenty - screen.yOffset - 24);

		if (GetLoc.RENDER) {
			g.setColor(Color.CYAN);
			int midofothername = GetLoc.pname.length() / 2;
			g.drawString(GetLoc.pname, GetLoc.xp1 - screen.xOffset - (charsize * midofothername), GetLoc.yp1 - screen.yOffset - 24);
		}

		// baseline locations
		// using these makes the UI scale for any resolution
		int hudx = Game.getWindowWidth(false) / 2 - 431; // main HUD
		int hudy = Game.getWindowHeight(false) - 222;
		int ixt = Game.getWindowWidth(false) - 499; // Inventory
		int iyt = Game.getWindowHeight(false) / 2 - 20;
		int cpx = 30; // Char Panel
		int cpy = 265;

		g.setColor(Color.BLACK);
		g.fillRect(hudx + 50, hudy + 70, 210, 75);
		g.fillRect(hudx + 50, hudy + 70, 210, 75);

		// health bar
		g.setColor(Color.RED);
		g.fillRect(hudx + 57, hudy + 71, ((int) InitialStat.healthpercent * 2), 30);

		// Magic bar
		g.setColor(Color.BLUE);
		g.fillRect(hudx + 57, hudy + 103, ((int) InitialStat.magicpercent * 2), 30);

		// Stamina bar
		g.setColor(Color.BLACK);
		g.fillRect(hudx + 381, hudy + 113, 100, 20);
		g.setColor(Color.GREEN);
		g.fillRect(hudx + 380, hudy + 113, (int) InitialStat.staminapercent, 20);

		// XP bar
		g.setColor(Color.BLACK);
		g.fillRect(hudx + 56, hudy + 147, 725, 20);
		g.setColor(Color.YELLOW);
		g.fillRect(hudx + 56, hudy + 147, (int) InitialStat.XPpercent, 20);

		// hud elements
		g.drawImage(spell_greenorb, hudx + 268, hudy + 74, null);
		g.drawImage(spell_rock, hudx + 323, hudy + 76, null);

		// unimplemented hud elements
		// g.drawImage(mailbutton1, hudx + 639, hudy + 88, null);
		g.drawImage(mapbutton1, hudx + 492, hudy + 88, null);
		g.drawImage(questbutton1, hudx + 542, hudy + 88, null);
		// g.drawImage(repbutton1, hudx, hudy + 88, null);
		g.drawImage(skillsbutton1, hudx + 591, hudy + 88, null);
		// g.drawImage(storebutton1, hudx, hudy + 88, null);
		g.drawImage(talentsbutton1, hudx + 690, hudy + 88, null);
		g.drawImage(characterbutton1, hudx + 641, hudy + 88, null);

		// HUD
		g.drawImage(hud, hudx, hudy, null);

		// HELP button
		if (Player.helpshow == true) g.drawImage(help2, hudx + 413, hudy + 65, null);
		else g.drawImage(help, hudx + 413, hudy + 65, null);

		// HELP PANEL
		if (Player.helpshow == true) {
			g.drawImage(helppage, Game.getWindowWidth(false) / 2 - 160, 35, null);
			g.setColor(Color.GREEN);
			g.drawString("- Shoot1 = left mouse", Game.getWindowWidth(false) / 2 - 60, 75);
			g.drawString("- Shoot2 = right mouse", Game.getWindowWidth(false) / 2 - 60, 100);
			g.drawString("- Inventory = I", Game.getWindowWidth(false) / 2 - 60, 125);
			g.drawString("- This Page = H", Game.getWindowWidth(false) / 2 - 60, 150);
			g.drawString("- Close game = Esc", Game.getWindowWidth(false) / 2 - 60, 175);
			g.setColor(Color.RED);
			g.drawString("- Finish adding UI elements", Game.getWindowWidth(false) / 2 - 60, 200);
			g.drawString("- Finish adding UI mouse interaction", Game.getWindowWidth(false) / 2 - 60, 225);
			g.drawString("- Finish adding basic player stats", Game.getWindowWidth(false) / 2 - 60, 250);
			g.drawString("- Finish adding Save/Load game", Game.getWindowWidth(false) / 2 - 60, 275);
			g.drawString("- Start adding items", Game.getWindowWidth(false) / 2 - 60, 300);
			g.drawString("- Start adding a chatbox", Game.getWindowWidth(false) / 2 - 60, 325);
			g.drawString("- Start adding Mob collision", Game.getWindowWidth(false) / 2 - 60, 350);
			g.drawString("- Start adding Database I/O", Game.getWindowWidth(false) / 2 - 60, 375);
			g.drawString("- Start adding Networking", Game.getWindowWidth(false) / 2 - 60, 400);
			g.setColor(Color.WHITE);
			g.drawString("I'll add more to this as needed ...", Game.getWindowWidth(false) / 2 - 60, 700);
		}

		// INVENTORY
		if (Player.invshow == true) {
			g.drawImage(inventorybutton2, hudx + 739, hudy + 88, null);
			g.drawImage(inventory, Game.getWindowWidth(false) - 499, Game.getWindowHeight(false) / 2 - 20, null);
			g.drawImage(broadsword, ixt + 10, iyt + 10, null);
		} else g.drawImage(inventorybutton1, hudx + 739, hudy + 88, null);

		// CHARACTER PANEL
		if (Player.charshow == true) {
			g.drawImage(charpanel, cpx, cpy, null);
			g.setColor(Color.GREEN);
			g.drawString("Name:  " + Game.Playername, cpx + 30, cpy + 24);
			g.drawString("Level:  " + InitialStat.PlayerLevel, cpx + 225, cpy + 24);
			g.drawString("Zone:  " + Game.currentzone, cpx + 370, cpy + 24);
			g.drawString("" + InitialStat.health, cpx + 105, cpy + 335);
			g.drawString("" + InitialStat.magic, cpx + 390, cpy + 335);
			g.drawString("" + InitialStat.strength, cpx + 105, cpy + 385);
			g.drawString("" + InitialStat.defense, cpx + 105, cpy + 410);
			g.drawString("" + InitialStat.vitality, cpx + 105, cpy + 435);
			g.drawString("" + InitialStat.stamina, cpx + 105, cpy + 460);
			g.drawString("" + InitialStat.agility, cpx + 390, cpy + 385);
			g.drawString("" + InitialStat.intelligence, cpx + 390, cpy + 410);
			g.drawString("" + InitialStat.dexterity, cpx + 390, cpy + 435);
			g.drawString("" + InitialStat.luck, cpx + 390, cpy + 460);
		}

		// DEV DISPLAY
		g.setColor(Color.WHITE);
		g.drawString("Mobs on screen: " + Game.mobsonscreen, 370, 16);
		g.drawString("FPS: " + Game.fpsDisplay, Game.getWindowWidth(false) - 100, 16);

		// logo
		g.drawImage(logo, Game.getWindowWidth(false) - 335, Game.getWindowHeight(false) - 105, null);

		if (SaveStat.saving == true) {
			g.setColor(Color.red);
			g.drawString("SAVING GAME ...", Game.getWindowWidth(false) / 2 - 80, 16);
		}

		// /////////////////////////////////
		// /////// DIALOG SECTION //////////
		// /////////////////////////////////

		if (npcDialog.showDialog) {
			g.drawString("dialog initiated", 50, 50);
			if (Level.entities.get(npcDialog.npcindex).npcType().equals("a simple test npc")) {
				int textrows = Level.entities.get(npcDialog.npcindex).npcText().size();
				int dtopX = Game.getWindowWidth(false) / 3 - 15;
				int dtopY = Game.getWindowHeight(false) / 7;
				int dtextY = dtopY + 87;
				int nameX = dtopX + 244;
				int nameOffset = Level.entities.get(npcDialog.npcindex).getName().length() * 4;

				g.drawImage(dialogwindow, dtopX, dtopY, null);
				g.drawString(Level.entities.get(npcDialog.npcindex).getName(), nameX - nameOffset, dtopY + 42);

				for (int i = 0; i < textrows; i++) {
					int y = dtextY + 10;
					g.drawString(Level.entities.get(npcDialog.npcindex).npcText().get(i), dtopX + 35, y);
					dtextY = dtextY + 20;
				}
				if (Keyboard.dialog) npcDialog.showDialog = false;
			} else if (Level.entities.get(npcDialog.npcindex).npcType().equals("quest")) {
				if (Level.entities.get(npcDialog.npcindex).getName().equals("Verdyn")) {
					for (int i = 0; i < InitialStat.questcount; i++) {
						if (InitialStat.listofquests.get(i).get(4).toString().equals("Ninja Slayer") && InitialStat.listofquests.get(i).get(2).toString().equals("0") && InitialStat.listofquests.get(i).get(3).toString().equals("0")) {
							int textrows = Level.entities.get(npcDialog.npcindex).npcText().size();
							int dtopX = Game.getWindowWidth(false) / 3 - 15;
							int dtopY = Game.getWindowHeight(false) / 7;
							int dtextY = dtopY + 87;
							int nameX = dtopX + 244;
							int nameOffset = Level.entities.get(npcDialog.npcindex).getName().length() * 4;

							g.drawImage(dialogwindow, dtopX, dtopY, null);
							g.drawString(Level.entities.get(npcDialog.npcindex).getName(), nameX - nameOffset, dtopY + 42);

							for (int j = 0; j < textrows; j++) {
								int y = dtextY + 10;
								g.drawString(Level.entities.get(npcDialog.npcindex).npcText().get(j), dtopX + 35, y);
								dtextY = dtextY + 20;
							}
							if (Keyboard.dialog) {
								InitialStat.listofquests.get(0).set(2, "1");
								SaveLoc.SaveQuests();
								InitialStat.questlog();
								npcDialog.showDialog = false;
							}
						} else if (InitialStat.listofquests.get(i).get(4).toString().equals("Ninja Slayer") && InitialStat.listofquests.get(i).get(2).toString().equals("1") && InitialStat.listofquests.get(i).get(3).toString().equals("0")) {
							int dtopX = Game.getWindowWidth(false) / 3 - 15;
							int dtopY = Game.getWindowHeight(false) / 7;
							int dtextY = dtopY + 87;
							int nameX = dtopX + 244;
							int nameOffset = Level.entities.get(npcDialog.npcindex).getName().length() * 4;

							g.drawImage(dialogwindow, dtopX, dtopY, null);
							g.drawString(Level.entities.get(npcDialog.npcindex).getName(), nameX - nameOffset, dtopY + 42);
							g.drawString("Come back when you've kill all of those nasty NinjaBots ...", dtopX + 35, dtopY + (42 * 5));

							if (Keyboard.dialog) npcDialog.showDialog = false;
						} else if (InitialStat.listofquests.get(i).get(0).toString().equals("0") && InitialStat.listofquests.get(i).get(2).toString().equals("1") && InitialStat.listofquests.get(i).get(3).toString().equals("1")) {
							int dtopX = Game.getWindowWidth(false) / 3 - 15;
							int dtopY = Game.getWindowHeight(false) / 7;
							int dtextY = dtopY + 87;
							int nameX = dtopX + 244;
							int nameOffset = Level.entities.get(npcDialog.npcindex).getName().length() * 4;

							g.drawImage(dialogwindow, dtopX, dtopY, null);
							g.drawString(Level.entities.get(npcDialog.npcindex).getName(), nameX - nameOffset, dtopY + 42);
							g.drawString("Great work! Here's your reward!", dtopX + 35, dtopY + (42 * 5));

							if (Keyboard.dialog) {
								InitialStat.listofquests.get(0).set(2, "0");
								SaveLoc.SaveQuests();
								InitialStat.questlog();
								npcDialog.showDialog = false;
							}
						} else if (InitialStat.listofquests.get(i).get(4).toString().equals("Ninja Slayer") && InitialStat.listofquests.get(i).get(2).toString().equals("0") && InitialStat.listofquests.get(i).get(3).toString().equals("1")) {
							int dtopX = Game.getWindowWidth(false) / 3 - 15;
							int dtopY = Game.getWindowHeight(false) / 7;
							int dtextY = dtopY + 87;
							int nameX = dtopX + 244;
							int nameOffset = Level.entities.get(npcDialog.npcindex).getName().length() * 4;

							g.drawImage(dialogwindow, dtopX, dtopY, null);
							g.drawString(Level.entities.get(npcDialog.npcindex).getName(), nameX - nameOffset, dtopY + 42);
							g.drawString("I have nothing more for you at this time ...", dtopX + 35, dtopY + (42 * 5));

							if (Keyboard.dialog) npcDialog.showDialog = false;
						}
					}
				}
			} else if (Level.entities.get(npcDialog.npcindex).npcType().equals("skills")) {
				int textrows = Level.entities.get(npcDialog.npcindex).npcText().size();
				int dtopX = Game.getWindowWidth(false) / 3 - 15;
				int dtopY = Game.getWindowHeight(false) / 7;
				int dtextY = dtopY + 87;
				int nameX = dtopX + 244;
				int nameOffset = Level.entities.get(npcDialog.npcindex).getName().length() * 4;

				g.drawImage(dialogwindow, dtopX, dtopY, null);
				g.drawString(Level.entities.get(npcDialog.npcindex).getName(), nameX - nameOffset, dtopY + 42);

				for (int i = 0; i < textrows; i++) {
					int y = dtextY + 10;
					g.drawString(Level.entities.get(npcDialog.npcindex).npcText().get(i), dtopX + 35, y);
					dtextY = dtextY + 20;
				}
				if (Keyboard.dialog) npcDialog.showDialog = false;
			}
		}

		// /////////////////////////////
		// ///// SHOW QUEST LOG ////////
		// /////////////////////////////
		if (Player.activequests > 0) {
			g.setFont(new Font("Veranda", 0, 10));
			g.setColor(Color.BLACK);
			g.fillRect((Game.getWindowWidth(false) / 4) * 3, Game.getWindowHeight(false) / 12, 300, 500);
			int logX = (Game.getWindowWidth(false) / 4) * 3;
			int logY = Game.getWindowHeight(false) / 12;
			for (int i = 0; i < InitialStat.questcount; i++) {
				if (InitialStat.listofquests.get(i).get(1).equals("kill") && InitialStat.listofquests.get(i).get(2).toString().equals("1")) {
					logY = logY + 35;
					g.setColor(Color.WHITE);
					g.drawString("Quest " + InitialStat.listofquests.get(i).get(0) + ": " + InitialStat.listofquests.get(i).get(4), logX + 10, logY);
					g.drawString("Kill: " + InitialStat.listofquests.get(i).get(10) + "  " + InitialStat.listofquests.get(i).get(12) + " / " + InitialStat.listofquests.get(i).get(11), logX + 35, logY + 15);
				}
			}
		}

		Game.portalcheck();
		if (Game.dungeonallowed == false) {
			g.setColor(Color.BLACK);
			g.fillRect(Game.getWindowWidth(false) / 2 - 135, Game.getWindowHeight(false) / 2 - 122, 330, 30);
			g.setColor(Color.RED);
			g.drawString("YOU CANNOT ENTER THIS DUNGEON YET.", Game.getWindowWidth(false) / 2 - 130, Game.getWindowHeight(false) / 2 - 100);
			Game.dungeonallowed = true;
		}
		// test();

	}
}