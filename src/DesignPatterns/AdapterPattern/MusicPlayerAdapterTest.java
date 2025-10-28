package DesignPatterns.AdapterPattern;

import DesignPatterns.AdapterPattern.Target.MusicPlayer;

/**
 * Test class demonstrating the Adapter Pattern.
 * Shows how AudioPlayer uses adapters to support multiple audio formats.
 */
public class MusicPlayerAdapterTest {
	public static void main(String[] args) {
		MusicPlayer audioPlayer = new AudioPlayer();

		System.out.println("=== Audio Player Test ===\n");

		// Native format - no adapter needed
		System.out.println("1. Testing native MP3 format:");
		audioPlayer.playMusic("mp3", "song1.mp3");

		// Advanced formats - using adapters
		System.out.println("\n2. Testing MP4 format (via adapter):");
		audioPlayer.playMusic("mp4", "movie.mp4");

		System.out.println("\n3. Testing VLC format (via adapter):");
		audioPlayer.playMusic("vlc", "clip.vlc");

		// Unsupported format - error handling
		System.out.println("\n4. Testing unsupported AVI format:");
		audioPlayer.playMusic("avi", "old_movie.avi");

		System.out.println("\n5. Testing invalid format:");
		audioPlayer.playMusic("xyz", "unknown.xyz");
	}
}
