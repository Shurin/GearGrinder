GearGrinder
===========

- Dec 09 2013 || AI & Tiles
started adding A* AI algorithm.
added vector handling
the tree tiles were defined with the wrong size

- Dec 06 2013 || Tiles
added tree tiles for grass terrain

- Dec 01 2013 || Quest & Clean Up
first quest officially done
removed quest.java since it's not used
fixed the quest problem
there is a bug in the quest load function, working on a fix
added a few more vars to the DB for quests such as Started and Complete
the quests are now stored in DB properly. now to make them save on exit

- Nov 30 2013 || Quest & Mob
game is pulling all quest data from DB on load and making a master list in the engine.
trying to handle storing the data in main quest matrix
added move limits to Mob based on original spawn locations

- Nov 29 2013 || Misc & Dungeon & Tiles & Day/Night
started to add the basics of quest handling, loooong way to go though
established a dungeon entrance, it's disabled for now
cleaned up cliff tiles so there is no longer a shadow in the corner
removed all unused tiles from the tile sheets and color maps
camp fires are synced to day/night

- Nov 28 2013 || Camp Fire & Projectiles & Tiles
made a new camp fire sprite and animation
projectiles can be static or animated
added new animated projectile function.
some tiles were rendering wrong

- Nov 27 2013 || Camp Fire & NPC & Tiles & Terrain & Collision
added general skills NPC
added animated camp fire
added new cliffs in starting zone
added a few crossover cliff tiles
added a quest type NPC in spawn area
added the display of NPC name in dialog (it scales so it's centered)
added some cliffs on the beach
finished cliff color codes
fixed center tiles for cliffs so you can walk on them

- Nov 26 2013 || Dialog & NPC
replaced dialog window with new one, old one is there for possible use later on
npc hp is now 2,147,483,647 (max 32bit int size)
created two test NPCs on the north beach
abandoning scalable dialog windows, too much work, too little reward

- Nov 25 2013 || Collision & Bugs & Terrain
you now collide with mobs while walking (sliding works too)
added more general world detail
typo in grasstile color code

- Nov 24 2013 || Collision & Tiles 
rebuilt the projectile > Mob collision system
all the terrain crossover tiles are finished
added all the crossover tiles, only sand and grass have colors atm

- Nov 23 2013 || Clean Up & Sprite & Terrain & Tiles & Player
removed old unused files and renamed some others
now other players can see the second player sprite instead of only the original
starter island updated
grasscliff tiles added
move speed x 10
sprint speed x 10
sand/water codes are fixed
added sand/grass tiles for both day/night
fixed ID of inland water tiles

- Nov 22 2013 || World
started making the actual world (basic terrain layout)

- Nov 21 2013 || Tiles
added a bunch of terrain color codes for tiles

- Nov 19 2013 || Tiles & Time
added a few hundred tiles for both day and night
the time display has been finalized

- Nov 18 2013 || Time
added server time display to the game (it's set to PST)

- Nov 17 2013 || Bug & Player & Day/Night
set time trigger for Day/Night (7am and 7pm)
all images are now defined with URL so they aren't lost when a JAR is made
nametags no longer render after the player logs out
added player nametags
random minor tweaks

- Nov 14 2013 || Server
testing the server functionality

- Nov 13 2013 || UI
added a few UI elements
need to make them URL based at some point

- Nov 12 2013 || Server
did some local testing, the engine is now set back to public

- Nov 11 2013 || Multiplayer & Server & JAR & Portals & Mob
DB keeps track of the player's sprite and direction
DB closed for local testing
JAR updated to latest build
redid portals to work in the new map
fixed mobs showing up instead of particles from projectile collision
now only online players are rendered
the main player ghost issue has been fixed
players appear but they are super buggy and appear on every level online or offline
new thread saves player's x, y to the DB
DB now keeps track of online status for every player
game now runs on one constant connection to the DB
Save lag has been fixed
Mobs now take damage when hit
removed some old interface images

- Nov 10 2013 || Combat & Mob & Inventory & Clean Up
added functions to get a projectiles damage, and get a mob's hp
Mobs can now be shot and die
put an item in the inventory just for show(making sure it doesn't mess up on resolution changes)
cleaned out old libraries

- Nov 9 2013 || Day/Night & Level
starting to add a Day/Night sequence(only effects outdoors)
added a BUILD folder with the latest JAR
level changing now works(red stones are portals down/ green are up)

- Nov 8 2013 || Clean Up & DB & GFX
removed old local data storage files/classes
Saves now go to the DB
playing with resolution to make sure things scale properly
DB working

- Nov 7 2013 || DB
player stats and location are pulled from DB on load
player stats and location are saved once a minute
added login page
added mySQL DB
added connector J and DPT for DB connectivity

- Nov 4 2013 || UI
new panels
if you're interacting with the UI you no longer shoot
new hud images

- Nov 3 2013 || UI & Tiles & Help & Misc
added glass
messing around with pokemon tiles
added some info to the help page
added mouse interaction for the interface
fixed a bug where the engine was constantly reloading the ui elements to memory

- Nov 2 2013 || UI
added some new UI elements
made the inventory transparent
removed dark grey bars, and moved the main bars from the edges of the screen

- Nov 1 2013 || Clean Up
cleaning up the code a little

- Oct 31 2013 || GFX 
changed the screen res and scale
added an inventory layout

- Oct 30 2013 || Combat
added alt fire

- Oct 29 2013 || Data IO
added local save/load for player location

- Oct 28 2013 || Mob & Player & Misc
added a spawn mob function (3 key)
added a key to decrease health (1 key)
added stamina regeneration
added some stamina (lost while sprinting)
added MP (lost while shooting)
minor tweaks and sorting of functions

- Oct 27 2013 || Player & Misc & AI & Mob
moved around some code for easier reading
added a sprint ability (shift key)
added a simple chaser AI for Mobs
fixed a bug in Mob > tile collosion
added a player array to keep track of different players
added a getClientPlayer to get and player's x, y
started working on Chaser AI for Mobs
fixed player update
fixed player remove
fixed player render
removed old move code
reverted to 4 directional sprites

- Oct 26 2013 || Mob & Level & Sprite & Particles
added two Mobs
added random walking AI
made spawn level smaller with more solid tiles
added subsheets
particle collision has been fully added

- Oct 25 2013 || Clean Up
cleaned out the /res

- Oct 23 2013 || Particles
minor fix to basic collision of particles
finished defining emitters ( entities, projectiles, particles)
particles now generate when a projectile hits a solid
added gravity to make particles bounce
added basic collision detection for particles
collide into a wall and make particles spawn

- Oct 21 2013 || Projectiles
adjusted projectile collision
projectiles now disappear on collision
projectile collision finished
changed projectile sprite to 8x8px from 16x16px

- Oct 20 2013 || Combat
added random particle fadeout 
added particle array for projectiles and others
increased projectile accuracy
added neat orb sprite for the projectile
you can now shoot a dummy projectile by clicking/holding in any direction
fixed a bug in tile rendering

- Oct19 2013 || UI
added "Tile X, Y" display

- Oct 18 2013 || Birth
cleaned up some old files
Base game core:
level "painting"
level rendering
player sprite rendering
8 direction sprite movement
tile collision
tile sliding
added the base of the engine




















































