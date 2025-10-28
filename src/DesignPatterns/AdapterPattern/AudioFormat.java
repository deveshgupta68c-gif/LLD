package DesignPatterns.AdapterPattern;

public enum AudioFormat {
	MP3,
	MP4,
	VLC,
	AVI;

	public static AudioFormat fromString(String format) {
		try {
			return AudioFormat.valueOf(format.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
