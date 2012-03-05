package project.dreamote.utils;

public class MouseEventCalculator {
	
	// Mouse movements smaller than this value should be considered as mouse clicks
	private static final int PAD_CLICK_DIFF = 7;
	
	// Different values used to determine different mouse move intervals for smoother mouse pointer movement
	public static final int MOUSE_MOVE_XSMALL = 3;
	public static final int MOUSE_MOVE_SMALL = 6;
	public static final int MOUSE_MOVE_MEDIUM = 10;
	public static final int MOUSE_MOVE_LARGE = 16;
	
	private float lastMouseX;		// Last known x-position of mouse movement
	private float lastMouseY;		// Last known y-position of mouse movement
	private float pressedMouseX;	// X-position of mousepad press
	private float pressedMouseY;	// Y-position of mousepad press
	
	// used to determine if movement is so small so that a mouse click should be performed
	private float xDiff;
	private float yDiff;
	
	private int mouseSensitivity;	// The sensitivity of the mousepad
	
	/**
	 * Construct new MouseEventCalculator
	 * 
	 * @param mouseSensitivity How sensitive the mouse movement is
	 */
	public MouseEventCalculator(int mouseSensitivity) {
		this.mouseSensitivity = mouseSensitivity;
	}
	
	/**
	 * Call when test if mouse click is performed
	 * 
	 * @return True if mouse click should be performed, false otherwise
	 */
	public boolean performMouseClick() {
		return xDiff < PAD_CLICK_DIFF && yDiff < PAD_CLICK_DIFF;  
	}
	
	/**
	 * Sets values when mousepad has been pressed
	 * 
	 * @param downX The x-position of pressed position
	 * @param downY The y-position of pressed position
	 */
	public void setMousepadDown(float downX, float downY) {
		pressedMouseX = lastMouseX = downX;
		pressedMouseY = lastMouseY = downY;
	}
	
	/**
	 * Calculates how far the mousepointer should move
	 * 
	 * @param newX The x-position where the mouse movement stopped
	 * @param newY The y-position where the mouse movement stopped
	 * 
	 * @return How far the mouse pointer should move ( [x-movement, y-movement] )
	 */
	public int[] calcMouseMove(float newX, float newY) {
		
		// calculate max diff in X and Y to determine what to do on action up
		float currXDiff = Math.abs(pressedMouseX-newX);
		float currYDiff = Math.abs(pressedMouseY-newY);
		xDiff = currXDiff > xDiff ? currXDiff : xDiff;
		yDiff = currYDiff > yDiff ? currYDiff : yDiff;
		
		// start calculate how far the mouse pointer should move
		int[] moves = new int[2];
		
		int moveX = -(int)((lastMouseX - newX));
		int moveY = -(int)((lastMouseY - newY));
		int sens;
		
		// use different intervals to create a smother mouse movement
		if(Math.abs(moveX) < MOUSE_MOVE_XSMALL && Math.abs(moveY) < MOUSE_MOVE_XSMALL) {
			sens = 1;
		} else if(Math.abs(moveX) < MOUSE_MOVE_SMALL && Math.abs(moveY) < MOUSE_MOVE_SMALL) {
			sens = mouseSensitivity/5;
		} else if(Math.abs(moveX) < MOUSE_MOVE_MEDIUM && Math.abs(moveY) < MOUSE_MOVE_MEDIUM) {
			sens = mouseSensitivity/4;
		} else if(Math.abs(moveX) < MOUSE_MOVE_LARGE && Math.abs(moveY) < MOUSE_MOVE_LARGE) {
		    sens = mouseSensitivity/3;
		} else
			sens = mouseSensitivity;
		
		sens = sens < 1 ? 1 : sens;
	    moves[0] = sens*moveX;
	    moves[1] = sens*moveY;
	    
	    lastMouseX = newX;
		lastMouseY = newY;
		
		return moves;
	}
	
	/**
	 * Calculate how far the mouse pointer should move on tilt movement
	 * 
	 * @param moveX The value of movement on x-axis
	 * @param moveY The value of movement on y-axis
	 * 
	 * @return How far the mouse pointer should move ( [x-movement, y-movement] )
	 */
	public int[] calcMouseTiltMove(float moveX, float moveY) {
		int[] moves = new int[2];
		
		// negative value to get correct mouse movement on computer
		moveX = -moveX;
		moveY = -moveY;
		
		// different intervals to get a smoother mouse pointer movement
		if(Math.abs(moveX) < MOUSE_MOVE_XSMALL && Math.abs(moveY) < MOUSE_MOVE_XSMALL) {
			moves[0] = (int)moveX;
			moves[1] = (int)moveY;
		} else {
			moves[0] = (int)(moveX*mouseSensitivity);
			moves[1] = (int)(moveY*mouseSensitivity);
		}
		return moves;
	}
	
	//get- and set-methods
	public void setXDiff(float value) { xDiff = value; }
	public void setYDiff(float value) { yDiff = value; }
	
	public float getLastMouseX() { return lastMouseX; }
	public float getLastMouseY() { return lastMouseY; }
	
	public void setMouseSensitivity(int value) { mouseSensitivity = value; }
}
