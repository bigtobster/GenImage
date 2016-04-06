package GenImage;

import org.apache.commons.imaging.ColorTools;
import org.apache.commons.imaging.common.ImageBuilder;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Performs transformations on images Created by Toby Leheup on 06/04/16 for GenImage.
 *
 * @author Bigtobster
 */
@SuppressWarnings("ClassMayBeInterface")
public final class ImageManipulator
{
	/**
	 * The colours available for each channel in RGB
	 */
	static final         int    RGB_RANGE = 256;
	@SuppressWarnings("ConstantNamingConvention")
	private static final double HALF      = 0.5;

	/**
	 * Converts a colour image to Greyscale
	 *
	 * @param original The image to convert
	 * @return The greyscaled image
	 */
	@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
	public static BufferedImage toGreyscale(final BufferedImage original)
	{
		final ColorTools colorTools = new ColorTools();
		return colorTools.convertToColorSpace(original, ColorSpace.getInstance(ColorSpace.CS_GRAY));
	}

	/**
	 * Inverts the colours of a greyscale image
	 *
	 * @param greyscale The greyscale image
	 * @return The inverted image
	 */
	@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
	public static BufferedImage toInvertedGreyscale(final BufferedImage greyscale)
	{
		final ImageBuilder imageBuilder = new ImageBuilder(greyscale.getWidth(), greyscale.getHeight(), false);
		for(int w = 0; w < greyscale.getWidth(); w++)
		{
			for(int h = 0; h < greyscale.getHeight(); h++)
			{
				final Color colour = new Color(greyscale.getRGB(w, h));

				final Color invertedColour = new Color(
						ImageManipulator.calcRGB(
								ImageManipulator.RGB_RANGE - 1 - colour.getRed(),
								ImageManipulator.RGB_RANGE - 1 - colour.getGreen(),
								ImageManipulator.RGB_RANGE - 1 - colour.getBlue()
												)
				);
				imageBuilder.setRGB(w, h, invertedColour.getRGB());
			}
		}
		return imageBuilder.getBufferedImage();
	}

	@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
	static BufferedImage breedCandidates(final BufferedImage mother, final BufferedImage father)
	{
		final int width = mother.getWidth();
		final int height = mother.getHeight();
		final ImageBuilder imageBuilder = new ImageBuilder(width, height, false);
		final Random randy = new Random(System.currentTimeMillis());
		for(int w = 0; w < width; w++)
		{
			for(int h = 0; h < height; h++)
			{
				final int colour;
				if(randy.nextDouble() < ImageManipulator.HALF)
				{
					colour = mother.getRGB(w, h);
				}
				else
				{
					colour = father.getRGB(w, h);
				}
				imageBuilder.setRGB(w, h, colour);
			}
		}
		return imageBuilder.getBufferedImage();
	}

	@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
	static int calcRGB(final int r, final int g, final int b)
	{
		return r + (g * ImageManipulator.RGB_RANGE) + (b * ImageManipulator.RGB_RANGE * ImageManipulator.RGB_RANGE);
	}

	@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
	static BufferedImage mutateImage(final BufferedImage image)
	{
		final Random randy = new Random(System.currentTimeMillis());
		final int pixelsToMutate = randy.nextInt(image.getHeight() * image.getWidth());
		for(int i = 0; i < pixelsToMutate; i++)
		{
			final int w = randy.nextInt(image.getWidth());
			final int h = randy.nextInt(image.getHeight());
			final int r = randy.nextInt(ImageManipulator.RGB_RANGE);
			final int g = randy.nextInt(ImageManipulator.RGB_RANGE);
			final int b = randy.nextInt(ImageManipulator.RGB_RANGE);
			image.setRGB(w, h, ImageManipulator.calcRGB(r, g, b));
		}
		return image;
	}
}
