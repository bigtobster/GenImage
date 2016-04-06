package GenImage;

import org.apache.commons.cli.*;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Interface to App Created by Toby Leheup on 04/04/16 for App.App.
 *
 * @author BigTobster
 */
@SuppressWarnings({"ClassWithTooManyFields", "ClassWithTooManyMethods"})
public final class View
{
	private static final String APP_NAME                      = "GenImage";
	private static final String BOX_SIZE_GT_0_MSG             = "Box Size must be greater than 0";
	private static final String BOX_SIZE_LONG_OPTION          = "boxSize";
	private static final String BOX_SIZE_OPTION_DESC          = "The amount of pixel placement tolerance in fitness comparison";
	private static final String BOX_SIZE_SHORT_OPTION         = "b";
	private static final String CROSSOVER_BETWEEN_0_AND_1_MSG = "Crossover Probability must be decimal value between 0 and 1";
	private static final String CROSSOVER_PROB_LONG_OPTION    = "crossover";
	private static final String CROSSOVER_PROB_OPTION_DESC    = "The probability that a candidate will be selected for crossover";
	private static final String CROSSOVER_PROB_SHORT_OPTION   = "c";
	private static final String DUMP_GT_1_MSG                 = "You must dump at least 1 image!";
	private static final String DUMP_GT_POP_SIZE_MSG          = "Maximum dump count is the size of the population!";
	private static final String DUMP_LONG_OPTION              = "dump";
	private static final String DUMP_OPTION_DESC              = "The number of best candidates to export";
	private static final String DUMP_SHORT_OPTION             = "d";
	private static final String HELP_LONG_OPTION              = "help";
	private static final String HELP_OPTION_DESC              = "Display help and usage info";
	private static final String HELP_SHORT_OPTION             = "h";
	private static final String IMPORT_LONG_OPTION            = "input";
	private static final String IMPORT_OPTION_DESC            = "Path to directory containing images to import";
	private static final String IMPORT_SHORT_OPTION           = "i";
	private static final String INCORRECT_USAGE_SEE_HELP_MSG  = "Incorrect usage. See help:";
	private static final String INPUT_DIRECTORY_NOT_EXIST_MSG = "Input Directory does not exist!";
	private static final String INPUT_GT_3_IMAGES_MSG         = "Input set must have at least 3 images";
	private static final String INPUT_NOT_A_DIRECTORY_MSG     = "Input is not a directory!";
	private static final String ITERATIONS_GT_1_MSG           = "Must have at least 1 iteration";
	private static final String ITERATIONS_LONG_OPTION        = "iterations";
	private static final String ITERATIONS_OPTION_DESC        = "The number of iterations before dumping results and awaiting further instructions";
	private static final String ITERATIONS_SHORT_OPTION       = "l";
	private static final String JPEG_EXTENSION                = ".jpeg";
	private static final String JPG_EXTENSION                 = ".jpg";
	@SuppressWarnings("UnusedDeclaration")
	private static final Logger LOGGER                        = Logger.getLogger(View.class.getName());
	private static final String MUTATION_BETWEEN_0_AND_1_MSG  = "Mutation Probability must be decimal value between 0 and 1";
	private static final String MUTATION_PROB_LONG_OPTION     = "mutation";
	private static final String MUTATION_PROB_OPTION_DESC     = "The probability that a candidate will be mutated";
	private static final String MUTATION_PROB_SHORT_OPTION    = "m";
	private static final String NOVELTY_BAR_LONG_OPTION       = "noveltyBarrier";
	private static final String
								NOVELTY_BAR_OPTION_DESC
															  = "The minimum percentage of pixels from a single input image which a candidate must match before being eliminated due to lack of novelty";
	private static final String NOVELTY_BAR_SHORT_OPTION      = "n";
	private static final String NOVELTY_BETWEEN_0_AND_1_MSG   = "Novelty Barrier must be decimal value between 0 and 1";
	private static final String PNG_EXTENSION                 = ".png";
	private static final String POP_SIZE_GT_1_MSG             = "Population Size must be greater than 1";
	private static final String POP_SIZE_LONG_OPTION          = "populationSize";
	private static final String POP_SIZE_OPTION_DESC          = "The size of the candidate population";
	private static final String POP_SIZE_SHORT_OPTION         = "s";
	private static final String RETENTION_BETWEEN_0_AND_1_MSG = "Retention must be decimal value between 0 and 1";
	private static final String RETENTION_LONG_OPTION         = "retention";
	private static final String
								RETENTION_OPTION_DESC
															  = "The percentage (as a decimal) of the population to retain during tournament selection";
	private static final String RETENTION_SHORT_OPTION        = "r";
	private static final String TOURN_SIZE_GT_0_MSG           = "Tournament Size must be greater than 0";
	private static final String TOURN_SIZE_LONG_OPTION        = "tournamentSize";
	private static final String TOURN_SIZE_LT_POP_SIZE_MSG    = "Tournament Size can be no greater than the population size";
	private static final String TOURN_SIZE_OPTION_DESC        = "The number of candidates in the tournaments during Tournament selection";
	private static final String TOURN_SIZE_SHORT_OPTION       = "t";
	private final Options options;
	private CommandLine commandLine = null;

