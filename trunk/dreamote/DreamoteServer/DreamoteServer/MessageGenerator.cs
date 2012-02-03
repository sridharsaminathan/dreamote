using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DreamoteServer
{
    public class MessageGenerator
    {
        public static String CreateConnectReply(String hostName, String ipAdress)
        {
            return ServerConstants.ACTION_CONNECT_REPLY + ";" + hostName + ";" + ipAdress;
        }

        public static String CreateStringOpenWindows(String openWindows)
        {
            return "" + ServerConstants.ACTION_GET_OPEN_WINDOWS_REPLY + openWindows;

        }


    }
}
