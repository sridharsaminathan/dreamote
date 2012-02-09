package project.dreamote;

import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Timer;
import java.util.TimerTask;

public class BroadCastRepliesThread implements Runnable, ActionConstants {

	private ServerSelectTabActivity serverTab;
	private boolean running = true;
	private DatagramSocket socket = null;
	public BroadCastRepliesThread(ServerSelectTabActivity serverTab){
		this.serverTab = serverTab;
		
	}
	
	
	public void run(){
		try{ 
			socket = new DatagramSocket(BROADCAST_PORT);
			socket.setSoTimeout(2000);
			byte[] receiveData = new byte[1024];
			while(running){
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(receivePacket);
				String data = new String( receivePacket.getData());
				String[] dataArray = data.split(";");
				
				if(dataArray.length == 4 && ACTION_BROADCAST_REPLY == Integer.parseInt(dataArray[0])){
					String[] d = {dataArray[1], dataArray[2], dataArray[3]};
					serverTab.receiveServerData(d);
				}
			}
			
		}catch(InterruptedIOException es){
			socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void stopThread(){
		running = false;
	}
	
	public void startKillThreadTimer(){
		new Timer(true).schedule(new TimerTask(){
			public void run(){
				running = false;
			}
		}, BROADCAST_WAIT_FOR_REPLY_TIME);
	}
}
