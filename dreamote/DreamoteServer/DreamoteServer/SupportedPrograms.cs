using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace DreamoteServer
{
    public class SupportedPrograms
    {



        public static List<String[]> GetSupportedPrograms()
        {
            List<String[]> supportedPrograms = new List<String[]>();
            try
            {
                
                using (StreamReader sr = new StreamReader("supported_programs.txt"))
                {
                    String line;
                    while ((line = sr.ReadLine()) != null)
                    {
                        if (!line.StartsWith("#"))
                        {
                            String[] program = line.Split(':');
                            supportedPrograms.Add(program);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("The file could not be read:");
                Console.WriteLine(e.Message);
            }

            return supportedPrograms;
        }



    }
}
