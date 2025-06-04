package Millionaire;

import java.util.Scanner;

public class MainTester {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Game game = null;
		try {
			while (true) {
				System.out.println("\n\n===================================================================");
				System.out.println("\t\t    Who Wants To Be A Millionaire");
				System.out.println("\t\t=====================================\n");
				System.out.print("1. Start the game\t | \t");
				System.out.print("2. View the rules of the game\t | \t");
				System.out.println("3. Exit the game");
				System.out.println("\t_______________________________________________________________\n");
				System.out.print("Please enter your choice (1 or 2 or 3): ");

				int choice = getValidChoice(scanner, 1, 3);

				switch (choice) {
				case 1:
					System.out.print("\nPlease Enter your name and press the enter: ");
					String playerName = scanner.nextLine();

					System.out.print("\nPlease select difficulty of the game(1 or 2) -->  ");
					System.out.print("1. Easy (9 questions)\t ");
					System.out.print("2. Hard (15 questions):  ");

					int difficulty = getValidChoice(scanner, 1, 2);
					Player player = new Player(playerName, difficulty == 1);
					game = new Game(player);
					game.start();
					break;
				case 2:
					displayRules(scanner);
					break;
				case 3:
					System.out.println("Thank you! Goodbye!");
					scanner.close();
					return;
				default:
					System.out.println("wrong number. Try again.");
				}
			}
		} catch (Exception e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());
		}
	}

	private static int getValidChoice(Scanner scanner, int min, int max) {
		while (true) {
			try {
				int choice = scanner.nextInt();
				scanner.nextLine();
				if (choice >= min && choice <= max) {
					return choice;
				}
				System.out.println("Please enter a number between " + min + " and " + max);
			} catch (Exception e) {
				System.out.println("Please enter a valid number");
				scanner.nextLine();
			}
		}
	}

	private static void displayRules(Scanner scanner) {
		System.out.println("\n -------- Game Rules --------");
		System.out.println("1. Choose one:  Easy Level (9 questions) or Hard Level (15 questions)");
		System.out.println("2. Answer questions correctly to win money");
		System.out.println("3. You can walk away after completing each round (except final round)");
		System.out.println("4. You have 3 lifelines: 50/50, Ask the Audience, Phone a Friend");
		System.out.println("5. Each lifeline can be used only once");
		System.out.println("6. Wrong answer means game over!");
		System.out.print("\nPress Enter to return to main menu...");
		scanner.nextLine();
	}
}
