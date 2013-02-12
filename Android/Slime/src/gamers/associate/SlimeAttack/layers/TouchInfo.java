package gamers.associate.SlimeAttack.layers;

import org.cocos2d.types.CGPoint;

public class TouchInfo {
	private CGPoint moveBeganAt;
	private CGPoint lastMoveReference;
	private long lastMoveTime;
	private CGPoint lastMoveDelta;
	private boolean isMoving;
	private long firstMoveTime;
	private CGPoint firstMoveReference;
		
	private int pointerId;

	public TouchInfo(int pointerId) {
		this.pointerId = pointerId;
		this.moveBeganAt = new CGPoint();
		this.lastMoveReference = new CGPoint();
		this.lastMoveDelta = new CGPoint();
		this.firstMoveReference = new CGPoint();
	}
	
	/**
	 * @return the moveBeganAt
	 */
	public CGPoint getMoveBeganAt() {
		return moveBeganAt;
	}

	/**
	 * @param moveBeganAt the moveBeganAt to set
	 */
	public void setMoveBeganAt(CGPoint moveBeganAt) {
		this.moveBeganAt = moveBeganAt;
	}

	/**
	 * @return the lastMoveReference
	 */
	public CGPoint getLastMoveReference() {
		return lastMoveReference;
	}

	/**
	 * @param lastMoveReference the lastMoveReference to set
	 */
	public void setLastMoveReference(CGPoint lastMoveReference) {
		this.lastMoveReference = lastMoveReference;
	}

	/**
	 * @return the lastMoveTime
	 */
	public long getLastMoveTime() {
		return lastMoveTime;
	}

	/**
	 * @param lastMoveTime the lastMoveTime to set
	 */
	public void setLastMoveTime(long lastMoveTime) {
		this.lastMoveTime = lastMoveTime;
	}
	
	public void setFirstMoveTime(long firstMoveTime) {
		this.firstMoveTime = firstMoveTime;
	}
	
	public long getFirstMoveTime() {
		return this.firstMoveTime;
	}

	/**
	 * @return the lastMoveDelta
	 */
	public CGPoint getLastMoveDelta() {
		return lastMoveDelta;
	}

	/**
	 * @param lastMoveDelta the lastMoveDelta to set
	 */
	public void setLastMoveDelta(CGPoint lastMoveDelta) {
		this.lastMoveDelta = lastMoveDelta;
	}

	/**
	 * @return the isMoving
	 */
	public boolean isMoving() {
		return isMoving;
	}

	/**
	 * @param isMoving the isMoving to set
	 */
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	/**
	 * @return the hachCode
	 */
	public int getPointerId() {
		return pointerId;
	}

	/**
	 * @param pointerId the hachCode to set
	 */
	public void setPointerId(int pointerId) {
		this.pointerId = pointerId;
	}
	
	public void setFirstMoveReference(CGPoint reference) {
		this.firstMoveReference = reference;
	}
	
	public CGPoint getFirstMoveReference() {
		return this.firstMoveReference;
	}
}
