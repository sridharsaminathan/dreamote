package project.dreamote;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainTabHostActivity extends ActivityGroup{
	private TabHost tabHost;		// The activity TabHost
	private Resources res;			// Resource object to get Drawables
	private ClientCommunication communication;  // Used to contact server
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_host);
        
        communication = new ClientCommunication("192.168.0.194");
        res = getResources(); 
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
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
}