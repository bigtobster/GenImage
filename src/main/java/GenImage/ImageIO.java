package GenImage;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingConstants;
import org.apache.commons.imaging.common.BufferedImageFactory;
import org.apache.commons.imaging.common.ImageBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controls the import and export of images Created by Toby Leheup on 05/04/16 for App. This file is APACHE GPL 2
 *
 * @author Bigtobster
 * @author https://svn.apache.org/repos/asf/commons/proper/imaging/trunk/src/test/java/org/apache/commons/imaging/examples/ImageReadExample.java
 */
public final class ImageIO
{
	private static final Logger LOGGER    = Logger.getLogger(ImageIO.class.getName());

	@SuppressWarnings({"PublicInnerClass", "JavaDoc"})
	public static class ManagedImageBufferedImageFactory implements BufferedImageFactory
	{

		@Override
		public BufferedImage getColorBufferedImage(
				final int width, final int height,
				final boolean hasAlpha
												  )
		{
			final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			final GraphicsDevice gd = ge.getDefaultScreenDevice();
			final GraphicsConfiguration gc = gd.getDefaultConfiguration();
			return gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		}

		@Override
		public BufferedImage getGrayscaleBufferedImage(
				final int width, final int height,
				final boolean hasAlpha
													  )
		{
			return this.getColorBufferedImage(width, height, hasAlpha);
		}
	}

	/**
	 * Imports all of the images in a given directory. Assumes that all the files in this directory are actually images...
	 *
	 * @param importDir The directory which to import from
	 * @return The list of images that has been imported
	 */
	@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
	public static LinkedList<BufferedImage> importSeedImages(final File importDir)
	{
		@SuppressWarnings("ConstantConditions")
		final LinkedList<BufferedImage> images = new LinkedList<BufferedImage>();
		//noinspection ConstantConditions
		for(final File imageFile : importDir.listFiles())
		{
			try
			{
				images.add(ImageIO.readImage(imageFile));
			}
			catch(final ImageReadException ire)
			{
				ImageIO.LOGGER.log(Level.SEVERE, ire.getMessage(), ire);
			}
			catch(final IOException ioe)
			{
				ImageIO.LOGGER.log(Level.SEVERE, ioe.getMessage(), ioe);
			}
		}
		return images;
	}

	/**
	 * Reads an image from a file
	 *
	 * @param file The file pointing to the image file
	 * @return The parsed image
	 * @throws ImageReadException Thrown on a bad or unsupported
	 * @throws IOException        Thrown on some other IO exception - possibly disk issues
	 */
	public static BufferedImage readImage(final File file)
			throws ImageReadException, IOException
	{
		final Map<String, Object> params = new HashMap<String, Object>(1);

		// set optional parameters if you like
		params.put(ImagingConstants.BUFFERED_IMAGE_FACTORY, new ImageIO.ManagedImageBufferedImageFactory());

		// read image
		return Imaging.getBufferedImage(file, params);
	}

	/**
	 * Generates an image that is just a load of random coloured pixels jammed together
	 *
	 * @return The noisy image
	 */
	@SuppressWarnings("StaticMethodOnlyUsedInOneClass")
	static BufferedImage genNoiseImage(final int width, final int height)
	{
		final ImageBuilder imageBuilder = new ImageBuilder(width, height, false);
		final Random randy = new Random(System.currentTimeMillis());
		for(int w = 0; w < width; w++)
		{
			for(int h = 0; h < height; h++)
			{
				final int r = randy.nextInt(ImageManipulator.RGB_RANGE);
				final int g = randy.nextInt(ImageManipulator.RGB_RANGE);
				final int b = randy.nextInt(ImageManipulator.RGB_RANGE);
				final int rgb = ImageManipulator.calcRGB(r, g, b);
				imageBuilder.setRGB(w, h, rgb);
			}
		}
		return imageBuilder.getBufferedImage();
	}
}
