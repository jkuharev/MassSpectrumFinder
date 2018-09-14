import java.io.File;
import java.util.List;
import java.util.Map;

import de.mz.jk.jsix.libs.XFiles;
import de.mz.jk.plgs.reader.MassSpectrumReader;

/** MassSpectrumFinder, , Oct 24, 2017*/
/**
 * <h3>{@link FindMassSpectrumXMLinPLGSproject}</h3>
 * @author jkuharev
 * @version Oct 24, 2017 3:19:22 PM
 */
public class FindMassSpectrumXMLinPLGSproject
{
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		String plgsProjectPath = "/Volumes/D/data/plgs302b5/projects/2017-086 Peptide Bernhard/root/Proj__15053071455980_029465567643771617";
		List<File> files = XFiles.getFileList( new File( plgsProjectPath ), "MassSpectrum.xml", 3, true, false );
		for ( File file : files )
		{
			MassSpectrumReader reader = new MassSpectrumReader();
			reader.openFile( file );
			Map<String, String> info = reader.getMetaInfo();
			System.out.println( info.get( "SampleDescription" ) + "," + file.getAbsolutePath() );
		}
	}
}

