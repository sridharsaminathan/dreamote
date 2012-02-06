package project.dreamote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ProgramsSelectTabActivity extends Activity implements OnClickListener{
	private MainTabHostActivity parent;
	
	private ListView supportedProgramsList;
	private ListView otherProgramsList;
	private Button refreshProgramsBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programs_select);
		parent = (MainTabHostActivity)this.getParent();
		findViews();
	}
	
	private void findViews(){
		supportedProgramsList = (ListView)findViewById(R.id.list_supported);
		otherProgramsList = (ListView)findViewById(R.id.list_other);
		refreshProgramsBtn = (Button)findViewById(R.id.btn_update_program_list);
	}
	
	private void setListeners(){
		refreshProgramsBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_update_program_list:
			{
				
				break;
			}
		
		}
		
	}
	
	
}
