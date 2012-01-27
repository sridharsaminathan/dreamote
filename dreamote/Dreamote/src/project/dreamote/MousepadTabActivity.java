package project.dreamote;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MousepadTabActivity extends Activity implements OnTouchListener, OnClickListener {
	private static final int PAD_CLICK_DIFF = 7;
	
	private MainTabHostActivity parent;
	
	private View mousePad;			// The view acting as mousepad
	private Button leftMouseBtn; 	// The left mouse button
	private Button rightMouseBtn;	// The right mouse button
	
	private float oldX;
	private float oldY;
	private float pressedX;
	private float pressedY;
	private float maxXdiff;
	private float maxYdiff;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mousepad);
		
		parent = (MainTabHostActivity)this.getParent();
		findViews();
		setListeners();
	}
	
	private void findViews() {
    	mousePad = findViewById(R.id.mousepad);
    	leftMouseBtn = (Button)findViewById(R.id.left_mouse_btn);
    	rightMouseBtn = (Button)findViewById(R.id.right_mouse_btn);
    }
    
    private void setListeners() {
    	mousePad.setOnTouchListener(this);
    	mousePad.setOnClickListener(this);
    	leftMouseBtn.setOnTouchListener(this);
    	rightMouseBtn.setOnTouchListener(this);
    }
	
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
		if(viewId == mousePad.getId()) {
			parent.sendMouseMove(oldX, oldY, eventX, eventY);
			oldX = eventX;
			oldY = eventY;
			float xDiff = Math.abs(pressedX-eventX);
			float yDiff = Math.abs(pressedY-eventY);
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
//			int xDiff = (int)Math.abs(pressedX - eventX);
//			int yDiff = (int)Math.abs(pressedY - eventY);
			if(maxXdiff > PAD_CLICK_DIFF || maxYdiff > PAD_CLICK_DIFF) {
				return true;  // OnClick on mousepad will not be performed
			}
		}
	    return false;
	}
	
	private boolean handleActionDown(int viewId, float eventX, float eventY) {
		if(viewId == mousePad.getId()) {
			pressedX = oldX = eventX;
			pressedY = oldY = eventY;
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
		if(v.getId() == mousePad.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_PRESS);
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_RELEAS);
		}
	}
}
