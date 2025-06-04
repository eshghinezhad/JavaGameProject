package Millionaire;

public class Game {
	private Player player;

	private QuestionBank questionBank;
	private Round[] rounds;

	public Game(Player player) {
		this.player = player;
		this.questionBank = new QuestionBank();

		// Select questions based on difficulty
		questionBank.selectQuestionsForGame(player.isEasyMode());

		// Create rounds with proper prize structures
		if (player.isEasyMode()) {
			// Easy mode: 9 questions total
			rounds = new Round[3];
			double[] round1Prizes = { 100, 500, 1000 };
			double[] round2Prizes = { 8000, 16000, 32000 };
			double[] round3Prizes = { 125000, 500000, 1000000 };

			rounds[0] = new Round(1, 3, round1Prizes, questionBank, player);
			rounds[1] = new Round(2, 3, round2Prizes, questionBank, player);
			rounds[2] = new Round(3, 3, round3Prizes, questionBank, player);
		} else {
			// Hard mode: 15 questions total
			rounds = new Round[3];
			double[] round1Prizes = { 100, 200, 300, 500, 1000 };
			double[] round2Prizes = { 2000, 4000, 8000, 16000, 32000 };
			double[] round3Prizes = { 64000, 125000, 250000, 500000, 1000000 };

			rounds[0] = new Round(1, 5, round1Prizes, questionBank, player);
			rounds[1] = new Round(2, 5, round2Prizes, questionBank, player);
			rounds[2] = new Round(3, 5, round3Prizes, questionBank, player);
		}
	}

	public void start() {
		System.out
				.println("\n\t\tWelcome " + player.getName().toUpperCase() + " to Who Wants To Be A Millionaire Game!");

		for (int i = 0; i < rounds.length; i++) {
			Round currentRound = rounds[i];

			boolean roundResult = currentRound.playRound();

			// If player answered incorrectly
			if (!roundResult && player.getCurrentMoney() == 0) {
				gameOver();
			}

			// If player walked away
			if (roundResult) {
				System.out.println("\t\t\t __________________________________");
				System.out.println("\t\t\t|          Congratulations!        |");
				System.out.printf("\t\t\t   You walked away with $%.0f  \n", player.getCurrentMoney());
				System.out.println("\t\t\t|__________________________________|");
				System.exit(0);
			}

			// If this is not the last round, prepare for next round
			if (i < rounds.length - 1) {
				System.out.println("===========================================================");
			}
		}

		// If player completes all rounds without walking away
		System.out.println("\t\t\t _________________________________");
		System.out.println("\t\t\t|         Congratulations!        |");
		System.out.println("\t\t\t|            You Won              |");
		System.out.printf("\t\t\t|          $%.0f            |\n", player.getCurrentMoney());
		System.out.println("\t\t\t|_________________________________|");
		System.exit(0);
	}

	private void gameOver() { // When the answer was wrong
		System.out.println("\nSorry " + player.getName().toUpperCase() + ", that's not the correct answer.");
		System.out.println("\t\t _______________________");
		System.out.println("\t\t|       GAME OVER!      |");
		System.out.println("\t\t|_______________________|");
		System.exit(0);
	}
}