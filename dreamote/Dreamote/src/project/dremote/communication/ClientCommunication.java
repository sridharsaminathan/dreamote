package project.dremote.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import project.dreamote.utils.ActionConstants;
import project.dreamote.utils.MessageGenerator;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;


public class ClientCommunication implements ActionConstants{

	
    private String serverIP;
    private int serverPort;
    private DatagramSocket clientSocket;
    private InetAddress IPAddress;
    
    /**
     * 
     * @param address String of IP address to the server.
     * @throws SocketException 
     */
    public ClientCommunication(String ip, int port){
        this.serverIP = ip;
        this.serverPort = port;
        clientSocket = null;
        createSocket();
    }
    
    /**
     * Creates a socket
     * @return true if is was successfull
     */
    private boolean createSocket(){
    	try {
    		clientSocket = new DatagramSocket();
    		IPAddress = InetAddress.getByName(serverIP);
			return true;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}catch(UnknownHostException es){
			es.printStackTrace();
			return false;
		}
    }
    
    
    /**
     * 
     * @param command String of the Command that is going to be executed.
     */
    public boolean sendCommand(String command){
        try {
            byte[] sendData = new byte[1024];
            sendData = command.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
            clientSocket.send(sendPacket);
            return true;
        }catch (SocketException e) {
            System.out.println("Could not create socket");
            return false;
        } catch (IOException e) {
            System.out.println("Could not send package");
            return false;
        }
    }
    
    /**
     * Acknowledgment from server that the command has been executed.
     */
    public void receiveACK(){
        try {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String ack = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + ack);
        }catch (IOException e) {
            System.out.println("Could not receive package");
        }
    }
    
    /**
     * Closes the socket
     * @return true if it socket was successfully closed
     */
    public boolean closeSocket(){
    	if(clientSocket != null){
    		clientSocket.close();
    		return true;
    	}
    	return false;
    }
    
    /**
     * Gets the socket
     * @return socket
     */
    public DatagramSocket getSocket(){
    	return clientSocket;
    }
    
    /**
     * Updates the connection info 
     * @param ip ipaddress
     * @param port port to the server
     * @return true if a creation of a socket was sucessful
     */
    public boolean updateServerInfo(String ip, int port) {
    	this.serverIP = ip;
    	this.serverPort = port;
    	return createSocket();
    }
    
    /**
     * Checks if the phone hase wireless turned on if it is connected to a wireless network.
     * @param context 
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }
        return networkInfo == null ? false : networkInfo.isConnected();
    }
    
   
    /**
     * Gets the broadcastaddress in the current network 
     * @param context Context of the application
     * @return the Inetaddress Object that contains the broadcastaddress
     */
    public static InetAddress getBroadcastAddress(Context context) {
    	try{
    		WifiManager myWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
    		DhcpInfo myDhcpInfo = myWifiManager.getDhcpInfo();
    		if (myDhcpInfo == null) {
    			System.out.println("Could not get broadcast address");
    			return null;
    		}
    		int broadcast = (myDhcpInfo.ipAddress & myDhcpInfo.netmask)
    					| ~myDhcpInfo.netmask;
    		byte[] quads = new byte[4];
    		for (int k = 0; k < 4; k++)
    		quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
    		return InetAddress.getByAddress(quads);
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * Sends a broadcastpacket
     * @param context context of application
     */
    public static void sendBroadCast(Context context){
    	InetAddress i = getBroadcastAddress(context);
		if(i != null){
			sendBroadcast(i);
		}
    }
    /**
     * Sends a broadcastpacket
     * @param broadcastAdr receiver address
     */
    private static void sendBroadcast(InetAddress broadcastAdr){
        try{            
            byte[] sendData = new byte[1024];        
            
            sendData = MessageGenerator.createBroadCast().getBytes();
            DatagramSocket bCastSocket = new DatagramSocket();
            DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,broadcastAdr, BROADCAST_PORT);
            bCastSocket.setBroadcast(true);
            bCastSocket.send(sendPacket);
            bCastSocket.close();
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
    	
  
    

}
