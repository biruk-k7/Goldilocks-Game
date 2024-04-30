This is a game that has a "infinite" (in reality really big) 2D universe where you control the spaceship. The game uses procedural world generation with noise maps (we use simplex noise) to generate planet locations, their textures, and the world. The planets are all generated at once at the execution of the program. During runtime, the program will render the planets based on their distance from the player's spaceship. 

The goal of the game is still being finalized, but the idea is to have a player develop a spaceship (there will be 3 upgrades) to then try to find a cirtain planet that either will have treasure or the one which a player will colonize (for that we might steal some ideas from interstellar).

To compile and run the program, type:
javac *.java
java Main

in the terminal; no command line arguments needed.

After running it, the usage of the program is pretty simple. To control the spaceship, use wasd keys (w: move up, s: move down, d: move right, a: move left). the world will be generated as you go. Other functionality will be added soon :)