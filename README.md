# Goldilocks
Goldilocks is a game that has a "infinite" (i.e reality really big) 2D universe where you control the spaceship. The game uses procedural world generation with noise maps (we use simplex noise) to generate planet locations, their textures, and the world. The planets are all generated at once at the execution of the program. During runtime, the program will render the planets based on their distance from the player's spaceship.

The goal of the game is to find "Goldilocks", a golden planet that was once lost to time. It will be found within the assortment of the procedurally generated worlds, and your goal is to destroy asteroids to get fuel for your spaceship. You can destroy asteroids to get fuel by clicking anywhere on the screen to shoot bullets.

To compile and run the program, type:
javac *.java
java Main
in the terminal. No command line arguments needed.

To control the spaceship, use WASD keys (w: move up, s: move down, d: move right, a: move left). Aim with your mouse and left click in a direction to shoot bullets.
