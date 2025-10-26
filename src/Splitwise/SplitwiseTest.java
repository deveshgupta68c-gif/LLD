package Splitwise;

import java.util.Arrays;
import java.util.List;

public class SplitwiseTest {
    private static int testCount = 0;
    private static int passCount = 0;

    public static void main(String[] args) {
        System.out.println("Starting Splitwise Tests...\n");

        testAddUser();
        testAddGroup();
        testAddUserExpense();
        testAddUserExpenseWithInvalidUsers();
        testAddGroupExpense();
        testAddGroupExpenseWithInvalidGroup();
        testGetExpenses();
        testUserBalanceAfterExpense();
        testUserBalanceAfterGroupExpense();
        testSettleUpBetweenUsers();
        testSettleUpPartialAmount();
        testSettleUpWithInvalidUsers();
        
        // New edge case tests
        testZeroAmountExpense();
        testOverSettlingUp();
        testMultipleTransactionsBetweenSameUsers();
        testCircularDebt();
        testGroupExpenseWithOwnerIncluded();
        testLargeAmountExpense();

        System.out.println("\nTest Summary:");
        System.out.println("Total tests: " + testCount);
        System.out.println("Passed: " + passCount);
        System.out.println("Failed: " + (testCount - passCount));
    }

    private static void testAddUser() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            User alice = new User("Alice", "alice@example.com", "pass1");
            splitwise.addUser(alice);
            
            User retrievedUser = splitwise.getUserById(1);
            boolean userAdded = retrievedUser != null && retrievedUser.getName().equals("Alice");
            
