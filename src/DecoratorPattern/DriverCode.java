package DecoratorPattern;

import DecoratorPattern.Pizza.BasePizza;
import DecoratorPattern.Pizza.Farmhouse;
import DecoratorPattern.Pizza.MargharitaPizza;
import DecoratorPattern.PizzaToppings.CheeseBurst;
import DecoratorPattern.PizzaToppings.Mushroom;

public class DriverCode {
	public static void main(String[] args) {
		BasePizza pizza = new Mushroom(new CheeseBurst(new Farmhouse()));
		System.out.println("Show pizza cost :" + pizza.cost());
	}
}
