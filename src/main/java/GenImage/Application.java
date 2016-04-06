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
 * The main application logic Created by Toby Leheup on 04/04/16 for Application.Application.
 *
 * @author Bigtobster
 */
@SuppressWarnings({"ClassWithTooManyMethods", "ClassWithTooManyFields"})
public class Application
{
	private static final String BASE_IMAGES_GENERATED_MSG = "Base Images Generated";
	private static final String CONT_ITERATIONS_PARSE_ERROR_MSG = "Parse error. Did you enter an integer greater than 0?";
	private static final File   DEBUG_DIR                 = new File("./debug");
	private static final String ENTER_FURTHER_ITERATIONS  = "Enter number of further iterations (0 to terminate): ";
	private static final String EXPORTED_FITNESS_MSG      = " with a fitness of ";
	private static final String EXPORTED_IMAGE_MSG        = "Exported image ";
	private static final String INIT_POP_GEN_MSG          = "Initial population generated";
	private static final String ITERATIONS_COMPLETE_MSG   = " images exported to ";
	private static final Logger LOGGER                    = Logger.getLogger(Application.class.getName());
	private static final File   OUTPUT_DIR                = new File("./output");
	private static final double SQUARE_POWER              = 2.0;
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
	 * Constructor for the Application class
	 *
	 * @param view The CLI interface options
	 */
	public Application(final View view)
	{
		super();
		this.initParameters(view);
		this.baseImages = this.generateBaseImages();
		System.out.println(Application.BASE_IMAGES_GENERATED_MSG);
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
		Application.createOutputDir(outputDir);
		for(int i = 0; i < imagesToExport.size(); i++)
		{
			final File toFile = new File(outputDir.getPath() + File.separator + (i + 1) + '.' + ImageFormats.PNG.getName());
			final CandidateImage image = imagesToExport.get(i);
			Application.exportImage(image.getImage(), toFile);
			if(verbose)
			{
				final String message = Application.EXPORTED_IMAGE_MSG + toFile.getName() + Application.EXPORTED_FITNESS_MSG + image.getScore();
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
			Application.LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		catch(final IOException e)
		{
			Application.LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static void exportImages(final LinkedList<BufferedImage> imagesToExport, final File outputDir)
	{
		Application.createOutputDir(outputDir);
		for(int i = 0; i < imagesToExport.size(); i++)
		{
			final File toFile = new File(outputDir.getPath() + File.separator + (i + 1) + '.' + ImageFormats.PNG.getName());
			final BufferedImage image = imagesToExport.get(i);
			Application.exportImage(image, toFile);
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
		return "Application{" +
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
		System.out.println(Application.INIT_POP_GEN_MSG);
		for(int i = 0; i < this.iterations; i++)
		{
			this.evaluate();
			this.select();
			this.crossover();
			this.mutate();
			//noinspection HardCodedStringLiteral
			System.out.println("Iteration " + (i + 1) + " complete");
			if(i == (this.iterations - 1))
			{
				this.exportKBest();
				this.iterations += this.queryNextIterations();
			}
		}
	}

	private Long calculateBaseDistance(final BufferedImage candidateImage, final BufferedImage baseImage)
	{
		Long distance = 0L;
		for(int w = 0; w < candidateImage.getWidth(); w++)
		{
			for(int h = 0; h < candidateImage.getHeight(); h++)
			{
				distance += this.calculatePixelBaseDistance(candidateImage, w, h, baseImage);
			}
		}
		return distance;
	}

	private Long calculateFitness(final BufferedImage candidateImage, final BufferedImage baseImage)
	{
		return this.calculateBaseDistance(candidateImage, baseImage) + this.calculateNeighbourDistance(candidateImage);
	}

	private Long calculateNeighbourDistance(final BufferedImage candidateImage)
	{
		Long distance = 0L;
		for(int w = 0; w < candidateImage.getWidth(); w++)
		{
			for(int h = 0; h < candidateImage.getHeight(); h++)
			{
				distance += this.calculatePixelBaseDistance(candidateImage, w, h, candidateImage);
			}
		}
		return distance;
	}

	private Long calculatePixelBaseDistance(final BufferedImage candidateImage, final int w, final int h, final BufferedImage baseImage)
	{
		final int halfBox = this.boxSize / 2;
		final int halfBoxWStart = Math.max(0, w - halfBox);
		final int halfBoxWEnd = Math.min(this.candidateImageWidth - 1, w + halfBox);
		final int halfBoxHStart = Math.max(0, h - halfBox);
		final int halfBoxHEnd = Math.min(this.candidateImageHeight - 1, h + halfBox);
		final int targetColour = candidateImage.getRGB(w, h);
		Long distance = 0L;
		for(int i = halfBoxWStart; i <= halfBoxWEnd; i++)
		{
			for(int j = halfBoxHStart; j <= halfBoxHEnd; j++)
			{
				final int currentColour = baseImage.getRGB(i, j);
				final double wDiff = (double) Math.abs(w - i);
				final double hDiff = (double) Math.abs(h - j);
				@SuppressWarnings("NonReproducibleMathCall")
				final double euclideanDistance = Math.sqrt(Math.pow(wDiff, Application.SQUARE_POWER) + Math.pow(hDiff, Application.SQUARE_POWER));
				distance += Math.round((double) Math.abs(currentColour - targetColour) * euclideanDistance);
			}
		}
		return distance;
	}

	private void crossover()
	{
		//TODO Implement
	}

	private void evaluate()
	{
		for(final CandidateImage candidateImage : this.candidateImages)
		{
			candidateImage.setScore(this.evaluateScore(candidateImage));
		}
	}

	private Long evaluateScore(final CandidateImage candidateImage)
	{
		final TreeSet<Long> scoreSet = new TreeSet<Long>();
		for(final BufferedImage baseImage : this.baseImages)
		{
			scoreSet.add(this.calculateFitness(candidateImage.getImage(), baseImage));
		}
		return scoreSet.pollFirst();
	}

	private void exportDebug()
	{
		Application.exportImages(this.baseImages, Application.DEBUG_DIR);
	}

	private void exportKBest()
	{
		final List<CandidateImage> imagesToExport = Application.getKBest(this.candidateImages, this.dumpCount);
		Application.exportCandidateImages(imagesToExport, Application.OUTPUT_DIR, true);
		System.out.println(imagesToExport.size() + Application.ITERATIONS_COMPLETE_MSG + Application.OUTPUT_DIR);
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
			System.out.print(Application.ENTER_FURTHER_ITERATIONS);
			try
			{
				continueIterations = Integer.parseInt(bufferedReader.readLine().replace(Application.ENTER_FURTHER_ITERATIONS, "").trim());
				if(continueIterations < 0)
				{
					//noinspection ThrowCaughtLocally
					throw new NumberFormatException("Continue iterations less than 0");
				}
			}
			catch(final NumberFormatException ignored)
			{
				System.out.print(Application.CONT_ITERATIONS_PARSE_ERROR_MSG);
				this.queryNextIterations();
			}
		}
		catch(final IOException e)
		{
			Application.LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return continueIterations;
	}

	private void select()
	{
		//TODO Implement
	}
}
