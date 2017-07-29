Program that creates 100 robots for testing of performance based on a square board.  Each robot starts out with 10 energy and each move it makes reduces the energy by 1.  Hitting the wall results in an additional reduction of energy by 1. Batteries will be randomly placed on the board and also have randomly populated "DNA" that dictates the movement based on checking the 4 surrounding spaces.

After each iteration, the top 50 robots will continue and the bottom 50 will be destroyed.  The top 50 will then create a 2 new robots.  Robot A will use half of its DNA and combine its DNA With half of Robot B to created Robot C.  Robot A will then use the remaining half of its DNA and combine with the remaining half of Robot B to created Robot D.  This continues until there are 100 robots to start the next iteration.

Console outputs each iteration, total batteries collected by all robots, the average batteries collected per robot, the number of moves completed by each robot, and the average turns completed by each robot.

To run, use "java filename > file.txt" to show the entire console output by running the robot program.
