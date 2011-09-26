package gamers.associate.Slime.items.base;

public class Target extends GameItem implements ITrigerable {
	private String name;
	
	public Target(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void trigger(Object source, String data) {		
	}
}
