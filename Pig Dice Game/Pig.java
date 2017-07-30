import java.util.*;

public class Pig {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Random rando = new Random();
		int turnCount = 1, turn = 0, roll = 0, playerTotal = 0, cpuTotal = 0, choice = 0;
		int tempTotal;
		randomNumber player = new randomNumber();
		randomNumber cpu = new randomNumber();
		pigTotals piggy = new pigTotals();
		
		System.out.printf("Welcome to the Pig game!%n" + "How to play: %n" + 
						  "Each player rolls a dice.  If it lands on 2-6 that total is given to the player." + 
						  "If it lands on 1, the player's turn ends and the total points that aren't%n banked are lost. " +
						  "After each roll, the player has an option to bank the points and end their%n turn or roll again." +
						  "First player to 100 wins.%n%n");
		while(true) {
			turn = turnCount % 2;
			tempTotal = 0;
			if (turn == 1) {
				System.out.printf("%nPlayers Turn!%n" + "------------%n" + "Player Total: %d%n", playerTotal);
				while(true) {
					roll = player.getRoll();
					tempTotal += roll;
					System.out.printf("Roll: %d%n", roll);
					if (roll == 1) {
						System.out.printf("Turn Ended!%n");
						break;
					}
					System.out.printf("Turn Total: %d%n", tempTotal);
					System.out.printf("%nType 1 to roll again or 2 bank your turn total and end your turn: ");
					choice = input.nextInt();
					System.out.printf("%n");
					if (choice == 1) 
						continue;
					if (choice == 2) {
						playerTotal += tempTotal;
						System.out.printf("Turn Ended!%n");
						break;
					}
				}
			}
			if (turn == 0) {
				System.out.printf("%nCPU Turn!%n" + "------------%n" + "CPU Total: %d%n", cpuTotal);
				while(true) {
					roll = cpu.getRoll();
					tempTotal += roll;
					System.out.printf("Roll: %d%n", roll);
					if (roll == 1) {
						System.out.printf("Turn Ended!%n");
						break;
					}
					System.out.printf("Turn Total: %d%n", tempTotal);
					choice = cpu.getCpuChoice();
					System.out.printf("CPU Choice: %d%n%n", choice);
					if (choice == 1)
						continue;
					if (choice == 2) {
						cpuTotal += tempTotal;
						System.out.printf("Turn Ended!%n");
						break;
					}
				}
			}
			turnCount += 1;
			if (playerTotal >= 100 || cpuTotal >= 100)
				break;
		}
		piggy.totals(turnCount, playerTotal, cpuTotal);
	}
}

class randomNumber {
	Random num = new Random();
	public int getRoll() {
		int number = 0;
		number = ((num.nextInt(Integer.MAX_VALUE) + 1) % 6 + 1);
		return number;
	}
	public int getCpuChoice() {
		int choice = 0;
		choice = ((num.nextInt(Integer.MAX_VALUE) + 1) % 2 + 1);
		return choice;
	}
}

class pigTotals {
	void totals(int turn, int player, int cpu) {
		System.out.printf("%nTotal Turns: %d%n", turn);
		System.out.printf("Player Total: %d%n", player);
		System.out.printf("CPU Total: %d%n", cpu);
		if (player > cpu)
			System.out.printf("%nPlayer Wins!");
		if (player < cpu)
			System.out.printf("%nCPU Wins!");
	}
}