package DesignPatterns.BridgeDesignPattern;

public abstract class Remote {
	private Device device;

	protected Remote(Device device) {
		this.device = device;
	}
	protected Device getDevice() {
		return device;
	}
	abstract void on();
	abstract void off();
	abstract void turnUpVolume();
	abstract  void turnDownVolume();
}
