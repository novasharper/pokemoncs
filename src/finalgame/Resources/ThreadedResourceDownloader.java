package finalgame.Resources;

import finalgame.Game;
import finalgame.Main;
import finalgame.Util;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ThreadedResourceDownloader extends Thread {

	private MessageDigest md;
	private static DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
	public final String BASE_URL = "http://dl.dropbox.com/u/41733151/PokemonResources/";
	private DocumentBuilder builder;
	public boolean finished;
	/**
	 * The folder to store the resources in.
	 */
	public File resourcesFolder;
	private Game main;
	private boolean closing;

	public ThreadedResourceDownloader(Game main) {
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
		}
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}
		finished = false;
		this.main = main;
		resourcesFolder = new File(Util.getWorkingDir(), "resources");
	}

	/**
	 * Download all the necessary resources.
	 */
	@Override
	public void run() {
		URL url;
		URLConnection connection;
		InputStream is;
		Document resindex;
		try {
			url = new URL(BASE_URL + "index.xml");
			connection = url.openConnection();
			is = connection.getInputStream();
			resindex = builder.parse(is);
		} catch (MalformedURLException e) {
			return;
		} catch (IOException e) {
			return;
		}catch (SAXException e) {
			return;
		}
		File workingDir = Util.getWorkingDir();
		NodeList nodes = resindex.getElementsByTagName("FileList").item(0)
				.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			boolean isFile = getTagValue("type", (Element) node).equals("file");
			String path = getTagValue("path", (Element) node);
			if (isFile) {
				if (shouldDownload(path, getTagValue("hash", (Element) node))) {
					downloadResource(path);
				}
			} else {
				File resDir = new File(workingDir, path);
				if (resDir.exists()) {
					continue;
				}
				resDir.mkdirs();
			}
		}
		finished = true;
	}

	/**
	 * Determine if resource should be downloaded.
	 * 
	 * @param path
	 *            Relative path to resource.
	 * @param newhash
	 *            Hash of file.
	 * @return
	 */
	private boolean shouldDownload(String path, String newhash) {
		File res = new File(Util.getWorkingDir(), path);
		boolean download = false;
		if (!res.exists()) {
			download = true;
		}
		if (!download) {
			try {
				InputStream fis = new FileInputStream(res);
				byte[] buffer = new byte[1024];
				int numRead;
				do {
					numRead = fis.read(buffer);
					if (numRead > 0) {
						md.update(buffer, 0, numRead);
					}
				} while (numRead != -1);
				fis.close();
				String hash = byteArray2Hex(md.digest());
				download = !newhash.equals(hash);
			} catch (FileNotFoundException e) {
				download = true;
			} catch (IOException e) {
				download = true;
			}
		}
		return download;
	}

	/**
	 * Download resource.
	 * 
	 * @param path
	 *            The path to the resource.
	 * @return
	 */
	private boolean downloadResource(String path) {
		System.out.println(path);
		try {
			URL fileURL = new URL(BASE_URL + path.replace('\\', '/'));
			URLConnection urlConnection = fileURL.openConnection();
			FileOutputStream fos;
			InputStream download = urlConnection.getInputStream();
			File outputFile = new File(Util.getWorkingDir(), path);
			outputFile.delete();
			fos = new FileOutputStream(outputFile);
			byte[] buffer = new byte[65536];
			int bufferSize;
			while ((bufferSize = download.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, bufferSize);
			}
			fos.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Get the value of a tag of an element.
	 * 
	 * @param sTag
	 *            tag
	 * @param eElement
	 *            element
	 * @return
	 */
	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	/**
	 * Convert a byte array to its hex representation.
	 * 
	 * @param hash
	 *            The byte array.
	 * @return
	 */
	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	/**
	 * Reloads the resource folder and passes the resources to PoketronCS to
	 * install.
	 */
	public void reloadResources() {
		loadResource(resourcesFolder, "");
	}

	/**
	 * Loads a resource and passes it to PoketronCS to install.
	 */
	private void loadResource(File direcory, String basepath) {
		File files[] = direcory.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				loadResource(files[i], (new StringBuilder()).append(basepath)
						.append(files[i].getName()).append("/").toString());
				continue;
			}

			try {
				main.installResource((new StringBuilder()).append(basepath)
						.append(files[i].getName()).toString(), files[i]);
			} catch (Exception exception) {
				System.out.println((new StringBuilder())
						.append("Failed to add ").append(basepath)
						.append(files[i].getName()).toString());
			}
		}
	}
}