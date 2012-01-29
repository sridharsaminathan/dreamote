package project.dreamote;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class About extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstance){
		super.onCreate(savedInstance);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
		
	}

}
