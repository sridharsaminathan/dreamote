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
	private MainTabHostActivity parent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programs_select);
		setListeners();
		parent = (MainTabHostActivity)this.getParent();
		
	}
	private void setListeners(){
		Button btnKeyboard = (Button)findViewById(R.id.btn_show_keyboard);
		btnKeyboard.setOnClickListener(this);
		
		
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		
		String sendString = "";
		if(event.getAction() == KeyEvent.ACTION_MULTIPLE){
			if(!event.getCharacters().equals("")){
				parent.sendKeyEvent(event.getCharacters());
				
				
			}	
		}
		else if(event.getAction() == KeyEvent.ACTION_UP){
				int keyCode = event.getKeyCode();
				if(event.getKeyCode() == KeyEvent.KEYCODE_SPACE){
					sendString = "{SPACE}";
				}
				else if(keyCode == KeyEvent.KEYCODE_DEL){
					sendString = "{DEL}";
				}
				else{
					sendString = (char)event.getUnicodeChar() + "";
				}
				
				if(!sendString.equals(" ")){
					parent.sendKeyEvent(sendString);
				}
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
