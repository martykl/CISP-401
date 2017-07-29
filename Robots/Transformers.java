import java.util.*; // imports java util

public class Transformers {
	// static variables and arrays
	static int cycle, robotRun, turn, locX, locY, power, action, batteries;
	static double averageTurns = 0;
	static int[][][] Robots = new int[200][243][6];
	static int[][] Grid = new int[12][12];
	static double[] TotalPerf = new double[1000];
	static int[] Perf = new int[200];
	static int[] Temp = new int[1];
	static int[][][] Temp2 = new int[1][243][6];
	static int[] Turns = new int[200];
	static double[] TotalTurns = new double[1000];
	static int[] tempTurns = new int[1];
	// main 
	public static void main(String[] args) {
		robotDNA(); // calls method to fill DNA
		//printDNA(); - used to check the DNA filled correctly
		//while loop to run 1000 cycles
		while(cycle < 400) {
			robotRun = 0;
			while(robotRun < 200) { // while loop to run through each 200 robots
				resetValues();
				resetBoard(Grid);
				setBoard();
				while(turn < 200) { // while loop to run 200 turns or end when power = 0
					grabLocation();
					action = getAction();
					runAction(action);
					Turns[robotRun] = turn;
					Perf[robotRun] = batteries;
					if (power < 1) { // if robot runs out of power it stores the current turn's information
						Perf[robotRun] = batteries;
						Turns[robotRun] = turn;
						break;
						}
				turn++;
				}
			robotRun++;
		}
			rankRobots();
			makeRobots();
			TotalPerf[cycle] = getAvg();
			TotalTurns[cycle] = averageTurns;
			printInfo(cycle+1);
			//printDNA();
			cycle++;
			//printRanking();
		}
	} // end of main
	// method to add all dna possibilities
	// toughest part of the assignment to make sure each possible DNA sequence was accounted for
	// have to give credit to Jeffrey Carrick for his help, without him I'd still be stuck on this part
	public static void robotDNA() {
		Random choice = new Random();
		for(int k = 0; k < 200; k++) {
			for ( int i = 0; i < 243; i++){
				for ( int j = 0; j < 6; j++){
					Robots[k][i][j] = 0;
				}
			}
		}
		for(int robo = 0; robo < 200; robo++) {
			for ( int i = 0 ; i < 243 -1; i++){
				
				if (Robots[robo][i][4] < 2){
					Robots[robo][i + 1][4] = Robots[robo][i][4] + 1;
				}
				if (Robots[robo][i][4] > 1){
					Robots[robo][i + 1][4] = 0;
				}	
			}
			int count = 0;
			int value = 0;
			int ket = 0;
			
			while ( count < 242){
				value = 0;
				while ( value < 3){
					ket = 0;
					while ( ket < 3){
						Robots[robo][count][3] = value;
						ket = ket + 1;
						count = count + 1;
						if ( ket > 2 ){
							value = value + 1;
						}
					}
				}
			}
			count = 0;
			value = 0;
			ket = 0;
			
			while ( count < 242){
				value = 0;
				while ( value < 3){
					ket = 0;
					while ( ket < 9){
						Robots[robo][count][2] = value;
						ket = ket + 1;
						count = count + 1;
						if ( ket == 9 ){
							value = value + 1;
						}
					}
				}
			}
			count = 0;
			value = 0;
			ket = 0;
			
			while ( count < 242){
				value = 0;
				while ( value < 3){
					ket = 0;
					while ( ket < 27){
						Robots[robo][count][1] = value;
						ket = ket + 1;
						count = count + 1;
						if ( ket == 27 ){
							value = value + 1;
						}
					}
				}
			}
			count = 0;
			value = 0;
			ket = 0;
			
			while ( count < 242){
				value = 0;
				while ( value < 3){
					ket = 0;
					while ( ket < 81){
						Robots[robo][count][0] = value;
						ket = ket + 1;
						count = count + 1;
						if ( ket == 81 ){
							value = value + 1;
						}
					}
				}
			}
			for ( int i = 0; i < 243; i++){
				Robots[robo][i][5] = ((choice.nextInt(Integer.MAX_VALUE) + 1) % 6);
			}
		}
	} // End of DNA method
	// Method to reset static values
	public static void resetValues() {
		action = 6;
		locX = 0;
		locY = 0;
		power = 5;
		batteries = 0;
		turn = 0;
	} // end of resetValues method
	
