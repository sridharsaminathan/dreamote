using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace DreamoteServer
{
    public class HandleAction
    {
        private PerformAction pa;
        private ServerCommunication serverCommunication;
        
        public HandleAction(ServerCommunication serverCommunication)
        {
            pa = new PerformAction();
            this.serverCommunication = serverCommunication;
        }

        public void performAction(String actionStr)
        {
            String[] splitStr = actionStr.Split(';');

            
            switch(int.Parse(splitStr[0]))
            {
                case ServerConstants.ACTION_MOUSE_MOVE:
                    if(splitStr.Length == 3)
                        PerformAction.SetCursorPosition(int.Parse(splitStr[1]), int.Parse(splitStr[2]));
                    break;

                case ServerConstants.ACTION_MOUSE_RIGHT_PRESS:
                    if (splitStr.Length == 1)
                        pa.RightMousePress();
                    break;
                case ServerConstants.ACTION_MOUSE_LEFT_PRESS:
                    if (splitStr.Length == 1)
                        pa.LeftMousePress();
                    break;
                case ServerConstants.ACTION_MOUSE_RIGHT_RELEASE:
                    if (splitStr.Length == 1)
                        pa.RightMouseRelease();
                    break;
                case ServerConstants.ACTION_MOUSE_LEFT_RELEASE:
                    if (splitStr.Length == 1)
                        pa.LeftMouseRelease();
                    break;
                case ServerConstants.ACTION_EVENT_KEYBOARD:
                    if (splitStr.Length > 1)
                    {
                        pa.sendKeyBoardKey(actionStr.Substring(actionStr.IndexOf(';')+1));
                    }
                    break;
                case ServerConstants.ACTION_GET_OPEN_WINDOWS:
                    String supported;
                    String other;
                    pa.GetOpenWindows(out supported, out other);
                    serverCommunication.send( MessageGenerator.CreateStringOpenSupportedWindows(supported));
                    serverCommunication.send(MessageGenerator.CreateStringOpenOtherWindows(other));
                    Console.WriteLine("Supported programs " + supported);
                    Console.WriteLine("Other " + other);
                    break;
                case ServerConstants.ACTION_SCROLL_UP:
                    pa.ScrollWheelUp();
                    break;
                case ServerConstants.ACTION_SCROLL_DOWN:
                    pa.ScrollWheelDown();
                    break;
                case ServerConstants.ACTION_SET_VOLUME:
                    if (splitStr.Length == 2)
                    {
                        int vol = 0;
                        if (int.TryParse(splitStr[1], out vol))
                        {
                            pa.SetMasterVolume(vol);
                        }
                    }
                    break;
                case ServerConstants.ACTION_GET_VOLUME:
                    serverCommunication.send(MessageGenerator.CreateStringGetVolume(pa.GetMasterVolume()));
                    break;
                case ServerConstants.ACTION_SET_FOCUS_WINDOW:
                    if (splitStr.Length == 2)
                    {
                        pa.SetActiveWindow(splitStr[1]);
                    }
                    break;
                case ServerConstants.ACTION_MEDIA_PLAY_PAUSE:
                    pa.MediaPausePlay();
                    break;
                case ServerConstants.ACTION_MEDIA_NEXT:
                    pa.MediaNext();
                    break;
                case ServerConstants.ACTION_MEDIA_PREVIOUS:
                    pa.MediaPrevious();
                    break;
            }
                
        }

    }
}
