package finalgame;

import finalgame.Engine.OptionsManager;
import java.io.File;
import java.util.Random;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

public class Jukebox {

	/**
	 * Set to true when the SoundManager has been initialised.
	 */
	private static boolean loaded = false;
	/**
	 * A reference to the sound system.
	 */
	private static SoundSystem sndSystem;
	private Random rand;
	private OptionsManager options;
	/**
	 * Sound pool containing sounds.
	 */
	private SoundPool soundPoolSounds;
	/**
	 * Sound pool containing streaming audio.
	 */
	private SoundPool soundPoolStreaming;
	/**
	 * Sound pool containing music.
	 */
	private SoundPool soundPoolMusic;
	private int latestSoundID;

	public Jukebox() {
		soundPoolSounds = new SoundPool();
		soundPoolStreaming = new SoundPool();
		soundPoolMusic = new SoundPool();
		latestSoundID = 0;
		rand = new Random();
	}

	/**
	 * Used for loading sound settings from GameSettings
	 */
	public void loadSoundSettings(OptionsManager par1GameSettings) {
		soundPoolStreaming.isGetRandomSound = false;
		options = par1GameSettings;

		if (!loaded
				&& (par1GameSettings == null
						|| par1GameSettings.soundVolume != 0.0F || par1GameSettings.musicVolume != 0.0F)) {
			tryToSetLibraryAndCodecs();
		}
	}

	/**
	 * Tries to add the paulscode library and the relevant codecs. If it fails,
	 * the volumes (sound and music) will be set to zero in the options file.
	 */
	private void tryToSetLibraryAndCodecs() {
		try {
			float soundVolume = options.soundVolume;
			float musicVolume = options.musicVolume;
			options.soundVolume = 0.0F;
			options.musicVolume = 0.0F;
			options.saveOptions();
			SoundSystemConfig
					.addLibrary(paulscode.sound.libraries.LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("ogg",
					paulscode.sound.codecs.CodecJOrbis.class);
			SoundSystemConfig.setCodec("wav",
					paulscode.sound.codecs.CodecWav.class);
			sndSystem = new SoundSystem();
			options.soundVolume = soundVolume;
			options.musicVolume = musicVolume;
			options.saveOptions();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			System.err
					.println("error linking with the LibraryJavaSound plug-in");
		}

		loaded = true;
	}

	/**
	 * Called when one of the sound level options has changed.
	 */
	public void onSoundOptionsChanged() {
		if (!loaded
				&& (options.soundVolume != 0.0F || options.musicVolume != 0.0F)) {
			tryToSetLibraryAndCodecs();
		}

		if (loaded) {
			if (options.musicVolume == 0.0F) {
				sndSystem.stop("BgMusic");
			} else {
				sndSystem.setVolume("BgMusic", options.musicVolume);
			}
		}
	}

	/**
	 * Called when the game is closing down.
	 */
	public void closeGame() {
		if (loaded) {
			sndSystem.cleanup();
		}
	}

	/**
	 * Adds a sounds with the name from the file. Args: name, file
	 */
	public void addSound(String name, File soundFile) {
		soundPoolSounds.addSound(name, soundFile);
	}

	/**
	 * Adds an audio file to the streaming SoundPool.
	 */
	public void addStreaming(String name, File soundFile) {
		soundPoolStreaming.addSound(name, soundFile);
	}

	/**
	 * Adds an audio file to the music SoundPool.
	 */
	public void addMusic(String name, File soundFile) {
		soundPoolMusic.addSound(name, soundFile);
	}

	/**
	 * If its time to play new music it starts it up.
	 */
	public void playRandomMusicIfReady() {
		if (!loaded || options.musicVolume == 0.0F) {
			return;
		}

		if (!sndSystem.playing("BgMusic") && !sndSystem.playing("streaming")) {
			SoundPoolEntry soundpoolentry = soundPoolMusic.getRandomSound();

			if (soundpoolentry != null) {
				sndSystem.backgroundMusic("BgMusic", soundpoolentry.soundUrl,
						soundpoolentry.soundName, false);
				sndSystem.setVolume("BgMusic", options.musicVolume);
				sndSystem.play("BgMusic");
			}
		}
	}

	/**
	 * Plays a sound effect with the volume and pitch of the parameters passed.
	 * The sound isn't affected by position of the player (full volume and
	 * center balanced)
	 */
	public void playSoundFX(String effect, float loudness, float pitch) {
		if (!loaded || options.soundVolume == 0.0F) {
			return;
		}

		SoundPoolEntry soundpoolentry = soundPoolSounds
				.getRandomSoundFromSoundPool(effect);

		if (soundpoolentry != null) {
			latestSoundID = (latestSoundID + 1) % 256;
			String s = (new StringBuilder()).append("sound_")
					.append(latestSoundID).toString();
			sndSystem.newSource(false, s, soundpoolentry.soundUrl,
					soundpoolentry.soundName, false, 0.0F, 0.0F, 0.0F, 0, 0.0F);

			if (loudness > 1.0F) {
				loudness = 1.0F;
			}

			loudness *= 0.25F;
			sndSystem.setPitch(s, pitch);
			sndSystem.setVolume(s, loudness * options.soundVolume);
			sndSystem.play(s);
		}
	}
}
