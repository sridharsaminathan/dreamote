package project.dreamote;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class ProgramsSelectTabActivity extends Activity implements OnClickListener{
	private InputMethodManager inputMgr = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programs_select);
		setListeners();
		
	}
	private void setListeners(){
		Button btnKeyboard = (Button)findViewById(R.id.btn_show_keyboard);
		btnKeyboard.setOnClickListener(this);
		
		
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		if(event.getAction() == KeyEvent.ACTION_UP){
			
			char t = (char)event.getUnicodeChar();
			String tmp = "";
		}
		
		return super.dispatchKeyEvent(event);

	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.btn_show_keyboard:
			{
				
				inputMgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMgr.toggleSoftInput(0, 0);
				
				break;
			}
		}
		
	}
	
	
}
