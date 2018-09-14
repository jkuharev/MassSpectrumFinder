import org.apache.commons.cli.Option;

import de.mz.jk.jsix.cli.SimpleCLI;

class AppCLI extends SimpleCLI
	{
		public AppCLI()
		{
			super();
			setHelpHeader(
				"\nList all sample names and corresponding MassSpectrum.xml files\nfrom a PLGS project  "
						+ "to a CSV table.\n\n" );
		setHelpFooter( "\n" + MassSpectrumFinder.class.getName() + ", build " + MassSpectrumFinder.buildVersion + "\n (c) Dr. Joerg Kuharev" );
		}

		@Override public Option[] getDefaultOptions()
		{
			return new Option[] 
			{
				Option.builder( "o" ).longOpt( "output" ).argName( "path" ).desc( "CSV file path" ).hasArg().required( true ).build(),
				Option.builder( "i" ).longOpt( "input" ).argName( "path" ).desc( "PLGS project folder path" ).hasArgs().required( true ).build(),
				Option.builder( "d" ).longOpt( "depth" ).argName( "0..9" ).desc( "subfolder depth for searching files (default 3)" )
							.hasArg().required( false ).build(), getDefaultHelpOption()
			};
		}

		@Override public String getExecutableJarFileName()
		{
			return MassSpectrumFinder.class.getName();
		}
	}