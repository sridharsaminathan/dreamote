package project.dreamote;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProgramsSelectTabActivity extends Activity implements OnClickListener{
	private MainTabHostActivity parent;
	

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programs_select);
		parent = (MainTabHostActivity)this.getParent();
		findViews();
		setListeners();
		
		//test
		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{ "Spotify", "Spotify - Nirvana - SmellsLike Teen Spirit" });
		list.add(new String[]{ "Vlc", "VideoLan Media Player" });
		list.add(new String[]{ "Itunes", "Itunes" });
		
		ArrayList<String[]> list2 = new ArrayList<String[]>();
		list.add(new String[]{ "Skype", "Skype - martinnume" });
		list.add(new String[]{ "firefox", "SweClockers.com" });
		list.add(new String[]{ "Eclipse", "Java - Dreamote/src/project/dreamote.." });
		
		fillSupportedProgramsList(list);
		fillOtherProgramsList(list2);
		
	}
	
	private void findViews(){
	}
	
	private void setListeners(){
		
	}
	
	private void fillSupportedProgramsList(ArrayList<String[]> list){
		if(list != null && list.size() > 0){
			LinearLayout v = (LinearLayout)findViewById(R.id.layout_supported_programs);
			v.removeAllViews();
			for(int i = 0; i < list.size(); i++){
				String[] info = list.get(i);
				if(info.length == 2){
					View listItem = getLayoutInflater().inflate(R.layout.programs_list_item, null);
				
					TextView programTitle = (TextView)listItem.findViewById(R.id.txt_programs_title);
					programTitle.setText(info[0]);
					TextView programInfo = (TextView)listItem.findViewById(R.id.txt_programs_info);
					programInfo.setText(info[1]);
					v.addView(listItem);
				}
			}
			
		}
		
	}
	private void fillOtherProgramsList(ArrayList<String[]> list ){
		if(list != null && list.size() > 0){
			LinearLayout v = (LinearLayout)findViewById(R.id.layout_other_programs);
			v.removeAllViews();
			for(int i = 0; i < list.size(); i++){
				String[] info = list.get(i);
				if(info.length == 2){
					View listItem = getLayoutInflater().inflate(R.layout.programs_list_item, null);
				
					TextView programTitle = (TextView)listItem.findViewById(R.id.txt_programs_title);
					programTitle.setText(info[0]);
					TextView programInfo = (TextView)listItem.findViewById(R.id.txt_programs_info);
					programInfo.setText(info[1]);
					v.addView(listItem);
				}
			}
			
		}
		
	}

	@Override
	public void onClick(View v) {
		
		
	}
	
	
}
