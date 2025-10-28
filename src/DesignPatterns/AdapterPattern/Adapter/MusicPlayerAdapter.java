package DesignPatterns.AdapterPattern.Adapter;

import DesignPatterns.AdapterPattern.Adaptee.AdvancedMusicPlayer;
import DesignPatterns.AdapterPattern.Target.MusicPlayer;

/**
 * Adapter that bridges the gap between MusicPlayer and AdvancedMusicPlayer interfaces.
 * This adapter follows the Adapter Pattern by converting the Target interface (MusicPlayer)
 * to work with the Adaptee interface (AdvancedMusicPlayer).
 */
public class MusicPlayerAdapter implements MusicPlayer {
	private final AdvancedMusicPlayer advancedMusicPlayer;

	/**
	 * Constructor accepting an AdvancedMusicPlayer instance.
	 * This follows dependency injection principles.
	 *
	 * @param advancedMusicPlayer the adaptee instance to delegate calls to
	 * @throws IllegalArgumentException if advancedMusicPlayer is null
	 */
	public MusicPlayerAdapter(AdvancedMusicPlayer advancedMusicPlayer) {
		if (advancedMusicPlayer == null) {
			throw new IllegalArgumentException("AdvancedMusicPlayer cannot be null");
		}
		this.advancedMusicPlayer = advancedMusicPlayer;
	}

	/**
	 * Adapts the playMusic call by delegating to the AdvancedMusicPlayer.
	 * The format parameter is ignored as the adaptee doesn't need it.
	 */
	@Override
	public void playMusic(String format, String filename) {
		advancedMusicPlayer.playMusic(filename);
	}
}
