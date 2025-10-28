package DesignPatterns.AdapterPattern.Target;

public class OldMp3Player implements MusicPlayer {

	@Override
	public void playMusic(String format, String filename) {
		if(format.equals("mp3")) {
			System.out.println("Playing music using OldMp3Player");
		}else {
			System.out.println("Format not supported");
		}
	}
}
