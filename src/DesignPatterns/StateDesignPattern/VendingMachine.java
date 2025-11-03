package DesignPatterns.StateDesignPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


import DesignPatterns.StateDesignPattern.Model.Item;
import DesignPatterns.StateDesignPattern.States.CoinInserted;
import DesignPatterns.StateDesignPattern.States.ItemSelected;
import DesignPatterns.StateDesignPattern.States.NoCoinState;
import DesignPatterns.StateDesignPattern.States.OutOfStockState;
import DesignPatterns.StateDesignPattern.States.VendingMachineStates;

public class VendingMachine implements IVendingMachine {
	private List<Item> items;
	private VendingMachineStates state;
	private Map<String, VendingMachineStates> fMachineStates;

	public VendingMachine(){
		state = new NoCoinState();
		items = new ArrayList<Item>();
		fMachineStates = new HashMap<String, VendingMachineStates>();
		fMachineStates.put("NoCoin",state);
		fMachineStates.put("CoinInserted", new CoinInserted());
		fMachineStates.put("Dispense", new ItemSelected());
		fMachineStates.put("OutOfStock", new OutOfStockState());
	}


	@Override
	public void insertCoin() {
		if(state.equals(fMachineStates.get("NoCoin"))) {
			changeState(fMachineStates.get("CoinInserted"));
			AtomicInteger i = new AtomicInteger(0);
			items.forEach(item -> {
				System.out.println(i.incrementAndGet() + ") " + item.getName());
			});
		} else {
			throw new RuntimeException("Can't insert coin. Please proceed with current state");
		}

	}

	@Override
	public boolean selectItem(Item item) {
		Optional<Item> item1 = findItem(item);
		boolean isItemAvailable = false;
		if(item1.isPresent()) {
			Item itemFound = item1.get();
			if(itemFound.getQuantity() > 0){
				itemFound.addQuantity(-1);
				changeState(fMachineStates.get("Dispense"));
				isItemAvailable = true;
			} else {
				changeState(fMachineStates.get("OutOfStock"));
			}
		}
		return isItemAvailable;
	}

	private Optional<Item> findItem(Item item) {
		return items.stream().filter(item1 -> item1.equals(item)).findFirst();
	}

	@Override
	public void dispense() {

	}

	@Override
	public void refund() {

	}

	@Override
	public void addItems(List<Item> items) {

	}

	@Override
	public void addItem(Item item) {

	}

	@Override
	public void reset() {
		state = fMachineStates.get("NoCoin");
	}

	@Override
	public void display() {
		state.display();
	}

	private void changeState(VendingMachineStates newState) {
		state = newState;
	}
}
