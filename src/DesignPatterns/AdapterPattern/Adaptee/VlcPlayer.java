package DesignPatterns.AdapterPattern.Adaptee;

public class VlcPlayer implements AdvancedMusicPlayer {
	@Override
	public void playMusic(String fileName) {
		System.out.println("Playing music using Vlc Player : "+fileName);
	}
}
