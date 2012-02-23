package project.dreamote;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Preferences extends PreferenceActivity{
		private static final int MAX_SAVED_HOSTS = 5;
	
	
	 @Override
     protected void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
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
		 
	 
}