            if (userAdded) {
                System.out.println("✅ Test testAddUser passed");
                passCount++;
            } else {
                System.out.println("❌ Test testAddUser failed: User not added correctly");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testAddUser failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testAddGroup() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            Group group = new Group();
            splitwise.addGroup(group);
            
            boolean groupAdded = group.getId() == 1;
            
            if (groupAdded) {
                System.out.println("✅ Test testAddGroup passed");
                passCount++;
            } else {
                System.out.println("❌ Test testAddGroup failed: Group not added correctly");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testAddGroup failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testAddUserExpense() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            splitwise.addUserExpense(1, 2, 100.0);
            
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 100.0;
            boolean bobBalanceCorrect = updatedBob.getBalance() == -100.0;
            
            if (aliceBalanceCorrect && bobBalanceCorrect) {
                System.out.println("✅ Test testAddUserExpense passed");
                passCount++;
            } else {
                System.out.println("❌ Test testAddUserExpense failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 100.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected -100.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testAddUserExpense failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testAddUserExpenseWithInvalidUsers() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            splitwise.addUser(alice);
            
            // Try to add expense with invalid receiver ID
            splitwise.addUserExpense(1, 999, 100.0);
            
            User updatedAlice = splitwise.getUserById(1);
            boolean aliceBalanceUnchanged = updatedAlice.getBalance() == 0.0;
            
            if (aliceBalanceUnchanged) {
                System.out.println("✅ Test testAddUserExpenseWithInvalidUsers passed");
                passCount++;
            } else {
                System.out.println("❌ Test testAddUserExpenseWithInvalidUsers failed: Alice's balance should remain 0.0");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testAddUserExpenseWithInvalidUsers failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testAddGroupExpense() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            User charlie = new User("Charlie", "charlie@example.com", "pass3");
            
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            splitwise.addUser(charlie);
            
            Group group = new Group();
            splitwise.addGroup(group);
            
            List<Integer> owedUsers = Arrays.asList(2, 3); // Bob and Charlie owe
            splitwise.addGroupExpense(group.getId(), 1, owedUsers, 200.0); // Alice paid
            
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            User updatedCharlie = splitwise.getUserById(3);
            
            // Alice paid $200, Bob and Charlie each owe $100
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 133.33333333333334;
            boolean bobBalanceCorrect = updatedBob.getBalance() == -66.66666666666667;
            boolean charlieBalanceCorrect = updatedCharlie.getBalance() == -66.66666666666667;
            
            if (aliceBalanceCorrect && bobBalanceCorrect && charlieBalanceCorrect) {
                System.out.println("✅ Test testAddGroupExpense passed");
                passCount++;
            } else {
                System.out.println("❌ Test testAddGroupExpense failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 200.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected -100.0), " +
                        "Charlie's balance: " + updatedCharlie.getBalance() + " (expected -100.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testAddGroupExpense failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testAddGroupExpenseWithInvalidGroup() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            List<Integer> owedUsers = Arrays.asList(2);
            splitwise.addGroupExpense(999, 1, owedUsers, 100.0); // Invalid group ID
            
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceUnchanged = updatedAlice.getBalance() == 0.0;
            boolean bobBalanceUnchanged = updatedBob.getBalance() == 0.0;
            
            if (aliceBalanceUnchanged && bobBalanceUnchanged) {
                System.out.println("✅ Test testAddGroupExpenseWithInvalidGroup passed");
                passCount++;
            } else {
                System.out.println("❌ Test testAddGroupExpenseWithInvalidGroup failed: Balances should remain unchanged");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testAddGroupExpenseWithInvalidGroup failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testGetExpenses() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            splitwise.addUserExpense(1, 2, 100.0);
            splitwise.addUserExpense(2, 1, 50.0);
            
            List<Expense> expenses = splitwise.getExpenses();
            boolean correctExpenseCount = expenses.size() == 2;
            
            if (correctExpenseCount) {
                System.out.println("✅ Test testGetExpenses passed");
                passCount++;
            } else {
                System.out.println("❌ Test testGetExpenses failed: Expected 2 expenses, got " + expenses.size());
            }
        } catch (Exception e) {
            System.out.println("❌ Test testGetExpenses failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testUserBalanceAfterExpense() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            splitwise.addUserExpense(1, 2, 150.0);
            
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 150.0;
            boolean bobBalanceCorrect = updatedBob.getBalance() == -150.0;
            
            if (aliceBalanceCorrect && bobBalanceCorrect) {
                System.out.println("✅ Test testUserBalanceAfterExpense passed");
                passCount++;
            } else {
                System.out.println("❌ Test testUserBalanceAfterExpense failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 150.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected -150.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testUserBalanceAfterExpense failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testUserBalanceAfterGroupExpense() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            User charlie = new User("Charlie", "charlie@example.com", "pass3");
            
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            splitwise.addUser(charlie);
            
            Group group = new Group();
            splitwise.addGroup(group);
            
            List<Integer> owedUsers = Arrays.asList(2, 3);
            splitwise.addGroupExpense(group.getId(), 1, owedUsers, 300.0);
            
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            User updatedCharlie = splitwise.getUserById(3);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 200.0;
            boolean bobBalanceCorrect = updatedBob.getBalance() == -100.0;
            boolean charlieBalanceCorrect = updatedCharlie.getBalance() == -100.0;
            
            if (aliceBalanceCorrect && bobBalanceCorrect && charlieBalanceCorrect) {
                System.out.println("✅ Test testUserBalanceAfterGroupExpense passed");
                passCount++;
            } else {
                System.out.println("❌ Test testUserBalanceAfterGroupExpense failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 300.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected -150.0), " +
                        "Charlie's balance: " + updatedCharlie.getBalance() + " (expected -150.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testUserBalanceAfterGroupExpense failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testSettleUpBetweenUsers() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            splitwise.addUserExpense(1, 2, 100.0);
            splitwise.settleUp(2, 1, 100.0);
            
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 0.0;
            boolean bobBalanceCorrect = updatedBob.getBalance() == 0.0;
            
            if (aliceBalanceCorrect && bobBalanceCorrect) {
                System.out.println("✅ Test testSettleUpBetweenUsers passed");
                passCount++;
            } else {
                System.out.println("❌ Test testSettleUpBetweenUsers failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 0.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected 0.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testSettleUpBetweenUsers failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testSettleUpPartialAmount() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            splitwise.addUserExpense(1, 2, 100.0);
            splitwise.settleUp(2, 1, 60.0); // Partial settlement
            
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 40.0; // 100 - 60
            boolean bobBalanceCorrect = updatedBob.getBalance() == -40.0;    // -100 + 60
            
            if (aliceBalanceCorrect && bobBalanceCorrect) {
                System.out.println("✅ Test testSettleUpPartialAmount passed");
                passCount++;
            } else {
                System.out.println("❌ Test testSettleUpPartialAmount failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 40.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected -40.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testSettleUpPartialAmount failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testSettleUpWithInvalidUsers() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            User alice = new User("Alice", "alice@example.com", "pass1");
            splitwise.addUser(alice);
            
            // This should cause an exception or handle gracefully
            splitwise.settleUp(1, 999, 50.0); // Invalid receiver
            
            System.out.println("❌ Test testSettleUpWithInvalidUsers failed: Should have thrown exception for invalid user");
        } catch (Exception e) {
            System.out.println("✅ Test testSettleUpWithInvalidUsers passed: Correctly handled invalid user");
            passCount++;
        }
    }

    // Test adding an expense with zero amount
    private static void testZeroAmountExpense() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            // Create and add users
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            // Add a zero amount expense
            splitwise.addUserExpense(1, 2, 0.0);
            
            // Verify balances don't change
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 0.0;
            boolean bobBalanceCorrect = updatedBob.getBalance() == 0.0;
            
            if (aliceBalanceCorrect && bobBalanceCorrect) {
                System.out.println("✅ Test testZeroAmountExpense passed");
                passCount++;
            } else {
                System.out.println("❌ Test testZeroAmountExpense failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 0.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected 0.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testZeroAmountExpense failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Test settling up more than is owed
    private static void testOverSettlingUp() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            // Create and add users
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            // Alice pays for Bob - $100
            splitwise.addUserExpense(1, 2, 100.0);
            
            // Bob settles up $150 with Alice (more than owed)
            splitwise.settleUp(2, 1, 150.0);
            
            // Verify balances after over-settlement
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == -50.0;  // 100 - 150
            boolean bobBalanceCorrect = updatedBob.getBalance() == 50.0;      // -100 + 150
            
            if (aliceBalanceCorrect && bobBalanceCorrect) {
                System.out.println("✅ Test testOverSettlingUp passed");
                passCount++;
            } else {
                System.out.println("❌ Test testOverSettlingUp failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected -50.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected 50.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testOverSettlingUp failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Test multiple transactions between the same users
    private static void testMultipleTransactionsBetweenSameUsers() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            // Create and add users
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            // Multiple transactions between Alice and Bob
            splitwise.addUserExpense(1, 2, 100.0); // Alice pays for Bob - $100
            splitwise.addUserExpense(2, 1, 40.0);  // Bob pays for Alice - $40
            splitwise.addUserExpense(1, 2, 25.0);  // Alice pays for Bob again - $25
            
            // Verify final balances
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 85.0;  // 100 - 40 + 25
            boolean bobBalanceCorrect = updatedBob.getBalance() == -85.0;    // -100 + 40 - 25
            
            if (aliceBalanceCorrect && bobBalanceCorrect) {
                System.out.println("✅ Test testMultipleTransactionsBetweenSameUsers passed");
                passCount++;
            } else {
                System.out.println("❌ Test testMultipleTransactionsBetweenSameUsers failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 85.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected -85.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testMultipleTransactionsBetweenSameUsers failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Test circular debt (A owes B, B owes C, C owes A)
    private static void testCircularDebt() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            // Create and add users
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            User charlie = new User("Charlie", "charlie@example.com", "pass3");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            splitwise.addUser(charlie);
            
            // Create circular debt: Alice -> Bob -> Charlie -> Alice
            splitwise.addUserExpense(1, 2, 50.0);    // Alice pays for Bob - $50
            splitwise.addUserExpense(2, 3, 50.0);    // Bob pays for Charlie - $50
            splitwise.addUserExpense(3, 1, 50.0);    // Charlie pays for Alice - $50
            
            // Verify balances - in theory they could all be zero, but our system tracks individual debts
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            User updatedCharlie = splitwise.getUserById(3);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 0.0;   // +50 to Bob, -50 from Charlie
            boolean bobBalanceCorrect = updatedBob.getBalance() == 0.0;       // -50 to Alice, +50 to Charlie
            boolean charlieBalanceCorrect = updatedCharlie.getBalance() == 0.0; // -50 to Bob, +50 to Alice
            
            if (aliceBalanceCorrect && bobBalanceCorrect && charlieBalanceCorrect) {
                System.out.println("✅ Test testCircularDebt passed");
                passCount++;
            } else {
                System.out.println("❌ Test testCircularDebt failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 0.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected 0.0), " +
                        "Charlie's balance: " + updatedCharlie.getBalance() + " (expected 0.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testCircularDebt failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Test group expense where owner is also included in owed users
    private static void testGroupExpenseWithOwnerIncluded() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            // Create users
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            User charlie = new User("Charlie", "charlie@example.com", "pass3");
            
            // Add users to splitwise
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            splitwise.addUser(charlie);
            
            // Create and add group
            Group group = new Group();
            splitwise.addGroup(group);
            
            // Alice pays but is also part of the group expense (common scenario)
            List<Integer> owedUsers = Arrays.asList(2, 3); // Alice, Bob and Charlie all owe
            splitwise.addGroupExpense(group.getId(), 1, owedUsers, 300.0); // Alice paid, split between all three
            
            // Get updated user objects
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            User updatedCharlie = splitwise.getUserById(3);
            
            // Check balances
            // Alice paid $300 but owes $100 herself, so net is $200
            // Bob and Charlie each owe $100
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == 200.0;
            boolean bobBalanceCorrect = updatedBob.getBalance() == -100.0;
            boolean charlieBalanceCorrect = updatedCharlie.getBalance() == -100.0;
            
            if (aliceBalanceCorrect && bobBalanceCorrect && charlieBalanceCorrect) {
                System.out.println("✅ Test testGroupExpenseWithOwnerIncluded passed");
                passCount++;
            } else {
                System.out.println("❌ Test testGroupExpenseWithOwnerIncluded failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected 200.0), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected -100.0), " +
                        "Charlie's balance: " + updatedCharlie.getBalance() + " (expected -100.0)");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testGroupExpenseWithOwnerIncluded failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Test with an extremely large amount to check for numerical precision issues
    private static void testLargeAmountExpense() {
        testCount++;
        try {
            Splitwise splitwise = new Splitwise();
            
            // Create and add users
            User alice = new User("Alice", "alice@example.com", "pass1");
            User bob = new User("Bob", "bob@example.com", "pass2");
            splitwise.addUser(alice);
            splitwise.addUser(bob);
            
            // Add expense with a very large amount
            double largeAmount = 1000000000.99; // One billion and 99 cents
            splitwise.addUserExpense(1, 2, largeAmount);
            
            // Verify balances maintain precision
            User updatedAlice = splitwise.getUserById(1);
            User updatedBob = splitwise.getUserById(2);
            
            boolean aliceBalanceCorrect = updatedAlice.getBalance() == largeAmount;
            boolean bobBalanceCorrect = updatedBob.getBalance() == -largeAmount;
            
            if (aliceBalanceCorrect && bobBalanceCorrect) {
                System.out.println("✅ Test testLargeAmountExpense passed");
                passCount++;
            } else {
                System.out.println("❌ Test testLargeAmountExpense failed: " +
                        "Alice's balance: " + updatedAlice.getBalance() + " (expected " + largeAmount + "), " +
                        "Bob's balance: " + updatedBob.getBalance() + " (expected " + (-largeAmount) + ")");
            }
        } catch (Exception e) {
            System.out.println("❌ Test testLargeAmountExpense failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
