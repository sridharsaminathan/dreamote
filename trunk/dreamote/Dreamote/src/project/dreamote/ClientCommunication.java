package project.dreamote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class ClientCommunication {

    private String serverIP;
    private DatagramSocket clientSocket;
    private InetAddress IPAddress;
    
    /**
     * 
     * @param address String of IP address to the server.
     * @throws SocketException 
     */
    public ClientCommunication(String address){
        this.serverIP = address;
        //sendCommand("Connecting");    //Establish connection.
        clientSocket = null;
        createSocket();
    }
    
    private void createSocket(){
    	
    	try {
    		IPAddress = InetAddress.getByName(serverIP);
			clientSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		
		}catch(UnknownHostException es){
			es.printStackTrace();
		}
    }
    
    
    /**
     * 
     * @param command String of the Command that is going to be executed.
     */
    public void sendCommand(String command){
        try {
            byte[] sendData = new byte[1024];
            sendData = command.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
        }catch (SocketException e) {
            System.out.println("Could not create socket");
        } catch (IOException e) {
            System.out.println("Could not send package");
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
      
}
