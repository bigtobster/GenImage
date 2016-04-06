package GenImage;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main application logic Created by Toby Leheup on 04/04/16 for App.App.
 *
 * @author Bigtobster
 */
@SuppressWarnings("ClassWithTooManyMethods")
public class App
{
	private static final String CONT_ITERATIONS_PARSE_ERROR_MSG = "Parse error. Did you enter an integer greater than 0?";
	private static final File   DEBUG_DIR               = new File("./debug");
	private static final String ENTER_FURTHER_ITERATIONS        = "Enter number of further iterations (0 to terminate): ";
	private static final String EXPORTED_FITNESS_MSG            = " with a fitness of ";
	private static final String EXPORTED_IMAGE_MSG              = "Exported image ";
	private static final String ITERATIONS_COMPLETE_MSG = " images exported to ";
	private static final Logger LOGGER                          = Logger.getLogger(App.class.getName());
	private static final File   OUTPUT_DIR                      = new File("./output");
	private final int                       candidateImageHeight;
	private final int                       candidateImageWidth;
	@SuppressWarnings("FieldMayBeFinal")
	private       LinkedList<BufferedImage> baseImages;
	private Integer boxSize = null;
	@SuppressWarnings("FieldMayBeFinal")
	private ArrayList<CandidateImage> candidateImages;
	private Double  crossoverProb  = null;
	private Integer dumpCount      = null;
	private File    importDir      = null;
	private Integer iterations     = null;
	private Double  mutationProb   = null;
	private Double  noveltyBarrier = null;
	private Integer populationSize = null;
	private Double  retentionRate  = null;
	private Integer tournamentSize = null;

	/**
	 * Constructor for the App class
	 *
	 * @param view The CLI interface options
	 */
	public App(final View view)
	{
		super();
		this.initParameters(view);
		this.baseImages = this.generateBaseImages();
		this.exportDebug();
		this.candidateImageWidth = this.baseImages.get(0).getWidth();
		this.candidateImageHeight = this.baseImages.get(0).getHeight();
		this.candidateImages = new ArrayList<CandidateImage>(this.populationSize);
	}

	private static void createOutputDir(final File dir)
	{
		if(! dir.exists())
		{
			//noinspection ResultOfMethodCallIgnored
			dir.mkdir();
		}
	}

	private static void exportCandidateImages(final List<CandidateImage> imagesToExport, final File outputDir, final boolean verbose)
	{
		App.createOutputDir(outputDir);
		for(int i = 0; i < imagesToExport.size(); i++)
		{
			final File toFile = new File(outputDir.getPath() + File.separator + (i + 1) + '.' + ImageFormats.PNG.getName());
			final CandidateImage image = imagesToExport.get(i);
			App.exportImage(image.getImage(), toFile);
			if(verbose)
			{
				final String message = App.EXPORTED_IMAGE_MSG + toFile.getName() + App.EXPORTED_FITNESS_MSG + image.getScore();
				System.out.println(message);
			}
		}
	}

