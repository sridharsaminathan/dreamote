using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

namespace DreamoteServer
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            DreamoteServerGui gui = new DreamoteServerGui();
            gui.Text = "Dreamote Server GUI";
            
            Application.Run(gui);
        }
    }
}
