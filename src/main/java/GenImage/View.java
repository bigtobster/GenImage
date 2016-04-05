package GenImage;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.logging.Logger;

/**
 * The Interface to GenImage Created by Toby Leheup on 04/04/16 for GenImage.GenImage.
 *
 * @author BigTobster
 */
@SuppressWarnings("ClassWithTooManyFields")
public final class View
{
	private static final String BOX_SIZE_LONG_OPTION        = "boxSize";
	private static final String BOX_SIZE_OPTION_DESC        = "The amount of pixel placement tolerance in fitness comparison";
	private static final String BOX_SIZE_SHORT_OPTION       = "b";
	private static final String CROSSOVER_PROB_LONG_OPTION  = "crossover";
	private static final String CROSSOVER_PROB_OPTION_DESC  = "The probability that a candidate will be selected for crossover";
	private static final String CROSSOVER_PROB_SHORT_OPTION = "c";
	private static final String DUMP_LONG_OPTION            = "dump";
	private static final String DUMP_OPTION_DESC            = "The number of best candidates to export";
	private static final String DUMP_SHORT_OPTION           = "d";
	private static final String HELP_LONG_OPTION            = "help";
	private static final String HELP_OPTION_DESC            = "Display help and usage info";
	private static final String HELP_SHORT_OPTION           = "h";
	private static final String IMPORT_LONG_OPTION          = "input";
	private static final String IMPORT_OPTION_DESC          = "Path to directory containing images to import";
	private static final String IMPORT_SHORT_OPTION         = "i";
	private static final String ITERATIONS_LONG_OPTION      = "iterations";
	private static final String ITERATIONS_OPTION_DESC      = "The number of iterations before dumping results and awaiting further instructions";
	private static final String ITERATIONS_SHORT_OPTION     = "l";
	private static final Logger LOGGER                      = Logger.getLogger(View.class.getName());
	private static final String MUTATION_PROB_LONG_OPTION   = "mutation";
	private static final String MUTATION_PROB_OPTION_DESC   = "The probability that a candidate will be mutated";
	private static final String MUTATION_PROB_SHORT_OPTION  = "m";
	private static final String NOVELTY_BAR_LONG_OPTION     = "noveltyBarrier";
	private static final String
								NOVELTY_BAR_OPTION_DESC
															= "The minimum percentage of pixels from a single input image which a candidate must match before being eliminated due to lack of novelty";
	private static final String NOVELTY_BAR_SHORT_OPTION    = "n";
	private static final String POP_SIZE_LONG_OPTION        = "populationSize";
	private static final String POP_SIZE_OPTION_DESC        = "The size of the candidate population";
	private static final String POP_SIZE_SHORT_OPTION       = "s";
	private static final String RETENTION_LONG_OPTION       = "retention";
	private static final String
									 RETENTION_OPTION_DESC
															 = "The percentage (as a decimal) of the population to retain during tournament selection";
	private static final String      RETENTION_SHORT_OPTION  = "r";
	private static final String      TOURN_SIZE_LONG_OPTION  = "tournamentSize";
	private static final String      TOURN_SIZE_OPTION_DESC  = "The number of candidates in the tournaments during Tournament selection";
	private static final String      TOURN_SIZE_SHORT_OPTION = "t";
	private              CommandLine commandLine             = null;

	/**
	 * Constructor for View. Initialises the options
	 *
	 * @param args The arguments on the command line
	 */
	public View(final String[] args)
	{
		super();
		final Options options = new Options();
		options.addOption(View.genImportOption());
		options.addOption(View.genPopSizeOption());
		options.addOption(View.genBoxSizeOption());
		options.addOption(View.genRetentionOption());
		options.addOption(View.genTournSizeOption());
		options.addOption(View.genNoveltyBarrierOption());
		options.addOption(View.genCrossoverProbOption());
		options.addOption(View.genMutationProbOption());
		options.addOption(View.genIterationsOption());
		options.addOption(View.genDumpOption());
		options.addOption(View.genHelpOption());
		final CommandLineParser parser = new DefaultParser();
		try
		{
			this.commandLine = parser.parse(options, args);
		}
		catch(final ParseException ignored)
		{
			//TODO Print help + user message here. Simples!
			//TODO Test here
		}
		//TODO Print help (HOW?!) if help flag present
		//TODO Test here
		//TODO Need to solve the dependency problem. Accompanying lib folder would work. So would shaded dependences (probably the nicest way)
		//TODO Test here
		//TODO Slightly nicer wrappers around getters that ensure what you're getting is sane - doubles between 1 and 0, ints greater than 0/1, etc
		//TODO Test
	}

