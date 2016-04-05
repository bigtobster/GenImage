package GenImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

/**
 * The main application logic Created by Toby Leheup on 04/04/16 for GenImage.GenImage.
 *
 * @author Bigtobster
 */
@SuppressWarnings("ClassWithTooManyMethods")
public class GenImage
{
	private final int                       candidateImageHeight;
	private final int                       candidateImageWidth;
	@SuppressWarnings("FieldMayBeFinal")
	private       LinkedList<BufferedImage> baseImages;
	private Integer boxSize        = null;
	@SuppressWarnings("FieldMayBeFinal")
	private LinkedList<BufferedImage> candidateImages;
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
	 * Constructor for the GenImage class
	 *
	 * @param view The CLI interface options
	 */
	public GenImage(final View view)
	{
		super();
		this.initParameters(view);
		this.baseImages = ImageIO.importSeedImages(this.importDir);
		this.candidateImageWidth = this.baseImages.get(0).getWidth();
		this.candidateImageHeight = this.baseImages.get(0).getHeight();
		this.candidateImages = new LinkedList<BufferedImage>();
	}

	private static Integer queryNextIterations()
	{
		//TODO Implement
		return - 1;
	}

	@SuppressWarnings({"HardCodedStringLiteral", "MagicCharacter"})
	@Override
	public String toString()
	{
		return "GenImage{" +
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

	void export()
	{
		//TODO Implement
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
			if(i == (this.iterations - 1))
			{
				this.iterations += GenImage.queryNextIterations();
			}
		}
		this.dumpOutput();
	}

	private void crossover()
	{
		//TODO Implement
	}

	private void dumpOutput()
	{
		//TODO Implement
	}

	private void evaluate()
	{
		//TODO Implement
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
			this.candidateImages.add(ImageIO.genNoiseImage(this.candidateImageWidth, this.candidateImageHeight));
		}
	}

	private void mutate()
	{
		//TODO Implement
	}

	private void select()
	{
		//TODO Implement
	}
}
