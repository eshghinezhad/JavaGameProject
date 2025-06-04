package Millionaire;

public class Player {
	private String name;
	private boolean isEasyMode;

	private double currentMoney;
	private boolean fiftyFiftyUsed;
	private boolean askAudienceUsed;
	private boolean phoneFriendUsed;

	public Player(String name, boolean isEasyMode) {
		this.name = name;
		this.isEasyMode = isEasyMode;

		this.currentMoney = 0;
		this.fiftyFiftyUsed = false;
		this.askAudienceUsed = false;
		this.phoneFriendUsed = false;
	}

	public String getName() {
		return name;
	}

	public boolean isEasyMode() {
		return isEasyMode;
	}

	public double getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(double money) {
		this.currentMoney = money;
	}

	public boolean useFiftyFifty() {
		if (!fiftyFiftyUsed) {
			fiftyFiftyUsed = true;
			return true;
		}
		return false;
	}

	public boolean useAskAudience() {
		if (!askAudienceUsed) {
			askAudienceUsed = true;
			return true;
		}
		return false;
	}

	public boolean usePhoneFriend() {
		if (!phoneFriendUsed) {
			phoneFriendUsed = true;
			return true;
		}
		return false;
	}
}