	// Method to reset the board values
	public static void resetBoard(int[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				board[i][j] = 0;
			}
		}
	} // end of resetBoard method
	
	// Method to set the board
	public static void setBoard() {
		Random randy = new Random();
		int number;
		for(int row = 0; row < Grid.length; row++) {
			for(int column = 0; column < Grid[row].length; column++) {
				number = ((randy.nextInt(Integer.MAX_VALUE) + 1) % 2);
				if((row == 0) || (column == 0) || (row == 11) || (column == 11)) {
					Grid[row][column] = 2;
				}
				else if (number == 0) {
					Grid[row][column] = 0;
				}
				else if (number == 1) {
					Grid[row][column] = 1;
				}
			}
		}
	} // end of setBoard method
	
	// Method to get action from robot
	// This should be bigger block of code, but I decided to only allow my robot to move within the middle of the 12x12 board.
	// Because the robot never is on a wall section, this section of code never goes out of bounds when checking where the robot is.
	public static int getAction() {
		int choice = 6;
		int i;
			for (i = 0; i < 243; i++){
				if(Robots[robotRun][i][0] == Grid[locY - 1][locX] && Robots[robotRun][i][1] == Grid[locY][locX + 1]
						&& Robots[robotRun][i][2] == Grid[locY + 1][locX] && Robots[robotRun][i][3] == Grid[locY][locX - 1]
						&& Robots[robotRun][i][4] == Grid[locY][locX]){
						choice = Robots[robotRun][i][5];
						return choice;
				}
			}
		return choice;
	} // end of getAction Method
	
	// method to pick a random location on the board
	public static void grabLocation() {
		Random randy = new Random();
		locX = ((randy.nextInt(Integer.MAX_VALUE) + 1) % 10) + 1;
		locY = ((randy.nextInt(Integer.MAX_VALUE) + 1) % 10) + 1;
	} // end of grabLocation method
	
	//method to run the action after running getAction()
	//By only allowing the robot to move within the middle 121 squares of the 12x12 array it allows me to not worry about running into a wall.
	//The if statements that reduce power by 3 only happen if the choice will move it into a wall section; however, the robot doesn't move to that spot
	//It stays in the same spot and loses 3 power.
	public static void runAction(int act) {
		Random move = new Random();
		if (act == 0) {
			locX = locX;
			locY = locY;
		}
		else if(act == 1) {
			if(locY > 0) {
				if(Grid[locY-1][locX] == 2)
					power -= 3;
				else {
					locY -= 1;
					power -= 1;
				}
			}
		}
		else if(act == 2) {
			if(locX < 11) {
				if(Grid[locY][locX-1] == 2)
					power -= 3;
				else {
					locX -= 1;
					power -= 1;
				}
			}
		}
		else if(act == 3) {
			if(locY < 11) {
				if(Grid[locY+1][locX] == 2)
					power -= 3;
				else {
					locY += 1;
					power -= 1;
				}
			}
		}
		else if(act == 4) {
			if(locX > 0) {
				if(Grid[locY][locX-1] == 2)
					power -= 3;
				else {
					locX -= 1;
					power -= 1;
				}
			}
		}
		else if(act == 5) {
			act = ((move.nextInt(Integer.MAX_VALUE) + 1) % 7);
			runAction(act);
		}
		else if(act == 6) {
			if(Grid[locY][locX] == 1) {
				Grid[locY][locX] = 0;
				power += 5;
				batteries += 1;
			}
		}
	} // end of action method
	
	// method to get the average number of batteries picked up each cycle by the robots
	public static double getAvg() {
		double average = 0;
		int total = 0;
		int totalTurns = 0;
		averageTurns = 0;
		for(int i = 0; i < 200; i++) {
			total = total + Perf[i];
			totalTurns = totalTurns + Turns[i];
		}
		average = (double)total / 200;
		averageTurns = (double)totalTurns / 200;
		return average;
	} // end of getAvg method
	
	// method to print the board if needed
	public static void printBoard() {
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				System.out.printf("%d", Grid[i][j]);
			}
			System.out.println("");
		}
	} //end of printBoard method
	
	// static ints for making robots
	static int position = 0;
	static int count = 0;
	static int robo = 100;
	static int robot1 = 0;
	static int robot2 = 0;
	static int position2 = 0;
	// method for making robots 
	public static void makeRobots() {
		Random randy = new Random();
		robot1 = 1;
		robot2 = 0;
		robo = 100;
		count = 0;
		position = 0;
		position2 = 0;
		while(robo < 200){
			position = 0;
			robot1 = (randy.nextInt(Integer.MAX_VALUE) + 1) % 100;
			robot2 = (randy.nextInt(Integer.MAX_VALUE) + 1) % 100;
			while(position < 243) {
				if ((Math.random() > .5)) {
					Robots[robo][position][5] = Robots[robot1][position][5];	
					position += 1;
				}else { 
					Robots[robo][position][5] = Robots[robot2][position][5];	
					position += 1;
				}
			} 
			robo +=1;
		}
	} //end of makeRobots method
	
	// method to rank the robots based on batteries collected
	public static void rankRobots() {
		/*for(int tempRun = 0; tempRun < 200; tempRun++) {
			for(int dex = 0; dex < 199; dex++) {
				if(Turns[dex] < Turns[dex+1]) {
					tempTurns[0] = Turns[dex];
					Temp2[0] = Robots[dex];
					Turns[dex] = Turns[dex+1];
					Robots[dex] = Robots[dex+1];
					Turns[dex+1] = Temp[0];
					Robots[dex+1] = Temp2[0];
				}
			}
		}
		*/
		for(int run = 0; run < 200; run++) {
			for(int spot = 0; spot < 199; spot++) {
				if(Perf[spot] < Perf[spot+1]) {
					Temp[0] = Perf[spot];
					Temp2[0] = Robots[spot];
					Perf[spot] = Perf[spot+1];
					Robots[spot] = Robots[spot+1];
					Perf[spot+1] = Temp[0];
					Robots[spot+1] = Temp2[0];
				}
			}
		}
	} // end of rankRobots method
	
	// method to print info for each cycle
	public static void printInfo(int part) {
			System.out.println("Cycle: " + part);
			System.out.println("Total Batteries Collected: " + (int)(TotalPerf[part-1]*200));
			System.out.println("Average Batteries Collected: " + TotalPerf[part-1]);
			System.out.println("Total Turns Completed: " + (int)(TotalTurns[part-1]*200));
			System.out.println("Average Turns Completed: " + TotalTurns[part-1]);
			System.out.println("");
			try { // sleeps for 100 to slow down iteration and display info slower
				Thread.sleep(100);
				} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
				}
	} // end of printInfo cycle
	
	// prints DNA for entire robot series
	public static void printDNA() {
		Random choice = new Random();
		for(int robo = 0; robo < 200; robo++) {
			System.out.printf("Robot%d: ", robo);
			for(int dna = 0; dna < 243; dna++) {
				System.out.printf("DNA%d: ", dna);
				for(int rawr = 0; rawr < 6; rawr++) {
					System.out.printf("%d", Robots[robo][dna][rawr]);
					try {
					Thread.sleep(50);
					} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
					}
				}
				System.out.println("");
			}
		}
	} // end of printDNA method if needed
	// printRanking if needed
	public static void printRanking() {
		for(int i = 0; i < 200; i++) {
			System.out.printf("Performance:");
			System.out.println(Perf[i]);
			try {
				Thread.sleep(50);
				} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
				}
		}
	} // end of method
}