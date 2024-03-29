﻿using System;
using System.Net;
using System.Net.Sockets;
using System.Text;



namespace DreamoteServer
{
    public class ServerCommunication
    {
        private HandleAction handleAction;
        private IPEndPoint sender;
        private UdpClient newsock = null;
        private bool threadRunning = true;
        private int port;


        public ServerCommunication(int port)
        {
            

            this.port = port;
        }


        public void receive()
        {
            byte[] data = new byte[1024];
            IPEndPoint ipep = new IPEndPoint(IPAddress.Any, port);
            sender = new IPEndPoint(IPAddress.Any, 0);
            newsock = new UdpClient(ipep);

            handleAction = new HandleAction(this);



            while (threadRunning)
            {
                try
                {
                    data = newsock.Receive(ref sender);
                    handleAction.performAction(Encoding.UTF8.GetString(data, 0, data.Length));
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                }
            }
        }

        public void send(String msg)
        {
            try
            {
                byte[] sendData = new byte[1024];
                sendData = Encoding.UTF8.GetBytes(msg);
                newsock.Send(sendData, sendData.Length, sender);
                Console.WriteLine("[Sent]" + msg);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        public void CloseConnections()
        {
            threadRunning = false;
            if (newsock != null)
                try
                {

                    newsock.Close();

                }
                catch (SocketException e)
                {
                    Console.WriteLine(e.Message);
                }

        }

    }
}