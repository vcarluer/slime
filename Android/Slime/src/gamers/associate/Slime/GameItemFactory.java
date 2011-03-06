package gamers.associate.Slime;


public abstract class GameItemFactory<T extends GameItem> {
	protected boolean isAttached;	
	protected Level level;	
	
	public void attach(Level level) {
		this.level = level;
		this.isAttached = true;
	}
	
	public void detach() {
		if (this.isAttached) {
			this.level = null;
			this.isAttached = false;
		}
	}
	
	public T create() {
		return this.create(0, 0);
	}
	
	public T create(float x, float y) {
		return this.create(x, y, 0, 0);
	}
		
	public T create(float x, float y, float width, float height) {
		if (this.isAttached) {			
			T item = this.instantiate(x, y, width, height);			
			this.level.addItemToAdd(item);			
			return item;
		}
		else
		{
			return null;
		}
	}
	
	protected abstract T instantiate(float x, float y, float width, float height);
}
