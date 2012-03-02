package project.dreamote;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class MousepadTabActivity extends Activity implements OnTouchListener, SensorEventListener, OnClickListener {
	private static final int PAD_CLICK_DIFF = 7;
	private static final int SCROLL_TICK = 150;
	
	private MainTabHostActivity parent;
	
	private View mousePad;			// The view acting as mousepad
	private View mouseScroll;		// The view acting as a mouse scroll
	private Button leftMouseBtn; 	// The left mouse button
	private Button rightMouseBtn;	// The right mouse button
	
	
	private float oldScrollY;
	
	private float oldMouseX;
	private float oldMouseY;
	private float pressedMouseX;
	private float pressedMouseY;
	private float maxXdiff;
	private float maxYdiff;
	
	private int scrollSensitivity;
	private int mouseSensitivity;
	
	SensorManager sensorManager = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mousepad);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		
		findViews();
		setListeners();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		scrollSensitivity = Preferences.getScrollSensitivity(this);
		mouseSensitivity = Preferences.getMouseSensitivity(this);
		
		setListeners();
		
		if(!ClientCommunication.isConnected(this) && Preferences.getShowEnableWifiPopup(this)){
			Toast toast = Toast.makeText(this, getString(R.string.enable_wifi_message), Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		
		if(Preferences.getTiltToSteer(this))
			setGyroListener(true);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		if(Preferences.getTiltToSteer(this))
			setGyroListener(false);
	}
	
	private void setGyroListener(boolean set) {
 		if(set) {
			sensorManager.registerListener(this, sensorManager.getDefaultSensor(
					Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
		} else {
			sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(
					Sensor.TYPE_ACCELEROMETER));
		}
	}
	
	private void findViews() {
		parent = (MainTabHostActivity)this.getParent();
    	mousePad = findViewById(R.id.mousepad);
    	mouseScroll = findViewById(R.id.mouse_scroll);
    	leftMouseBtn = (Button)findViewById(R.id.left_mouse_btn);
    	rightMouseBtn = (Button)findViewById(R.id.right_mouse_btn);
    }
    
    private void setListeners() {
    	mousePad.setOnTouchListener(this);
    	
    	if(Preferences.getTapToClick(this))
    		mousePad.setOnClickListener(this);
    	else
    		mousePad.setOnClickListener(null);
    	
    	mouseScroll.setOnTouchListener(this);
    	leftMouseBtn.setOnTouchListener(this);
    	rightMouseBtn.setOnTouchListener(this);
    }
    
    @Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return handleActionDown(v.getId(), event.getX(), event.getY());
		case MotionEvent.ACTION_MOVE:
			return handleActionMove(v.getId(), event.getX(), event.getY());
		case MotionEvent.ACTION_UP:
			boolean returnValue = handleActionUp(v.getId(), event.getX(), event.getY());
			maxXdiff = maxYdiff = 0;
			return returnValue;
		default:
			return false;
		}
	}
	
	private boolean handleActionMove(int viewId, float eventX, float eventY) {
		if(viewId == mouseScroll.getId()) {
			if(Math.abs(oldScrollY-eventY) > SCROLL_TICK/scrollSensitivity) {
				int action = oldScrollY > eventY ?
						ActionConstants.ACTION_SCROLL_UP : ActionConstants.ACTION_SCROLL_DOWN;
				parent.sendMouseScroll(action);
				oldScrollY = eventY;
			}
			return true;
		} else if(viewId == mousePad.getId()) {
//			parent.sendMouseMove(oldMouseX, oldMouseY, eventX, eventY);
//			sendMouseMoves(calcXDiff(oldMouseX, eventX), calcYDiff(oldMouseY, eventY));
			sendMouseMoves(oldMouseX, oldMouseY, eventX, eventY);
			oldMouseX = eventX;
			oldMouseY = eventY;
			
			// calculate max diff in X and Y to determine what to do on action up
			float xDiff = Math.abs(pressedMouseX-eventX);
			float yDiff = Math.abs(pressedMouseY-eventY);
			maxXdiff = xDiff > maxXdiff ? xDiff : maxXdiff;
			maxYdiff = yDiff > maxYdiff ? yDiff : maxYdiff;
			return true;
		}
		return false;
	}

	private boolean handleActionUp(int viewId, float eventX, float eventY) {
		if (viewId == leftMouseBtn.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_RELEAS);
		} else if (viewId == rightMouseBtn.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_RIGHT_RELEASE);
		} else if (viewId == mousePad.getId()) {
			if(maxXdiff > PAD_CLICK_DIFF || maxYdiff > PAD_CLICK_DIFF) {
				return true;  // OnClick on mousepad will not be performed
			}
		}
	    return false;
	}
	
	private boolean handleActionDown(int viewId, float eventX, float eventY) {
		if (viewId == mouseScroll.getId()) {
			oldScrollY = eventY;
			return true;
		} else if(viewId == mousePad.getId()) {
			pressedMouseX = oldMouseX = eventX;
			pressedMouseY = oldMouseY = eventY;
			return false;
		} else if (viewId == leftMouseBtn.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_PRESS);
			leftMouseBtn.performClick();
			return false;
		} else if (viewId == rightMouseBtn.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_RIGHT_PRESS);
			return false;
		} else
			return false;
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
		int moveX = -(int)event.values[0];
		int moveY = -(int)event.values[1];
		if(Math.abs(moveX) < 3 && Math.abs(moveY) < 3)
			sendMouseMoves(moveX, moveY);
		else
			sendMouseMoves(mouseSensitivity*moveX, mouseSensitivity*moveY);
	}
	
	private void sendMouseMoves(float oldX, float oldY, float newX, float newY) {
		int diffX = -(int)((oldX - newX));
		int diffY = -(int)((oldY - newY));
		
		if(diffX >= 10 || diffY >=10) {
			Log.e("X Move", "" + diffX);
			Log.e("Y Move", "" + diffY);
		}
		
		if(Math.abs(diffX) < 3 && Math.abs(diffY) < 3)
			sendMouseMoves(diffX, diffY);
		else if(Math.abs(diffX) < 6 && Math.abs(diffY) < 6) {
			int sens = mouseSensitivity/5;
		    sens = sens < 1 ? 1 : sens;
			sendMouseMoves(sens*diffX, sens*diffY);
		} else if(Math.abs(diffX) < 10 && Math.abs(diffY) < 10) {
			int sens = mouseSensitivity/4;
		    sens = sens < 1 ? 1 : sens;
			sendMouseMoves(sens*diffX, sens*diffY);
		} else if(Math.abs(diffX) < 15 && Math.abs(diffY) < 15) {
		    int sens = mouseSensitivity/3;
		    sens = sens < 1 ? 1 : sens;
			sendMouseMoves(sens*diffX, sens*diffY);
		} else
			parent.sendMouseMove(mouseSensitivity*diffX, mouseSensitivity*diffY);
	}
	
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
