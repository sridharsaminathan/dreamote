package project.dreamote;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;

public class Preferences extends PreferenceActivity{
	 @Override
     protected void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
             addPreferencesFromResource(R.xml.preferences);
	 }
}
