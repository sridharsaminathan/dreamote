package project.dreamote;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.SeekBar;

public class Preferences extends PreferenceActivity implements OnPreferenceClickListener{
	 private static final int MAX_SAVED_HOSTS = 5;
	 
	 private static final int MAX_SCROLL_SENS = 19;
	 private static final int MAX_MOUSE_SENS = 10;
	 private static final int DEFAULT_SCROLL_SENS = 14;
	 private static final int DEFAULT_MOUSE_SENS = 5;
	
	
	 @Override
     protected void onCreate(Bundle savedInstanceState) {
	     super.onCreate(savedInstanceState);
         addPreferencesFromResource(R.xml.preferences);
        
         Preference mouseSensitivityPrefs = (Preference) findPreference(getString(R.string.prefs_mouse_sensitivity_key));
         mouseSensitivityPrefs.setOnPreferenceClickListener(this);
         Preference scrollSensitivityPrefs = (Preference) findPreference(getString(R.string.prefs_scroll_sensitivity_key));
         scrollSensitivityPrefs.setOnPreferenceClickListener(this);
	 }
	 
	 public static void setConnectedServer(Context context,String name, String ip, int port) {
		 SharedPreferences pref = context.getSharedPreferences(
				 context.getString(R.string.shared_prefs_key), MODE_PRIVATE);
		 Editor edit = pref.edit();
		 edit.putString(context.getString(R.string.computer_name_key), name);
		 edit.putString(context.getString(R.string.ip_key), ip);
		 edit.putInt(context.getString(R.string.port_key), port);
		 edit.commit();
	 }
	 
	 public static String getConnectedServerIp(Context context) {
		 SharedPreferences pref = context.getSharedPreferences(
				 context.getString(R.string.shared_prefs_key), MODE_PRIVATE);
		 
		 return pref.getString(context.getString(R.string.ip_key), "");
	 }
	 
	 public static int getConnectedServerPort(Context context) {
		 SharedPreferences pref = context.getSharedPreferences(
				 context.getString(R.string.shared_prefs_key), MODE_PRIVATE);
		 
		 return pref.getInt(context.getString(R.string.port_key), 0);
	 }
	 
	 public static String getConnectedServerName(Context context){
		 SharedPreferences pref = context.getSharedPreferences(
				 context.getString(R.string.shared_prefs_key), MODE_PRIVATE);
		 
		 return pref.getString(context.getString(R.string.computer_name_key), "");
		 
	 }
	 
	 
	 public static boolean getShowEnableWifiPopup(Context context){
		 SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		 return pref.getBoolean(context.getString(R.string.prefs_enable_wifi_key), true);
	 }
	 
	 public static boolean getTapToClick(Context context){
		 SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		 return pref.getBoolean(context.getString(R.string.prefs_tap_to_click_key), true);
		 
	 }
	 public static boolean getTiltToSteer(Context context){
		 SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		 return pref.getBoolean(context.getString(R.string.prefs_enable_mouse_tilting_key), false);
		 
	 }
	 
	 
	 public static void writeRecentIPs(Context context, ArrayList<String[]> list){
		 SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.shared_prefs_key), MODE_PRIVATE);
		 Editor edit = pref.edit();
        
         for(int index = 0; index < list.size() && index < MAX_SAVED_HOSTS; index++ ){
           	 String[] nameAndIpPair = list.get(index);
        	 for(int j = 0; j < nameAndIpPair.length; j++){
        		 edit.putString(context.getString(R.string.prefs_recent_ip_prefix) + index + j, nameAndIpPair[j]);
        	 }
         }
         edit.commit();
	 }
	 
	 public static ArrayList<String[]> readRecentIPs(Context context){
		 SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.shared_prefs_key), MODE_PRIVATE);
		 ArrayList<String[]> list = new ArrayList<String[]>();
		 
		 for(int index = 0; index < MAX_SAVED_HOSTS; index++){
			 String[] nameAndIpPair = new String[3];
			 int emptyStrings = 0;
			 for(int j = 0; j < 3; j++){
				 String str= pref.getString(context.getString(R.string.prefs_recent_ip_prefix) + index + j, "");
				 nameAndIpPair[j] = str;
				 if(str.equals("")){
					 emptyStrings++;
				 }
			 }
			 if(emptyStrings == 0)
				 list.add(nameAndIpPair);
		 }
			return list;
	 }

	 
	 public static int getMouseSensitivity(Context context) {
		 final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		 return pref.getInt(context.getString(R.string.prefs_mouse_sensitivity_key), DEFAULT_MOUSE_SENS) + 1;  // +1 to set minimum value to 1
	 }
	 
	 public static int getScrollSensitivity(Context context) {
		 final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		 return pref.getInt(context.getString(R.string.prefs_scroll_sensitivity_key), DEFAULT_SCROLL_SENS) + 1;  // +1 to set minimum value to 1
	 }
	 
	 private void createSeekBarPrefs(AlertDialog.Builder alert, final String prefsKey, String title, int max, int defaultValue) {
		 	alert.setTitle(title);
			final SeekBar seekBar = new SeekBar(this);
			seekBar.setMax(max);
			final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
			int progress = pref.getInt(prefsKey, defaultValue);
			seekBar.setProgress(progress);
			
			alert.setView(seekBar);
			alert.setPositiveButton(getString(R.string.ok_lbl), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					int value = seekBar.getProgress();
					
					Editor edit = pref.edit();
					edit.putInt(prefsKey, value);
					edit.commit();
					dialog.cancel();
				}
			});
			alert.setNegativeButton(getString(R.string.cancel_lbl), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			});
	 }
	 
	 @Override
	 public boolean onPreferenceClick(Preference preference) {
		 final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		 String prefsKey = preference.getKey();
		 
		 if(prefsKey.equals(getString(R.string.prefs_mouse_sensitivity_key))) {
			 createSeekBarPrefs(alert, prefsKey, getString(R.string.prefs_mouse_sensitivity_title), MAX_MOUSE_SENS, DEFAULT_MOUSE_SENS);
			 alert.show();
		 } else if(prefsKey.equals(getString(R.string.prefs_scroll_sensitivity_key))) {
			 createSeekBarPrefs(alert, prefsKey, getString(R.string.prefs_scroll_sensitivity_title), MAX_SCROLL_SENS, DEFAULT_SCROLL_SENS);
			 alert.show();
		 }
		 return true;
	 }
		 
	 
}
