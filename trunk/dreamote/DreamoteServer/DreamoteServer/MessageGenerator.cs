using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DreamoteServer
{
    public class MessageGenerator
    {
        

        public static String CreateStringOpenSupportedWindows(String openWindows)
        {
            return "" + ServerConstants.ACTION_GET_OPEN_SUPPORTED_WINDOWS_REPLY + ";" + openWindows;
        }

        public static String CreateStringOpenOtherWindows(String openWindows)
        {
            return "" + ServerConstants.ACTION_GET_OPEN_OTHER_WINDOWS_REPLY + ";" + openWindows;
        }


        public static String CreateStringGetVolume(int vol)
        {
            return ServerConstants.ACTION_GET_VOLUME.ToString() + ";" + vol.ToString();
        }

       


    }
}
