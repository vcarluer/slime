package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.LevelDefinitionParser;

public abstract class BlocDefinition extends LevelDefinitionParser
{
  public static int BlocHeight;
  public static int BlocWidth;
  public static int Default_Bloc_Height = 400;
  public static int Default_Bloc_Width = 400;
  protected int currentXOffset;
  protected int currentYOffset;
  protected LevelGenNode genNode;

  public BlocDefinition()
  {	  
	  this.genNode = new LevelGenNode();
	  BlocWidth = Default_Bloc_Width;
	  BlocHeight = Default_Bloc_Height;    
  }
  
  public BlocDefinition(String resourceName, boolean noUserInfoStore) {
	  super(resourceName, noUserInfoStore);
  }

  public BlocDefinition(int width, int height)
  {
    this();
    BlocWidth = width;
    BlocHeight = height;
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
}