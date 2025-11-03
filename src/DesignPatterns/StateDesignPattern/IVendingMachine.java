package DesignPatterns.StateDesignPattern;

import java.util.List;

import DesignPatterns.StateDesignPattern.Model.Item;

public interface IVendingMachine {
	void insertCoin();
	boolean selectItem(Item item);
	void dispense();
	void refund();
	void addItems(List<Item> items);
	void addItem(Item item);
	void reset();
	void display();
}
