/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalgame.Engine;

import finalgame.Game;
import finalgame.Main;
import finalgame.Util;
import java.io.*;

/**
 * 
 * @author Pat
 */
public class OptionsManager {
	public float musicVolume;
	public float soundVolume;
	protected Game main;
	private File optionsFile;

	public OptionsManager(Game main, File workingDir) {
		this();
		this.main = main;
		optionsFile = new File(workingDir, "options.txt");
		loadOptions();
	}

	public OptionsManager() {
		musicVolume = 1F;
		soundVolume = 1F;
	}

	public void setFloatOption(EnumOptions option, float val) {

		if (option == EnumOptions.MUSIC) {
			musicVolume = val;
			main.jukebox.onSoundOptionsChanged();
		}

		if (option == EnumOptions.SOUND) {
			soundVolume = val;
			main.jukebox.onSoundOptionsChanged();
		}
	}

	/**
	 * Loads the options from the options file. It appears that this has
	 * replaced the previous 'loadOptions'
	 */
	public void loadOptions() {
		try {
			if (!optionsFile.exists()) {
				return;
			}

			BufferedReader bufferedreader = new BufferedReader(new FileReader(
					optionsFile));

			for (String s = ""; (s = bufferedreader.readLine()) != null;) {
				try {
					String as[] = s.split(":");

					if (as[0].equals("music")) {
						musicVolume = parseFloat(as[1]);
					}

					if (as[0].equals("sound")) {
						soundVolume = parseFloat(as[1]);
					}
				} catch (Exception exception1) {
					System.out.println((new StringBuilder())
							.append("Skipping bad option: ").append(s)
							.toString());
				}
			}
			bufferedreader.close();
		} catch (Exception exception) {
			System.out.println("Failed to load options");
			exception.printStackTrace();
		}
	}

	/**
	 * Parses a string into a float.
	 */
	private float parseFloat(String val) {
		if (val.equals("true")) {
			return 1.0F;
		}

		if (val.equals("false")) {
			return 0.0F;
		} else {
			return Float.parseFloat(val);
		}
	}

	/**
	 * Saves the options to the options file.
	 */
	public void saveOptions() {
		try {
			PrintWriter printwriter = new PrintWriter(new FileWriter(
					optionsFile));
			printwriter.println((new StringBuilder()).append("music:")
					.append(musicVolume).toString());
			printwriter.println((new StringBuilder()).append("sound:")
					.append(soundVolume).toString());
			printwriter.close();
		} catch (Exception exception) {
			System.out.println("Failed to save options");
			exception.printStackTrace();
		}
	}
}
