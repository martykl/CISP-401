import java.awt.*;
import java.util.*;
import javax.swing.*;

public class RobotGene extends JFrame {
	public RobotGene() {
		add(new GeneFunctions());
	}
	// static variables and arrays
	static int cycle, robotRun, turn, locX, locY, power, action, batteries;
	static int[][][] Robots = new int[200][243][6];
	static int[][] Grid = new int[12][12];
	static double[] TotalPerf = new double[1000];
	static int[] Perf = new int[200];
	static int[] Temp = new int[1];
	static int[][][] Temp2 = new int[1][243][6];
	// main 
	public static void main(String[] args) {
		robotDNA(); // calls method to fill DNA
		//printDNA(); - used to check the DNA filled correctly
		//while loop to run 300 cycles
		while(cycle < 300) {
			robotRun = 0;
			while(robotRun < 200) { // while loop to run through each 200 robots
				resetValues();
				resetBoard(Grid);
				setBoard();
				while(turn < 200) { // while loop to run 200 turns or end when power = 0
					grabLocation();
					action = getAction();
					runAction(action);
					if (power == 0) {
						Perf[robotRun] = batteries;
						break;
					}
					turn++;
				}
			robotRun++;
			}
			rankRobots();
			makeRobots();
			TotalPerf[cycle] = getAvg();
			printInfo(cycle);
			cycle++;
		}
		RobotGene frame = new RobotGene();
		frame.setTitle("Robot Genetics");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // Centers the frame
		frame.setSize(1000, 650);
		frame.setLocation(900, 50);
		frame.setVisible(true);
		GeneFunctions.Draw();
	}
	// method to add all dna possibilities
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
	} // END setGeneArray method
	// Method to reset static values
	public static void resetValues() {
		action = 6;
		locX = 0;
		locY = 0;
		power = 5;
		batteries = 0;
		turn = 0;
	}
	
	// Method to reset the board values
	public static void resetBoard(int[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				board[i][j] = 0;
			}
		}
	}
	
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
	}
	// Method to get action from robot
	public static int getAction() {
		int choice = 6;
		int i;
			for (i = 0; i < 243; i++){
				if(Robots[robotRun][i][0] == Grid[locY - 1][locX] && Robots[robotRun][i][1] == Grid[locY][locX + 1]
						&& Robots[robotRun][i][2] == Grid[locY + 1][locX] && Robots[robotRun][i][3] == Grid[locY][locX - 1]
						&& Robots[robotRun][i][4] == Grid[locX][locY]){
						choice = Robots[robotRun][i][5];
						return choice;
				}
			}
		return choice;
	}
	// method to pick a random location on the board
	public static void grabLocation() {
		Random randy = new Random();
		locX = ((randy.nextInt(Integer.MAX_VALUE) + 1) % 10) + 1;
		locY = ((randy.nextInt(Integer.MAX_VALUE) + 1) % 10) + 1;
	}
	//method to run the action after running getAction()
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
	}
	// method to get the average number of batteries picked up each cycle by the robots
	public static double getAvg() {
		double average = 0;
		int total = 0;
		for(int i = 0; i < 200; i++) {
			total = total + Perf[i];
		}
		average = (double)total / 200;
		return average;
	}
	// method to print the board if needed
	public static void printBoard() {
		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 12; j++) {
				System.out.printf("%d", Grid[i][j]);
			}
			System.out.println("");
		}
	}
	// static ints for making robots
	static int position = 0;
	static int count = 0;
	static int robo = 100;
	static int robot1 = 0;
	static int robot2 = 0;
	// method for making robots 
	public static void makeRobots() {
		robot1 = 0;
		robot2 = 0;
		robo = 100;
		count = 0;
		position = 0;
		while(robo < 200){
			position = 0;
			robot1 = (int)(Math.random() * 100);
			robot2 = (int)(Math.random() * 100);
			while(position < 243) {
				if ((Math.random() > .5)) {
					for (int j = 0 ; j < 6 ; j++) {
						Robots[robo][position][j] = Robots[robot1][position][j];	
					}
					position += 1;
				}else 
					for ( int j = 0 ; j < 6 ; j++){
						Robots[robo][position][j] = Robots[robot2][position][j];	
					}
				position += 1;
			} 
			  robo +=1;
		}
	}
	// method to rank the robots based on batteries collected
	public static void rankRobots() {
		for(int run = 0; run < 100; run++) {
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
	}
	// method to print info for each cycle
	public static void printInfo(int part) {
			System.out.println("Cycle: " + part);
			System.out.println("Total Batteries Collected: " + (TotalPerf[part]*200));
			System.out.println("Average Batteries Collected: " + TotalPerf[part]);
			System.out.println("");
			try {
				Thread.sleep(50);
				} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
				}
	}
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
	}
}
