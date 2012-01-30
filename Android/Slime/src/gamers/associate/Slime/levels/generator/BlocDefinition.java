package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public abstract class BlocDefinition
{
  public static int BlocHeight;
  public static int BlocWidth;
  public static int Default_Bloc_Height;
  public static int Default_Bloc_Width = 600;
  protected int currentXOffset;
  protected int currentYOffset;
  protected LevelGenNode genNode;

  static
  {
    Default_Bloc_Height = 600;
    BlocWidth = Default_Bloc_Width;
    BlocHeight = Default_Bloc_Height;
  }

  public BlocDefinition()
  {
    initGenNode();
  }

  public BlocDefinition(int width, int height)
  {
    this();
    BlocWidth = width;
    BlocHeight = height;
  }

  private final void initGenNode()
  {
    LevelGenNode localLevelGenNode = new LevelGenNode();
    localLevelGenNode.setComplexity(getComplexity());
    initGenNodeInternal(localLevelGenNode);
    setGenNode(localLevelGenNode);
    SlimeFactory.LevelGenerator.addNode(localLevelGenNode);
  }

  public abstract void buildLevel(Level level, int xOffset, int yOffset);

  protected abstract int getComplexity();

  protected float getX(float x)
  {
    return getX(x, this.currentXOffset);
  }

  protected float getX(float x, float xOffset)
  {
    return x + xOffset * BlocWidth;
  }

  protected float getY(float y)
  {
    return getY(y, this.currentYOffset);
  }

  protected float getY(float y, float yOffset)
  {
    return y + yOffset * BlocHeight;
  }

  protected abstract void initGenNodeInternal(LevelGenNode genNode);

  public void setGenNode(LevelGenNode genNode)
  {
    this.genNode = genNode;
    this.genNode.setBlocDefinition(this);
  }

  protected void setOffset(int xOffset, int yOffset)
  {
    this.currentXOffset = xOffset;
    this.currentYOffset = yOffset;
  }
}