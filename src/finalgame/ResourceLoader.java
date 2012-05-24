package finalgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourceLoader {
	
	/**
	 * Get the resource as a stream
	 * 
	 * @param file
	 *            The file to load
	 * @param relative
	 *            Whether the path is relative to the root directory
	 * @return
	 */
	public static InputStream getResourceAsStream(String path, boolean relative) {
		File f = getResourceFile(path, relative);
		return getResourceAsStream(f);
	}

	/**
	 * Get the resource as a stream
	 * 
	 * @param path
	 *            The path
	 * @param relative
	 *            Whether the path is relative to the root directory
	 * @return
	 */
	public static InputStream getResourceAsStream(File f) {
		InputStream is;
		try {
			is = new FileInputStream(f);
			return is;
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
	 * Get the resource as a file
	 * 
	 * @param path
	 *            The path
	 * @param relative
	 *            Whether the path is relative to the root directory
	 * @return
	 */
	public static File getResourceFile(String path, boolean relative) {
		String absolutePath;
		if (relative) {
			absolutePath = getAbsolutePath(path);
		} else {
			absolutePath = path;
		}
		File f = new File(absolutePath);
		return f;
	}

	/**
	 * Get absolute path relative to the root directory
	 * 
	 * @param relativePath
	 *            the relative path
	 * @return the absolute path
	 */
	public static String getAbsolutePath(String relativePath) {
		File dir = Util.getWorkingDir();
		String absolutePath = new File(dir, relativePath).getAbsolutePath();
		System.out.println(absolutePath);
		return absolutePath;
	}
}