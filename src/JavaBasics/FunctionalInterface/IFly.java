package JavaBasics.FunctionalInterface;

@FunctionalInterface
public interface IFly {
	void fly();
	// void dontFly(); // This will make it non functional interface and cause some error as Functional Interface only
	// supports one abstract method
	default void land(){
		System.out.println("Landing");
	}

	static void takeOff(){
		System.out.println("Taking Off");
	}

	String toString(); // Object class method - works fine
}
