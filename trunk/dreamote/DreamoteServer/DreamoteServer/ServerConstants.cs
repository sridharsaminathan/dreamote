using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DreamoteServer
{
   public class ServerConstants
    {
        public const int ACTION_MOUSE_MOVE = 0x00;
        public const int ACTION_MOUSE_RIGHT_PRESS = 0x01;
        public const int ACTION_MOUSE_LEFT_PRESS = 0x02;
        public const int ACTION_MOUSE_RIGHT_RELEASE = 0x03;
        public const int ACTION_MOUSE_LEFT_RELEASE = 0x04;
        public const int ACTION_EVENT_KEYBOARD = 0x05;
        public const int ACTION_GET_OPEN_WINDOWS = 0x06;
        public const int ACTION_CONNECT = 0x07;
        public const int ACTION_CONNECT_REPLY = 0x08;
        public const int ACTION_GET_OPEN_WINDOWS_REPLY = 0x09;
    }
}
