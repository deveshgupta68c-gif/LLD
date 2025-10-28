package DesignPatterns.SingletonDesignPatter;

public class DatabaseConnectionEager {
	private static final DatabaseConnectionEager instance = new DatabaseConnectionEager();
	private  DatabaseConnectionEager() {
		System.out.println("Instantiated Database Connection");
	}
	public  static DatabaseConnectionEager getInstance() {
		return instance;
	}
	public void query(String sql) {
		System.out.println("Executing query: " + sql);
	}
	public void connect() {
		System.out.println("Connecting to database...");
	}
}
