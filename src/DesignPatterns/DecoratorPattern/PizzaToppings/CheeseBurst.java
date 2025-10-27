package DesignPatterns.DecoratorPattern.PizzaToppings;

import DesignPatterns.DecoratorPattern.Pizza.BasePizza;

public class CheeseBurst extends PizzaToppings{
	BasePizza fBasePizza;


	public CheeseBurst(BasePizza basePizza){
		fBasePizza = basePizza;
	}
	@Override
	public int cost() {
		return this.fBasePizza.cost() + 100;
	}
}