	private static Option genBoxSizeOption()
	{
		return Option.builder().argName(View.BOX_SIZE_SHORT_OPTION)
					 .desc(View.BOX_SIZE_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.BOX_SIZE_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Integer.class)
					 .build();
	}

	private static Option genCrossoverProbOption()
	{
		return Option.builder().argName(View.CROSSOVER_PROB_SHORT_OPTION)
					 .desc(View.CROSSOVER_PROB_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.CROSSOVER_PROB_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Double.class)
					 .build();
	}

	private static Option genDumpOption()
	{
		return Option.builder().argName(View.DUMP_SHORT_OPTION)
					 .desc(View.DUMP_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.DUMP_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Integer.class)
					 .build();
	}

	private static Option genHelpOption()
	{
		return Option.builder().argName(View.HELP_SHORT_OPTION)
					 .desc(View.HELP_OPTION_DESC)
					 .hasArg(false)
					 .longOpt(View.HELP_LONG_OPTION)
					 .numberOfArgs(0)
					 .required(false)
					 .build();
	}

	private static Option genImportOption()
	{
		return Option.builder().argName(View.IMPORT_SHORT_OPTION)
					 .desc(View.IMPORT_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.IMPORT_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(String.class)
					 .build();
	}

	private static Option genIterationsOption()
	{
		return Option.builder().argName(View.ITERATIONS_SHORT_OPTION)
					 .desc(View.ITERATIONS_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.ITERATIONS_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Integer.class)
					 .build();
	}

	private static Option genMutationProbOption()
	{
		return Option.builder().argName(View.MUTATION_PROB_SHORT_OPTION)
					 .desc(View.MUTATION_PROB_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.MUTATION_PROB_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Double.class)
					 .build();
	}

	private static Option genNoveltyBarrierOption()
	{
		return Option.builder().argName(View.NOVELTY_BAR_SHORT_OPTION)
					 .desc(View.NOVELTY_BAR_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.NOVELTY_BAR_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Double.class)
					 .build();
	}

	private static Option genPopSizeOption()
	{
		return Option.builder().argName(View.POP_SIZE_SHORT_OPTION)
					 .desc(View.POP_SIZE_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.POP_SIZE_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Integer.class)
					 .build();
	}

	private static Option genRetentionOption()
	{
		return Option.builder().argName(View.RETENTION_SHORT_OPTION)
					 .desc(View.RETENTION_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.RETENTION_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Double.class)
					 .build();
	}

	private static Option genTournSizeOption()
	{
		return Option.builder().argName(View.TOURN_SIZE_SHORT_OPTION)
					 .desc(View.TOURN_SIZE_OPTION_DESC)
					 .hasArg(true)
					 .longOpt(View.TOURN_SIZE_LONG_OPTION)
					 .numberOfArgs(1)
					 .required(true)
					 .type(Integer.class)
					 .build();
	}

	@SuppressWarnings({"HardCodedStringLiteral", "MagicCharacter"})
	@Override
	public String toString()
	{
		return "View{" +
			   "commandLine=" + this.commandLine +
			   '}';
	}

	Integer getBoxSize()
	{
		return Integer.valueOf(this.commandLine.getOptionValue(View.BOX_SIZE_SHORT_OPTION));
	}

	Double getCrossoverProb()
	{
		return Double.valueOf(this.commandLine.getOptionValue(View.CROSSOVER_PROB_SHORT_OPTION));
	}

	Integer getDumpCount()
	{
		return Integer.valueOf(this.commandLine.getOptionValue(View.DUMP_SHORT_OPTION));
	}

	File getImportDir()
	{
		return new File(this.commandLine.getOptionValue(View.IMPORT_SHORT_OPTION));
	}

	Integer getIterations()
	{
		return Integer.valueOf(this.commandLine.getOptionValue(View.ITERATIONS_SHORT_OPTION));
	}

	Double getMutationProb()
	{
		return Double.valueOf(this.commandLine.getOptionValue(View.MUTATION_PROB_SHORT_OPTION));
	}

	Double getNoveltyBarrier()
	{
		return Double.valueOf(this.commandLine.getOptionValue(View.NOVELTY_BAR_SHORT_OPTION));
	}

	Integer getPopSize()
	{
		return Integer.valueOf(this.commandLine.getOptionValue(View.POP_SIZE_SHORT_OPTION));
	}

	Double getRetention()
	{
		return Double.valueOf(this.commandLine.getOptionValue(View.RETENTION_SHORT_OPTION));
	}

	Integer getTournSize()
	{
		return Integer.valueOf(this.commandLine.getOptionValue(View.TOURN_SIZE_SHORT_OPTION));
	}
}
