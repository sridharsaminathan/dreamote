package project.dreamote.utils;

import project.dreamote.ActionConstants;
import project.dreamote.MainTabHostActivity;
import project.dreamote.Preferences;
import project.dreamote.R;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class EventHandler implements OnTouchListener, SensorEventListener, OnClickListener {
	private MainTabHostActivity parent;
	
	SensorManager sensorManager = null;
	
	private MouseEventCalculator mouseCalc;		// Helps calculate mouse actions
	private ScrollEventCalculator scrollCalc;	// Helps calculate scroll actions
	
	private Context context;
	
	public EventHandler(Context context, MainTabHostActivity parent) {
		this.context = context;
		
		this.parent = parent;
		
		scrollCalc = new ScrollEventCalculator(Preferences.getScrollSensitivity(context));
		mouseCalc = new MouseEventCalculator(Preferences.getMouseSensitivity(context));
		
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	}
	
	/**
	 * Register or unregister to listen to Gyro sensor changes
	 * @param set
	 */
	public void setGyroListener(boolean set) {
 		if(set) {
			sensorManager.registerListener(this, sensorManager.getDefaultSensor(
					Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		} else {
			sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(
					Sensor.TYPE_ACCELEROMETER));
		}
	}
	
	/**
	 * Called when activity resumes to update values
	 */
	public void onResume() {
		scrollCalc.setScrollSensitivity(Preferences.getScrollSensitivity(context));
		mouseCalc.setMouseSensitivity(Preferences.getMouseSensitivity(context));
	}
	
	/**
	 * Called when onTouch event is triggered.
	 * Calls different methods depending on what type of MotionEvent triggered onTouch
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return handleActionDown(v.getId(), event.getX(), event.getY());
		case MotionEvent.ACTION_MOVE:
			return handleActionMove(v.getId(), event.getX(), event.getY());
		case MotionEvent.ACTION_UP:
			return handleActionUp(v.getId(), event.getX(), event.getY());
		default:
			return false;
		}
	}
	
	/**
	 * Help method that determines what to do when MotionEvent of type Move is triggered
	 * 
	 * @param viewId The id of the view that triggered the event
	 * @param eventX The x-position of where the event was triggered
	 * @param eventY The y-position where the event was triggered
	 * 
	 * @return true if the event was taken care of, false otherwise to bubble the event
	 */
	private boolean handleActionMove(int viewId, float eventX, float eventY) {
		switch(viewId) {
		case R.id.mouse_scroll:
			if(scrollCalc.performScroll(eventY)) {
				int action = scrollCalc.calcScrollMove(eventY);
				for(int i = 0; i < scrollCalc.getScrollSensitivity(); i++)
					parent.sendMouseScroll(action);
			}
			return true;
		case R.id.mousepad:
			int[] moves = mouseCalc.calcMouseMove(eventX, eventY);
			if(moves[0] < MouseEventCalculator.MOUSE_MOVE_MEDIUM && moves[1] < MouseEventCalculator.MOUSE_MOVE_MEDIUM)
				sendMouseMoves(moves[0], moves[1]);
			else
				sendMouseMove(moves[0], moves[1]);
			return true;
		default:
			return false;
		}
	}

	/**
	 * Help method that determines what to do when MotionEvent of type Up is triggered
	 * 
	 * @param viewId The id of the view that triggered the event
	 * @param eventX The x-position of where the event was triggered
	 * @param eventY The y-position where the event was triggered
	 * 
	 * @return true if the event was taken care of, false otherwise to bubble the event
	 */
	private boolean handleActionUp(int viewId, float eventX, float eventY) {
		switch(viewId) {
		case R.id.left_mouse_btn:
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_RELEAS);
			return false;
		case R.id.right_mouse_btn:
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_RIGHT_RELEASE);
			return false;
		case R.id.mousepad:
			boolean value = !mouseCalc.performMouseClick(); // Determine if OnClick on mousepad will be performed
			mouseCalc.setXDiff(0);
			mouseCalc.setYDiff(0);
			return value;
		default:
			return false;
		}
	}
	
	/**
	 * Help method that determines what to do when MotionEvent of type Down is triggered
	 * 
	 * @param viewId The id of the view that triggered the event
	 * @param eventX The x-position of where the event was triggered
	 * @param eventY The y-position where the event was triggered
	 * 
	 * @return true if the event was taken care of, false otherwise to bubble the event
	 */
	private boolean handleActionDown(int viewId, float eventX, float eventY) {
		switch(viewId) {
		case R.id.mouse_scroll:
			scrollCalc.setLastScrollY(eventY);
			return true;
		case R.id.mousepad:
			mouseCalc.setMousepadDown(eventX, eventY);
			return false;
		case R.id.left_mouse_btn:
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_PRESS);
			return false;
		case R.id.right_mouse_btn:
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_RIGHT_PRESS);
			return false;
		default:
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.mousepad:
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_PRESS);
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_RELEAS);
			break;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		int[] moves = mouseCalc.calcMouseTiltMove(event.values[0], event.values[1]);
		sendMouseMoves(moves[0], moves[1]);
	}
	
	/**
	 * Sends a mouse movement to server program
	 * 
	 * @param moveX The number of pixels to move mouse pointer in x-position
	 * @param moveY The number of pixels to move mouse pointer in y-position
	 */
	private void sendMouseMove(int moveX, int moveY) {
		parent.sendMouseMove(moveX, moveY);
	}
	
	/**
	 * Sends a mouse movement to server program for each pixel to move
	 * 
	 * @param moveX The number of pixels to move mouse pointer in x-position
	 * @param moveY The number of pixels to move mouse pointer in y-position
	 */
	private void sendMouseMoves(int moveX, int moveY) {
		int xDir;
		int yDir;
		while(moveX != 0 || moveY != 0) {
			xDir = moveX < 0 ? -1 : moveX == 0 ? 0 : 1;
			yDir = moveY < 0 ? -1 : moveY == 0 ? 0 : 1;
			
			parent.sendMouseMove(xDir, yDir);
			moveX = moveX == 0 ? 0 : moveX - xDir;
			moveY = moveY == 0 ? 0 : moveY - yDir;
		}
	}
}
