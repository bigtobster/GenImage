package GenImage;

/**
 * Invoking class for application. Doesn't do anything else
 * Created by Toby Leheup on 05/04/16 for Application.
 * @author Bigtobster
 */
public final class Main
{
	/**
	 * Starting point for application
	 *
	 * @param args Command line arguments
	 */
	public static void main(final String[] args)
	{
		final View view = new View(args);
		final Application application = new Application(view);
		application.run();
	}
}
