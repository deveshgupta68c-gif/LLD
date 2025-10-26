package DecoratorPattern.PizzaToppings;

import DecoratorPattern.Pizza.BasePizza;
import DecoratorPattern.Pizza.MargharitaPizza;

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
