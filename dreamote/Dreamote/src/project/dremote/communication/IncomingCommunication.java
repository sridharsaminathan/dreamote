package project.dremote.communication;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Observable;

import project.dreamote.utils.ActionConstants;

/**
 * Thread for receiving the incoming data sent from the server
 * @author nume
 *
 */
public class IncomingCommunication extends Observable implements Runnable, ActionConstants{

	private boolean running = true;
	private DatagramSocket socket = null;
	
	
	public IncomingCommunication(DatagramSocket socket){
		this.socket = socket;
		
		
	}
	@Override
	public void run() {
		
			byte[] receiveData = new byte[1024];
			
			while(running){
				try{
					
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					socket.setSoTimeout(2000);
					socket.receive(receivePacket);
					String data = new String( receivePacket.getData(), 0 , receivePacket.getLength());
					handleData(data);
				
				}catch(SocketTimeoutException es){
					if(!running){
						socket.close();
						break;
					}else
						continue;
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
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
			setChanged();
			notifyObservers(dataArray);
			
		}
		
	}
	
	public void stopThread(){
		running = false;
		
	}
	
	
}
