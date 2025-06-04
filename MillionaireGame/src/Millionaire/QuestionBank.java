package Millionaire;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class QuestionBank {
	private List<Question> allQuestions;
	private List<Question> gameQuestions;

	public QuestionBank() {
		allQuestions = new ArrayList<>();
		gameQuestions = new ArrayList<>();
		loadAllQuestions();
	}

	private void loadAllQuestions() { // Loads all questions from file into allQuestions list----------
		File file = new File("src/Millionaire/allQuestions.txt");
		try (Scanner read = new Scanner(file)) {
			// System.out.println("Start to Loading all questions "); // Debugging

			while (read.hasNextLine()) {
				String line = read.nextLine();

				Question question = splitLine(line);
				if (question != null) { // if the line format is invalid, the question will be null
					allQuestions.add(question);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error reading questions file: " + e.getMessage());
		}
	}

	// Converts a line from file into a Question object
	private Question splitLine(String line) {
		String[] parts = line.split("_"); // Split the line by underscore character
		if (parts.length == 7) {
			String questionText = parts[1];
			String[] options = new String[4];
			options[0] = parts[2]; // option 1
			options[1] = parts[3]; // option 2
			options[2] = parts[4]; // option 3
			options[3] = parts[5]; // option 4
			int correctAnswer = Integer.parseInt(parts[6]);

			return new Question(questionText, options, correctAnswer, 0);
		}
		return null;
	}

	// -------------Create (9-15) random unique number for questions-----------------
	private int[] selectRandomNumber(int count) {
		int[] qNumber = new int[count];
		int currentSize = 0;

		while (currentSize < count) {
			int randomIndex = (int) (Math.random() * allQuestions.size()) + 1; // Create a random number between 1 to 30

			// Check if this number is already in the array
			boolean isUnique = true;
			for (int i = 0; i < currentSize; i++) {
				if (qNumber[i] == randomIndex) {
					isUnique = false;
					break;
				}
			}
			// If unique, add it to the array
			if (isUnique) {
				qNumber[currentSize] = randomIndex;
				currentSize++;
			}
		}

		return qNumber;
	}

	// Select questions for the current game-----------------------------------------------------------
	public void selectQuestionsForGame(boolean isEasyMode) {
		gameQuestions.clear();
		int numQuestions = isEasyMode ? 9 : 15;
		int[] selectedQNumbers = selectRandomNumber(numQuestions);

		for (int qNum : selectedQNumbers) {
			Question question = allQuestions.get(qNum - 1); // get the specific question from the allQuestions list
			gameQuestions.add(question);
		}

		writeSelectedQuestionsToFile(); // for testing
	}

	// Write selected questions to file------ only for testing purposes
	public void writeSelectedQuestionsToFile() {
		try (PrintWriter writer = new PrintWriter("src/Millionaire/questions.txt")) {
			for (Question question : gameQuestions) {
				String[] options = question.getOptions();
				writer.println(question.getQuestionText() + "_" + options[0] + "_" + options[1] + "_" + options[2] + "_"
						+ options[3] + "_" + question.getCorrectAnswer() + "_" + question.getPrizeMoney());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error writing to questions.txt: " + e.getMessage());
		}
	}

	public Question getNextQuestion() {
		if (gameQuestions.isEmpty()) {
			return null;
		}
		return gameQuestions.remove(0); // remove the first question from the list (gameQuestions)
	}

	// Reset the game questions
	public void reset() {
		gameQuestions.clear();
	}

	// Get total number of questions
	public int getTotalQuestions() {
		return gameQuestions.size();
	}
}