package project.dreamote;

import android.app.Activity;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_select);
		
		findViews();
		setListeners();
	}
	
	private void findViews() {
		foundServersList = (ListView)findViewById(R.id.found_servers_list);
		connectManuallyBtn = (Button)findViewById(R.id.manually_connect_btn);
	}
	
	private void setListeners() {
		connectManuallyBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == connectManuallyBtn.getId()) {
			startActivityForResult(new Intent(this, ManuallyConnectActivity.class), 0);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK) {
			((MainTabHostActivity)this.getParent()).updateServerInfo();
		}
	}
}