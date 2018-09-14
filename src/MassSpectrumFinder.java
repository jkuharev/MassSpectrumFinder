import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;

import de.mz.jk.jsix.libs.XFiles;
import de.mz.jk.jsix.libs.XJava;
import de.mz.jk.jsix.utilities.CSVUtils;
import de.mz.jk.plgs.reader.MassSpectrumReader;

/** MassSpectrumFinder, , Nov 23, 2017*/
/**
 * <h3>{@link MassSpectrumFinder}</h3>
 * @author jkuharev
 * @version Nov 23, 2017 11:04:22 AM
 */
public class MassSpectrumFinder
{
	public static final String buildVersion = "20171123";
	private File plgsRoot = null;
	private File csvFile = null;
	private String SearchFilePattern = "MassSpectrum.xml";
	private final int defaultRecursionDepth = 3;
	private int recursionDepth = defaultRecursionDepth;

	private AppCLI appCLI = new AppCLI();

	public static void main(String[] args)
	{
		new MassSpectrumFinder( args );
	}

	public MassSpectrumFinder(String[] args)
	{
		initCli( args );
		run();
	}
	
	private void run()
	{
		try
		{
			List<File> files = XFiles.getFileList( plgsRoot, SearchFilePattern, recursionDepth, true, false );
			System.out.println( files.size() + " " + SearchFilePattern + " files found." );
			PrintStream csvStream = new PrintStream( new FileOutputStream( csvFile, false ) );
			CSVUtils csv = new CSVUtils( csvStream );
			csv.txtCell( "sample" ).colSep().txtCell( "file" ).endLine();
			int i = 0;
			int digits = ( "" + files.size() ).length();
			for ( File file : files )
			{
				i++;
				String ooi = XJava.numString( i, digits );
				System.out.print( ooi + ":	" );
				MassSpectrumReader reader = new MassSpectrumReader();
				reader.openFile( file );
				Map<String, String> info = reader.getMetaInfo();
				String sample = info.get( "SampleDescription" );
				System.out.println( sample );
				csv.txtCell( sample ).colSep().txtCell( file.getAbsolutePath() ).endLine();
			}
			csv.close();
			System.out.println( "done!" );
		}
		catch (Exception e)
		{}
	}

	private void initCli(String[] args)
	{
		try
		{
			CommandLine cli = appCLI.parseCommandLine( args );
			// take parameters
			csvFile = new File( cli.getOptionValue( "o" ) );
			plgsRoot = new File( cli.getOptionValue( "i" ) );
			if (cli.hasOption( "d" ))
			{
				try
				{
					int d = Integer.parseInt( cli.getOptionValue( "d" ) );
					if (d < 0 || d > 9) throw new Exception( "recursion depth must be in range 0..9" );
					recursionDepth = d;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					System.out.println( "falling back to default recursion depth (=" + defaultRecursionDepth + ")" );
				}
			}
			//
			if (cli.hasOption( "h" ))
			{
				appCLI.showHelp();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			appCLI.showHelp();
		}
	}
}
