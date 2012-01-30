package gamers.associate.Slime.levels.generator;

public class BlocHardInit
{
  public static void InitHardCoded()
  {
    BlocDefinitionHardCodedStart.StartDirection = BlocDirection.Top;
    new BlocDefinitionHardCodedStart();
    BlocDefinitionHardCodedStart.StartDirection = BlocDirection.Right;
    new BlocDefinitionHardCodedStart();
    BlocDefinitionHardCodedStart.StartDirection = BlocDirection.Bottom;
    new BlocDefinitionHardCodedStart();
    BlocDefinitionHardCodedStart.StartDirection = BlocDirection.Left;
    new BlocDefinitionHardCodedStart();
    BlocDefinitionHardCodedEnd.EndDirection = BlocDirection.Top;
    new BlocDefinitionHardCodedEnd();
    BlocDefinitionHardCodedEnd.EndDirection = BlocDirection.Right;
    new BlocDefinitionHardCodedEnd();
    BlocDefinitionHardCodedEnd.EndDirection = BlocDirection.Bottom;
    new BlocDefinitionHardCodedEnd();
    BlocDefinitionHardCodedEnd.EndDirection = BlocDirection.Left;
    new BlocDefinitionHardCodedEnd();
    new BlocDefinitionHardCodedAll();
    new BlocDefinitionHardCodedBoss();
  }
}

/* Location:           C:\projects\slimedecomp\slime-dex2jar.jar
 * Qualified Name:     gamers.associate.Slime.levels.generator.BlocHardInit
 * JD-Core Version:    0.6.0
 */