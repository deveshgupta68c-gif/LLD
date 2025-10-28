package DesignPatterns.AdapterPattern;

import DesignPatterns.AdapterPattern.Enums.AudioFormat;
import DesignPatterns.AdapterPattern.Target.MusicPlayer;

/**
 * Universal audio player that supports both native (MP3) and advanced formats (MP4, VLC).
 * For advanced formats, it uses the Adapter pattern to delegate to specialized players.
 */
public class AudioPlayer implements MusicPlayer {

	/**
	 * Plays audio files of various formats.
	 * MP3 is handled natively, while other formats are delegated to adapters via factory.
	 *
	 * @param format the audio format (e.g., "mp3", "mp4", "vlc")
	 * @param filename the name of the file to play
	 */
	@Override
	public void playMusic(String format, String filename) {
		AudioFormat audioFormat = AudioFormat.fromString(format);

		if (audioFormat == null) {
			System.out.println("Error: Invalid or unsupported format '" + format + "'");
			return;
		}

		switch (audioFormat) {
			case MP3:
				playMp3(filename);
				break;
			case MP4:
			case VLC:
				playAdvancedFormat(audioFormat, filename);
				break;
			default:
				System.out.println("Error: Format '" + format + "' is not supported");
		}
	}

	/**
	 * Native MP3 playback - no adapter needed.
	 */
	private void playMp3(String filename) {
		System.out.println("Playing mp3 music from file: " + filename);
	}

	/**
	 * Delegates advanced format playback to appropriate adapter via factory.
	 */
	private void playAdvancedFormat(AudioFormat format, String filename) {
		MusicPlayer player = AudioPlayerFactory.createPlayer(format);
		if (player != null) {
			player.playMusic(format.name().toLowerCase(), filename);
		} else {
			System.out.println("Error: No player available for format '" + format + "'");
		}
	}
}
