package Splitwise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Splitwise {

	private int id;
	private List<User> users;
	private List<Group> groups;
	private List<Expense> expenses;
	private List<GroupExpense> groupExpenses;

	public Splitwise(){
		id = 0;
		users = new ArrayList<>();
		groups = new ArrayList<>();
		expenses = new ArrayList<>();
		groupExpenses = new ArrayList<>();
	}
	public void addUser(User user){
		user.setId(++id);
		users.add(user);
	}
	public void addGroup(Group group){
		group.setId(++id);
		groups.add(group);
	}

	public User getUserById(int id){
		for(User user : users){
			if(user.getId() == id)
				return user;
		}
		return null;
	}
	private Group getGroupById(int id){
		for(Group group : groups){
			if(group.getId() == id)
				return group;
		}
		return null;
	}
	public void addUserExpense(int ownerId, int receiverId, double amount){
		User owner = getUserById(ownerId);
		User receiver = getUserById(receiverId);
		if(owner == null || receiver == null){
			if(owner == null)
				System.out.println("Owner User not found");
			if(receiver == null)
				System.out.println("Receiver User not found");
			return;
		}
		Expense expense = new Expense(owner, receiver, amount);
		expenses.add(expense);
		owner.addOwnedExpense(expense);
		receiver.addOwedExpense(expense);
	}
	public void addGroupExpense(int groupId, Integer ownerId, List<Integer> owedIds, double amount){
		User owner = getUserById(ownerId);
		Set<Integer> owedIdSet = new HashSet<>(owedIds);
		List<User>  owedUsers = new ArrayList<>();
		for(User user : users) {
			if(owedIdSet.contains(user.getId())){
				owedUsers.add(user);
			}
		}
		Group group = getGroupById(groupId);
		if(owner == null || group == null){
			if(owner == null)
				System.out.println("Owner User not found");
			if(group == null)
				System.out.println("Group not found");
			return;
		}
		GroupExpense groupExpense = new GroupExpense(owner, owedUsers, amount, group);
		groupExpenses.add(groupExpense);
		group.addExpense(groupExpense);
	}

	public void  settleUp(Integer senderId, Integer receiverId, Double amount){
		User sender = getUserById(senderId);
		User receiver = getUserById(receiverId);
		sender.settleAmount(receiver, amount);
		receiver.settleAmount(sender, -1 * amount);
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

}
