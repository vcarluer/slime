package gamers.associate.Slime.game.achievements;

public class Achievement {
	private int code;
	private String name;
	private String description;
	private boolean achieved;
	
	public Achievement(int code, String name, String description) {
		this.setCode(code);
		this.setName(name);
		this.setDescription(description);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
}
