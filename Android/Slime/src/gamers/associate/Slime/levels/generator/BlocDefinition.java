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

  public BlocDefinition(int paramInt1, int paramInt2)
  {
    this();
    BlocWidth = paramInt1;
    BlocHeight = paramInt2;
  }

  private final void initGenNode()
  {
    LevelGenNode localLevelGenNode = new LevelGenNode();
    localLevelGenNode.setComplexity(getComplexity());
    initGenNodeInternal(localLevelGenNode);
    setGenNode(localLevelGenNode);
    SlimeFactory.LevelGenerator.addNode(localLevelGenNode);
  }

  public abstract void buildLevel(Level paramLevel, int paramInt1, int paramInt2);

  protected abstract int getComplexity();

  protected float getX(float paramFloat)
  {
    return getX(paramFloat, this.currentXOffset);
  }

  protected float getX(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 + paramFloat2 * BlocWidth;
  }

  protected float getY(float paramFloat)
  {
    return getY(paramFloat, this.currentYOffset);
  }

  protected float getY(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 + paramFloat2 * BlocHeight;
  }

  protected abstract void initGenNodeInternal(LevelGenNode paramLevelGenNode);

  public void setGenNode(LevelGenNode paramLevelGenNode)
  {
    this.genNode = paramLevelGenNode;
    this.genNode.setBlocDefinition(this);
  }

  protected void setOffset(int paramInt1, int paramInt2)
  {
    this.currentXOffset = paramInt1;
    this.currentYOffset = paramInt2;
  }
}

/* Location:           C:\projects\slimedecomp\slime-dex2jar.jar
 * Qualified Name:     gamers.associate.Slime.levels.generator.BlocDefinition
 * JD-Core Version:    0.6.0
 */