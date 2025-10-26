package KeyValueStore;

public class DriverCode {
	public static void main(String[] args) {
		System.out.println("Starting InMemoryKeyValueStore Tests...");

		// Test 1: Basic Put and Get Operations
		try {
			KeyValueStore<String, String> store = new InMemoryKeyValueStore<>(String.class);
			System.out.println("Test 1: Basic Put and Get Operations");
			store.put("key1", "value1");
			store.put("key2", "value2");
			System.out.println("Get key1: " + store.get("key1")); // Expected: value1
			System.out.println("Get key2: " + store.get("key2")); // Expected: value2
			System.out.println();
		} catch (Exception e) {
			System.out.println("Test 1 Failed: " + e.getMessage());
		}

		// Test 2: Get Non-Existent Key
		try {
			KeyValueStore<String, String> store = new InMemoryKeyValueStore<>(String.class);
			System.out.println("Test 2: Get Non-Existent Key");
			System.out.println("Get nonExistentKey: " + store.get("nonExistentKey")); // Expected: null
			System.out.println();
		} catch (Exception e) {
			System.out.println("Test 2 Failed: " + e.getMessage());
		}

		// Test 3: Null Key
		try {
			KeyValueStore<String, String> store = new InMemoryKeyValueStore<>(String.class);
			System.out.println("Test 3: Null Key");
			store.put(null, "value");
		} catch (Exception e) {
			System.out.println("Test 3 Passed: " + e.getMessage());
		}

		// Test 4: Null Value
		try {
			KeyValueStore<String, String> store = new InMemoryKeyValueStore<>(String.class);
			System.out.println("Test 4: Null Value");
			store.put("key1", null);
			System.out.println("Get key1: " + store.get("key1")); // Expected: null
			System.out.println();
		} catch (Exception e) {
			System.out.println("Test 4 Failed: " + e.getMessage());
		}

		// Test 5: Delete Operation
		try {
			KeyValueStore<String, String> store = new InMemoryKeyValueStore<>(String.class);
			System.out.println("Test 5: Delete Operation");
			store.put("key1", "value1");
			store.delete("key1");
			System.out.println("Get key1 after deletion: " + store.get("key1")); // Expected: null
			System.out.println();
		} catch (Exception e) {
			System.out.println("Test 5 Failed: " + e.getMessage());
		}

		// Test 6: Invalid Key Type
		try {
			KeyValueStore<Integer, String> store = new InMemoryKeyValueStore<>(Integer.class);
			System.out.println("Test 6: Invalid Key Type");
			store.put(1, "value1");
			System.out.println("Get key1: " + store.get(1)); // Expected: value1
			System.out.println();
		} catch (Exception e) {
			System.out.println("Test 6 Failed: " + e.getMessage());
		}

		// Test 7: Ensure Hashable Key
		try {
			System.out.println("Test 7: Ensure Hashable Key");
			class NonHashable {
			}
			new InMemoryKeyValueStore<>(NonHashable.class);
		} catch (Exception e) {
			System.out.println("Test 7 Passed: " + e.getMessage());
		}

		// Test 8: Concurrent Operations
		try {
			KeyValueStore<Integer, String> store = new InMemoryKeyValueStore<>(Integer.class);
			System.out.println("Test 8: Concurrent Operations");

			Thread writer = new Thread(() -> {
				for (int i = 0; i < 10; i++) {
					System.out.println("Writing : key " + i + " -> " + "value"+i);

					store.put(i, "value" + i);

				}
			});

			Thread reader = new Thread(() -> {
				for (int i = 0; i < 10; i++) {
					System.out.println("Reading: key " + i + " -> " + store.get(i));
				}
			});

			writer.start();
			reader.start();


			System.out.println("Concurrent Operations Completed.");
			System.out.println();
		} catch (Exception e) {
			System.out.println("Test 8 Failed: " + e.getMessage());
		}

		System.out.println("All Tests Completed.");
	}
}
