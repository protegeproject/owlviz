package uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: May 18, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DotPreParser
{
    private static Logger logger = Logger.getLogger(DotPreParser.class);

    static String preParse(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringWriter sr = new StringWriter();

            try
            {
                String line = br.readLine();

                while(line != null)
                {

                    if(line.endsWith("\\"))
                    {
                        // We need to concatenate the next line

                        while(line.endsWith("\\"))
                        {
                            sr.write(line.substring(0, line.length() - 1));

                            line = br.readLine();
                        }
                    }

                    sr.write(line);

                    line = br.readLine();
                }
            }
            catch(IOException e)
            {
                logger.error(e);
            }
            return sr.toString();
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e);
        }
        return null;
    }
}

