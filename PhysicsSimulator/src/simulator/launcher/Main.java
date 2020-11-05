package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

/*
 * Examples of command-line parameters:
 * 
 *  -h
 *  -i resources/examples/ex4.4body.txt -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl ftcg
 *  -i resources/examples/ex4.4body.txt -o resources/examples/ex4.4body.out -s 100 -gl nlug
 *
 */

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.factories.BasicBodyBuilder;
import simulator.factories.Builder;
import simulator.factories.BuilderBasedFactory;
import simulator.factories.Factory;
import simulator.factories.FallingToCenterGravityBuilder;
import simulator.factories.MassLossingBodyBuilder;
import simulator.factories.NewtonUniversalGravitationBuilder;
import simulator.factories.NoGravityBuilder;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

public class Main {

	// default values for some parameters
	//
	private final static Double _dtimeDefaultValue = 2500.0;

	// some attributes to stores values corresponding to command-line parameters
	//
	private static Double _dtime = null;
	private static String _inFile = null;
	private static JSONObject _gravityLawsInfo = null;
	private static int _steps = 150;
	private static String _outFile = null;
	private static String mode = "batch";

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<GravityLaws> _gravityLawsFactory;

	private static void init() {
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>(); 
		bodyBuilders.add(new BasicBodyBuilder()); 
		bodyBuilders.add(new MassLossingBodyBuilder());
		_bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);

		ArrayList<Builder<GravityLaws>> gravityLawsBuilders = new ArrayList<>(); 
		gravityLawsBuilders.add(new NewtonUniversalGravitationBuilder()); 
		gravityLawsBuilders.add(new FallingToCenterGravityBuilder()); 
		gravityLawsBuilders.add(new NoGravityBuilder());
		_gravityLawsFactory = new BuilderBasedFactory<GravityLaws>(gravityLawsBuilders);
	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseMode(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseStepsOption(line);
			parseDeltaTimeOption(line);
			parseGravityLawsOption(line);

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

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());
		
		//output file
		cmdLineOptions.addOption(Option.builder("o").longOpt("output").hasArg().desc("JSON output file.").build());

		//steps option
		cmdLineOptions.addOption(Option.builder("s").longOpt("steps").hasArg().desc("Steps option.").build());
		
		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Execution Mode. Possible values: ’batch’\n" + 
				"(Batch mode), ’gui’ (Graphical User Interface mode). Default value: ’batch’.").build());
		

		// gravity laws -- there is a workaround to make it work even when
		// _gravityLawsFactory is null. 
		//
		String gravityLawsValues = "N/A";
		String defaultGravityLawsValue = "N/A";
		if (_gravityLawsFactory != null) {
			gravityLawsValues = "";
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gravityLawsValues.length() > 0) {
					gravityLawsValues = gravityLawsValues + ", ";
				}
				gravityLawsValues = gravityLawsValues + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
			}
			defaultGravityLawsValue = _gravityLawsFactory.getInfo().get(0).getString("type");
		}
		cmdLineOptions.addOption(Option.builder("gl").longOpt("gravity-laws").hasArg()
				.desc("Gravity laws to be used in the simulator. Possible values: " + gravityLawsValues
						+ ". Default value: '" + defaultGravityLawsValue + "'.")
				.build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}
	
	private static void parseStepsOption(CommandLine line) throws ParseException {
		if (line.hasOption("s")) {
			try {
				_steps = Integer.parseInt(line.getOptionValue("s"));
			}
			catch (NumberFormatException e) {
				throw new ParseException("The step's number is incorrect");
			}
		}
		
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (mode == "batch" & _inFile == null) {
			throw new ParseException("An input file of bodies is required");
		}
	}
	
	private static void parseOutFileOption(CommandLine line) {
		_outFile = line.getOptionValue("o");
	}

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static void parseGravityLawsOption(CommandLine line) throws ParseException {

		// this line is just a work around to make it work even when _gravityLawsFactory
		// is null, you can remove it when've defined _gravityLawsFactory
		if (_gravityLawsFactory == null)
			return;

		String gl = line.getOptionValue("gl");
		if (gl != null) {
			for (JSONObject fe : _gravityLawsFactory.getInfo()) {
				if (gl.equals(fe.getString("type"))) {
					_gravityLawsInfo = fe;
					break;
				}
			}
			if (_gravityLawsInfo == null) {
				throw new ParseException("Invalid gravity laws: " + gl);
			}
		} else {
			_gravityLawsInfo = _gravityLawsFactory.getInfo().get(0);
		}
	}
	
	private static void parseMode(CommandLine line) {
		String m = line.getOptionValue("m");
		if (m != null && m.equalsIgnoreCase("gui")) {
			mode = "gui";
		}
		else {
			mode = "batch";
		}
	}

	private static void startBatchMode() throws Exception {
		InputStream is = new FileInputStream(_inFile);
		OutputStream os = null;
		if (_outFile != null) {
			os = new FileOutputStream(_outFile);
		}
		GravityLaws gravityLaws = _gravityLawsFactory.createInstance(_gravityLawsInfo);
		PhysicsSimulator sim = new PhysicsSimulator(_dtime, gravityLaws);
		Controller ctrl = new Controller(sim,_bodyFactory, _gravityLawsFactory); 
		ctrl.loadBodies(is);
		ctrl.run(_steps, os);
	}
	
	private static void startGUIMode() throws Exception {
		GravityLaws gravityLaws = null;
		InputStream is = null;
		if (_gravityLawsInfo != null) {
			gravityLaws = _gravityLawsFactory.createInstance(_gravityLawsInfo);
		}
		
		PhysicsSimulator sim = new PhysicsSimulator(_dtime, gravityLaws);
		Controller ctrl = new Controller(sim,_bodyFactory, _gravityLawsFactory);
		SwingUtilities.invokeAndWait(new Runnable() {
		    @Override
		    public void run() {
		       new MainWindow(ctrl);
		} });
		sim.setGravityLaws(gravityLaws);
		if (_inFile != null) {
			 is = new FileInputStream(_inFile);
			 ctrl.loadBodies(is);
		}
	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		if (mode == "batch") {
			startBatchMode();
		}
		else if (mode == "gui") {
			startGUIMode();
		}
	}

	public static void main(String[] args) {
		try {
			init();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
