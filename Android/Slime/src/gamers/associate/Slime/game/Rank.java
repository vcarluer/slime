package gamers.associate.Slime.game;

public enum Rank {
	Lock (0),
	None (1),
	Bronze (2),
	Silver (3),	
	Gold (4);
	
	private final int index;   

	Rank(int index) {
        this.index = index;
    }

    public int index() { 
        return index; 
    }
}
