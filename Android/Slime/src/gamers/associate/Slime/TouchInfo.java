package gamers.associate.Slime;

import org.cocos2d.types.CGPoint;

public class TouchInfo {
	private CGPoint moveBeganAt;
	private CGPoint lastMoveReference;
	private float lastMoveTime;
	private CGPoint lastMoveDelta;
	private boolean isMoving;
		
	private int pointerId;

	public TouchInfo(int pointerId) {
		this.pointerId = pointerId;
		this.moveBeganAt = new CGPoint();
		this.lastMoveReference = new CGPoint();
		this.lastMoveDelta = new CGPoint();
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
	public float getLastMoveTime() {
		return lastMoveTime;
	}

	/**
	 * @param lastMoveTime the lastMoveTime to set
	 */
	public void setLastMoveTime(float lastMoveTime) {
		this.lastMoveTime = lastMoveTime;
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
}
