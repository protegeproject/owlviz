package org.coode.owlviz.util.graph.layout.dotlayoutengine.dotparser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

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
    private static Logger logger = LoggerFactory.getLogger(DotPreParser.class);

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
                logger.error(e.getMessage());
            }
            return sr.toString();
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}

