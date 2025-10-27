package DesignPatterns.DecoratorPattern;

import DesignPatterns.DecoratorPattern.Pizza.BasePizza;
import DesignPatterns.DecoratorPattern.Pizza.Farmhouse;
import DesignPatterns.DecoratorPattern.PizzaToppings.CheeseBurst;
import DesignPatterns.DecoratorPattern.PizzaToppings.Mushroom;

public class DriverCode {
	public static void main(String[] args) {
		BasePizza pizza = new Mushroom(new CheeseBurst(new Farmhouse()));
		System.out.println("Show pizza cost :" + pizza.cost());
	}
}
