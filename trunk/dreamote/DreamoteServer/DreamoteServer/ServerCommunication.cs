﻿using System;
using System.Net;
using System.Net.Sockets;
using System.Text;



namespace DreamoteServer
{
    public class ServerCommunication
    {

        IPEndPoint sender;
        UdpClient newsock;
        public ServerCommunication()
        {

        }
        public void receive()
        {
            byte[] data = new byte[1024];
            IPEndPoint ipep = new IPEndPoint(IPAddress.Any, 9876);
            newsock = new UdpClient(ipep);
            sender = new IPEndPoint(IPAddress.Any, 0);

            data = newsock.Receive(ref sender);
            //send("Connected");
            while (true)
            {
                data = newsock.Receive(ref sender);
                //Console.WriteLine(Encoding.ASCII.GetString(data, 0, data.Length));

                String[] spkit = Encoding.ASCII.GetString(data, 0, data.Length).Split(';');
                PerformAction.SetCursorPosition(int.Parse(spkit[1]), int.Parse(spkit[2]));
                //ac.action(Encoding.ASCII.GetString(data, 0, data.Length));
                //send(Encoding.ASCII.GetString(data, 0, data.Length));
            }
        }
        public void send(String msg)
        {

            //        Console.WriteLine("Message received from {0}:", sender.ToString());
            //        Console.WriteLine(Encoding.ASCII.GetString(data, 0, data.Length));


            byte[] sendData = new byte[1024];
            sendData = Encoding.ASCII.GetBytes(msg);
            newsock.Send(sendData, sendData.Length, sender);


        }
    }
}