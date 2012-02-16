package project.dreamote;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import project.dreamote.components.OnVolumeChangeListener;
import project.dreamote.components.VolumeController;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class MainTabHostActivity extends ActivityGroup implements OnClickListener, OnVolumeChangeListener, Observer{
	private TabHost tabHost;		// The activity TabHost
	private VolumeController volumeController;
	private Resources res;			// Resource object to get Drawables
	private ClientCommunication communication;  // Used to contact server
	private Thread receiverThread = null;
	private InputMethodManager inputMgr = null;
	private Button keyboardBtn;
	private IncomingCommunication com = null;
	
	private Context context = this;
	private String[] programsDataArray = null;
	private boolean dataAvailable = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_host);
        
        communication = new ClientCommunication(
        		Preferences.getConnectedServerIp(this), Preferences.getConnectedServerPort(this));
        
        findViews();
        setupVolumeController();
        setListeners();
        createTabs();
        
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	startReceiverThread();
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	stopReceiverThread();
    }
    private void findViews() {
    	tabHost = (TabHost)findViewById(android.R.id.tabhost);
    	keyboardBtn = (Button)findViewById(R.id.keyboard_btn);
    	volumeController = (VolumeController)findViewById(R.id.volume_controller);
    }
    
    private void startReceiverThread(){
    	int receiverPort = 52568;
    	//int receiverPort = communication.getCurrentPort();
    	if(receiverPort >= 0 ){
    		com = new IncomingCommunication(receiverPort);
    		com.addObserver(this);
        	receiverThread = new Thread(com);
        	receiverThread.start();
    	}
    }
    
    public boolean isDataAvailable(){
    	return dataAvailable;
    }
    
    public void setDataAvailble(boolean value){
    	dataAvailable = value;
    }
    
    public String[] getProgramsDataArray(){
    	dataAvailable = false;
    	return programsDataArray;
    }
    
    private void stopReceiverThread(){
    	if(com != null){
    		com.shutDown();
    		
    		
    	}
    	if(receiverThread != null){
    		receiverThread.interrupt();
    		receiverThread = null;
    	}
    }
    
    private void setListeners() {
    	keyboardBtn.setOnClickListener(this);
    	volumeController.setOnVolumeChangeListener(this);
    }
    
    private void setupVolumeController() {
    	String[] imageNames = getResources().getStringArray(R.array.volume_image_names);
    	ArrayList<Integer> imageIds = new ArrayList<Integer>();
    	for(String name : imageNames) {
	    	try {
	    	    Class<R.drawable> res = R.drawable.class;
	    	    Field field = res.getField(name);
	    	    int drawableId = field.getInt(null);
	    	    imageIds.add(drawableId);
	    	}
	    	catch (Exception e) {
	    	    Log.e("MyTag", "Failure to get drawable id.", e);
	    	}
    	}
    	volumeController.setBackgroundImages(imageIds);
    }
    
    private void createTabs() {
    	res = getResources(); 
        tabHost.setup(getLocalActivityManager());
        
        tabHost.getTabWidget().setBackgroundDrawable(res.getDrawable(R.drawable.tab_background));
        
        // Create an Intent to launch an Activity for the tab (to be reused)
        Intent intent = new Intent().setClass(this, MousepadTabActivity.class);
        createTab(intent, getString(R.string.mousepad_tab_tag), R.drawable.mousepad_tab_selector);
        
        // Do the same for the other tabs
        intent = new Intent().setClass(this, ProgramsSelectTabActivity.class);
        createTab(intent, getString(R.string.programs_tab_tag), R.drawable.program_tab_selector);
        
        intent = new Intent().setClass(this, EmptyTabActivity.class);
        createTab(intent, "", R.drawable.empty_tab);
        
        intent = new Intent().setClass(this, ServerSelectTabActivity.class);
        createTab(intent, getString(R.string.server_tab_tag), R.drawable.server_tab_selector);
        
        intent = new Intent().setClass(this, MoreTabActivity.class);
        createTab(intent, getString(R.string.more_tab_tag), R.drawable.more_tab_selector);

        tabHost.setCurrentTab(0);
    }
    
    public void updateServerInfo() {
    	
    	boolean connected = communication.updateServerInfo(
    			Preferences.getConnectedServerIp(this), Preferences.getConnectedServerPort(this));
    	
    	if(!connected) {
    		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		 builder.setMessage(context.getString(R.string.server_connect_error))
    	       .setCancelable(false)
    	       .setPositiveButton(context.getString(R.string.ok_lbl), new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	           }
    	       });
    		 AlertDialog alert = builder.create();
    		 alert.show();
    	}
    }
    
    /**
     * @param intent
     * @param tagId
     * @param drawableId
     * @param text
     */
    private void createTab(Intent intent, String tag, int drawableId) {
        // Initialize a TabSpec for each tab and add it to the TabHost
    	TextView view = new TextView(this);
    	view.setTextAppearance(this, R.style.tab_text);
    	view.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    	view.setPadding(0, 0, 0, 4);
    	view.setText(tag);
    	view.setBackgroundDrawable(res.getDrawable(drawableId));
        TabSpec spec = tabHost.newTabSpec(tag).setIndicator(view).setContent(intent);
        tabHost.addTab(spec);
    }

    public void sendMouseMove(float oldX, float oldY, float newX, float newY) {
    	communication.sendCommand(MessageGenerator.createMouseMoveMessage(oldX, oldY, newX, newY));
    }
    
    public void sendKeyEvent(String key){
    	communication.sendCommand(MessageGenerator.createKeyboardEvent(key));
    }
    
    public void sendMouseClick(int action) {
		switch(action) {
		case ActionConstants.ACTION_MOUSE_LEFT_PRESS:
			communication.sendCommand(MessageGenerator.createMouseLeftBtnPress());
			break;
		case ActionConstants.ACTION_MOUSE_RIGHT_PRESS:
			communication.sendCommand(MessageGenerator.createMouseRightBtnPress());
			break;
		case ActionConstants.ACTION_MOUSE_LEFT_RELEAS:
			communication.sendCommand(MessageGenerator.createMouseLeftBtnRelease());
			break;
		case ActionConstants.ACTION_MOUSE_RIGHT_RELEASE:
			communication.sendCommand(MessageGenerator.createMouseRightBtnRelease());
			break;
		default:
			return;
		}
	}
    public void sendGetOpenWindows(){
    	communication.sendCommand(MessageGenerator.createGetOpenWindows());
    }
    
    
    public void sendMouseScroll(int action) {
    	switch(action) {
    	case ActionConstants.ACTION_SCROLL_DOWN:
    		communication.sendCommand(MessageGenerator.createScrollDown());
    		break;
    	case ActionConstants.ACTION_SCROLL_UP:
    		communication.sendCommand(MessageGenerator.createScrollUp());
    		break;
    	}
    }
    
    @Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.keyboard_btn:
			inputMgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMgr.toggleSoftInput(0, 0);
			break;
		}
	}
    
    @Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		String sendString = "";
		if(event.getAction() == KeyEvent.ACTION_MULTIPLE){
			if(!event.getCharacters().equals("")){
				sendKeyEvent(event.getCharacters());
			}	
		}
		else if(event.getAction() == KeyEvent.ACTION_UP){
			int keyCode = event.getKeyCode();
			if(event.getKeyCode() == KeyEvent.KEYCODE_SPACE){
				sendString = "{SPACE}";
			} else if(keyCode == KeyEvent.KEYCODE_DEL){
				sendString = "{BACKSPACE}";
			} else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
				sendString = "{LEFT}";
			}  else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
				sendString = "{RIGHT}";
			}  else if(keyCode == KeyEvent.KEYCODE_DPAD_UP) {
				sendString = "{UP}";
			} else if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
				sendString = "{DOWN}";
			} else{
				sendString = String.valueOf((char)event.getUnicodeChar());
			}		
			if(!sendString.equals(" ")){
				sendKeyEvent(sendString);
			}
		}
		return super.dispatchKeyEvent(event);
	}
    
    @Override
	public void onVolumeChange(int volume) {
		communication.sendCommand(MessageGenerator.createSetVolume(volume));
	}

	@Override
	public void update(Observable observable, Object data) {
		if(data instanceof String[]){
			
			programsDataArray = (String[])data;
			dataAvailable = true;
		}
		
	}
   
    
}