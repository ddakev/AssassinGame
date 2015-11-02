# assassinAndroidGame
A Game for Assassin for the VandyHacks Hackathon

This is a part of a project by my team at VandyHacks Hackathon in 2015. The game begins by a player starting a server. Other players can join the hosted server until about 10 people have joined in. The only restriction is that players will be allowed to join only if they are within a reasonable distance from each other (200-300 meters). When the game begins, the player list is randomly reordered, so that each person will have a random target they have to "kill" and each person will have exactly one "assassin" trying to kill them. The players are then taken to a screen showing a compass, which guides them to their target. They do not know their target's location or distance from them, only the relative direction. As the assassing moves within 50 meters of the target, the compass disappears and is replaced by a number showing the distance from the target. As the assassin comes within 10 meters, the Kill button becomes enabled, and the player can press it to "kill" their target. They are then given their target's target. The game continues until only 1 player remains, and they win the game.
This part of the project, implemented by me, is at an experimental phase. Currently, it generates a random target within 50-100 meters away from the user, and simulates the guiding and killing process as it will be during the actual game.
To run the app on your phone, connect it with a USB, start up Android Studio, load up the project and run it to the phone.
