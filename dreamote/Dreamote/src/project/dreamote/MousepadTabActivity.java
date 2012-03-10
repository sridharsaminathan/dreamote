package project.dreamote;

import project.dreamote.utils.EventHandler;
import project.dremote.communication.ClientCommunication;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MousepadTabActivity extends Activity {
	
	private View mousePad;			// The view acting as mousepad
	private View mouseScroll;		// The view acting as a mouse scroll
	private Button leftMouseBtn; 	// The left mouse button
	private Button rightMouseBtn;	// The right mouse button
	
	private EventHandler eventHandler;
	
	private MainTabHostActivity parent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mousepad);
		
		findViews();
		eventHandler = new EventHandler(this, parent);
		
		setListeners();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		setListeners();
		
		eventHandler.onResume();
		
		if(!ClientCommunication.isConnected(this) && Preferences.getShowEnableWifiPopup(this)){
			Toast toast = Toast.makeText(this, getString(R.string.enable_wifi_message), Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		
		if(Preferences.getTiltToSteer(this))
			eventHandler.setGyroListener(true);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		if(Preferences.getTiltToSteer(this))
			eventHandler.setGyroListener(false);
	}
	
	private void findViews() {
		parent = (MainTabHostActivity)this.getParent();
    	mousePad = findViewById(R.id.mousepad);
    	mouseScroll = findViewById(R.id.mouse_scroll);
    	leftMouseBtn = (Button)findViewById(R.id.left_mouse_btn);
    	rightMouseBtn = (Button)findViewById(R.id.right_mouse_btn);
    }
    
    private void setListeners() {
    	mousePad.setOnTouchListener(eventHandler);
    	
    	if(Preferences.getTapToClick(this))
    		mousePad.setOnClickListener(eventHandler);
    	else
    		mousePad.setOnClickListener(null);
    	
    	mouseScroll.setOnTouchListener(eventHandler);
    	leftMouseBtn.setOnTouchListener(eventHandler);
    	rightMouseBtn.setOnTouchListener(eventHandler);
    }
}
