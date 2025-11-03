package DesignPatterns.StateDesignPattern.Model;

import java.util.Objects;

public class Item {
	private final String name;
	private Integer quantity;

	public  Item(String name, Integer quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public  String getName() {
		return  name;
	}

	public   Integer getQuantity() {
		return   quantity;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Item)) {
			return  false;
		}
		Item item = (Item) obj;
		return this.name.equals(item.getName());
	}
}
