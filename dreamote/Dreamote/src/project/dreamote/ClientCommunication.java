package project.dreamote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ClientCommunication {

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
//        ip = "192.168.0.197"; // Niclas
        //sendCommand("Connecting");    //Establish connection.
        clientSocket = null;
        createSocket();
    }
    
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
    
    public boolean closeSocket(){
    	if(clientSocket != null){
    		clientSocket.close();
    		return true;
    	}
    	return false;
    }
    
    public boolean updateServerInfo(String ip, int port) {
    	this.serverIP = ip;
    	this.serverPort = port;
    	
    	return createSocket();
    }
    
    

}
