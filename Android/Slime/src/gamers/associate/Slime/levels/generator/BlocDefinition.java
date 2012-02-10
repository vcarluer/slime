package gamers.associate.Slime.levels.generator;

import java.util.UUID;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.LevelDefinitionParser;

public abstract class BlocDefinition extends LevelDefinitionParser
{
  public static int BlocHeight;
  public static int BlocWidth;
  public static int Default_Bloc_Height = 480;
  public static int Default_Bloc_Width = 480;
  protected int currentXOffset;
  protected int currentYOffset;
  protected LevelGenNode genNode;
  private UUID blocId;
  
  public BlocDefinition(String resourceName, boolean noUserInfoStore) {	  
	  super(resourceName, noUserInfoStore);
	  this.genNode = new LevelGenNode();
	  this.blocId = UUID.randomUUID();
	  BlocWidth = Default_Bloc_Width;
	  BlocHeight = Default_Bloc_Height;
  }

  public abstract void buildLevel(Level level, int xOffset, int yOffset);

  public void setGenNode(LevelGenNode genNode)
  {
    this.genNode = genNode;
    this.genNode.setBlocDefinition(this);
  }
  
  public LevelGenNode getGenNode() {
	  return this.genNode;
  }

  protected void setOffset(int xOffset, int yOffset)
  {
    this.currentXOffset = xOffset;
    this.currentYOffset = yOffset;
  }

public UUID getBlocId() {
	return blocId;
}

public void setBlocId(UUID blocId) {
	this.blocId = blocId;
}
}