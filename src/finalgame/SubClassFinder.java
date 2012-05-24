package finalgame;

import java.io.File;
import java.net.URL;

import finalgame.GUI.battle.BattleScreenManager;

public class SubClassFinder {
	public static void find(String pckgname, Class cls) {
		// Code from JWhich
		// ======
		// Translate the package name into an absolute path
		String name = new String(pckgname);
		if (!name.startsWith("/")) {
			name = "/" + name;
		}
		name = name.replace('.', '/');

		// Get a File object for the package
		URL url = BattleScreenManager.class.getResource(name);
		if(url == null) return;
		File directory = new File(url.getFile());
		// New code
		// ======
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {

				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					String classname = files[i].substring(0,
							files[i].length() - 6);
					try {
						// Try to create an instance of the object
						Object o = Class.forName(pckgname + "." + classname)
								.newInstance();
						if (cls.isInstance(o)) {
							System.out.println(classname);
						}
					} catch (ClassNotFoundException cnfex) {
						System.err.println(cnfex);
					} catch (InstantiationException iex) {
						// We try to instantiate an interface
						// or an object that does not have a
						// default constructor
					} catch (IllegalAccessException iaex) {
						// The class is not public
					}
				}
			}
		}
	}

	public static void find(String tosubclassname) {
		try {
			Class tosubclass = Class.forName(tosubclassname);
			Package[] pcks = Package.getPackages();
			for (int i = 0; i < pcks.length; i++) {
				find(pcks[i].getName(), tosubclass);
			}
		} catch (ClassNotFoundException ex) {
			System.err.println("Class " + tosubclassname + " not found!");
		}
	}
}
