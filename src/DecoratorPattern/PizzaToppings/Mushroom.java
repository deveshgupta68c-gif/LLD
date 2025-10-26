package DecoratorPattern.PizzaToppings;

import DecoratorPattern.Pizza.BasePizza;

public class Mushroom extends PizzaToppings{
	BasePizza fBasePizza;
	public Mushroom(BasePizza basePizza){
		fBasePizza = basePizza;
	}
	@Override
	public int cost() {
		return this.fBasePizza.cost() + 20;
	}
}
