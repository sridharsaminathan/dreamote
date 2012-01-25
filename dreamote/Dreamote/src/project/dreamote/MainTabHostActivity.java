package project.dreamote;

import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainTabHostActivity extends ActivityGroup {
	private TabHost tabHost;	// The activity TabHost
	private Resources res;		// Resource object to get Drawables
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_host);
        
        res = getResources(); 
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(getLocalActivityManager());
        
        tabHost.getTabWidget().setBackgroundDrawable(res.getDrawable(R.drawable.tab_background));
        tabHost.getTabWidget().getBackground().setDither(true);
        
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
//    	view.setTextSize(10);
    	view.setTextAppearance(this, R.style.tab_text);
    	view.setGravity(Gravity.CENTER_HORIZONTAL);
    	view.setText(tag);
    	view.setBackgroundDrawable(res.getDrawable(drawableId));
        TabSpec spec = tabHost.newTabSpec(tag).setIndicator(view).setContent(intent);
        tabHost.addTab(spec);
    }
    
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
}