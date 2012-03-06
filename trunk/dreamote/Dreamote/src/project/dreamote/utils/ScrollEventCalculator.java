package project.dreamote.utils;


public class ScrollEventCalculator {
	private static final int SCROLL_TICK = 10;
	
	private float lastScrollY;
	
	private int scrollSensitivity;
	
	public ScrollEventCalculator(int scrollSensitivity) {
		this.scrollSensitivity = scrollSensitivity;
	}
	
	public boolean performScroll(float newY) {
		return Math.abs(lastScrollY-newY) > SCROLL_TICK;
	}
	
	public int calcScrollMove(float newY) {
		int action = lastScrollY > newY ?
				ActionConstants.ACTION_SCROLL_UP : ActionConstants.ACTION_SCROLL_DOWN;
		lastScrollY = newY;
		return action;
	}
	
	public void setLastScrollY(float value) { lastScrollY = value; }
	public void setScrollSensitivity(int value) { scrollSensitivity = value; }
	public int getScrollSensitivity() { return scrollSensitivity; }
}
