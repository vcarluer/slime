package gamers.associate.Slime.game.achievements;

import gamers.associate.Slime.layers.MessageLayer;

public abstract class Achievement {
	private String name;
	private String description;
	private boolean achieved;
	private boolean isEndLevel;
	
	public Achievement(String name, String description, boolean endLevel) {
		this.setName(name);
		this.setDescription(description);
		this.setEndLevel(endLevel);
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

	public boolean isAchieved() {
		return achieved;
	}

	public void setAchieved(boolean achieved) {
		this.achieved = achieved;
	}
	
	public void test() {
		if (this.testInternal()) {
			MessageLayer.get().show(this.getName());
		}
	}

	protected abstract boolean testInternal();

	public boolean isEndLevel() {
		return isEndLevel;
	}

	public void setEndLevel(boolean isEndLevel) {
		this.isEndLevel = isEndLevel;
	}
}
