using System;
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
        private bool retreiveData = true;
        private int port;


        public ServerCommunication( int port)
        {
            handleAction = new HandleAction();
            this.port = port;
        }


        public void receive()
        {
            byte[] data = new byte[1024];
            IPEndPoint ipep = new IPEndPoint(IPAddress.Any, port);
            newsock = new UdpClient(ipep);
            sender = new IPEndPoint(IPAddress.Any, 0);

           
            while (retreiveData)
            {
                data = newsock.Receive(ref sender);
                handleAction.performAction(Encoding.ASCII.GetString(data, 0, data.Length));
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

        public void CloseConnections()
        {
            retreiveData = false;
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