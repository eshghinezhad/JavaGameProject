package Millionaire;

public class Question {
	private String questionText;
	private String[] options;
	private int correctAnswer;
	private double prizeMoney;

	public Question(String questionText, String[] options, int correctAnswer, double prizeMoney) {
		this.questionText = questionText;
		this.options = options;
		this.correctAnswer = correctAnswer;
		this.prizeMoney = prizeMoney;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String[] getOptions() {
		return options;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public double getPrizeMoney() {
		return prizeMoney;
	}

	public void setPrizeMoney(double prizeMoney) {
		this.prizeMoney = prizeMoney;
	}

	public void displayQuestion() {
		System.out.printf("\n\t   %s ($ %.2f) \n", questionText, prizeMoney);
		System.out.println("\t_____________________________________________________________________\n");
		for (int i = 0; i < options.length; i++) {
			System.out.print((i + 1) + ". " + options[i] + "\t|\t");
		}
		System.out.println("\n------------------------------------------------------------------------------------");
	}

}