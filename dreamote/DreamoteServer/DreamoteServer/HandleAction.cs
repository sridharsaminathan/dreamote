using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace DreamoteServer
{
    public class HandleAction
    {
        PerformAction pa;
        
        public HandleAction()
        {
            pa = new PerformAction();
        }

        public void performAction(String actionStr)
        {
            String[] splitStr = splitString(actionStr);

            
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
            }
                
        }

        public String[] splitString(String str)
        {

            String[] splitStr = str.Split(';');
            return splitStr;
        }

    }
}
