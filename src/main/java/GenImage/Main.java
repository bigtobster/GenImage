package GenImage;

/**
 * Created by Toby Leheup on 05/04/16 for GenImage.
 */
public class Main
{
	public static void main(final String[] args)
	{
		final View view = new View(args);
		final GenImage genImage = new GenImage(view);
		genImage.run();
		genImage.export();
	}
}
