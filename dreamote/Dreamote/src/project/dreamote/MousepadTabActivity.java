package project.dreamote;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MousepadTabActivity extends Activity implements OnTouchListener {
	private MainTabHostActivity parent;
	
	private View mousePad;			// The view acting as mousepad
	private Button leftMouseBtn; 	// The left mouse button
	private Button rightMouseBtn;	// The right mouse button
	
	private float oldX;
	private float oldY;
	int diffX;
	int diffY;
	
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
    	leftMouseBtn.setOnTouchListener(this);
    	rightMouseBtn.setOnTouchListener(this);
    }
	
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return handleActionDown(v.getId(), event.getX(), event.getY());
		case MotionEvent.ACTION_MOVE:
			boolean returnValue = handleActionMove(v.getId(), event.getX(), event.getY());
			return returnValue;
		case MotionEvent.ACTION_UP:
			return handleActionUp(v.getId());
		default:
			return false;
		}
	}
	
	private boolean handleActionMove(int viewId, float eventX, float eventY) {
		if(viewId == mousePad.getId()) {
			parent.sendMouseMove(oldX, oldY, eventX, eventY);
			oldX = eventX;
			oldY = eventY;
			return true;
		}
		return false;
	}
	
	private boolean handleActionUp(int viewId) {
		if (viewId == leftMouseBtn.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_RELEAS);
			return true;
		} else if (viewId == rightMouseBtn.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_RIGHT_RELEASE);
			return true;
		} else
			return false;
	}
	
	private boolean handleActionDown(int viewId, float eventX, float eventY) {
		if(viewId == mousePad.getId()) {
			oldX = eventX;
			oldY = eventY;
			return true;
		} else if (viewId == leftMouseBtn.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_LEFT_PRESS);
			return true;
		} else if (viewId == rightMouseBtn.getId()) {
			parent.sendMouseClick(ActionConstants.ACTION_MOUSE_RIGHT_PRESS);
			return true;
		} else
			return false;
	}
}
