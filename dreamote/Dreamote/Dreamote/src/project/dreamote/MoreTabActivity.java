package project.dreamote;


import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MoreTabActivity extends ListActivity{
	private ListView lv;
	private final String[] moreItemsList = {"Settings", "Rate Dreamote", "About"};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_tab);
		lv = (ListView)this.findViewById(android.R.id.list);
		setListeners();
		fillList();
	}
	
	
	private void setListeners(){
		lv.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	startActivity(position);
		    }
		  });
	}
	
	private void  startActivity(int index){
		switch(index)
    	{
			case 0:
			{
				startActivity(new Intent(this, Preferences.class));
				break;
			}
			case 1:
			{
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id=iixi.gowest"));
				startActivity(intent);
				break;
			}
	    	case 2:
	    	{
	    		startActivity(new Intent(this, About.class));
	    		break;
	    	}
    	}
	}
	
	private void fillList(){
		lv.setAdapter(new ArrayAdapter<String>( this.getApplicationContext(), android.R.layout.simple_list_item_1, moreItemsList));
		
	}
	
	
}