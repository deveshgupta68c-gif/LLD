package DesignPatterns.BridgeDesignPattern;

public class TV implements Device{
	private Boolean deviceStatus;
	private Integer volume;

	public TV(){
		this.deviceStatus = false;
		this.volume = 50;
	}
	@Override
	public void switchOff() {
		if(this.deviceStatus.equals(false)){
			System.out.println("Already Switched Off");
			return;
		}
		else {
			this.deviceStatus = false;
			System.out.println("Switched Off the TV");
		}
	}

	@Override
	public void switchOn() {
		if(this.deviceStatus.equals(false)){
			this.deviceStatus = true;
			System.out.println("Switched On the TV");
		}
		else {
			System.out.println("Already Switched On");
		}
	}

	@Override
	public Integer VolumeUp() {
		volume++;
		if(volume > 100){
			System.out.println("Max volume allowed is 100");
			volume = 100;
		}
		System.out.println("Volume is " + volume);
		return volume;
	}

	@Override
	public Integer VolumeDown() {
		volume--;
		if(volume < 0){
			volume = 0;
		}
		System.out.println("Volume is " + volume);
		return  volume;
	}
}
