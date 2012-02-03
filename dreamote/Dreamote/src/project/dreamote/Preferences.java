package project.dreamote;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity{
	 @Override
     protected void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
	 }
	 
	 public static void setConnectedServer(Context context, String ip, int port) {
		 SharedPreferences pref = context.getSharedPreferences(
				 context.getString(R.string.shared_prefs_key), MODE_PRIVATE);
		 Editor edit = pref.edit();
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
}
