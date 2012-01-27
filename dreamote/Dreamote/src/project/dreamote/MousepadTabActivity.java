package project.dreamote;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class MousepadTabActivity extends Activity implements OnTouchListener, OnClickListener {

	private View mousePad;			// The view acting as mousepad
	private Button leftMouseBtn; 	// The left mouse button
	private Button rightMouseBtn;	// The right mouse button
	
	private float oldX;
	private float oldY;
	float diffX;
	float diffY;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mousepad);
		
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
    	leftMouseBtn.setOnClickListener(this);
    	rightMouseBtn.setOnClickListener(this);
    }
	
	private void sendMouseMove(float newX, float newY) {
    	diffX = oldX - newX;
		diffY = oldY - newY;
		TextView tv = (TextView)findViewById(R.id.mouse_pos_txt);
		tv.setText("" + ((int)newX) + " ; " + ((int)newY));
		((MainTabHostActivity)this.getParent()).sendData("mouseMove;" + diffX + ";" + diffY);
    }
	
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldX = event.getX();
			oldY = event.getY();
			return true;
		case MotionEvent.ACTION_MOVE:
			sendMouseMove(event.getX(), event.getY());
			oldX = event.getX();
			oldY = event.getY();
			return true;
		default:
			return false;
		}
	}

	public void onClick(View v) {
		if(v.getId() == leftMouseBtn.getId()) {
			
		} else if (v.getId() == rightMouseBtn.getId()) {
			
		}
	}
}
