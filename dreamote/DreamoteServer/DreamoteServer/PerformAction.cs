using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using System.Management;
using System.Diagnostics;
using Microsoft.Win32;
using System.Net;
using System.Net.Sockets;

namespace DreamoteServer
{
    public class PerformAction
    {
        //Mouse Constants
        private const int MOUSEEVENT_LEFTDOWN = 0x02;
        private const int MOUSEEVENT_LEFTUP = 0x04;
        private const int MOUSEEVENT_RIGHTDOWN = 0x08;
        private const int MOUSEEVENT_RIGHTUP = 0x10;
        private const int MOUSEEVENT_MIDDLEDOWN = 0x020;
        private const int MOUSEEVENT_MIDDLEUP = 0x040;
        private const int MOUSEEVENT_WHEEL = 0x800;


        //Scroll Constants
        private const int WM_SCROLL = 276; // Horizontal scroll
        private const int WM_VSCROLL = 277; // Vertical scroll
        private const int SB_LINEUP = 0; // Scrolls one line up
        private const int SB_LINELEFT = 0;// Scrolls one cell left
        private const int SB_LINEDOWN = 1; // Scrolls one line down
        private const int SB_LINERIGHT = 1;// Scrolls one cell right
        private const int SB_PAGEUP = 2; // Scrolls one page up
        private const int SB_PAGELEFT = 2;// Scrolls one page left
        private const int SB_PAGEDOWN = 3; // Scrolls one page down
        private const int SB_PAGERIGTH = 3; // Scrolls one page right
        private const int SB_PAGETOP = 6; // Scrolls to the upper left
        private const int SB_LEFT = 6; // Scrolls to the left
        private const int SB_PAGEBOTTOM = 7; // Scrolls to the upper right
        private const int SB_RIGHT = 7; // Scrolls to the right
        private const int SB_ENDSCROLL = 8; // Ends scroll




        [DllImport("user32.dll")]
        private static extern void mouse_event(int dwFlags, int dx, int dy, int dwData, int dwExtraInfo);

        [DllImport("user32.dll", EntryPoint = "SetCursorPos")]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool SetCursorPos(int X, int Y);

        [DllImport("user32.dll")]
        static extern IntPtr GetForegroundWindow();

        [DllImport("user32.dll")]
        static extern int GetWindowText(IntPtr hWnd, StringBuilder text, int count);

        [DllImport("user32.dll")]
        static extern bool SetForegroundWindow(IntPtr hWnd);

        [DllImport("user32.dll", CharSet = CharSet.Auto)]
        private static extern int SendMessage(IntPtr hWnd, int wMsg, IntPtr wParam, IntPtr lParam);

        public PerformAction()
        {

        }

        public static void SetCursorPosition(int xDiff, int yDiff)
        {
            SetCursorPos(Cursor.Position.X + xDiff, Cursor.Position.Y + yDiff);
        }


        private void DoMouseEvent(int dwFlags)
        {
            mouse_event(dwFlags, Cursor.Position.X, Cursor.Position.Y, 0, 0);
        }


        public void LeftMousePress()
        {
            DoMouseEvent(MOUSEEVENT_LEFTDOWN);
        }
        public void LeftMouseRelease()
        {
            DoMouseEvent(MOUSEEVENT_LEFTUP);
        }

        public void RightMousePress()
        {
            DoMouseEvent(MOUSEEVENT_RIGHTDOWN);
        }
        public void RightMouseRelease()
        {
            DoMouseEvent(MOUSEEVENT_RIGHTUP);
        }
        public void MiddleMousePress()
        {
            DoMouseEvent(MOUSEEVENT_MIDDLEDOWN);
        }
        public void MiddleMouseRelease()
        {
            DoMouseEvent(MOUSEEVENT_MIDDLEUP);
        }

        public void ScrollWheelDown()
        {
         
            mouse_event(MOUSEEVENT_WHEEL,0, 0, -50, 0);
            
        }
        public void ScrollWheelUp()
        {

            mouse_event(MOUSEEVENT_WHEEL, 0, 0, 50, 0);
        }

        
        //gets the title of active window
        public string GetActiveWindowTitle()
        {
            const int nChars = 256;
            IntPtr handle = IntPtr.Zero;
            StringBuilder Buff = new StringBuilder(nChars);
            handle = GetForegroundWindow();

            if (GetWindowText(handle, Buff, nChars) > 0)
            {
                return Buff.ToString();
            }
            return null;
        }
        private IntPtr GetActiveWindowHandle()
        {
            return GetForegroundWindow();
        }

        //set the activ window
        public Boolean SetActiveWindow(String program)
        {
            System.Diagnostics.Process[] p = System.Diagnostics.Process.GetProcessesByName(program);
            if (p.Length > 0)
            {
                SetForegroundWindow(p[0].MainWindowHandle);
                return true;
            }
            return false;
        }

        public String GetOpenWindows()
        {
            IEnumerable<Process> processes = Process.GetProcesses().Where(p => p.MainWindowHandle != IntPtr.Zero && !p.ProcessName.Equals("explorer") && !p.ProcessName.Equals("DreamoteServer"));
           

            StringBuilder builder = new StringBuilder();
            
            foreach (var process in processes)
            {
                builder.Append(";");
                builder.Append(process.ProcessName +  ":" + process.MainWindowTitle);
                
                
            }
            builder.Remove(0, 1);
            return builder.ToString();
        }

        /*private void Shutdown()
        {
            
            ManagementBaseObject mboShutdown = null;
            ManagementClass mcWin32 = new ManagementClass("Win32_OperatingSystem");
            mcWin32.Get();

            // You can't shutdown without security privileges
            mcWin32.Scope.Options.EnablePrivileges = true;
            ManagementBaseObject mboShutdownParams =
                     mcWin32.GetMethodParameters("Win32Shutdown");

            // Flag 1 means we want to shut down the system. Use "2" to reboot.
            mboShutdownParams["Flags"] = "1";
            mboShutdownParams["Reserved"] = "0";
            foreach (ManagementObject manObj in mcWin32.GetInstances())
            {
                mboShutdown = manObj.InvokeMethod("Win32Shutdown",
                                               mboShutdownParams, null);
            }
        }*/

        //sends a keyboard key to the application in focus
        public void sendKeyBoardKey(String key)
        {
            if(key.Equals("{SPACE}"))
            {
                SendKeys.SendWait(" ");
            }
            else if(key.Equals("{BACKSPACE}") || key.Equals("\n") || key.Equals("{LEFT}") || key.Equals("{UP}") || key.Equals("{RIGHT}") || key.Equals("{DOWN}")) {
                SendKeys.SendWait(key);
            }
            else
            {
                try
                {
                    for (int i = 0; i < key.Length; i++)
                    {
                        SendKeys.SendWait("{" + key.Substring(i, 1) + "}");
                    }
                }
                catch (ArgumentException e)
                {
                    Console.WriteLine(e.Message);
                }
            }  
        }

        public List<String> GetInstalledApps()
        {
            List<String> lstInstalled = new List<String>();

            string uninstallKey = @"SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall";
            using (RegistryKey rk = Registry.LocalMachine.OpenSubKey(uninstallKey))
            {
                foreach (string skName in rk.GetSubKeyNames())
                {
                    using (RegistryKey sk = rk.OpenSubKey(skName))
                    {
                        try
                        {
                            String program = (String)sk.GetValue("DisplayName");
                            lstInstalled.Add(program);
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine(ex.Message);
                        }
                    }
                }
            }
            return lstInstalled;
        }

        public String GetComputerName()
        {
            
            return System.Environment.MachineName;
        }

         

        public static String GetIpAddres()
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
