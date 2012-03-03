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
        public const int ACTION_BROADCAST = 0x07;
        public const int ACTION_BROADCAST_REPLY = 0x08;
        public const int ACTION_GET_OPEN_SUPPORTED_WINDOWS_REPLY = 0x09;
        
        public const int ACTION_SCROLL_UP = 0x0C;
        public const int ACTION_SCROLL_DOWN = 0x0D;

        public const int ACTION_SET_VOLUME = 0x0E;
        public const int ACTION_GET_VOLUME = 0x0F;

        public const int ACTION_SET_FOCUS_WINDOW = 0x10;
        public const int ACTION_GET_OPEN_OTHER_WINDOWS_REPLY = 0x11;

        //mediabuttons
        public const int ACTION_MEDIA_PLAY_PAUSE = 0x12;
        public const int ACTION_MEDIA_NEXT = 0x13;
        public const int ACTION_MEDIA_PREVIOUS  = 0x14;
        public const int ACTION_MAXIMIZE_OR_RESTORE_WINDOW = 0x15;

       public const int BROADCAST_PORT = 9050;
    }
}
