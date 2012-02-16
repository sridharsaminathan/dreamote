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
using System.Threading;

namespace DreamoteServer
{
    public partial class DreamoteServerGui : Form
    {
        private const int DEFAULT_PORT = 9876;
        private RegistryKey rkApp = Registry.CurrentUser.OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", true);
        private const String REGISTRY_NAME = "DreamoteServer";
        private ServerCommunication serverCom = null;
        private Broadcast broadcastCom = null;
        private Thread workThread = null;
        private Thread broadcastThread = null;

        public DreamoteServerGui()
        {
            InitializeComponent();
            InitializeGuiComponents();
            
        }

        private void InitializeGuiComponents()
        {
            txt_ip.Text = PerformAction.GetIpAddres();
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
                 //(new PerformAction()).SetMasterVolume(50);
                 
                //start server
                txt_port.Enabled = false;
                lbl_port_invalid.Visible = false;
                btn_start_server.Enabled = false;

                broadcastCom = new Broadcast(port);
                broadcastThread = new Thread(new ThreadStart(broadcastCom.broadcastReceive));
                broadcastThread.Start();


                serverCom = new ServerCommunication(port);
                workThread = new Thread(new ThreadStart(serverCom.receive));
                workThread.Start();

            }
            else
            {
                
                //show popup about wrong port
                lbl_port_invalid.Visible = true;
            }
            
            
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

        private void btn_stop_server_Click(object sender, EventArgs e)
        {
            btn_start_server.Enabled = true;
            txt_port.Enabled = true;
            if (serverCom != null)
            {
                serverCom.CloseConnections();
                if (workThread != null)
                    workThread.Abort();
                
            }
            if (broadcastCom != null)
            {
                broadcastCom.CloseConnections();
                if (broadcastThread != null)
                    broadcastThread.Abort();
            }


        }
    }
}
