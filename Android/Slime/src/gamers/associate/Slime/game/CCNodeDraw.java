package gamers.associate.Slime.game;

import javax.microedition.khronos.opengles.GL10;

import org.cocos2d.nodes.CCNode;

public class CCNodeDraw extends CCNode {
	private Level level;
	
	public CCNodeDraw(Level level) {
		this.level = level;
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.nodes.CCNode#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {		
		super.draw(gl);
		this.level.draw(gl);
	}
}
