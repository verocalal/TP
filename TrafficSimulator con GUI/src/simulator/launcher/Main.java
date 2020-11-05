package simulator.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.model.*;
import simulator.view.MainWindow;
import simulator.control.*;
import simulator.factories.*;

public class Main {

	private final static Integer _timeLimitDefaultValue = 10;
	private static Integer _nonDefaultTimeValue = -1;
	private static String _inFile = null;
	private static String _outFile = null;
	private static Factory<Event> _eventsFactory = null;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTimeOption(line);
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("time").hasArg().desc("Set time").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Execution Mode. Possible values: ’batch’\n" + 
				"(Batch mode), ’gui’ (Graphical User Interface mode). Default value: ’console’.").build());
		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}
	
	private static void parseTimeOption(CommandLine line) {
		if (line.hasOption("t")) {
			String s = line.getOptionValue("t");
			int tiempo = Integer.parseInt(s);
			setNonDefaultTime(tiempo);
		}
	}
	private static int parseMode(CommandLine line)
	{
		int option=1; // 1 significa guimode, 0 batchmode
		if(line.hasOption("m"))
		{
			String s = line.getOptionValue("m");
			if(s.equals("console"))
				option=0;

		}
		return option;

		
	}
	private static void initFactories() {

		ArrayList<Builder<LightSwitchingStrategy>> lsbs= new ArrayList<>(); 
		lsbs.add(new RoundRobinStrategyBuilder()); 
		lsbs.add(new MostCrowdedStrategyBuilder()); 
		Factory<LightSwitchingStrategy> lssFactory= new BuilderBasedFactory<>(lsbs);
		
		
		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory= new BuilderBasedFactory<>(dqbs);
		
		
		List<Builder<Event>> eventBuilders= new ArrayList<>();
		eventBuilders.add(new NewJunctionEventBuilder(lssFactory, dqsFactory));
		eventBuilders.add(new NewCityRoadEventBuilder()); 
		eventBuilders.add(new NewInterCityRoadEventBuilder()); 
		eventBuilders.add(new NewVehicleEventBuilder());
		eventBuilders.add(new SetContClassEventBuilder()); 
		eventBuilders.add(new SetWeatherEventBuilder()); 


		
		_eventsFactory = new BuilderBasedFactory<>(eventBuilders);

	}

	private static void startBatchMode() throws IOException {
		InputStream in = new FileInputStream(new File(_inFile));
		OutputStream out= _outFile == null ? System.out : new FileOutputStream(new File(_outFile));
		TrafficSimulator sim= new TrafficSimulator();
		Controller ctrl= new Controller(sim, Main._eventsFactory);
		ctrl.loadEvents(in);
		ctrl.run((_nonDefaultTimeValue!=-1)? _nonDefaultTimeValue : _timeLimitDefaultValue, out);
		in.close(); 
		System.out.println("Done!");
	}
	
	private static void startGUIMode() throws IOException
	{
		//TODO
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TrafficSimulator sim= new TrafficSimulator();
				InputStream in = null;
				//try {
					//in = new FileInputStream(new File(_inFile));
					int a;
				//} catch (FileNotFoundException e) {
					//e.printStackTrace();
				//}
				Controller c= new Controller(sim, Main._eventsFactory);
				//c.loadEvents(in);
				new MainWindow(c);
			}
		});

	}
	private static void start(String[] args,int option) throws IOException {
		
		initFactories();
		if(option==1)
			startGUIMode();
		else
		{
			parseArgs(args);
			startBatchMode();
		}
	}

	private static void setNonDefaultTime(int t)
	{
		_nonDefaultTimeValue = t;
	}

	public static void main(String[] args) {
		try {
			Options cmdLineOptions = buildOptions();
			CommandLineParser parser = new DefaultParser();
			CommandLine line = parser.parse(cmdLineOptions, args);
			int option = parseMode(line);
			start(args,option);
			initFactories();
			} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}

	}

}