	/**
	 * Constructor for View. Initialises the options
	 *
	 * @param args The arguments on the command line
	 */
	public View(final String[] args)
	{
		super();
		View.LOGGER.setLevel(Level.ALL);
		this.options = new Options();
		this.options.addOption(View.genImportOption());
		this.options.addOption(View.genPopSizeOption());
		this.options.addOption(View.genBoxSizeOption());
		this.options.addOption(View.genRetentionOption());
		this.options.addOption(View.genTournSizeOption());
		this.options.addOption(View.genNoveltyBarrierOption());
		this.options.addOption(View.genCrossoverProbOption());
		this.options.addOption(View.genMutationProbOption());
		this.options.addOption(View.genIterationsOption());
		this.options.addOption(View.genDumpOption());
		this.options.addOption(View.genHelpOption());
		final CommandLineParser parser = new DefaultParser();
		try
		{
			this.commandLine = parser.parse(this.options, args);
		}
		catch(final ParseException ignored)
		{
			System.out.println(View.INCORRECT_USAGE_SEE_HELP_MSG);
			this.printHelp();
			System.exit(1);
		}
		if(this.commandLine.hasOption(View.HELP_SHORT_OPTION))
		{
			this.printHelp();
			System.exit(0);
		}
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

	private static String nvl(final String str1, final String str2)
	{
		if(str1 != null)
		{
			return str1;
		}
		return str2;
	}

	@SuppressWarnings({"HardCodedStringLiteral", "MagicCharacter"})
	@Override
	public String toString()
	{
		//noinspection ObjectToString
		return "View{" +
			   "commandLine=" + this.commandLine +
			   '}';
	}

	Integer getBoxSize()
	{
		final Integer boxSize = Integer.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.BOX_SIZE_SHORT_OPTION),
						this.commandLine.getOptionValue(View.BOX_SIZE_LONG_OPTION)
						)
											   );
		if(boxSize < 0)
		{
			System.out.println(View.BOX_SIZE_GT_0_MSG);
			this.printHelp();
			System.exit(1);
		}
		return boxSize;
	}

	Double getCrossoverProb()
	{
		final Double crossoverProb = Double.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.CROSSOVER_PROB_SHORT_OPTION),
						this.commandLine.getOptionValue(View.CROSSOVER_PROB_LONG_OPTION)
						)
												   );
		if((crossoverProb < 0.0) || (crossoverProb > 1.0))
		{
			System.out.println(View.CROSSOVER_BETWEEN_0_AND_1_MSG);
			this.printHelp();
			System.exit(1);
		}
		return crossoverProb;
	}

	Integer getDumpCount()
	{
		final Integer dumpCount = Integer.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.DUMP_SHORT_OPTION),
						this.commandLine.getOptionValue(View.DUMP_LONG_OPTION)
						)
												 );
		final Integer populationSize = this.getPopSize();
		if(dumpCount < 0)
		{
			System.out.println(View.DUMP_GT_1_MSG);
			this.printHelp();
			System.exit(1);
		}
		if(dumpCount > populationSize)
		{
			System.out.println(View.DUMP_GT_POP_SIZE_MSG);
			this.printHelp();
			System.exit(1);
		}
		return dumpCount;
	}

	File getImportDir()
	{
		final File importDir = new File(
				View.nvl(
						this.commandLine.getOptionValue(View.IMPORT_SHORT_OPTION),
						this.commandLine.getOptionValue(View.IMPORT_LONG_OPTION)
						)
		);
		boolean failed = false;
		String message = "";
		if(importDir.exists())
		{
			if(importDir.isDirectory())
			{
				int imageCount = 0;
				if(importDir.listFiles() != null)
				{
					//noinspection ConstantConditions
					for(final File file : importDir.listFiles())
					{
						final String path = file.getAbsolutePath();
						final boolean isImage = path.contains(View.JPEG_EXTENSION) ||
												path.contains(View.JPG_EXTENSION) ||
												path.contains(View.PNG_EXTENSION);
						if(isImage)
						{
							imageCount++;
						}
					}
					if(imageCount < 3)
					{
						failed = true;
						message = View.INPUT_GT_3_IMAGES_MSG;
					}
				}
				else
				{
					failed = true;
					message = View.INPUT_GT_3_IMAGES_MSG;
				}
			}
			else
			{
				failed = true;
				message = View.INPUT_NOT_A_DIRECTORY_MSG;
			}
		}
		else
		{
			failed = true;
			message = View.INPUT_DIRECTORY_NOT_EXIST_MSG;
		}
		if(failed)
		{
			System.out.println(message);
			this.printHelp();
			System.exit(1);
		}
		return importDir;
	}

	Integer getIterations()
	{
		final Integer iterations = Integer.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.ITERATIONS_SHORT_OPTION),
						this.commandLine.getOptionValue(View.ITERATIONS_LONG_OPTION)
						)
												  );
		if(iterations <= 0)
		{
			System.out.println(View.ITERATIONS_GT_1_MSG);
			this.printHelp();
			System.exit(1);
		}
		return iterations;
	}

	Double getMutationProb()
	{
		final Double mutationProb = Double.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.MUTATION_PROB_SHORT_OPTION),
						this.commandLine.getOptionValue(View.MUTATION_PROB_LONG_OPTION)
						)
												  );
		if((mutationProb < 0.0) || (mutationProb > 1.0))
		{
			System.out.println(View.MUTATION_BETWEEN_0_AND_1_MSG);
			this.printHelp();
			System.exit(1);
		}
		return mutationProb;
	}

	Double getNoveltyBarrier()
	{
		final Double noveltyBarrier = Double.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.NOVELTY_BAR_SHORT_OPTION),
						this.commandLine.getOptionValue(View.NOVELTY_BAR_LONG_OPTION)
						)
													);
		if((noveltyBarrier < 0.0) || (noveltyBarrier > 1.0))
		{
			System.out.println(View.NOVELTY_BETWEEN_0_AND_1_MSG);
			this.printHelp();
			System.exit(1);
		}
		return noveltyBarrier;
	}

	Integer getPopSize()
	{
		final Integer popSize = Integer.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.POP_SIZE_SHORT_OPTION),
						this.commandLine.getOptionValue(View.POP_SIZE_LONG_OPTION)
						)
											   );
		if(popSize < 1)
		{
			System.out.println(View.POP_SIZE_GT_1_MSG);
			this.printHelp();
			System.exit(1);
		}
		return popSize;
	}

	Double getRetention()
	{
		final Double retention = Double.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.RETENTION_SHORT_OPTION),
						this.commandLine.getOptionValue(View.RETENTION_LONG_OPTION)
						)
											   );
		if((retention < 0.0) || (retention > 1.0))
		{
			System.out.println(View.RETENTION_BETWEEN_0_AND_1_MSG);
			this.printHelp();
			System.exit(1);
		}
		return retention;
	}

	Integer getTournSize()
	{
		final Integer tournamentSize = Integer.valueOf(
				View.nvl(
						this.commandLine.getOptionValue(View.TOURN_SIZE_SHORT_OPTION),
						this.commandLine.getOptionValue(View.TOURN_SIZE_LONG_OPTION)
						)
													  );
		if(tournamentSize <= 0)
		{
			System.out.println(View.TOURN_SIZE_GT_0_MSG);
			this.printHelp();
			System.exit(1);
		}
		if(tournamentSize > this.getPopSize())
		{
			System.out.println(View.TOURN_SIZE_LT_POP_SIZE_MSG);
			this.printHelp();
			System.exit(1);
		}
		return tournamentSize;
	}

	private void printHelp()
	{
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(View.APP_NAME, this.options);
	}
}
