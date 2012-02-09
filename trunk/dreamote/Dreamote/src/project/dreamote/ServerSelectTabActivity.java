package project.dreamote;

import java.net.InetAddress;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ServerSelectTabActivity extends Activity implements OnClickListener{
	private ListView foundServersList;
	private ListView serverHistoryList;
	private Button updateServerListBtn;
	private Button connectManuallyBtn;
	
	private ArrayList<String[]> broadCastList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_select);
		
		findViews();
		setListeners();
		broadCastList = new ArrayList<String[]>();
		
		// TestValues
		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{ "Niclas server", "192.168.0.197" });
		list.add(new String[]{ "Martin server", "192.168.0.192" });
		list.add(new String[]{ "Daniel server", "192.168.0.194" });
		//fillFoundServerList();
		fillServerHistoryList(list);
	}
	
	private void findViews() {
		foundServersList = (ListView)findViewById(R.id.found_servers_list);
		serverHistoryList = (ListView)findViewById(R.id.server_history_list);
		updateServerListBtn = (Button)findViewById(R.id.update_server_list_btn);
		connectManuallyBtn = (Button)findViewById(R.id.manually_connect_btn);
	}
	
	private void setListeners() {
		connectManuallyBtn.setOnClickListener(this);
		updateServerListBtn.setOnClickListener(this);
	}
	
	private void fillFoundServerList() {
		foundServersList.setAdapter(new ServerListAdapter(this, R.layout.server_list_item, broadCastList));
		foundServersList.invalidate();
	}
	
	private void fillServerHistoryList(ArrayList<String[]> list) {
		serverHistoryList.setAdapter(new ServerListAdapter(this, R.layout.server_list_item, list));
		serverHistoryList.invalidate();
	}
	
	 private void threadedFillFoundServerList(){
			this.runOnUiThread(new Runnable(){
				@Override
					public void run() {
						fillFoundServerList();
						
					}
	    		});
		}
	
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
			final Context context = this.getApplicationContext();
			new Thread(new Runnable() {
			    public void run() {
			    	ClientCommunication.sendBroadCast(context);
			    }
			  }).start();
			
			Thread thread = new Thread(new BroadCastRepliesThread(this));
			thread.start();
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			((MainTabHostActivity)this.getParent()).updateServerInfo();
		}
	}
}