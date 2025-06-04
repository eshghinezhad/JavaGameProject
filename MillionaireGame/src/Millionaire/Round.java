package Millionaire;

import java.util.Scanner;

public class Round {
	private int roundNumber;
	private int questionsPerRound;
	private double[] prizeMoney;
	private QuestionBank questionBank;
	private Player player;
	private Scanner scanner;
	private Question currentQuestion;

	public Round(int roundNumber, int questionsPerRound, double[] prizeMoney, QuestionBank questionBank,
			Player player) {
		this.roundNumber = roundNumber;
		this.questionsPerRound = questionsPerRound;
		this.prizeMoney = prizeMoney;
		this.questionBank = questionBank;
		this.player = player;
		this.scanner = new Scanner(System.in);
		this.currentQuestion = null;
	}

	public boolean playRound() {
		System.out.println("\n\t\t\t ======      Round " + roundNumber + "     ======");

		// Ask all questions
		for (int i = 0; i < questionsPerRound; i++) {
			System.out.println("Great");
			System.out.println("\nQuestion " + (i + 1) + " of " + questionsPerRound + " in Round " + roundNumber);

			currentQuestion = questionBank.getNextQuestion();
			if (currentQuestion == null) {
				System.out.println("Error: No more questions available!");
				return false;
			}

			currentQuestion.setPrizeMoney(prizeMoney[i]);
			currentQuestion.displayQuestion();

			if (!askQuestion(currentQuestion)) {
				player.setCurrentMoney(0); // Reset money if wrong answer
				return false;
			}
			player.setCurrentMoney(prizeMoney[i]);
		}

		// After all questions are answered correctly
		if (roundNumber < 3) { // Not the final round
			System.out.println("\n\t\t **Congratulations! You've completed Round " + roundNumber + "!**");
			if (askToWalkAway()) {
				return true; // Player walked away
			}
			System.out.println("\n** Great! You're moving on to Round " + (roundNumber + 1) + "! **");
			return false; // Continue to next round
		}

		// Final round completed
		if (roundNumber == 3) {
			System.out.println("\n\t **********    You've completed the final round!    **********");
		}
		return false;
	}

	private boolean askQuestion(Question question) {

		if (player.isEasyMode() || (!player.isEasyMode() && roundNumber > 1)) {
			System.out.print("5. Use 50/50" + "      ");
			System.out.print("6. Ask the Audience" + "      ");
			System.out.println("7. Phone a Friend");
		}

		while (true) {
			System.out.println("------------------------------------------------------------------------------------");
			System.out.print("For your answer select (1-4) ");
			if (player.isEasyMode() || (!player.isEasyMode() && roundNumber > 1)) {
				System.out.print(" and for using a lifeline select (5-7) ");
			}

			int maxChoice = (player.isEasyMode() || (!player.isEasyMode() && roundNumber > 1)) ? 7 : 4;
			int choice = getValidChoice(1, maxChoice);

			if (choice >= 5) {
				if (!handleLifeline(choice, question)) {
					continue;
				}
			} else {
				if (confirmAnswer(choice)) {
					return choice == question.getCorrectAnswer();
				}
			}
		}
	}

	// --------------------------------------------------------------------------------------
	// lifelines
	private boolean handleLifeline(int choice, Question question) {
		switch (choice) {
		case 5:
			if (player.useFiftyFifty()) {
				fiftyFifty(question);
				return true;
			}
			System.out.println("50/50 has already been used!");
			return false;
		case 6:
			if (player.useAskAudience()) {
				simulateAskAudience(question);
				return true;
			}
			System.out.println("Ask the Audience has already been used!");
			return false;
		case 7:
			if (player.usePhoneFriend()) {
				simulatePhoneFriend(question);
				return true;
			}
			System.out.println("Phone a Friend has already been used!");
			return false;
		default:
			return false;
		}
	}

	private void simulateAskAudience(Question question) { // --------------- Audience ---------------
		int sum = 0;
		System.out.println("\nAsk Audience Lifeline activated - Audience Results are: \n");
		int correctAnswer = question.getCorrectAnswer();
		for (int i = 0; i < 3; i++) {
			int percentage = (i + 1 == correctAnswer) ? 45 : (int) (Math.random() * 19);
			sum += percentage;
			System.out.print((i + 1) + ". " + percentage + "% \t|\t");
		}
		System.out.println((4) + ". " + (99 - sum) + "%");

	}

	private void simulatePhoneFriend(Question question) { // ----------------- Friend ---------------
		System.out.println("\nYour friend says:");

		System.out.println("I'm " + (int) (Math.random() * 30 + 60) + "% sure it's "
				+ question.getOptions()[question.getCorrectAnswer() - 1]);

	}

	private void fiftyFifty(Question question) { // ----------------- 50/50 ----------
		System.out.println("\n\t50/50 Lifeline activated and Two incorrect answers removed \n");

		int correctAnswer = question.getCorrectAnswer();
		int[] remainingOptions = new int[2];
		remainingOptions[0] = correctAnswer;

		// Select one random wrong answer to keep
		int wrongAnswer;
		do {
			wrongAnswer = (int) (Math.random() * 4) + 1;
		} while (wrongAnswer == correctAnswer);

		remainingOptions[1] = wrongAnswer;

		// Display remaining options
		for (int i = 0; i < 4; i++) {
			boolean isRemaining = false;
			for (int j = 0; j < 2; j++) {
				if (i + 1 == remainingOptions[j]) {
					isRemaining = true;
					break;
				}
			}
			if (isRemaining) {
				System.out.print((i + 1) + ". " + question.getOptions()[i] + "\t|\t");
			} else {
				System.out.print((i + 1) + ". ___\t|\t");
			}
		}
		System.out.println();

	}

	private boolean askToWalkAway() {
		System.out.printf("\n\t\t Would you like to walk away with $%.0f? (y/n) ", player.getCurrentMoney());
		return scanner.nextLine().toLowerCase().startsWith("y");
	}

	private boolean confirmAnswer(int choice) {
		System.out.print("\nIs " + choice + " your final answer? (y/n)");
		return scanner.nextLine().toLowerCase().startsWith("y");
	}

	private int getValidChoice(int min, int max) {
		while (true) {
			try {
				int choice = scanner.nextInt();
				scanner.nextLine();

				// Check if the number is within valid range
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
}