package DesignPatterns.AdapterPattern;

import DesignPatterns.AdapterPattern.Adaptee.Mp4Player;
import DesignPatterns.AdapterPattern.Adaptee.VlcPlayer;
import DesignPatterns.AdapterPattern.Target.MusicPlayer;

/**
 * Factory class responsible for creating appropriate MusicPlayer instances
 * based on the audio format. This separates player creation logic from
 * the business logic.
 */
public class AudioPlayerFactory {

	/**
	 * Creates a MusicPlayer for the specified audio format.
	 * For advanced formats (MP4, VLC), it creates an adapter wrapping the appropriate player.
	 * Returns null for unsupported formats.
	 *
	 * @param format the audio format
	 * @return MusicPlayer instance or null if format is unsupported
	 */
	public static MusicPlayer createPlayer(AudioFormat format) {
		if (format == null) {
			return null;
		}

		switch (format) {
			case MP4:
				return new MusicPlayerAdapter(new Mp4Player());
			case VLC:
				return new MusicPlayerAdapter(new VlcPlayer());
			default:
				return null;
		}
	}
}
