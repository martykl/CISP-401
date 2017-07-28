import java.util.*; // Imports Java utilities class

public class ForestFire {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int turnCount = 0;
		int clearedCount, fireCount;
		char[][] Trees = new char[12][12]; // create 12x12 array
		char[][] Temp = new char[12][12];
		
		createForest(Trees); // calls method to create a 12x12 forest
		System.out.printf("How many fires are started?: ");
		int fires = input.nextInt();
		System.out.printf("What is the probability of the fire spreading? (XX%% 0-100%%): ");
		int Rate = input.nextInt(); 
		createFires(Trees, fires); // calls method to create randomly generated fires
		
		// main procession loop
		while(true) {
			fireCount = 0;
			clearedCount = 0;
			Random fireProb = new Random();
			copyArray(Trees, Temp);
			changeForest(Temp);
			for (int row = 0; row < Trees.length; row++) {
				for (int column = 0; column < Trees[row].length; column++) {
					if (Trees[row][column] == '^') {
						if (row == 0) {
							if (column == 0) {
								if (Trees[row+1][column] == '*' || Trees[row][column+1] == '*') {
									if(fireProb.nextDouble() <= Rate) {
										Trees[row][column] = '*';
									}
								}
							}
							else if (column != 11) { 
								if (Trees[row][column-1] == '*' || Trees[row+1][column] == '*' || Trees[row][column+1] == '*') {
									if (fireProb.nextInt(100) <= Rate) {
										Trees[row][column] = '*';
									}
								}
							}
							else if (Trees[row][column-1] == '*' || Trees[row+1][column] == '*') {
								if (fireProb.nextInt(100) <= Rate) {
									Trees[row][column] = '*';
								}
							}
						}
						else if (row != 11) {
							if (column == 0) {
								if (Trees[row-1][column] == '*' || Trees[row][column+1] == '*' || Trees[row+1][column] == '*') {
									if(fireProb.nextInt(100) <= Rate) {
										Trees[row][column] = '*';
									}
								}
							}
							else if (column != 11) { 
								if (Trees[row-1][column] == '*' || Trees[row][column-1] == '*' || Trees[row+1][column] == '*' || Trees[row][column+1] == '*') {
									if (fireProb.nextInt(100) <= Rate) {
										Trees[row][column] = '*';
									}
								}
							}
							else if (Trees[row-1][column] == '*' || Trees[row][column-1] == '*' || Trees[row+1][column] == '*') {
								if (fireProb.nextInt(100) <= Rate) {
									Trees[row][column] = '*';
								}
							}
						}
						else if (column == 0) {
								if (Trees[row-1][column] == '*' || Trees[row][column+1] == '*') {
									if(fireProb.nextInt(100) <= Rate) {
										Trees[row][column] = '*';
									}
								}
							}
							else if (column != 11) { 
								if (Trees[row][column-1] == '*' || Trees[row-1][column] == '*' || Trees[row][column+1] == '*') {
									if (fireProb.nextInt(100) <= Rate) {
										Trees[row][column] = '*';
									}
								}
							}
							else if (Trees[row][column-1] == '*' || Trees[row-1][column] == '*') {
								if (fireProb.nextInt(100) <= Rate) {
									Trees[row][column] = '*';
								}
							}
					}
					else {
						continue;
					}
				}
			}
			for (int i = 0; i < Trees.length; i++) {
				for (int j = 0; j < Trees[i].length; j++) {
					if (Temp[i][j] == '.') {
						Trees[i][j] = Temp[i][j];
						clearedCount++;
					}
					if (Trees[i][j] == '*') {
						fireCount++;
					}
				}
			}
			turnCount++;
			System.out.printf("Turn: %d%n", turnCount);
			printForest(Trees);
			System.out.printf("%n");
			if (fireCount < 1) {
				break;
			}	
		}
		double percentBurned = ((double) clearedCount / 144) * 100;
		System.out.printf("Forest Fire Aftermath: %n");
		printForest(Trees);
		System.out.printf("Percentage burned by fire: %.2f%%%n", percentBurned);
		System.out.printf("Number of steps required required: %d%n", turnCount);
	}
	
	public static void createForest(char array[][]) {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				array[i][j] = '^';
			}
		}	
	}
	
	public static void createFires(char fire[][], int flame) {
		Random rando = new Random();
		for (int i = 0; i < flame; i++) {
			int fire1 = rando.nextInt(10000000) % 12;
			int fire2 = rando.nextInt(10000000) % 12;
			fire[fire1][fire2] = '*';
		}
	}
	
	public static void changeForest(char change[][]) {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (change[i][j] == '*') {
					change[i][j] = '.';
				}
			}
		}	
	}
	
	public static void printForest(char array[][]) {
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 12; y++) {
				System.out.printf("%s", array[x][y]);
			}
			System.out.printf("%n");
		}
	}
	
	public static void copyArray(char array[][], char copy[][]) {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				copy[i][j] = array[i][j];
			}
		}
	}
}