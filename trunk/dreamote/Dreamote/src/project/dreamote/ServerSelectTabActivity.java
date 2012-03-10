package project.dreamote;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import project.dreamote.utils.BroadCastRepliesThread;
import project.dreamote.utils.ServerListAdapter;
import project.dremote.communication.ClientCommunication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ServerSelectTabActivity extends Activity implements OnClickListener, OnItemClickListener{
	private ListView foundServersList;
	private ListView serverHistoryListView;
	private Button updateServerListBtn;
	private Button connectManuallyBtn;
	
	private ArrayList<String[]> broadCastList;
	private ArrayList<String[]> serverHistoryList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_select);
		
		findViews();
		setListeners();
		broadCastList = new ArrayList<String[]>();
		serverHistoryList = Preferences.readRecentIPs(this);
		
		fillServerHistoryList();
		// TestValues

//		list.add(new String[]{ "Niclas server", "192.168.0.197" });
//		list.add(new String[]{ "Martin server", "192.168.0.192" });
//		list.add(new String[]{ "Daniel server", "192.168.0.194" });
		//fillFoundServerList();
//		fillServerHistoryList(list);
	}
	
	/**
	 * Find views used in the activity
	 */
	private void findViews() {
		foundServersList = (ListView)findViewById(R.id.found_servers_list);
		serverHistoryListView = (ListView)findViewById(R.id.server_history_list);
		updateServerListBtn = (Button)findViewById(R.id.update_server_list_btn);
		connectManuallyBtn = (Button)findViewById(R.id.manually_connect_btn);
	}
	
	/**
	 * Connect the listeners
	 */
	private void setListeners() {
		connectManuallyBtn.setOnClickListener(this);
		updateServerListBtn.setOnClickListener(this);
		foundServersList.setOnItemClickListener(this);
		serverHistoryListView.setOnItemClickListener(this);
	}
	/**
	 * Fill the broadcast listview
	 */
	private void fillFoundServerList() {
		foundServersList.setAdapter(new ServerListAdapter(this, R.layout.server_list_item, broadCastList));
		foundServersList.invalidate();
	}
	
	/**
	 * Fill the server history listview
	 */
	private void fillServerHistoryList() {
		serverHistoryListView.setAdapter(new ServerListAdapter(this, R.layout.server_list_item, serverHistoryList));
		serverHistoryListView.invalidate();
	}
	
	/**
	 * Threaded fill server list. used when we need to fill the listview from another thread than gui-thread
	 */
	 private void threadedFillFoundServerList(){
			this.runOnUiThread(new Runnable(){
				@Override
					public void run() {
						fillFoundServerList();
						
					}
	    		});
		}
	
	/**
	 * Adds the serverInfo data to the broadCastList and updates the listview
	 * @param data serverInfoarray
	 */
	public void receiveServerData(String[] data){
		broadCastList.add(data);
		threadedFillFoundServerList();
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.manually_connect_btn:
			startActivityForResult(new Intent(this, ManuallyConnectActivity.class), 0);
			break;
		case R.id.update_server_list_btn:
			broadCastList.clear();
			Thread thread = new Thread(new BroadCastRepliesThread(this));
			thread.start();
			
			final Context context = this.getApplicationContext();
			new Thread(new Runnable() {
			    public void run() {
			    	ClientCommunication.sendBroadCast(context);
			    }
			  }).start();
			
			
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			((MainTabHostActivity)this.getParent()).updateServerInfo();
			String[] tripple = new String[3];
			tripple[0] = Preferences.getConnectedServerName(this);
			tripple[1] = Preferences.getConnectedServerIp(this);
			tripple[2] = "" + Preferences.getConnectedServerPort(this);
			if(!isServerAlreadyInHistoryList(tripple))
				addServerToHistoryList(tripple);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		if(parent.getId() == R.id.found_servers_list){
			if(position <= broadCastList.size()){
				String[] item = broadCastList.get(position);
				if(item.length >= 3){
					Preferences.setConnectedServer(this,item[0], item[1], Integer.parseInt(item[2]));
					((MainTabHostActivity)this.getParent()).updateServerInfo();
					if(!isServerAlreadyInHistoryList(item))
						addServerToHistoryList(item);
				}
			}
		}else if(parent.getId() == R.id.server_history_list){
			if(position <= serverHistoryList.size()){
				String[] item = serverHistoryList.get(position);
				if(item.length >= 3){
					Preferences.setConnectedServer(this, item[0], item[1], Integer.parseInt(item[2]));
					((MainTabHostActivity)this.getParent()).updateServerInfo();
				}
				
			}
			
		}
	}
	
	/**
	 * Checks if the tripple-string-array with serverinfo already exists in the serverHistoryList
	 * @param serverInfo 
	 * @return returns true if the serverInfo already exists
	 */
	private boolean isServerAlreadyInHistoryList(String[] serverInfo){
		for(int i = 0; i < serverHistoryList.size(); i++){
			String[] tripple = serverHistoryList.get(i);
			if(tripple.length >= 2  && serverInfo.length >= 2){
				if(tripple[0].equalsIgnoreCase(serverInfo[0]) && tripple[1].equalsIgnoreCase(serverInfo[1])){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Adds the server to the serverHistoryList
	 * @param trupple an array with length=3 {computername, ipaddress, port}
	 */
	private void addServerToHistoryList(String[] trupple){
		//add the new server first in the list
		serverHistoryList.add(0, trupple);
		//fill the listview with the new list
		fillServerHistoryList();
		//save the new list to preferences
		Preferences.writeRecentIPs(this, serverHistoryList);
	}

}