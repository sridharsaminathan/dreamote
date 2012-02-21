package project.dreamote;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramsSelectTabActivity extends Activity implements OnClickListener{
	private MainTabHostActivity parent;
	private LinearLayout supportedPrograms;
	private LinearLayout otherPrograms;
	private Button refreshButton;
	private boolean fetchingData = false;
	private Toast toast = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programs_select);
		parent = (MainTabHostActivity)this.getParent();
		findViews();
		setListeners();
		
		
		//test
		/*ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{ "Spotify", "Spotify - Nirvana - SmellsLike Teen Spirit" });
		list.add(new String[]{ "Vlc", "VideoLan Media Player" });
		list.add(new String[]{ "Itunes", "Itunes" });
		
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		list2.add(new String[]{ "Skype", "Skype - martinnume" });
		list2.add(new String[]{ "firefox", "SweClockers.com" });
		list2.add(new String[]{ "Eclipse", "Java - Dreamote/src/project/dreamote.." });
		list2.add(new String[]{ "Skype", "Skype - martinnume" });
		list2.add(new String[]{ "firefox", "SweClockers.com" });
		list2.add(new String[]{ "Eclipse", "Java - Dreamote/src/project/dreamote.." });
		list2.add(new String[]{ "Skype", "Skype - martinnume" });
		list2.add(new String[]{ "firefox", "SweClockers.com" });
		list2.add(new String[]{ "Eclipse", "Java - Dreamote/src/project/dreamote.." });
		
		fillSupportedProgramsList(list);
		fillOtherProgramsList(list2);*/
		
	}
	
	private void findViews(){
		supportedPrograms = (LinearLayout)findViewById(R.id.layout_supported_programs);
		otherPrograms = (LinearLayout)findViewById(R.id.layout_other_programs);
		refreshButton = (Button)findViewById(R.id.btn_update_programs_list);
	}
	
	private void setListeners(){
		refreshButton.setOnClickListener(this);
		
	}
	
	private void fillSupportedProgramsList(ArrayList<String[]> list){
		if(list != null && list.size() > 0){
			supportedPrograms.removeAllViews();
			for(int i = 0; i < list.size(); i++){
				String[] info = list.get(i);
				if(info.length == 2){
					View listItem = getLayoutInflater().inflate(R.layout.programs_list_item, null);
					(listItem.findViewById(R.id.programs_clickarea)).setOnClickListener(this);
					TextView programTitle = (TextView)listItem.findViewById(R.id.txt_programs_title);
					programTitle.setText(info[0]);
					TextView programInfo = (TextView)listItem.findViewById(R.id.txt_programs_info);
					programInfo.setText(info[1]);
					supportedPrograms.addView(listItem);
				}
			}
			showToast(getString(R.string.pop_up_programs_updated));
		}
		
	}
	private void threadedFillSupportedProgramsList(final ArrayList<String[]> list){
		this.runOnUiThread(new Runnable(){
			@Override
				public void run() {
					fillSupportedProgramsList(list);
					
				}
    		});
	}
	
	
	private void fillOtherProgramsList(ArrayList<String[]> list ){
		if(list != null && list.size() > 0){
			otherPrograms.removeAllViews();
			for(int i = 0; i < list.size(); i++){
				String[] info = list.get(i);
				if(info.length == 2){
					View listItem = getLayoutInflater().inflate(R.layout.programs_list_item, null);
					(listItem.findViewById(R.id.programs_clickarea)).setOnClickListener(this);
					TextView programTitle = (TextView)listItem.findViewById(R.id.txt_programs_title);
					programTitle.setText(info[0]);
					TextView programInfo = (TextView)listItem.findViewById(R.id.txt_programs_info);
					programInfo.setText(info[1]);
					otherPrograms.addView(listItem);
				}
			}
			showToast(getString(R.string.pop_up_programs_updated));
		}
		
	}
	
	private void threadedFillOtherProgramsList(final ArrayList<String[]> list){
		this.runOnUiThread(new Runnable(){
			@Override
				public void run() {
					fillOtherProgramsList(list);
					
				}
    		});
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_update_programs_list){
			
			//test if we are already fetching data. prevents spamming on button to create loads of threads
			if(!fetchingData){
				//set the flag to false so we only fetch the new data
				parent.setSupportedDataAvailble(false);
				parent.setOtherDataAvailable(false);
				fetchingData = true;
				fetchData();
				parent.sendGetOpenWindows();
				
			}
		}
		else if(v.getId() == R.id.programs_clickarea){
			TextView titleTextView = (TextView)v.findViewById(R.id.txt_programs_title);
			String windowTitle = titleTextView.getText().toString().toLowerCase();
			parent.sendSetFocusWindow(windowTitle);
		}
		
		
	}
	
	
	//method to fetch the received data from the server-program
	//loops for 3sec. if no data is available by then the thread shuts down because there was no data to fetch
	private void fetchData(){
		
		new Thread( new Runnable(){
			@Override
			public void run() {
				int fetchAttempts = 0;
				 int listsFilled = 0;
				while(fetchAttempts < 30){
					if(parent.isSupportedDataAvailable()){
						threadedFillSupportedProgramsList(handleFetchedData(parent.getSupportedProgramsDataArray()));
						listsFilled++;
					}if(parent.isOtherDataAvailable()){
						threadedFillOtherProgramsList(handleFetchedData(parent.getOtherProgramsDataArray()));
						listsFilled++;
					}
					if(listsFilled == 2)
						break;
					else{
						fetchAttempts++;
						try{
							Thread.sleep(100);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
				fetchingData = false;
			}
		}).start();
		
	}
	
	
	//take care of the data from the server. string looks like "program1:program1info;program2:program2info;" .....and so on
	private ArrayList<String[]> handleFetchedData(String[] data){
		ArrayList<String[]> inData = new ArrayList<String[]>();
		if(data != null && data.length > 0 ){
			
			
			for(int i = 0; i < data.length; i++ ){
				String[] singleProgram = data[i].split(":");
				inData.add(singleProgram);
				
			}
			
		}
		return inData;
	}
	
	private void showToast(String str){
		if(toast != null){
			toast.cancel();
		}
		toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
		toast.show();
		
		
	}

	
	
}
