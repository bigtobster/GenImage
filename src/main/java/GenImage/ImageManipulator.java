package GenImage;

import org.apache.commons.imaging.ColorTools;
import org.apache.commons.imaging.common.ImageBuilder;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

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
	static final int RGB_RANGE = 256;

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
	static int calcRGB(final int r, final int g, final int b)
	{
		return r + (g * ImageManipulator.RGB_RANGE) + (b * ImageManipulator.RGB_RANGE * ImageManipulator.RGB_RANGE);
	}
}