	private static void exportImage(final BufferedImage image, final File toFile)
	{
		try
		{
			Imaging.writeImage(image, toFile, ImageFormats.PNG, new HashMap<String, Object>(0));
		}
		catch(final ImageWriteException e)
		{
			App.LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		catch(final IOException e)
		{
			App.LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static void exportImages(LinkedList<BufferedImage> imagesToExport, File outputDir)
	{
		App.createOutputDir(outputDir);
		for(int i = 0; i < imagesToExport.size(); i++)
		{
			final File toFile = new File(outputDir.getPath() + File.separator + (i + 1) + '.' + ImageFormats.PNG.getName());
			final BufferedImage image = imagesToExport.get(i);
			App.exportImage(image, toFile);
		}
	}

	private static List<CandidateImage> getKBest(final ArrayList<CandidateImage> candidates, final Integer k)
	{
		Collections.sort(candidates);
		return candidates.subList(0, k);

	}

	@SuppressWarnings({"HardCodedStringLiteral", "MagicCharacter"})
	@Override
	public String toString()
	{
		return "App{" +
			   "boxSize=" + this.boxSize +
			   ", crossoverProb=" + this.crossoverProb +
			   ", dumpCount=" + this.dumpCount +
			   ", importDir=" + this.importDir +
			   ", iterations=" + this.iterations +
			   ", mutationProb=" + this.mutationProb +
			   ", noveltyBarrier=" + this.noveltyBarrier +
			   ", populationSize=" + this.populationSize +
			   ", retentionRate=" + this.retentionRate +
			   ", tournamentSize=" + this.tournamentSize +
			   '}';
	}

	@SuppressWarnings("InstanceMethodNamingConvention")
	void run()
	{
		this.initPopulation();
		for(int i = 0; i < this.iterations; i++)
		{
			this.evaluate();
			this.select();
			this.crossover();
			this.mutate();
			//noinspection HardCodedStringLiteral
			System.out.println("Iteration " + i + " complete");
			if(i == (this.iterations - 1))
			{
				this.exportKBest();
				this.iterations += this.queryNextIterations();
			}
		}
	}

	private void crossover()
	{
		//TODO Implement
	}

	private void evaluate()
	{
		//TODO Implement
	}

	private void exportDebug()
	{
		App.exportImages(this.baseImages, App.DEBUG_DIR);
	}

	private void exportKBest()
	{
		final List<CandidateImage> imagesToExport = App.getKBest(this.candidateImages, this.dumpCount);
		App.exportCandidateImages(imagesToExport, App.OUTPUT_DIR, true);
		System.out.println(imagesToExport.size() + App.ITERATIONS_COMPLETE_MSG + App.OUTPUT_DIR);
	}

	private LinkedList<BufferedImage> generateBaseImages()
	{
		final LinkedList<BufferedImage> originals = ImageIO.importSeedImages(this.importDir);
		final LinkedList<BufferedImage> greyscales = new LinkedList<BufferedImage>();
		final LinkedList<BufferedImage> inversions = new LinkedList<BufferedImage>();
		for(final BufferedImage original : originals)
		{
			greyscales.add(ImageManipulator.toGreyscale(original));
		}
		for(final BufferedImage greyscale : greyscales)
		{
			inversions.add(ImageManipulator.toInvertedGreyscale(greyscale));
		}
		greyscales.addAll(inversions);
		return greyscales;
	}

	@SuppressWarnings("FeatureEnvy")
	private void initParameters(final View view)
	{
		this.importDir = view.getImportDir();
		this.boxSize = view.getBoxSize();
		this.crossoverProb = view.getCrossoverProb();
		this.dumpCount = view.getDumpCount();
		this.iterations = view.getIterations();
		this.mutationProb = view.getMutationProb();
		this.noveltyBarrier = view.getNoveltyBarrier();
		this.populationSize = view.getPopSize();
		this.retentionRate = view.getRetention();
		this.tournamentSize = view.getTournSize();
	}

	private void initPopulation()
	{
		for(int i = 0; i < this.populationSize; i++)
		{
			final CandidateImage candidateImage = new CandidateImage(ImageIO.genNoiseImage(this.candidateImageWidth, this.candidateImageHeight), 0L);
			this.candidateImages.add(candidateImage);
		}
	}

	private void mutate()
	{
		//TODO Implement
	}

	private Integer queryNextIterations()
	{
		int continueIterations = 0;
		@SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			System.out.print(App.ENTER_FURTHER_ITERATIONS);
			try
			{
				continueIterations = Integer.parseInt(bufferedReader.readLine().replace(App.ENTER_FURTHER_ITERATIONS, "").trim());
				if(continueIterations < 0)
				{
					//noinspection ThrowCaughtLocally
					throw new NumberFormatException("Continue iterations less than 0");
				}
			}
			catch(final NumberFormatException ignored)
			{
				System.out.print(App.CONT_ITERATIONS_PARSE_ERROR_MSG);
				this.queryNextIterations();
			}
		}
		catch(final IOException e)
		{
			App.LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return continueIterations;
	}

	private void select()
	{
		//TODO Implement
	}
}
