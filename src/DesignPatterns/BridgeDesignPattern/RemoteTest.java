package DesignPatterns.BridgeDesignPattern;

public class RemoteTest {
	public static void main(String[] args) {
		Device device = new TV();
		Remote remote = new AdvancedRemote(device);

		remote.on();
		remote.turnDownVolume();
		remote.turnDownVolume();
		int i = 0;
		while(i < 54) {
			remote.turnUpVolume();
			i++;
		}
		remote.off();
	}
}
