package project.dreamote;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ServerListAdapter extends ArrayAdapter<ArrayList<String[]>>{

	private ArrayList<String[]> serverInfo;
	private Context context;
	
	private ViewHolder holder;
	
	public ServerListAdapter(Context context, int viewId, ArrayList<String[]> serverInfo) {
		super(context, viewId);
		
		this.context = context;
		this.serverInfo = serverInfo;
		
		this.holder = new ViewHolder();
	}
	
	@Override
	public int getCount() {
		return serverInfo.size();
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (v == null) {
            LayoutInflater li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.server_list_item, null);
        }
		
		String[] info = serverInfo.get(position);
        if (info != null && info.length >= 2) {
                holder.serverName = (TextView) v.findViewById(R.id.server_name_txt);
                holder.serverIp = (TextView) v.findViewById(R.id.server_ip_txt);
	            
                holder.serverName.setText(info[0]);
                holder.serverIp.setText(info[1]);
        }
		return v;
	}
	
	private class ViewHolder {
		TextView serverName;
		TextView serverIp;
	}
}
