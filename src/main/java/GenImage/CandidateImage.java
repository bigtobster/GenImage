package GenImage;

import java.awt.image.BufferedImage;

/**
 * An image which is a genetic candidate Really just an image with an associated score Created by Toby Leheup on 06/04/16 for App.
 *
 * @author Bigtobster
 */
@SuppressWarnings("PublicMethodNotExposedInInterface")
public class CandidateImage implements Comparable<CandidateImage>
{
	private final BufferedImage image;
	private       Integer       score;

	/**
	 * Creates a new candidate image
	 *
	 * @param newImage The candidate image
	 * @param newScore The fitness score
	 */
	public CandidateImage(final BufferedImage newImage, final Integer newScore)
	{
		this.image = newImage;
		this.score = newScore;
	}

	@SuppressWarnings("NullableProblems")
	@Override
	public int compareTo(final CandidateImage foe)
	{
		//noinspection CallToSimpleGetterFromWithinClass
		if(foe.getScore() > this.getScore())
		{
			return 1;
		}
		//noinspection CallToSimpleGetterFromWithinClass
		if(foe.getScore() < this.getScore())
		{
			return - 1;
		}
		return 0;
	}

	@Override
	public boolean equals(final Object obj)
	{
		return super.equals(obj);
	}

	/**
	 * Gets the BufferedImage of a CandidateImage
	 *
	 * @return The BufferedImage of a CandidateImage
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}

	/**
	 * Gets the score of the candidate image
	 *
	 * @return The score of the candidate image
	 */
	public Integer getScore()
	{
		return this.score;
	}

	/**
	 * Sets the score of the candidate image
	 *
	 * @param newScore The new score of the candidate image
	 */
	public void setScore(final Integer newScore)
	{
		this.score = newScore;
	}

	@SuppressWarnings({"HardCodedStringLiteral", "MagicCharacter"})
	@Override
	public String toString()
	{
		return "CandidateImage{" +
			   "image=" + this.image +
			   ", score=" + this.score +
			   '}';
	}
}
