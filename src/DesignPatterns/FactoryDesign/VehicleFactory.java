package DesignPatterns.FactoryDesign;

public class VehicleFactory {

	 public Vehicle getVehicle(String vehicleType) {
		if(vehicleType.equals("car")) {
			return new Car();
		}else if(vehicleType.equals("bike")) {
			return new Bike();
		} else {
			throw  new IllegalArgumentException("Invalid Vehicle Type");
		}
	}
}
