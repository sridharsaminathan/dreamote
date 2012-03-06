package project.dreamote.utils;

public interface ActionConstants {
	public static final int ACTION_MOUSE_MOVE = 0x00;
	public static final int ACTION_MOUSE_RIGHT_PRESS = 0x01;
	public static final int ACTION_MOUSE_LEFT_PRESS = 0x02;
	public static final int ACTION_MOUSE_RIGHT_RELEASE = 0x03;
	public static final int ACTION_MOUSE_LEFT_RELEAS = 0x04;
	public static final int ACTION_EVENT_KEYBOARD = 0x05;
	public static final int ACTION_GET_OPEN_WINDOWS = 0x06;
	public static final int ACTION_BROADCAST = 0x07;
	public static final int ACTION_BROADCAST_REPLY = 0x08;
	public static final int ACTION_GET_OPEN_SUPPORTED_WDINOWS_REPLY = 0x09;
	public static final int ACTION_TEST_CONNECTION = 0x0A;
	public static final int ACTION_TEST_CONNECTION_REPLY = 0x0B;
	public static final int ACTION_SCROLL_UP = 0x0C;
	public static final int ACTION_SCROLL_DOWN = 0x0D;
	
	public static final int ACTION_SET_VOLUME = 0x0E;
	public static final int ACTION_GET_VOLUME = 0x0F;
	
	public static final int ACTION_SET_FOCUS_WINDOW = 0x10;
	public static final int ACTION_GET_OPEN_OTHER_WINDOWS_REPLY = 0x11;
	
	//Mediabuttons
	public static final int ACTION_MEDIA_PLAY_PAUSE = 0x12;
	public static final int ACTION_MEDIA_NEXT = 0x13;
	public static final int ACTION_MEDIA_PREVIOUS = 0x14;
	
	public static final int ACTION_MAXIMIZE_OR_RESTORE_WINDOW = 0x15;
	
	
	public static final int BROADCAST_PORT = 9050;
	public static final long BROADCAST_WAIT_FOR_REPLY_TIME = 1500;
}
