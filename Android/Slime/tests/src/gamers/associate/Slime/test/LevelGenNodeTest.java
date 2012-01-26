package gamers.associate.Slime.test;

import gamers.associate.Slime.levels.generator.LevelGenNode;
import junit.framework.TestCase;

public class LevelGenNodeTest extends TestCase {

	public void testCreate() {
		LevelGenNode node = new LevelGenNode();
		assertNotNull(node);
	}
}
