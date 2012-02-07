using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DreamoteServer
{
    public class MessageGenerator
    {
        

        public static String CreateStringOpenWindows(String openWindows)
        {
            return "" + ServerConstants.ACTION_GET_OPEN_WINDOWS_REPLY + openWindows;

        }


    }
}
