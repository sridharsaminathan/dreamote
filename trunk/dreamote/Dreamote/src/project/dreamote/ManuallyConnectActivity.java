package project.dreamote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ManuallyConnectActivity extends Activity implements OnClickListener{
	private EditText ipTxt, portTxt;
	private Button connectBtn;
	
	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		setContentView(R.layout.manually_connect);
		
		findViews();
		setEditTextContent();
		setListeners();
	}
	
	private void setEditTextContent() {
		String ip = Preferences.getConnectedServerIp(this);
		int port = Preferences.getConnectedServerPort(this);
		ipTxt.setText(ip);
		portTxt.setText("" + port);
	}
	
	private void findViews() {
		connectBtn = (Button)findViewById(R.id.connect_btn);
		ipTxt = (EditText)findViewById(R.id.ip_edit_text);
		portTxt = (EditText)findViewById(R.id.port_edit_text);
	}
	
	private void setListeners() {
		connectBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == connectBtn.getId()) {
			String ip = ipTxt.getText().toString();
			String port = portTxt.getText().toString();
			if(isIPValid(ip)) {
				Preferences.setConnectedServer(this, ip, Integer.parseInt(port));
				this.setResult(RESULT_OK);
				this.finish();
			
			} else {
				(Toast.makeText(this, "Invalid ip", Toast.LENGTH_SHORT)).show();
			}
		}
	}
	private boolean isIPValid(String ip) {
        try{
			String[] octets = ip.split("\\.");
	        if(octets.length != 4)
	        	return false;
	        
	        for (String s : octets) {
	                int i = Integer.parseInt(s);
	                if (i > 255 || i < 0) {
	                        throw new NumberFormatException();
	                }
	        }
        }
        catch(NumberFormatException es){
        	es.printStackTrace();
        	return false;
        }
        return true;
	}
}
