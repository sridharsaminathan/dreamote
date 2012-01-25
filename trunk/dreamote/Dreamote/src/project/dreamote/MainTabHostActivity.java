package project.dreamote;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainTabHostActivity extends TabActivity {
	private TabHost tabHost;	// The activity TabHost
	private Resources res;		// Resource object to get Drawables
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_host);
        
        res = getResources(); 
        tabHost = getTabHost(); 
        
        
        // Create an Intent to launch an Activity for the tab (to be reused)
        Intent intent = new Intent().setClass(this, MousepadTabActivity.class);
        createTab(intent, R.string.mousepad_tab_tag, android.R.drawable.ic_media_play);
        
        // Do the same for the other tabs
        intent = new Intent().setClass(this, ProgramsSelectTabActivity.class);
        createTab(intent, R.string.programs_tab_tag, android.R.drawable.ic_btn_speak_now);
        
        intent = new Intent().setClass(this, EmptyTabActivity.class);
        createTab(intent, R.string.empty_tab_tag, android.R.drawable.btn_plus);
        
        intent = new Intent().setClass(this, ServerSelectTabActivity.class);
        createTab(intent, R.string.server_tab_tag, android.R.drawable.ic_menu_agenda);
        
        intent = new Intent().setClass(this, MoreTabActivity.class);
        createTab(intent, R.string.more_tab_tag, android.R.drawable.ic_dialog_alert);

        tabHost.setCurrentTab(1);
        
    }
    
    private void createTab(Intent intent, int tagId, int drawableId) {
        // Initialize a TabSpec for each tab and add it to the TabHost
        TabSpec spec = tabHost.newTabSpec(getString(tagId)).setIndicator("",
                          res.getDrawable(drawableId)).setContent(intent);
        tabHost.addTab(spec);
    }
}