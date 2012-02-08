package project.dreamote;

public class MessageGenerator implements ActionConstants{
	
	private static final double MOUSE_MOVE_MULTI = 1;
	
	public static String createMouseMoveMessage(float oldX, float oldY, float newX, float newY) {
		int diffX = -(int)(MOUSE_MOVE_MULTI*(oldX - newX));
		int diffY = -(int)(MOUSE_MOVE_MULTI*(oldY - newY));
		return ACTION_MOUSE_MOVE + ";" + diffX + ";" + diffY*2;
	}
	
	public static String createMouseLeftBtnPress() {
		return "" + ACTION_MOUSE_LEFT_PRESS;
	}
	
	public static String createMouseRightBtnPress() {
		return "" + ACTION_MOUSE_RIGHT_PRESS;
	}
	
	public static String createMouseLeftBtnRelease() {
		return "" + ACTION_MOUSE_LEFT_RELEAS;
	}
	
	public static String createMouseRightBtnRelease() {
		return "" + ACTION_MOUSE_RIGHT_RELEASE;
	}
	
	public static String createKeyboardEvent(String key){
		return "" + ACTION_EVENT_KEYBOARD + ";" + key;
	}
	
	public static String createScrollUp(){
		return "" + ACTION_SCROLL_UP;
	}
	public static String createScrollDown(){
		return "" + ACTION_SCROLL_DOWN;
	}
	
	public static String createSetVolume(int volume){
		return "" + ACTION_SET_VOLUME + ";" + volume;
	}
	
}
