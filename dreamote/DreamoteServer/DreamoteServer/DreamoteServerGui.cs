using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Net;
using System.Net.Sockets;

namespace DreamoteServer
{
    public partial class DreamoteServerGui : Form
    {
        private const int DEFAULT_PORT = 53135;

        public DreamoteServerGui()
        {
            InitializeComponent();
            txt_ip.Text = getIpAddres();
            txt_port.Text = DEFAULT_PORT.ToString();
        }

        private void btn_start_server_Click(object sender, EventArgs e)
        {
            //read port number
            int port = DEFAULT_PORT;
            if (int.TryParse(txt_port.Text, out port))
            {
                //start server
                txt_port.Enabled = false;
            }

        }

        

        private String getIpAddres()
        {
            IPHostEntry host;
            string localIP = "?";
            host = Dns.GetHostEntry(Dns.GetHostName());
            foreach (IPAddress ip in host.AddressList)
            {
                if (ip.AddressFamily == AddressFamily.InterNetwork)
                {
                    localIP = ip.ToString();
                }
            }
            return localIP;
        }
    }
}
