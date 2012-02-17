using System;

using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Net;


namespace DreamoteServer
{
    public class Broadcast

    {
        private int port;
        private IPEndPoint sender;
        private Socket sock = null;
        private bool threadRunning = true;


        public Broadcast(int port)
        {
            this.port = port;
        }

        public void broadcastReceive()
        {
            sock = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);
            IPEndPoint iep = new IPEndPoint(IPAddress.Any, ServerConstants.BROADCAST_PORT);
            sock.Bind(iep);

            
            EndPoint ep = (EndPoint)iep;
            byte[] data = new byte[1024];
            while (threadRunning)
            {
                int recv = sock.ReceiveFrom(data, ref ep);
                string stringData = Encoding.UTF8.GetString(data, 0, recv);
                String[] bIP = null;
                if (ep.ToString() != null)
                    bIP = ep.ToString().Split(':');
                    
               
                    Console.WriteLine("Broadcast Received");

                String sendData = handleBroadcast(stringData, port);
                
                if (sendData != null)
                {
                    Console.WriteLine(sendData);
                    
                      broadcastSend(sendData,bIP);
                }
            }
            //sock.Close();
        }

        public void broadcastSend(String strData, String[] ip)
        {
            byte[] data = new byte[1024];
            sock = new Socket(AddressFamily.InterNetwork, SocketType.Dgram,ProtocolType.Udp);
            data = Encoding.UTF8.GetBytes(strData);
            sender = new IPEndPoint(IPAddress.Parse(ip[0]), ServerConstants.BROADCAST_PORT);

            sock.SendTo(data, sender);
        }

        private String handleBroadcast(String data, int port)
        {
            String[] splitStr = data.Split(';');
            if (ServerConstants.ACTION_BROADCAST == int.Parse(data))
            {
                return ServerConstants.ACTION_BROADCAST_REPLY.ToString() + ";" + PerformAction.GetComputerName() + ";" + PerformAction.GetIpAddres() + ";" + port;
                
            }
            return null;
        }

        public void CloseConnections()
        {
            threadRunning = false;
            try
            {
                if (sock != null)
                {

                    sock.Close();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

        }


    }
}
