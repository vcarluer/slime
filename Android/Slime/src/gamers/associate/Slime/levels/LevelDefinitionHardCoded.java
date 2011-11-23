package gamers.associate.Slime.levels;



public abstract class LevelDefinitionHardCoded extends LevelDefinition {
	
	protected LevelDefinitionHardCoded() {
		this.gamePlay = GamePlay.None;
		this.init();
	}
	
	protected void init() {
		this.initLevel();
	}		
	
	protected abstract void initLevel();
}
