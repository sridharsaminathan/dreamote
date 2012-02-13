package project.dreamote;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Observable;

public class IncomingCommunication extends Observable implements Runnable, ActionConstants{

	private boolean running = true;
	private DatagramSocket socket = null;
	private int port;
	
	
	public IncomingCommunication(int port){
		this.port = port;
		
		
	}
	
	
	
	@Override
	public void run() {
		try{
			socket = new DatagramSocket(port);
			byte[] receiveData = new byte[1024];
			
			while(running){
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(receivePacket);
				String data = new String( receivePacket.getData(), 0 , receivePacket.getLength());
				handleData(data);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void handleData(String data){
		String[] dataArray = data.split(";");
		if(dataArray != null && dataArray.length > 0){
			int action = -1;
			try{
				action = Integer.parseInt(dataArray[0]);
			}catch(NumberFormatException e){
				e.printStackTrace();
				return;
			}
			notifyObservers(dataArray);
			
		}
		
	}
	
	public void stopThread(){
		running = false;
		if(socket != null){
			socket.disconnect();
			socket.close();
		}
		
	}
	
	
}
