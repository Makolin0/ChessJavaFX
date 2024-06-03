# Engineer Degree Project
I'm trying to create an app that shows you current state of a physical chess board, on a monitor by using javaFX.
## Board
Board detects pieces by having two steel plates on each square, and piece having a plate attached to the bottom, so when it's placed, the circut is closed.
Changes are detected by Arduino microcontroller and written on serial port.
## Java
Java program displays board, and updates it accordingly to read serial port, checking if the move is legal, and looking for checks.
It saves every move, every game to a database with a posibility to recreate the game and see your mistakes.
