package DesignPatterns.BridgeDesignPattern;

public class AdvancedRemote extends Remote{

	public AdvancedRemote(Device device) {
		super(device);
	}

	@Override
	void on() {
		this.getDevice().switchOn();
	}

	@Override
	void off() {
		this.getDevice().switchOff();
	}

	@Override
	void turnUpVolume() {
		this.getDevice().VolumeUp();
	}

	@Override
	void turnDownVolume() {
		this.getDevice().VolumeDown();
	}
}
