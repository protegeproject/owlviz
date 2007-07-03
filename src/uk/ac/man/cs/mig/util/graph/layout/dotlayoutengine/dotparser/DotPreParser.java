package uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser;

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

	static Reader preParse(InputStream is)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringWriter sr = new StringWriter();

		try
		{
			String line = br.readLine();

			while(line != null)
			{

				if(line.endsWith("\\"))
				{
					// We need to concatente the next line

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
			e.printStackTrace();
		}

		return new StringReader(sr.toString());
	}
}

