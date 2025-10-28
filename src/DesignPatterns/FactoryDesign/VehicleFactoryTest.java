package DesignPatterns.FactoryDesign;

public class VehicleFactoryTest {
	public static void main(String[] args) {
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("car");
		vehicle.unlock();
		vehicle.start();
		vehicle.drive();

		Vehicle vehicle2 = vehicleFactory.getVehicle("bike");
		vehicle2.unlock();
		vehicle2.start();
		vehicle2.drive();

		try{
			Vehicle vehicle3 = vehicleFactory.getVehicle("truck");
			vehicle3.unlock();
			vehicle3.start();
			vehicle3.drive();
		}
		catch(Exception e){
			System.err.println("Error creating vehicle: " + e.getMessage());
		}
	}
}
