# Goldilocks: A 2D Game with Procedurally Generated World
![frame goldilocks preview](https://github.com/user-attachments/assets/180f3cd0-25ad-4033-a1d0-043e9cc15961)

Goldilocks is a game that has a "infinite" (i.e reality really big) 2D universe where you control the spaceship. The game uses procedural world generation with noise maps (we use simplex noise) to generate planet locations, their textures, and the world. The planets are all generated at once at the execution of the program. During runtime, the program will render the planets based on their distance from the player's spaceship.

The goal of the game is to find "Goldilocks", a golden planet that was once lost to time. It will be found within the assortment of the procedurally generated worlds, and your goal is to destroy asteroids to get fuel for your spaceship. You can destroy asteroids to get fuel by clicking anywhere on the screen to shoot bullets.

To compile and run the program, type:
javac *.java
java Main
in the terminal. No command line arguments needed.

To control the spaceship, use WASD keys (w: move up, s: move down, d: move right, a: move left). Aim with your mouse and left click in a direction to shoot bullets.

You can view a video demonstration of the game below:
<a href="http://www.youtube.com/watch?feature=player_embedded&v=rhNE35C7s5U
" target="_blank"><img src="http://img.youtube.com/vi/rhNE35C7s5U/0.jpg" 
alt="IMAGE ALT TEXT HERE" width="220" height="180" border="10" /></a>
