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
using Microsoft.Win32;

namespace DreamoteServer
{
    public partial class DreamoteServerGui : Form
    {
        private const int DEFAULT_PORT = 53135;
        private RegistryKey rkApp = Registry.CurrentUser.OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", true);
        private const String REGISTRY_NAME = "DreamoteServer";

        public DreamoteServerGui()
        {
            InitializeComponent();
            InitializeGuiComponents();
            
        }

        private void InitializeGuiComponents()
        {
            txt_ip.Text = getIpAddres();
            txt_port.Text = DEFAULT_PORT.ToString();

            if (rkApp.GetValue(REGISTRY_NAME) == null)
            {
                checkbox_boot.Checked = false;
            }
            else
            {
                checkbox_boot.Checked = true;
            }

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
            PerformAction per = new PerformAction();
            System.Threading.Thread.Sleep(5000);
            per.ScrollWheel();
            
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

        private void DreamoteServerGui_Resize(object sender, EventArgs e)
        {
            if (FormWindowState.Minimized == this.WindowState)
            {
                notifyIcon1.Visible = true;
                notifyIcon1.ShowBalloonTip(500);
                this.Hide();
            }else if(FormWindowState.Normal == this.WindowState)
            {
                notifyIcon1.Visible = false;

            }

        }

        private void checkbox_boot_CheckStateChanged(object sender, EventArgs e)
        {
            if (checkbox_boot.CheckState == CheckState.Checked)
            {
                rkApp.SetValue(REGISTRY_NAME, Application.ExecutablePath.ToString());
            }
            else if (checkbox_boot.CheckState == CheckState.Unchecked)
            {
                rkApp.DeleteValue(REGISTRY_NAME, false);
            }

        }
    }
}
