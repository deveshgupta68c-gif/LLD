package DesignPatterns.AdapterPattern.Adaptee;

public class Mp4Player implements AdvancedMusicPlayer {
	@Override
	public void playMusic(String fileName) {
		System.out.println("Playing mp4 file: " + fileName);
	}
}
