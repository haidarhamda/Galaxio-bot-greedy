Greedy implementation for bot in “Galaxio”

## General Information

Galaxio is a battle royale game that pits your ship bot against several other ship bots. Each player will have a ship bot, and the objective of the game is to keep your ship bot alive until the end. In order to win the match, each bot must implement specific strategies to secure victory in the game.

This is a simple program in Java that implements the Greedy Algorithm in the "Galaxio" game application. We have chosen three algorithmic strategies based on priority: Escaping, Chasing, and Fetching. The ship will try to "escape" from various threats that endanger it. Once the danger is gone, our ship will start attacking enemy ships. If our ship is in a disadvantaged position (such as being smaller than the enemy ship) and there is no immediate danger on a given tick, the ship will attempt to feed on food as much as possible.

## Penjelasan Singkat Algoritma Greedy yang Diimplementasikan
The goal of each player in the Galaxio game is to survive until they become the last remaining player. There are various ways to be the last survivor, such as shooting other players, focusing on finding food, and so on. In this program, the bot will focus on finding food, but when there are other players that can be shot or chased, the bot will shoot or chase those players.

## Prerequisites
* Java versi 11 atau lebih
* NodeJS
* .Net Core 3.1
* Apache Maven
* Download starter pack.zip Galaxio: https://github.com/EntelectChallenge/2021-Galaxio/releases/tag/2021.3.2

## Build and Run Program
1. Extract the starter pack into the desired folder.
2. Clone this repository.
3. Open the terminal inside the cloned folder.
4. Run the command `mvn clean package`.
5. Go to the starter-pack folder.
6. Edit the run.bat file in the starter-pack folder if necessary, then run the .bat file.
7. Once finished, click on Galaxio.exe in the visualizer folder to view the Galaxio game animation.
8. Load the game with the latest .json file, which should be provided in the autofill.
9. Start the game.


## Author

- Muhammad Farrel Danendra Rachim (13521048)
- Haidar Hamda (13521105)
- Reza Pahlevi Ubaidillah (13521165)

