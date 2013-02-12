package gamers.associate.SlimeAttack.levels;



public abstract class LevelTimeAttack extends LevelDefinitionHardCoded implements ITimeAttackLevel {
	
	@Override
	protected void init() {		
		super.init();
		this.setGamePlay(GamePlay.TimeAttack);		
	}
}
