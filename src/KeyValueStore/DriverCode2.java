package KeyValueStore;

public class DriverCode2 {
	public static void main(String[] args) {
		InMemoryKeyValueStore<String, String> mp = new InMemoryKeyValueStore<>(String.class);
		mp.put("ABC", "ascnoansoca");
		String test = mp.get("ABC");
		System.out.println(test);
	}
}
